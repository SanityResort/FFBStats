/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.net;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.CardType;
import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.ConcedeGameStatus;
import com.balancedbytes.games.ffb.FantasyFootballException;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.InducementType;
import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.PlayerChoiceMode;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.Pushback;
import com.balancedbytes.games.ffb.ReRollSource;
import com.balancedbytes.games.ffb.ReRolledAction;
import com.balancedbytes.games.ffb.SeriousInjury;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.SpecialEffect;
import com.balancedbytes.games.ffb.TeamSetup;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerMode;
import com.balancedbytes.games.ffb.model.InducementSet;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.net.INetCommandHandler;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.commands.ClientCommand;
import com.balancedbytes.games.ffb.net.commands.ClientCommandActingPlayer;
import com.balancedbytes.games.ffb.net.commands.ClientCommandApothecaryChoice;
import com.balancedbytes.games.ffb.net.commands.ClientCommandArgueTheCall;
import com.balancedbytes.games.ffb.net.commands.ClientCommandBlock;
import com.balancedbytes.games.ffb.net.commands.ClientCommandBlockChoice;
import com.balancedbytes.games.ffb.net.commands.ClientCommandBuyCard;
import com.balancedbytes.games.ffb.net.commands.ClientCommandBuyInducements;
import com.balancedbytes.games.ffb.net.commands.ClientCommandCloseSession;
import com.balancedbytes.games.ffb.net.commands.ClientCommandCoinChoice;
import com.balancedbytes.games.ffb.net.commands.ClientCommandConcedeGame;
import com.balancedbytes.games.ffb.net.commands.ClientCommandConfirm;
import com.balancedbytes.games.ffb.net.commands.ClientCommandDebugClientState;
import com.balancedbytes.games.ffb.net.commands.ClientCommandEndTurn;
import com.balancedbytes.games.ffb.net.commands.ClientCommandFollowupChoice;
import com.balancedbytes.games.ffb.net.commands.ClientCommandFoul;
import com.balancedbytes.games.ffb.net.commands.ClientCommandGaze;
import com.balancedbytes.games.ffb.net.commands.ClientCommandHandOver;
import com.balancedbytes.games.ffb.net.commands.ClientCommandIllegalProcedure;
import com.balancedbytes.games.ffb.net.commands.ClientCommandInterceptorChoice;
import com.balancedbytes.games.ffb.net.commands.ClientCommandJoin;
import com.balancedbytes.games.ffb.net.commands.ClientCommandJourneymen;
import com.balancedbytes.games.ffb.net.commands.ClientCommandKickoff;
import com.balancedbytes.games.ffb.net.commands.ClientCommandMove;
import com.balancedbytes.games.ffb.net.commands.ClientCommandPass;
import com.balancedbytes.games.ffb.net.commands.ClientCommandPasswordChallenge;
import com.balancedbytes.games.ffb.net.commands.ClientCommandPettyCash;
import com.balancedbytes.games.ffb.net.commands.ClientCommandPing;
import com.balancedbytes.games.ffb.net.commands.ClientCommandPlayerChoice;
import com.balancedbytes.games.ffb.net.commands.ClientCommandPushback;
import com.balancedbytes.games.ffb.net.commands.ClientCommandReceiveChoice;
import com.balancedbytes.games.ffb.net.commands.ClientCommandReplay;
import com.balancedbytes.games.ffb.net.commands.ClientCommandRequestVersion;
import com.balancedbytes.games.ffb.net.commands.ClientCommandSetMarker;
import com.balancedbytes.games.ffb.net.commands.ClientCommandSetupPlayer;
import com.balancedbytes.games.ffb.net.commands.ClientCommandStartGame;
import com.balancedbytes.games.ffb.net.commands.ClientCommandTalk;
import com.balancedbytes.games.ffb.net.commands.ClientCommandTeamSetupDelete;
import com.balancedbytes.games.ffb.net.commands.ClientCommandTeamSetupLoad;
import com.balancedbytes.games.ffb.net.commands.ClientCommandTeamSetupSave;
import com.balancedbytes.games.ffb.net.commands.ClientCommandThrowTeamMate;
import com.balancedbytes.games.ffb.net.commands.ClientCommandTouchback;
import com.balancedbytes.games.ffb.net.commands.ClientCommandUseApothecary;
import com.balancedbytes.games.ffb.net.commands.ClientCommandUseInducement;
import com.balancedbytes.games.ffb.net.commands.ClientCommandUseReRoll;
import com.balancedbytes.games.ffb.net.commands.ClientCommandUseSkill;
import com.balancedbytes.games.ffb.net.commands.ClientCommandUserSettings;
import com.balancedbytes.games.ffb.net.commands.ClientCommandWizardSpell;
import com.balancedbytes.games.ffb.net.commands.ServerCommand;
import com.fumbbl.rng.MouseEntropySource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientCommunication
implements Runnable,
INetCommandHandler {
    private boolean fStopped;
    private List<NetCommand> fCommandQueue;
    private FantasyFootballClient fClient;

    public ClientCommunication(FantasyFootballClient pClient) {
        this.fClient = pClient;
        this.fCommandQueue = new ArrayList<NetCommand>();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void handleCommand(NetCommand pNetCommand) {
        List<NetCommand> list = this.fCommandQueue;
        synchronized (list) {
            this.fCommandQueue.add(pNetCommand);
            this.fCommandQueue.notify();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void stop() {
        if (!this.fStopped) {
            this.fStopped = true;
            List<NetCommand> list = this.fCommandQueue;
            synchronized (list) {
                this.fCommandQueue.notifyAll();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        do {
            NetCommand netCommand = null;
            List<NetCommand> list = this.fCommandQueue;
            synchronized (list) {
                try {
                    while (this.fCommandQueue.isEmpty() && !this.fStopped) {
                        this.fCommandQueue.wait();
                    }
                }
                catch (InterruptedException e) {
                    break;
                }
                if (this.fStopped) {
                    break;
                }
                netCommand = this.fCommandQueue.remove(0);
            }
            switch (netCommand.getId()) {
                case SERVER_PONG: 
                case SERVER_TALK: 
                case SERVER_SOUND: 
                case SERVER_REPLAY: 
                case INTERNAL_SERVER_SOCKET_CLOSED: {
                    break;
                }
                default: {
                }
            }
        } while (true);
    }

    protected void send(ClientCommand clientCommand) {
        if (clientCommand == null) {
            return;
        }
        try {
            MouseEntropySource entropySource = this.getClient().getUserInterface().getMouseEntropySource();
            if (entropySource.hasEnoughEntropy()) {
                clientCommand.setEntropy(entropySource.getEntropy());
            }
            this.getClient().getCommandEndpoint().send(clientCommand);
        }
        catch (IOException pIoException) {
            throw new FantasyFootballException(pIoException);
        }
    }

    public void sendDebugClientState(ClientStateId pClientStateId) {
        this.send(new ClientCommandDebugClientState(pClientStateId));
    }

    public void sendJoin(String pCoach, String pPassword, long pGameId, String pGameName, String pTeamId, String pTeamName) {
        ClientCommandJoin joinCommand = new ClientCommandJoin(this.getClient().getMode());
        joinCommand.setCoach(pCoach);
        joinCommand.setPassword(pPassword);
        joinCommand.setGameId(pGameId);
        joinCommand.setGameName(pGameName);
        joinCommand.setTeamId(pTeamId);
        joinCommand.setTeamName(pTeamName);
        this.send(joinCommand);
    }

    public void sendJourneymen(String[] pPositionsIds, int[] pSlots) {
        this.send(new ClientCommandJourneymen(pPositionsIds, pSlots));
    }

    public void sendTalk(String pTalk) {
        this.send(new ClientCommandTalk(pTalk));
    }

    public void sendPasswordChallenge() {
        this.send(new ClientCommandPasswordChallenge(this.getClient().getParameters().getCoach()));
    }

    public void sendPing(long timestamp) {
        this.send(new ClientCommandPing(timestamp));
    }

    public void sendSetupPlayer(Player pPlayer, FieldCoordinate pCoordinate) {
        this.send(new ClientCommandSetupPlayer(pPlayer.getId(), pCoordinate));
    }

    public void sendTouchback(FieldCoordinate pBallCoordinate) {
        this.send(new ClientCommandTouchback(pBallCoordinate));
    }

    public void sendPlayerMove(String pActingPlayerId, FieldCoordinate pCoordinateFrom, FieldCoordinate[] pCoordinatesTo) {
        this.send(new ClientCommandMove(pActingPlayerId, pCoordinateFrom, pCoordinatesTo));
    }

    public void sendStartGame() {
        this.send(new ClientCommandStartGame());
    }

    public void sendEndTurn() {
        this.send(new ClientCommandEndTurn());
    }

    public void sendConfirm() {
        this.send(new ClientCommandConfirm());
    }

    public void sendCloseSession() {
        this.send(new ClientCommandCloseSession());
    }

    public void sendConcedeGame(ConcedeGameStatus pStatus) {
        this.send(new ClientCommandConcedeGame(pStatus));
    }

    public void sendIllegalProcedure() {
        this.send(new ClientCommandIllegalProcedure());
    }

    public void sendRequestVersion() {
        this.send(new ClientCommandRequestVersion());
    }

    public void sendCoinChoice(boolean pChoiceHeads) {
        this.send(new ClientCommandCoinChoice(pChoiceHeads));
    }

    public void sendReceiveChoice(boolean pChoiceReceive) {
        this.send(new ClientCommandReceiveChoice(pChoiceReceive));
    }

    public void sendPlayerChoice(PlayerChoiceMode pMode, Player[] pPlayers) {
        this.send(new ClientCommandPlayerChoice(pMode, pPlayers));
    }

    public void sendPettyCash(int pPettyCash) {
        this.send(new ClientCommandPettyCash(pPettyCash));
    }

    public void sendActingPlayer(Player pPlayer, PlayerAction pPlayerAction, boolean pLeaping) {
        String playerId = pPlayer != null ? pPlayer.getId() : null;
        this.send(new ClientCommandActingPlayer(playerId, pPlayerAction, pLeaping));
    }

    public void sendUseReRoll(ReRolledAction pReRolledAction, ReRollSource pReRollSource) {
        this.send(new ClientCommandUseReRoll(pReRolledAction, pReRollSource));
    }

    public void sendUseSkill(Skill pSkill, boolean pSkillUsed) {
        this.send(new ClientCommandUseSkill(pSkill, pSkillUsed));
    }

    public void sendKickoff(FieldCoordinate pBallCoordinate) {
        this.send(new ClientCommandKickoff(pBallCoordinate));
    }

    public void sendHandOver(String pActingPlayerId, Player pCatcher) {
        String catcherId = pCatcher != null ? pCatcher.getId() : null;
        this.send(new ClientCommandHandOver(pActingPlayerId, catcherId));
    }

    public void sendGaze(String pActingPlayerId, Player pVictim) {
        String victimId = pVictim != null ? pVictim.getId() : null;
        this.send(new ClientCommandGaze(pActingPlayerId, victimId));
    }

    public void sendPass(String pActingPlayerId, FieldCoordinate pTargetCoordinate) {
        this.send(new ClientCommandPass(pActingPlayerId, pTargetCoordinate));
    }

    public void sendBlock(String pActingPlayerId, Player pDefender, boolean pUsingStab) {
        String defenderId = pDefender != null ? pDefender.getId() : null;
        this.send(new ClientCommandBlock(pActingPlayerId, defenderId, pUsingStab));
    }

    public void sendFoul(String pActingPlayerId, Player pDefender) {
        String defenderId = pDefender != null ? pDefender.getId() : null;
        this.send(new ClientCommandFoul(pActingPlayerId, defenderId));
    }

    public void sendBlockChoice(int pDiceIndex) {
        this.send(new ClientCommandBlockChoice(pDiceIndex));
    }

    public void sendUseInducement(InducementType pInducement) {
        this.send(new ClientCommandUseInducement(pInducement));
    }

    public void sendUseInducement(Card pCard) {
        this.send(new ClientCommandUseInducement(pCard));
    }

    public void sendUseInducement(InducementType pInducement, String pPlayerId) {
        this.send(new ClientCommandUseInducement(pInducement, pPlayerId));
    }

    public void sendUseInducement(Card pCard, String pPlayerId) {
        this.send(new ClientCommandUseInducement(pCard, pPlayerId));
    }

    public void sendUseInducement(InducementType pInducement, String[] pPlayerIds) {
        this.send(new ClientCommandUseInducement(pInducement, pPlayerIds));
    }

    public void sendArgueTheCall(String playerId) {
        this.send(new ClientCommandArgueTheCall(playerId));
    }

    public void sendArgueTheCall(String[] playerIds) {
        this.send(new ClientCommandArgueTheCall(playerIds));
    }

    public void sendPushback(Pushback pPushback) {
        this.send(new ClientCommandPushback(pPushback));
    }

    public void sendFollowupChoice(boolean pFollowupChoice) {
        this.send(new ClientCommandFollowupChoice(pFollowupChoice));
    }

    public void sendInterceptorChoice(Player pInterceptor) {
        String interceptorId = pInterceptor != null ? pInterceptor.getId() : null;
        this.send(new ClientCommandInterceptorChoice(interceptorId));
    }

    public void sendTeamSetupLoad(String pSetupName) {
        this.send(new ClientCommandTeamSetupLoad(pSetupName));
    }

    public void sendTeamSetupDelete(String pSetupName) {
        this.send(new ClientCommandTeamSetupDelete(pSetupName));
    }

    public void sendTeamSetupSave(TeamSetup pTeamSetup) {
        this.send(new ClientCommandTeamSetupSave(pTeamSetup.getName(), pTeamSetup.getPlayerNumbers(), pTeamSetup.getCoordinates()));
    }

    public void sendUseApothecary(String pPlayerId, boolean pApothecaryUsed) {
        this.send(new ClientCommandUseApothecary(pPlayerId, pApothecaryUsed));
    }

    public void sendApothecaryChoice(String pPlayerId, PlayerState pPlayerState, SeriousInjury pSeriousInjury) {
        this.send(new ClientCommandApothecaryChoice(pPlayerId, pPlayerState, pSeriousInjury));
    }

    public void sendUserSettings(String[] pSettingNames, String[] pSettingValues) {
        this.send(new ClientCommandUserSettings(pSettingNames, pSettingValues));
    }

    public void sendReplay(long pGameId, int pReplayToCommandNr) {
        this.send(new ClientCommandReplay(pGameId, pReplayToCommandNr));
    }

    public void sendThrowTeamMate(String pActingPlayerId, FieldCoordinate pTargetCoordinate) {
        this.send(new ClientCommandThrowTeamMate(pActingPlayerId, pTargetCoordinate));
    }

    public void sendThrowTeamMate(String pActingPlayerId, String pPlayerId) {
        this.send(new ClientCommandThrowTeamMate(pActingPlayerId, pPlayerId));
    }

    public void sendBuyInducements(String pTeamId, int pAvailableGold, InducementSet pInducementSet, String[] pStarPlayerPositionIds, String[] pMercenaryPositionIds, Skill[] pMercenarySkills) {
        this.send(new ClientCommandBuyInducements(pTeamId, pAvailableGold, pInducementSet, pStarPlayerPositionIds, pMercenaryPositionIds, pMercenarySkills));
    }

    public void sendBuyCard(CardType pType) {
        this.send(new ClientCommandBuyCard(pType));
    }

    public void sendSetMarker(String pPlayerId, String pText) {
        this.send(new ClientCommandSetMarker(pPlayerId, pText));
    }

    public void sendSetMarker(FieldCoordinate pCoordinate, String pText) {
        this.send(new ClientCommandSetMarker(pCoordinate, pText));
    }

    public void sendWizardSpell(SpecialEffect pWizardSpell, FieldCoordinate pCoordinate) {
        this.send(new ClientCommandWizardSpell(pWizardSpell, pCoordinate));
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }

}


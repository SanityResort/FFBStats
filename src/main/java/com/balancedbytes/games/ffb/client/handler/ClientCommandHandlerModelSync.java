/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.handler;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.RangeRuler;
import com.balancedbytes.games.ffb.SoundId;
import com.balancedbytes.games.ffb.TurnMode;
import com.balancedbytes.games.ffb.client.ClientData;
import com.balancedbytes.games.ffb.client.ClientReplayer;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.FieldComponent;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.StatusReport;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.animation.AnimationSequenceFactory;
import com.balancedbytes.games.ffb.client.animation.IAnimationListener;
import com.balancedbytes.games.ffb.client.animation.IAnimationSequence;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandler;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerFactory;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerMode;
import com.balancedbytes.games.ffb.client.layer.FieldLayer;
import com.balancedbytes.games.ffb.client.layer.FieldLayerPitch;
import com.balancedbytes.games.ffb.client.layer.FieldLayerPlayers;
import com.balancedbytes.games.ffb.client.layer.FieldLayerRangeRuler;
import com.balancedbytes.games.ffb.client.layer.FieldLayerUnderPlayers;
import com.balancedbytes.games.ffb.client.ui.LogComponent;
import com.balancedbytes.games.ffb.client.util.UtilClientThrowTeamMate;
import com.balancedbytes.games.ffb.client.util.UtilClientTimeout;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.Animation;
import com.balancedbytes.games.ffb.model.AnimationType;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.GameOptions;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.change.ModelChange;
import com.balancedbytes.games.ffb.model.change.ModelChangeId;
import com.balancedbytes.games.ffb.model.change.ModelChangeList;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ServerCommandModelSync;
import com.balancedbytes.games.ffb.option.GameOptionId;
import com.balancedbytes.games.ffb.option.IGameOption;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportBlockChoice;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.ReportList;
import com.balancedbytes.games.ffb.util.StringTool;

public class ClientCommandHandlerModelSync
extends ClientCommandHandler
implements IAnimationListener {
    private ServerCommandModelSync fSyncCommand;
    private ClientCommandHandlerMode fMode;
    private FieldCoordinate fBallCoordinate;
    private FieldCoordinate fBombCoordinate;
    private FieldCoordinate fThrownPlayerCoordinate;
    private boolean fUpdateActingPlayer;
    private boolean fUpdateTurnNr;
    private boolean fUpdateTurnMode;
    private boolean fUpdateTimeout;
    private boolean fClearSelectedPlayer;
    private boolean fReloadPitch;

    protected ClientCommandHandlerModelSync(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_MODEL_SYNC;
    }

    @Override
    public boolean handleNetCommand(NetCommand pNetCommand, ClientCommandHandlerMode pMode) {
        boolean waitForAnimation;
        this.fSyncCommand = (ServerCommandModelSync)pNetCommand;
        this.fMode = pMode;
        Game game = this.getClient().getGame();
        if (this.fMode == ClientCommandHandlerMode.QUEUING || this.fMode == ClientCommandHandlerMode.PLAYING) {
            game.setGameTime(this.fSyncCommand.getGameTime());
            game.setTurnTime(this.fSyncCommand.getTurnTime());
        }
        if (this.fMode == ClientCommandHandlerMode.QUEUING) {
            return true;
        }
        ModelChangeList modelChangeList = this.fSyncCommand.getModelChanges();
        modelChangeList.applyTo(game);
        UserInterface userInterface = this.getClient().getUserInterface();
        if (pMode != ClientCommandHandlerMode.REPLAYING) {
            userInterface.getLog().markCommandBegin(this.fSyncCommand.getCommandNr());
            userInterface.getStatusReport().report(this.fSyncCommand.getReportList());
            userInterface.getLog().markCommandEnd(this.fSyncCommand.getCommandNr());
        }
        this.findUpdates(this.fSyncCommand.getModelChanges());
        this.handleExtraEffects(this.fSyncCommand.getReportList());
        Animation animation = this.fSyncCommand.getAnimation();
        boolean bl = waitForAnimation = animation != null && (this.fMode == ClientCommandHandlerMode.PLAYING || this.fMode == ClientCommandHandlerMode.REPLAYING && this.getClient().getReplayer().isReplayingSingleSpeedForward());
        if (waitForAnimation) {
            switch (animation.getAnimationType()) {
                case THROW_BOMB: 
                case HAIL_MARY_BOMB: {
                    game.getFieldModel().setRangeRuler(null);
                    this.fBombCoordinate = game.getFieldModel().getBombCoordinate();
                    game.getFieldModel().setBombCoordinate(null);
                    break;
                }
                case PASS: 
                case KICK: 
                case HAIL_MARY_PASS: {
                    game.getFieldModel().setRangeRuler(null);
                    this.fBallCoordinate = game.getFieldModel().getBallCoordinate();
                    game.getFieldModel().setBallCoordinate(null);
                    break;
                }
                case THROW_TEAM_MATE: {
                    game.getFieldModel().setRangeRuler(null);
                    Player thrownPlayer = game.getPlayerById(animation.getThrownPlayerId());
                    this.fThrownPlayerCoordinate = game.getFieldModel().getPlayerCoordinate(thrownPlayer);
                    game.getFieldModel().remove(thrownPlayer);
                    break;
                }
            }
        }
        this.updateUserinterface();
        if (waitForAnimation) {
            this.startAnimation(animation);
        } else {
            this.playSound(this.fSyncCommand.getSound(), this.fMode, true);
        }
        return !waitForAnimation;
    }

    @Override
    public void animationFinished() {
        Game game = this.getClient().getGame();
        UserInterface userInterface = this.getClient().getUserInterface();
        Animation animation = this.fSyncCommand.getAnimation();
        switch (animation.getAnimationType()) {
            case THROW_BOMB: 
            case HAIL_MARY_BOMB: {
                game.getFieldModel().setBombCoordinate(this.fBombCoordinate);
                break;
            }
            case PASS: 
            case KICK: 
            case HAIL_MARY_PASS: {
                game.getFieldModel().setBallCoordinate(this.fBallCoordinate);
                break;
            }
            case THROW_TEAM_MATE: {
                Player thrownPlayer = game.getPlayerById(animation.getThrownPlayerId());
                game.getFieldModel().setPlayerCoordinate(thrownPlayer, this.fThrownPlayerCoordinate);
                break;
            }
        }
        userInterface.getFieldComponent().refresh();
        this.playSound(this.fSyncCommand.getSound(), this.fMode, true);
        this.getClient().getCommandHandlerFactory().updateClientState(this.fSyncCommand, this.fMode);
        if (this.fMode == ClientCommandHandlerMode.REPLAYING) {
            this.getClient().getReplayer().resume();
        }
    }

    private void findUpdates(ModelChangeList pModelChangeList) {
        if (pModelChangeList != null) {
            this.fUpdateTurnNr = false;
            this.fUpdateTurnMode = false;
            this.fUpdateActingPlayer = false;
            this.fUpdateTimeout = false;
            this.fClearSelectedPlayer = false;
            this.fReloadPitch = false;
            block8 : for (ModelChange modelChange : pModelChangeList.getChanges()) {
                switch (modelChange.getChangeId()) {
                    case ACTING_PLAYER_MARK_SKILL_USED: 
                    case ACTING_PLAYER_SET_CURRENT_MOVE: 
                    case ACTING_PLAYER_SET_DODGING: 
                    case ACTING_PLAYER_SET_GOING_FOR_IT: 
                    case ACTING_PLAYER_SET_HAS_BLOCKED: 
                    case ACTING_PLAYER_SET_HAS_FED: 
                    case ACTING_PLAYER_SET_HAS_FOULED: 
                    case ACTING_PLAYER_SET_HAS_MOVED: 
                    case ACTING_PLAYER_SET_HAS_PASSED: 
                    case ACTING_PLAYER_SET_LEAPING: 
                    case ACTING_PLAYER_SET_PLAYER_ACTION: 
                    case ACTING_PLAYER_SET_PLAYER_ID: 
                    case ACTING_PLAYER_SET_STANDING_UP: 
                    case ACTING_PLAYER_SET_STRENGTH: 
                    case ACTING_PLAYER_SET_SUFFERING_ANIMOSITY: 
                    case ACTING_PLAYER_SET_SUFFERING_BLOOD_LUST: {
                        this.fUpdateActingPlayer = true;
                        continue block8;
                    }
                    case TURN_DATA_SET_TURN_NR: {
                        this.fUpdateTurnNr = true;
                        continue block8;
                    }
                    case GAME_SET_TIMEOUT_POSSIBLE: {
                        this.fUpdateTimeout = true;
                        continue block8;
                    }
                    case GAME_SET_DEFENDER_ID: {
                        this.fClearSelectedPlayer = modelChange.getValue() != null;
                        continue block8;
                    }
                    case GAME_SET_TURN_MODE: {
                        this.fUpdateTurnMode = true;
                        continue block8;
                    }
                    case GAME_OPTIONS_ADD_OPTION: {
                        IGameOption gameOption = (IGameOption)modelChange.getValue();
                        if (gameOption == null || GameOptionId.PITCH_URL != gameOption.getId()) continue block8;
                        this.fReloadPitch = true;
                        break;
                    }
                }
            }
        }
    }

    private void updateUserinterface() {
        ClientData clientData = this.getClient().getClientData();
        UserInterface userInterface = this.getClient().getUserInterface();
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (this.fUpdateTimeout && this.fMode == ClientCommandHandlerMode.PLAYING) {
            UtilClientTimeout.showTimeoutStatus(this.getClient());
        }
        if (this.fUpdateActingPlayer) {
            clientData.setActingPlayerUpdated(true);
            userInterface.getFieldComponent().getLayerUnderPlayers().clearMovePath();
            FieldCoordinate playerCoordinate = game.getFieldModel().getPlayerCoordinate(actingPlayer.getPlayer());
            if (playerCoordinate != null && !playerCoordinate.isBoxCoordinate()) {
                userInterface.getFieldComponent().getLayerPlayers().updateBallAndPlayers(playerCoordinate, true);
            }
        }
        if (this.fClearSelectedPlayer) {
            clientData.setSelectedPlayer(null);
        }
        if (this.fUpdateTurnNr || this.fUpdateTurnMode && TurnMode.KICKOFF != game.getTurnMode()) {
            clientData.clear();
        }
        if (this.fReloadPitch) {
            String pitchUrl = game.getOptions().getOptionWithDefault(GameOptionId.PITCH_URL).getValueAsString();
            if (StringTool.isProvided(pitchUrl) && !userInterface.getIconCache().loadIconFromArchive(pitchUrl)) {
                userInterface.getIconCache().loadIconFromUrl(pitchUrl);
            }
            userInterface.getFieldComponent().getLayerField().init();
        }
        if (this.fMode == ClientCommandHandlerMode.PLAYING) {
            UtilClientThrowTeamMate.updateThrownPlayer(this.getClient());
            this.refreshFieldComponent();
            this.updateDialog();
            this.refreshSideBars();
            this.refreshGameMenuBar();
        }
    }

    private void handleExtraEffects(ReportList pReportList) {
        ClientData clientData = this.getClient().getClientData();
        for (IReport report : pReportList.getReports()) {
            switch (report.getId()) {
                case BLOCK_CHOICE: {
                    ReportBlockChoice reportBlockChoice = (ReportBlockChoice)report;
                    clientData.setBlockDiceResult(reportBlockChoice.getNrOfDice(), reportBlockChoice.getBlockRoll(), reportBlockChoice.getDiceIndex());
                    break;
                }
            }
        }
    }

    private void startAnimation(Animation pAnimation) {
        IAnimationSequence animationSequence = AnimationSequenceFactory.getInstance().getAnimationSequence(this.getClient(), pAnimation);
        if (animationSequence != null) {
            if (this.fMode == ClientCommandHandlerMode.REPLAYING) {
                this.getClient().getReplayer().pause();
            }
            FieldLayerRangeRuler fieldLayerRangeRuler = this.getClient().getUserInterface().getFieldComponent().getLayerRangeRuler();
            animationSequence.play(fieldLayerRangeRuler, this);
        }
    }

}


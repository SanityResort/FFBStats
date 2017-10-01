/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.CardEffect;
import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.TurnMode;
import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.util.UtilClientActionKeys;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.util.UtilCards;
import com.balancedbytes.games.ffb.util.UtilPlayer;

import javax.swing.*;
import java.util.ArrayList;

public class ClientStateSelect
extends ClientState {
    protected ClientStateSelect(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void enterState() {
        super.enterState();
        this.getClient().getGame().setDefenderId(null);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.SELECT_PLAYER;
    }

    @Override
    public void clickOnPlayer(Player pPlayer) {
        Game game = this.getClient().getGame();
        PlayerState playerState = game.getFieldModel().getPlayerState(pPlayer);
        if (game.getTeamHome().hasPlayer(pPlayer) && playerState.isActive()) {
            this.createAndShowPopupMenuForPlayer(pPlayer);
        }
    }

    @Override
    public void menuItemSelected(Player pPlayer, int pMenuKey) {
        if (pPlayer != null) {
            ClientCommunication communication = this.getClient().getCommunication();
            switch (pMenuKey) {
                case 66: {
                    communication.sendActingPlayer(pPlayer, PlayerAction.BLOCK, false);
                    break;
                }
                case 90: {
                    communication.sendActingPlayer(pPlayer, PlayerAction.BLITZ_MOVE, false);
                    break;
                }
                case 70: {
                    communication.sendActingPlayer(pPlayer, PlayerAction.FOUL_MOVE, false);
                    break;
                }
                case 77: {
                    communication.sendActingPlayer(pPlayer, PlayerAction.MOVE, false);
                    break;
                }
                case 83: {
                    communication.sendActingPlayer(pPlayer, PlayerAction.STAND_UP, false);
                    break;
                }
                case 87: {
                    communication.sendActingPlayer(pPlayer, PlayerAction.STAND_UP_BLITZ, false);
                    break;
                }
                case 72: {
                    communication.sendActingPlayer(pPlayer, PlayerAction.HAND_OVER_MOVE, false);
                    break;
                }
                case 80: {
                    communication.sendActingPlayer(pPlayer, PlayerAction.PASS_MOVE, false);
                    break;
                }
                case 84: {
                    communication.sendActingPlayer(pPlayer, PlayerAction.THROW_TEAM_MATE_MOVE, false);
                    break;
                }
                case 82: {
                    communication.sendActingPlayer(pPlayer, PlayerAction.REMOVE_CONFUSION, false);
                    break;
                }
                case 85: {
                    this.getClient().getCommunication().sendActingPlayer(pPlayer, PlayerAction.MULTIPLE_BLOCK, false);
                    break;
                }
                case 79: {
                    if (!this.isThrowBombActionAvailable(pPlayer)) break;
                    this.getClient().getCommunication().sendActingPlayer(pPlayer, PlayerAction.THROW_BOMB, false);
                }
            }
        }
    }

    private void createAndShowPopupMenuForPlayer(Player pPlayer) {
        JMenuItem confusionAction;
        JMenuItem standUpAction;
        JMenuItem moveAction;
        Game game = this.getClient().getGame();
        IconCache iconCache = this.getClient().getUserInterface().getIconCache();
        ArrayList<JMenuItem> menuItemList = new ArrayList<JMenuItem>();
        if (this.isBlockActionAvailable(pPlayer)) {
            JMenuItem blockAction = new JMenuItem("Block Action", new ImageIcon(iconCache.getIconByProperty("action.block")));
            blockAction.setMnemonic(66);
            blockAction.setAccelerator(KeyStroke.getKeyStroke(66, 0));
            menuItemList.add(blockAction);
        }
        if (this.isMultiBlockActionAvailable(pPlayer)) {
            JMenuItem multiBlockAction = new JMenuItem("Multiple Block", new ImageIcon(iconCache.getIconByProperty("action.multiple.block")));
            multiBlockAction.setMnemonic(85);
            multiBlockAction.setAccelerator(KeyStroke.getKeyStroke(85, 0));
            menuItemList.add(multiBlockAction);
        }
        if (this.isThrowBombActionAvailable(pPlayer)) {
            moveAction = new JMenuItem("Throw Bomb Action", new ImageIcon(iconCache.getIconByProperty("action.bomb")));
            moveAction.setMnemonic(79);
            moveAction.setAccelerator(KeyStroke.getKeyStroke(79, 0));
            menuItemList.add(moveAction);
        }
        if (this.isMoveActionAvailable(pPlayer)) {
            moveAction = new JMenuItem("Move Action", new ImageIcon(iconCache.getIconByProperty("action.move")));
            moveAction.setMnemonic(77);
            moveAction.setAccelerator(KeyStroke.getKeyStroke(77, 0));
            menuItemList.add(moveAction);
        }
        if (this.isBlitzActionAvailable(pPlayer)) {
            JMenuItem blitzAction = new JMenuItem("Blitz Action", new ImageIcon(iconCache.getIconByProperty("action.blitz")));
            blitzAction.setMnemonic(90);
            blitzAction.setAccelerator(KeyStroke.getKeyStroke(90, 0));
            menuItemList.add(blitzAction);
        }
        if (this.isFoulActionAvailable(pPlayer)) {
            JMenuItem foulAction = new JMenuItem("Foul Action", new ImageIcon(iconCache.getIconByProperty("action.foul")));
            foulAction.setMnemonic(70);
            foulAction.setAccelerator(KeyStroke.getKeyStroke(70, 0));
            menuItemList.add(foulAction);
        }
        if (this.isPassActionAvailable(pPlayer)) {
            JMenuItem passAction = new JMenuItem("Pass Action", new ImageIcon(iconCache.getIconByProperty("action.pass")));
            passAction.setMnemonic(80);
            passAction.setAccelerator(KeyStroke.getKeyStroke(80, 0));
            menuItemList.add(passAction);
        }
        if (this.isHandOverActionAvailable(pPlayer)) {
            JMenuItem handOverAction = new JMenuItem("Hand Over Action", new ImageIcon(iconCache.getIconByProperty("action.handover")));
            handOverAction.setMnemonic(72);
            handOverAction.setAccelerator(KeyStroke.getKeyStroke(72, 0));
            menuItemList.add(handOverAction);
        }
        if (this.isThrowTeamMateActionAvailable(pPlayer)) {
            JMenuItem throwTeamMateAction = new JMenuItem("Throw Team-Mate Action", new ImageIcon(iconCache.getIconByProperty("action.pass")));
            throwTeamMateAction.setMnemonic(84);
            throwTeamMateAction.setAccelerator(KeyStroke.getKeyStroke(84, 0));
            menuItemList.add(throwTeamMateAction);
        }
        if (this.isRecoverFromConfusionActionAvailable(pPlayer)) {
            confusionAction = new JMenuItem("Recover from Confusion & End Move", new ImageIcon(iconCache.getIconByProperty("action.standup")));
            confusionAction.setMnemonic(82);
            confusionAction.setAccelerator(KeyStroke.getKeyStroke(82, 0));
            menuItemList.add(confusionAction);
        }
        if (this.isRecoverFromGazeActionAvailable(pPlayer)) {
            confusionAction = new JMenuItem("Recover from Gaze & End Move", new ImageIcon(iconCache.getIconByProperty("action.standup")));
            confusionAction.setMnemonic(82);
            confusionAction.setAccelerator(KeyStroke.getKeyStroke(82, 0));
            menuItemList.add(confusionAction);
        }
        if (this.isStandUpActionAvailable(pPlayer) && UtilCards.hasSkill(game, pPlayer, Skill.WILD_ANIMAL) && !game.getTurnData().isBlitzUsed()) {
            standUpAction = new JMenuItem("Stand Up & End Move (using Blitz)", new ImageIcon(iconCache.getIconByProperty("action.standup")));
            standUpAction.setMnemonic(87);
            standUpAction.setAccelerator(KeyStroke.getKeyStroke(87, 0));
            menuItemList.add(standUpAction);
        }
        if (this.isStandUpActionAvailable(pPlayer)) {
            standUpAction = new JMenuItem("Stand Up & End Move", new ImageIcon(iconCache.getIconByProperty("action.standup")));
            standUpAction.setMnemonic(83);
            standUpAction.setAccelerator(KeyStroke.getKeyStroke(83, 0));
            menuItemList.add(standUpAction);
        }
        if (menuItemList.size() > 0) {
            this.createPopupMenu(menuItemList.toArray(new JMenuItem[menuItemList.size()]));
            this.showPopupMenuForPlayer(pPlayer);
        }
    }

    @Override
    public boolean actionKeyPressed(ActionKey pActionKey) {
        boolean actionHandled = true;
        Game game = this.getClient().getGame();
        UserInterface userInterface = this.getClient().getUserInterface();
        Player selectedPlayer = this.getClient().getClientData().getSelectedPlayer();
        switch (pActionKey) {
            case PLAYER_SELECT: {
                if (selectedPlayer == null) break;
                this.createAndShowPopupMenuForPlayer(selectedPlayer);
                break;
            }
            case PLAYER_CYCLE_RIGHT: {
                selectedPlayer = UtilClientActionKeys.cyclePlayer(game, selectedPlayer, true);
                if (selectedPlayer == null) break;
                this.hideSelectSquare();
                FieldCoordinate selectedCoordinate = game.getFieldModel().getPlayerCoordinate(selectedPlayer);
                this.showSelectSquare(selectedCoordinate);
                this.getClient().getClientData().setSelectedPlayer(selectedPlayer);
                userInterface.refreshSideBars();
                break;
            }
            case PLAYER_CYCLE_LEFT: {
                selectedPlayer = UtilClientActionKeys.cyclePlayer(game, selectedPlayer, false);
                if (selectedPlayer == null) break;
                this.hideSelectSquare();
                FieldCoordinate selectedCoordinate = game.getFieldModel().getPlayerCoordinate(selectedPlayer);
                this.showSelectSquare(selectedCoordinate);
                this.getClient().getClientData().setSelectedPlayer(selectedPlayer);
                userInterface.refreshSideBars();
                break;
            }
            case PLAYER_ACTION_BLOCK: {
                this.menuItemSelected(selectedPlayer, 66);
                break;
            }
            case PLAYER_ACTION_MOVE: {
                this.menuItemSelected(selectedPlayer, 77);
                break;
            }
            case PLAYER_ACTION_BLITZ: {
                this.menuItemSelected(selectedPlayer, 90);
                break;
            }
            case PLAYER_ACTION_FOUL: {
                this.menuItemSelected(selectedPlayer, 70);
                break;
            }
            case PLAYER_ACTION_STAND_UP: {
                this.menuItemSelected(selectedPlayer, 83);
                break;
            }
            case PLAYER_ACTION_HAND_OVER: {
                this.menuItemSelected(selectedPlayer, 72);
                break;
            }
            case PLAYER_ACTION_PASS: {
                this.menuItemSelected(selectedPlayer, 80);
                break;
            }
            case PLAYER_ACTION_MULTIPLE_BLOCK: {
                this.menuItemSelected(selectedPlayer, 85);
                break;
            }
            default: {
                actionHandled = false;
            }
        }
        return actionHandled;
    }

    @Override
    public void endTurn() {
        this.getClient().getCommunication().sendEndTurn();
        this.getClient().getClientData().setEndTurnButtonHidden(true);
    }

    private boolean isBlockActionAvailable(Player pPlayer) {
        Game game = this.getClient().getGame();
        PlayerState playerState = game.getFieldModel().getPlayerState(pPlayer);
        if (playerState != null && !game.getFieldModel().hasCardEffect(pPlayer, CardEffect.ILLEGALLY_SUBSTITUTED) && playerState.isActive() && !UtilCards.hasSkill(game, pPlayer, Skill.BALL_AND_CHAIN) && (playerState.getBase() != 3 || playerState.getBase() == 3 && UtilCards.hasSkill(game, pPlayer, Skill.JUMP_UP))) {
            FieldCoordinate playerCoordinate = game.getFieldModel().getPlayerCoordinate(pPlayer);
            int blockablePlayers = UtilPlayer.findAdjacentBlockablePlayers(game, game.getTeamAway(), playerCoordinate).length;
            return blockablePlayers > 0;
        }
        return false;
    }

    private boolean isMultiBlockActionAvailable(Player pPlayer) {
        Game game = this.getClient().getGame();
        PlayerState playerState = game.getFieldModel().getPlayerState(pPlayer);
        if (playerState != null && !game.getFieldModel().hasCardEffect(pPlayer, CardEffect.ILLEGALLY_SUBSTITUTED) && playerState.isActive() && UtilCards.hasSkill(game, pPlayer, Skill.MULTIPLE_BLOCK) && !UtilCards.hasSkill(game, pPlayer, Skill.BALL_AND_CHAIN) && (playerState.getBase() != 3 || playerState.getBase() == 3 && UtilCards.hasSkill(game, pPlayer, Skill.JUMP_UP))) {
            FieldCoordinate playerCoordinate = game.getFieldModel().getPlayerCoordinate(pPlayer);
            int blockablePlayers = UtilPlayer.findAdjacentBlockablePlayers(game, game.getTeamAway(), playerCoordinate).length;
            return blockablePlayers > 1;
        }
        return false;
    }

    private boolean isThrowBombActionAvailable(Player pPlayer) {
        Game game = this.getClient().getGame();
        PlayerState playerState = game.getFieldModel().getPlayerState(pPlayer);
        return playerState != null && !game.getFieldModel().hasCardEffect(pPlayer, CardEffect.ILLEGALLY_SUBSTITUTED) && !playerState.isProne() && UtilCards.hasSkill(game, pPlayer, Skill.BOMBARDIER);
    }

    private boolean isMoveActionAvailable(Player pPlayer) {
        Game game = this.getClient().getGame();
        PlayerState playerState = game.getFieldModel().getPlayerState(pPlayer);
        return playerState != null && playerState.isAbleToMove();
    }

    private boolean isBlitzActionAvailable(Player pPlayer) {
        Game game = this.getClient().getGame();
        PlayerState playerState = game.getFieldModel().getPlayerState(pPlayer);
        return !game.getTurnData().isBlitzUsed() && !game.getFieldModel().hasCardEffect(pPlayer, CardEffect.ILLEGALLY_SUBSTITUTED) && playerState != null && playerState.isActive() && playerState.isAbleToMove() && !UtilCards.hasSkill(game, pPlayer, Skill.BALL_AND_CHAIN);
    }

    private boolean isFoulActionAvailable(Player pPlayer) {
        Game game = this.getClient().getGame();
        PlayerState playerState = game.getFieldModel().getPlayerState(pPlayer);
        if (playerState != null && !game.getFieldModel().hasCardEffect(pPlayer, CardEffect.ILLEGALLY_SUBSTITUTED) && playerState.isActive() && !game.getTurnData().isFoulUsed() && game.getTurnMode() != TurnMode.BLITZ && !UtilCards.hasSkill(game, pPlayer, Skill.BALL_AND_CHAIN)) {
            for (Player opponent : game.getTeamAway().getPlayers()) {
                PlayerState opponentState = game.getFieldModel().getPlayerState(opponent);
                if (!opponentState.canBeFouled()) continue;
                return true;
            }
        }
        return false;
    }

    private boolean isPassActionAvailable(Player pPlayer) {
        Game game = this.getClient().getGame();
        PlayerState playerState = game.getFieldModel().getPlayerState(pPlayer);
        return !game.getTurnData().isPassUsed() && !game.getFieldModel().hasCardEffect(pPlayer, CardEffect.ILLEGALLY_SUBSTITUTED) && UtilPlayer.isBallAvailable(game, pPlayer) && playerState != null && !UtilCards.hasSkill(game, pPlayer, Skill.BALL_AND_CHAIN) && (playerState.isAbleToMove() || UtilPlayer.hasBall(game, pPlayer)) && !UtilCards.hasCard(game, pPlayer, Card.GLOVES_OF_HOLDING);
    }

    private boolean isHandOverActionAvailable(Player pPlayer) {
        Game game = this.getClient().getGame();
        PlayerState playerState = game.getFieldModel().getPlayerState(pPlayer);
        return !game.getTurnData().isHandOverUsed() && !game.getFieldModel().hasCardEffect(pPlayer, CardEffect.ILLEGALLY_SUBSTITUTED) && UtilPlayer.isBallAvailable(game, pPlayer) && playerState != null && !UtilCards.hasSkill(game, pPlayer, Skill.BALL_AND_CHAIN) && (playerState.isAbleToMove() || UtilPlayer.hasBall(game, pPlayer)) && !UtilCards.hasCard(game, pPlayer, Card.GLOVES_OF_HOLDING);
    }

    private boolean isThrowTeamMateActionAvailable(Player pPlayer) {
        FieldCoordinate playerCoordinate;
        Game game = this.getClient().getGame();
        PlayerState playerState = game.getFieldModel().getPlayerState(pPlayer);
        if (playerState == null || UtilCards.hasSkill(game, pPlayer, Skill.BALL_AND_CHAIN)) {
            return false;
        }
        boolean rightStuffAvailable = false;
        FieldModel fieldModel = this.getClient().getGame().getFieldModel();
        Player[] teamPlayers = pPlayer.getTeam().getPlayers();
        for (int i = 0; i < teamPlayers.length; ++i) {
            playerCoordinate = fieldModel.getPlayerCoordinate(teamPlayers[i]);
            if (!UtilCards.hasSkill(game, teamPlayers[i], Skill.RIGHT_STUFF) || playerCoordinate.isBoxCoordinate()) continue;
            rightStuffAvailable = true;
            break;
        }
        boolean rightStuffAdjacent = false;
        playerCoordinate = game.getFieldModel().getPlayerCoordinate(pPlayer);
        Player[] adjacentTeamPlayers = UtilPlayer.findAdjacentPlayersWithTacklezones(game, pPlayer.getTeam(), playerCoordinate, false);
        for (int i = 0; i < adjacentTeamPlayers.length; ++i) {
            if (!UtilCards.hasSkill(game, adjacentTeamPlayers[i], Skill.RIGHT_STUFF)) continue;
            rightStuffAdjacent = true;
            break;
        }
        return !game.getTurnData().isPassUsed() && !game.getFieldModel().hasCardEffect(pPlayer, CardEffect.ILLEGALLY_SUBSTITUTED) && UtilCards.hasSkill(game, pPlayer, Skill.THROW_TEAM_MATE) && rightStuffAvailable && (playerState.isAbleToMove() || rightStuffAdjacent);
    }

    private boolean isStandUpActionAvailable(Player pPlayer) {
        Game game = this.getClient().getGame();
        PlayerState playerState = game.getFieldModel().getPlayerState(pPlayer);
        return playerState != null && playerState.getBase() == 3 && playerState.isActive() && !UtilCards.hasSkill(game, pPlayer, Skill.BALL_AND_CHAIN);
    }

    private boolean isRecoverFromConfusionActionAvailable(Player pPlayer) {
        Game game = this.getClient().getGame();
        PlayerState playerState = game.getFieldModel().getPlayerState(pPlayer);
        return playerState != null && playerState.isConfused() && playerState.isActive() && playerState.getBase() != 3 && !UtilCards.hasSkill(game, pPlayer, Skill.BALL_AND_CHAIN);
    }

    private boolean isRecoverFromGazeActionAvailable(Player pPlayer) {
        Game game = this.getClient().getGame();
        PlayerState playerState = game.getFieldModel().getPlayerState(pPlayer);
        return playerState != null && playerState.isHypnotized() && playerState.getBase() != 3 && !UtilCards.hasSkill(game, pPlayer, Skill.BALL_AND_CHAIN);
    }

}


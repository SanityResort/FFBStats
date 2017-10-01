/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.util;

import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;

public class UtilActingPlayer {
    public static boolean changeActingPlayer(Game pGame, String pActingPlayerId, PlayerAction pPlayerAction, boolean pLeaping) {
        boolean changed = false;
        FieldModel fieldModel = pGame.getFieldModel();
        ActingPlayer actingPlayer = pGame.getActingPlayer();
        Player oldPlayer = actingPlayer.getPlayer();
        Player newPlayer = pGame.getPlayerById(pActingPlayerId);
        if (oldPlayer != null && oldPlayer != newPlayer) {
            changed = true;
            PlayerState currentState = pGame.getFieldModel().getPlayerState(oldPlayer);
            if (currentState.getBase() == 2) {
                if (actingPlayer.hasActed() && (PlayerAction.THROW_BOMB != actingPlayer.getPlayerAction() && PlayerAction.HAIL_MARY_BOMB != actingPlayer.getPlayerAction() || actingPlayer.isSkillUsed(Skill.BOMBARDIER))) {
                    pGame.getFieldModel().setPlayerState(oldPlayer, currentState.changeBase(1).changeActive(false));
                } else if (actingPlayer.isStandingUp()) {
                    pGame.getFieldModel().setPlayerState(oldPlayer, currentState.changeBase(3));
                } else {
                    pGame.getFieldModel().setPlayerState(oldPlayer, currentState.changeBase(1));
                }
            }
            pGame.getActingPlayer().setPlayer(null);
        }
        if (newPlayer != null) {
            if (newPlayer != oldPlayer) {
                changed = true;
                actingPlayer.setPlayer(newPlayer);
                PlayerState oldState = pGame.getFieldModel().getPlayerState(newPlayer);
                actingPlayer.setStandingUp(oldState.getBase() == 3);
                fieldModel.setPlayerState(newPlayer, oldState.changeBase(2));
            }
            actingPlayer.setPlayerAction(pPlayerAction);
            actingPlayer.setLeaping(pLeaping);
        }
        if (changed) {
            PlayerState playerState;
            fieldModel.clearTrackNumbers();
            fieldModel.clearDiceDecorations();
            fieldModel.clearPushbackSquares();
            fieldModel.clearMoveSquares();
            if (pGame.getActingPlayer().getPlayer() != null && (playerState = pGame.getFieldModel().getPlayerState(pGame.getActingPlayer().getPlayer())).hasUsedPro()) {
                pGame.getActingPlayer().markSkillUsed(Skill.PRO);
            }
            Player[] players = pGame.getPlayers();
            for (int i = 0; i < players.length; ++i) {
                PlayerState playerState2 = fieldModel.getPlayerState(players[i]);
                if (playerState2.getBase() != 12 && (playerState2.getBase() != 2 || players[i] == actingPlayer.getPlayer() || players[i] == pGame.getThrower())) continue;
                fieldModel.setPlayerState(players[i], playerState2.changeBase(1));
            }
        }
        return changed;
    }
}


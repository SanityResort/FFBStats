/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.ui;

import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.SendToBoxReason;
import com.balancedbytes.games.ffb.SeriousInjury;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.GameResult;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.PlayerResult;
import java.awt.Rectangle;

public class BoxSlot {
    private PlayerState fType;
    private Rectangle fLocation;
    private Player fPlayer;

    public BoxSlot(Rectangle pLocation, PlayerState pType) {
        this.fLocation = pLocation;
    }

    public PlayerState getType() {
        return this.fType;
    }

    public void setType(PlayerState pType) {
        this.fType = pType;
    }

    public Rectangle getLocation() {
        return this.fLocation;
    }

    public Player getPlayer() {
        return this.fPlayer;
    }

    public void setPlayer(Player pPlayer) {
        this.fPlayer = pPlayer;
    }

    public String getToolTip(Game pGame) {
        if (pGame != null && this.getPlayer() != null) {
            StringBuilder toolTip = new StringBuilder();
            toolTip.append("<html>");
            toolTip.append(this.getPlayer().getName());
            PlayerResult playerResult = pGame.getGameResult().getPlayerResult(this.getPlayer());
            if (playerResult.getSeriousInjury() != null) {
                toolTip.append(" ").append(playerResult.getSeriousInjury().getDescription()).append(".");
                if (playerResult.getSeriousInjuryDecay() != null) {
                    toolTip.append("<br>").append(this.getPlayer().getName()).append(" ").append(playerResult.getSeriousInjuryDecay().getDescription()).append(".");
                }
            } else {
                PlayerState playerState = pGame.getFieldModel().getPlayerState(this.getPlayer());
                if (playerState != null) {
                    toolTip.append(" ").append(playerState.getDescription()).append(".");
                }
            }
            if (playerResult.getSendToBoxReason() != null) {
                Player attacker;
                toolTip.append("<br>Player ").append(playerResult.getSendToBoxReason().getReason());
                if (playerResult.getSendToBoxHalf() > 0) {
                    toolTip.append(" in turn ").append(playerResult.getSendToBoxTurn());
                    if (playerResult.getSendToBoxHalf() > 2) {
                        toolTip.append(" of Overtime");
                    } else if (playerResult.getSendToBoxHalf() > 1) {
                        toolTip.append(" of 2nd half");
                    } else {
                        toolTip.append(" of 1st half");
                    }
                }
                if ((attacker = pGame.getPlayerById(playerResult.getSendToBoxByPlayerId())) != null) {
                    toolTip.append(" by ").append(attacker.getName());
                }
                toolTip.append(".");
            }
            toolTip.append("</html>");
            return toolTip.toString();
        }
        return null;
    }
}


/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.util.UtilClientCursor;
import com.balancedbytes.games.ffb.client.util.UtilClientStateBlocking;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.util.UtilCards;
import com.balancedbytes.games.ffb.util.UtilPlayer;

public class ClientStateBlitz
extends ClientStateMove {
    protected ClientStateBlitz(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.BLITZ;
    }

    @Override
    public void enterState() {
        super.enterState();
    }

    @Override
    public void clickOnPlayer(Player pPlayer) {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (pPlayer == actingPlayer.getPlayer()) {
            super.clickOnPlayer(pPlayer);
        } else if (UtilPlayer.isNextMoveGoingForIt(game) && !actingPlayer.isGoingForIt()) {
            this.createAndShowPopupMenuForActingPlayer();
        } else if (!actingPlayer.hasBlocked()) {
            UtilClientStateBlocking.showPopupOrBlockPlayer(this, pPlayer, true);
        }
    }

    @Override
    protected boolean mouseOverPlayer(Player pPlayer) {
        super.mouseOverPlayer(pPlayer);
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (!actingPlayer.hasBlocked() && UtilPlayer.isBlockable(game, pPlayer)) {
            UtilClientCursor.setCustomCursor(this.getClient().getUserInterface(), "cursor.block");
        } else {
            UtilClientCursor.setDefaultCursor(this.getClient().getUserInterface());
        }
        return true;
    }

    @Override
    public boolean actionKeyPressed(ActionKey pActionKey) {
        boolean actionHandled = UtilClientStateBlocking.actionKeyPressed(this, pActionKey, true);
        if (!actionHandled) {
            actionHandled = super.actionKeyPressed(pActionKey);
        }
        return actionHandled;
    }

    @Override
    protected void menuItemSelected(Player pPlayer, int pMenuKey) {
        if (pPlayer != null) {
            Game game = this.getClient().getGame();
            ActingPlayer actingPlayer = game.getActingPlayer();
            ClientCommunication communication = this.getClient().getCommunication();
            switch (pMenuKey) {
                case 69: {
                    communication.sendActingPlayer(null, null, false);
                    break;
                }
                case 76: {
                    if (!UtilCards.hasUnusedSkill(game, actingPlayer, Skill.LEAP) || !UtilPlayer.isNextMovePossible(game, false)) break;
                    communication.sendActingPlayer(pPlayer, actingPlayer.getPlayerAction(), !actingPlayer.isLeaping());
                    break;
                }
                case 77: {
                    if (!actingPlayer.isSufferingBloodLust()) break;
                    this.getClient().getCommunication().sendActingPlayer(pPlayer, PlayerAction.MOVE, actingPlayer.isLeaping());
                    break;
                }
                default: {
                    UtilClientStateBlocking.menuItemSelected(this, pPlayer, pMenuKey);
                }
            }
        }
    }
}


/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.Direction;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.Pushback;
import com.balancedbytes.games.ffb.PushbackSquare;
import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.FieldComponent;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.layer.FieldLayerOverPlayers;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.state.ClientState;
import com.balancedbytes.games.ffb.client.util.UtilClientActionKeys;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;

public class ClientStatePushback
extends ClientState {
    protected ClientStatePushback(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.PUSHBACK;
    }

    @Override
    protected boolean mouseOverPlayer(Player pPlayer) {
        super.mouseOverPlayer(pPlayer);
        FieldCoordinate playerCoordinate = this.getClient().getGame().getFieldModel().getPlayerCoordinate(pPlayer);
        return !this.mouseOverPushback(playerCoordinate);
    }

    @Override
    protected boolean mouseOverField(FieldCoordinate pCoordinate) {
        super.mouseOverField(pCoordinate);
        return !this.mouseOverPushback(pCoordinate);
    }

    @Override
    protected void clickOnField(FieldCoordinate pCoordinate) {
        Pushback pushback = this.findPushback(this.findUnlockedPushbackSquare(pCoordinate));
        if (pushback != null) {
            this.getClient().getCommunication().sendPushback(pushback);
        }
    }

    @Override
    protected void clickOnPlayer(Player pPlayer) {
        FieldCoordinate playerCoordinate = this.getClient().getGame().getFieldModel().getPlayerCoordinate(pPlayer);
        Pushback pushback = this.findPushback(this.findUnlockedPushbackSquare(playerCoordinate));
        if (pushback != null) {
            this.getClient().getCommunication().sendPushback(pushback);
        }
    }

    private PushbackSquare findUnlockedPushbackSquare(FieldCoordinate pCoordinate) {
        PushbackSquare unlockedPushbackSquare = null;
        PushbackSquare[] pushbackSquares = this.getClient().getGame().getFieldModel().getPushbackSquares();
        for (int i = 0; i < pushbackSquares.length; ++i) {
            if (pushbackSquares[i].isLocked() || !pushbackSquares[i].getCoordinate().equals(pCoordinate) || !pushbackSquares[i].isHomeChoice()) continue;
            unlockedPushbackSquare = pushbackSquares[i];
            break;
        }
        return unlockedPushbackSquare;
    }

    private boolean mouseOverPushback(FieldCoordinate pCoordinate) {
        boolean overPushback = false;
        FieldComponent fieldComponent = this.getClient().getUserInterface().getFieldComponent();
        PushbackSquare[] pushbackSquares = this.getClient().getGame().getFieldModel().getPushbackSquares();
        for (int i = 0; i < pushbackSquares.length; ++i) {
            if (pCoordinate.equals(pushbackSquares[i].getCoordinate())) {
                overPushback = true;
                if (!pushbackSquares[i].isHomeChoice() || pushbackSquares[i].isSelected() || pushbackSquares[i].isLocked()) continue;
                pushbackSquares[i].setSelected(true);
                fieldComponent.getLayerOverPlayers().drawPushbackSquare(pushbackSquares[i]);
                continue;
            }
            if (!pushbackSquares[i].isSelected() || pushbackSquares[i].isLocked()) continue;
            pushbackSquares[i].setSelected(false);
            fieldComponent.getLayerOverPlayers().drawPushbackSquare(pushbackSquares[i]);
        }
        fieldComponent.refresh();
        return overPushback;
    }

    @Override
    public boolean actionKeyPressed(ActionKey pActionKey) {
        boolean actionHandled = false;
        Game game = this.getClient().getGame();
        Direction moveDirection = UtilClientActionKeys.findMoveDirection(this.getClient(), pActionKey);
        if (moveDirection != null) {
            Pushback pushback;
            PushbackSquare pushbackSquare = null;
            PushbackSquare[] pushbackSquares = game.getFieldModel().getPushbackSquares();
            for (int i = 0; i < pushbackSquares.length; ++i) {
                if (pushbackSquares[i].isLocked() || pushbackSquares[i].getDirection() != moveDirection) continue;
                pushbackSquare = pushbackSquares[i];
                break;
            }
            if ((pushback = this.findPushback(pushbackSquare)) != null) {
                actionHandled = true;
                this.getClient().getCommunication().sendPushback(pushback);
            }
        }
        return actionHandled;
    }

    private Pushback findPushback(PushbackSquare pPushbackSquare) {
        Pushback pushback = null;
        if (pPushbackSquare != null) {
            FieldCoordinate fromSquare = null;
            FieldCoordinate toSquare = pPushbackSquare.getCoordinate();
            if (toSquare != null) {
                switch (pPushbackSquare.getDirection()) {
                    case NORTH: {
                        fromSquare = toSquare.add(0, 1);
                        break;
                    }
                    case NORTHEAST: {
                        fromSquare = toSquare.add(-1, 1);
                        break;
                    }
                    case EAST: {
                        fromSquare = toSquare.add(-1, 0);
                        break;
                    }
                    case SOUTHEAST: {
                        fromSquare = toSquare.add(-1, -1);
                        break;
                    }
                    case SOUTH: {
                        fromSquare = toSquare.add(0, -1);
                        break;
                    }
                    case SOUTHWEST: {
                        fromSquare = toSquare.add(1, -1);
                        break;
                    }
                    case WEST: {
                        fromSquare = toSquare.add(1, 0);
                        break;
                    }
                    case NORTHWEST: {
                        fromSquare = toSquare.add(1, 1);
                    }
                }
            }
            Player pushedPlayer = this.getClient().getGame().getFieldModel().getPlayer(fromSquare);
            if (fromSquare != null && pushedPlayer != null) {
                pushback = new Pushback(pushedPlayer.getId(), toSquare);
            }
        }
        return pushback;
    }

}


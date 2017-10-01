/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.FieldCoordinateBounds;
import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.util.UtilClientPlayerDrag;
import com.balancedbytes.games.ffb.dialog.DialogTeamSetupParameter;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.commands.ServerCommandTeamSetupList;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class ClientStateSetup
extends ClientState {
    protected boolean fLoadDialog;
    private boolean fReservesBoxOpened;

    protected ClientStateSetup(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void enterState() {
        super.enterState();
        this.getClient().getClientData().clear();
    }

    @Override
    public void leaveState() {
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.SETUP;
    }

    @Override
    public void mouseEntered(MouseEvent pMouseEvent) {
    }

    @Override
    public void mousePressed(MouseEvent pMouseEvent) {
        UtilClientPlayerDrag.mousePressed(this.getClient(), pMouseEvent, false);
    }

    @Override
    public void mouseDragged(MouseEvent pMouseEvent) {
        UtilClientPlayerDrag.mouseDragged(this.getClient(), pMouseEvent, false);
    }

    @Override
    public void mouseReleased(MouseEvent pMouseEvent) {
        if (SwingUtilities.isRightMouseButton(pMouseEvent)) {
            super.mouseReleased(pMouseEvent);
        } else {
            UtilClientPlayerDrag.mouseReleased(this.getClient(), pMouseEvent, false);
        }
    }

    @Override
    public boolean actionKeyPressed(ActionKey pActionKey) {
        boolean actionHandled = true;
        switch (pActionKey) {
            case MENU_SETUP_LOAD: {
                this.fLoadDialog = true;
                this.getClient().getCommunication().sendTeamSetupLoad(null);
                break;
            }
            case MENU_SETUP_SAVE: {
                this.fLoadDialog = false;
                this.getClient().getCommunication().sendTeamSetupLoad(null);
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
        UtilClientPlayerDrag.resetDragging(this.getClient());
        this.getClient().getCommunication().sendEndTurn();
    }

    @Override
    public void handleCommand(NetCommand pNetCommand) {
        Game game = this.getClient().getGame();
        UserInterface userInterface = this.getClient().getUserInterface();
        switch (pNetCommand.getId()) {
            case SERVER_TEAM_SETUP_LIST: {
                ServerCommandTeamSetupList setupListCommand = (ServerCommandTeamSetupList)pNetCommand;
                game.setDialogParameter(new DialogTeamSetupParameter(this.fLoadDialog, setupListCommand.getSetupNames()));
                break;
            }
        }
    }

    @Override
    public boolean isInitDragAllowed(FieldCoordinate pCoordinate) {
        return true;
    }

    @Override
    public boolean isDragAllowed(FieldCoordinate pCoordinate) {
        Game game = this.getClient().getGame();
        return pCoordinate != null && (FieldCoordinateBounds.HALF_HOME.isInBounds(pCoordinate) || pCoordinate.isBoxCoordinate()) && game.getFieldModel().getPlayer(pCoordinate) == null;
    }

    @Override
    public boolean isDropAllowed(FieldCoordinate pCoordinate) {
        return pCoordinate != null && (FieldCoordinateBounds.HALF_HOME.isInBounds(pCoordinate) || pCoordinate.getX() == -1);
    }

}


/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PassingDistance;
import com.balancedbytes.games.ffb.RangeRuler;
import com.balancedbytes.games.ffb.client.ClientData;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.FieldComponent;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.state.ClientStateMove;
import com.balancedbytes.games.ffb.client.util.UtilClientCursor;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.util.UtilPassing;
import com.balancedbytes.games.ffb.util.UtilRangeRuler;

public class ClientStateDumpOff
extends ClientStateMove {
    protected ClientStateDumpOff(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.DUMP_OFF;
    }

    @Override
    public void enterState() {
        super.enterState();
        this.setSelectable(true);
        Game game = this.getClient().getGame();
        game.setPassCoordinate(null);
    }

    @Override
    protected void clickOnPlayer(Player pPlayer) {
        Game game = this.getClient().getGame();
        FieldCoordinate playerCoordinate = game.getFieldModel().getPlayerCoordinate(pPlayer);
        this.clickOnField(playerCoordinate);
    }

    @Override
    protected void clickOnField(FieldCoordinate pCoordinate) {
        Game game = this.getClient().getGame();
        UserInterface userInterface = this.getClient().getUserInterface();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (this.testCoordinateInRange(pCoordinate)) {
            game.setPassCoordinate(pCoordinate);
            game.getFieldModel().setRangeRuler(null);
            userInterface.getFieldComponent().refresh();
            this.getClient().getCommunication().sendPass(actingPlayer.getPlayerId(), pCoordinate);
        }
    }

    @Override
    protected boolean mouseOverField(FieldCoordinate pCoordinate) {
        boolean selectable = false;
        Game game = this.getClient().getGame();
        UserInterface userInterface = this.getClient().getUserInterface();
        if (this.testCoordinateInRange(pCoordinate) && game.getPassCoordinate() == null) {
            RangeRuler rangeRuler = UtilRangeRuler.createRangeRuler(game, game.getThrower(), pCoordinate, false);
            game.getFieldModel().setRangeRuler(rangeRuler);
            UtilClientCursor.setCustomCursor(userInterface, "cursor.pass");
        } else {
            UtilClientCursor.setDefaultCursor(userInterface);
            selectable = true;
        }
        userInterface.getFieldComponent().refresh();
        return selectable;
    }

    @Override
    protected boolean mouseOverPlayer(Player pPlayer) {
        Game game = this.getClient().getGame();
        UserInterface userInterface = this.getClient().getUserInterface();
        FieldCoordinate playerCoordinate = game.getFieldModel().getPlayerCoordinate(pPlayer);
        boolean selectable = this.mouseOverField(playerCoordinate);
        this.getClient().getClientData().setSelectedPlayer(pPlayer);
        userInterface.refreshSideBars();
        return selectable;
    }

    private boolean testCoordinateInRange(FieldCoordinate pCoordinate) {
        FieldCoordinate throwerCoordinate;
        boolean validInRange = false;
        Game game = this.getClient().getGame();
        PassingDistance passingDistance = UtilPassing.findPassingDistance(game, throwerCoordinate = game.getFieldModel().getPlayerCoordinate(game.getThrower()), pCoordinate, false);
        validInRange = PassingDistance.QUICK_PASS == passingDistance;
        return validInRange;
    }
}


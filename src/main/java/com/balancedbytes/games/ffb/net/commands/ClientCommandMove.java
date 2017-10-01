/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonFieldCoordinateArrayOption;
import com.balancedbytes.games.ffb.json.JsonFieldCoordinateOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ClientCommand;
import com.balancedbytes.games.ffb.net.commands.ICommandWithActingPlayer;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClientCommandMove
extends ClientCommand
implements ICommandWithActingPlayer {
    private String fActingPlayerId;
    private FieldCoordinate fCoordinateFrom;
    private List<FieldCoordinate> fCoordinatesTo = new ArrayList<FieldCoordinate>();

    public ClientCommandMove() {
    }

    public ClientCommandMove(String pActingPlayerId, FieldCoordinate pCoordinateFrom, FieldCoordinate[] pCoordinatesTo) {
        this();
        this.fActingPlayerId = pActingPlayerId;
        this.fCoordinateFrom = pCoordinateFrom;
        this.addCoordinatesTo(pCoordinatesTo);
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_MOVE;
    }

    @Override
    public String getActingPlayerId() {
        return this.fActingPlayerId;
    }

    private void addCoordinateTo(FieldCoordinate pCoordinateTo) {
        if (pCoordinateTo != null) {
            this.fCoordinatesTo.add(pCoordinateTo);
        }
    }

    private void addCoordinatesTo(FieldCoordinate[] pCoordinatesTo) {
        if (ArrayTool.isProvided(pCoordinatesTo)) {
            for (FieldCoordinate coordinate : pCoordinatesTo) {
                this.addCoordinateTo(coordinate);
            }
        }
    }

    public FieldCoordinate[] getCoordinatesTo() {
        return this.fCoordinatesTo.toArray(new FieldCoordinate[this.fCoordinatesTo.size()]);
    }

    public FieldCoordinate getCoordinateFrom() {
        return this.fCoordinateFrom;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.ACTING_PLAYER_ID.addTo(jsonObject, this.fActingPlayerId);
        IJsonOption.COORDINATE_FROM.addTo(jsonObject, this.fCoordinateFrom);
        IJsonOption.COORDINATES_TO.addTo(jsonObject, this.fCoordinatesTo);
        return jsonObject;
    }

    @Override
    public ClientCommandMove initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fActingPlayerId = IJsonOption.ACTING_PLAYER_ID.getFrom(jsonObject);
        this.fCoordinateFrom = IJsonOption.COORDINATE_FROM.getFrom(jsonObject);
        this.addCoordinatesTo(IJsonOption.COORDINATES_TO.getFrom(jsonObject));
        return this;
    }
}


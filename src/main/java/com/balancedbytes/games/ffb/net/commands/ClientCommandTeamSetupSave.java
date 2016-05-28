/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonFieldCoordinateArrayOption;
import com.balancedbytes.games.ffb.json.JsonIntArrayOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClientCommandTeamSetupSave
extends NetCommand {
    private String fSetupName;
    private List<Integer> fPlayerNumbers = new ArrayList<Integer>();
    private List<FieldCoordinate> fPlayerCoordinates = new ArrayList<FieldCoordinate>();

    public ClientCommandTeamSetupSave() {
    }

    public ClientCommandTeamSetupSave(String pSetupName, int[] pPlayerNumbers, FieldCoordinate[] pPlayerCoordinates) {
        this();
        this.fSetupName = pSetupName;
        this.addPlayerNumbers(pPlayerNumbers);
        this.addPlayerCoordinates(pPlayerCoordinates);
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_TEAM_SETUP_SAVE;
    }

    public String getSetupName() {
        return this.fSetupName;
    }

    public int[] getPlayerNumbers() {
        int[] playerNumbers = new int[this.fPlayerNumbers.size()];
        for (int i = 0; i < playerNumbers.length; ++i) {
            playerNumbers[i] = this.fPlayerNumbers.get(i);
        }
        return playerNumbers;
    }

    private void addPlayerNumber(int pPlayerNumber) {
        this.fPlayerNumbers.add(pPlayerNumber);
    }

    private void addPlayerNumbers(int[] pPlayerNumbers) {
        if (ArrayTool.isProvided(pPlayerNumbers)) {
            for (int i = 0; i < pPlayerNumbers.length; ++i) {
                this.addPlayerNumber(pPlayerNumbers[i]);
            }
        }
    }

    public FieldCoordinate[] getPlayerCoordinates() {
        return this.fPlayerCoordinates.toArray(new FieldCoordinate[this.fPlayerCoordinates.size()]);
    }

    private void addPlayerCoordinate(FieldCoordinate pPlayerCoordinate) {
        if (pPlayerCoordinate != null) {
            this.fPlayerCoordinates.add(pPlayerCoordinate);
        }
    }

    private void addPlayerCoordinates(FieldCoordinate[] pPlayerCoordinates) {
        if (ArrayTool.isProvided(pPlayerCoordinates)) {
            for (FieldCoordinate playerCoordinate : pPlayerCoordinates) {
                this.addPlayerCoordinate(playerCoordinate);
            }
        }
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.SETUP_NAME.addTo(jsonObject, this.fSetupName);
        IJsonOption.PLAYER_NUMBERS.addTo(jsonObject, this.fPlayerNumbers);
        IJsonOption.PLAYER_COORDINATES.addTo(jsonObject, this.fPlayerCoordinates);
        return jsonObject;
    }

    @Override
    public ClientCommandTeamSetupSave initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fSetupName = IJsonOption.SETUP_NAME.getFrom(jsonObject);
        this.addPlayerNumbers(IJsonOption.PLAYER_NUMBERS.getFrom(jsonObject));
        this.addPlayerCoordinates(IJsonOption.PLAYER_COORDINATES.getFrom(jsonObject));
        return this;
    }
}


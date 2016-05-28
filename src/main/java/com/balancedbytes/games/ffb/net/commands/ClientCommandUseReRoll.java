/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.ReRollSource;
import com.balancedbytes.games.ffb.ReRolledAction;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandUseReRoll
extends NetCommand {
    private ReRolledAction fReRolledAction;
    private ReRollSource fReRollSource;

    public ClientCommandUseReRoll() {
    }

    public ClientCommandUseReRoll(ReRolledAction pReRolledAction, ReRollSource pReRollSource) {
        this.fReRolledAction = pReRolledAction;
        this.fReRollSource = pReRollSource;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_USE_RE_ROLL;
    }

    public ReRolledAction getReRolledAction() {
        return this.fReRolledAction;
    }

    public ReRollSource getReRollSource() {
        return this.fReRollSource;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.RE_ROLLED_ACTION.addTo(jsonObject, this.fReRolledAction);
        IJsonOption.RE_ROLL_SOURCE.addTo(jsonObject, this.fReRollSource);
        return jsonObject;
    }

    @Override
    public ClientCommandUseReRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fReRolledAction = (ReRolledAction)IJsonOption.RE_ROLLED_ACTION.getFrom(jsonObject);
        this.fReRollSource = (ReRollSource)IJsonOption.RE_ROLL_SOURCE.getFrom(jsonObject);
        return this;
    }
}


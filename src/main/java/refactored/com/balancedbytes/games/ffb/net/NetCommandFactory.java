package refactored.com.balancedbytes.games.ffb.net;

import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class NetCommandFactory {
    public NetCommand forJsonValue(JsonValue pJsonValue) {
        if (pJsonValue == null || pJsonValue.isNull()) {
            return null;
        }
        NetCommand netCommand = null;
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        NetCommandId netCommandId = (NetCommandId) IJsonOption.NET_COMMAND_ID.getFrom(jsonObject);
        if (netCommandId != null && (netCommand = netCommandId.createNetCommand()) != null) {
            netCommand.initFrom(pJsonValue);
        }
        return netCommand;
    }
}


package refactored.com.balancedbytes.games.ffb.net;

import refactored.com.balancedbytes.games.ffb.IEnumWithIdFactory;
import refactored.com.balancedbytes.games.ffb.IEnumWithNameFactory;

public class NetCommandIdFactory implements IEnumWithIdFactory, IEnumWithNameFactory {
    @Override
    public NetCommandId forName(String pName) {
        for (NetCommandId commandId : NetCommandId.values()) {
            if (!commandId.getName().equalsIgnoreCase(pName)) continue;
            return commandId;
        }
        return null;
    }

    @Override
    public NetCommandId forId(int pId) {
        for (NetCommandId commandId : NetCommandId.values()) {
            if (commandId.getId() != pId) continue;
            return commandId;
        }
        return null;
    }
}


package refactored.com.balancedbytes.games.ffb.net.commands;

import refactored.com.balancedbytes.games.ffb.net.NetCommand;
import refactored.com.balancedbytes.games.ffb.net.NetCommandId;

class UtilNetCommand {
    static void validateCommandId(NetCommand pNetCommand, NetCommandId pReceivedId) {
        if (pNetCommand == null) {
            throw new IllegalArgumentException("Parameter netCommand must not be null.");
        }
        if (pNetCommand.getId() != pReceivedId) {
            throw new IllegalStateException("Wrong netCommand id. Expected " + pNetCommand.getId().getName() + " received " + (pReceivedId != null ? pReceivedId.getName() : "null"));
        }
    }
}


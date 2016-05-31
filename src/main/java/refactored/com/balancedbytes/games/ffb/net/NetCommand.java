package refactored.com.balancedbytes.games.ffb.net;

import refactored.com.balancedbytes.games.ffb.json.IJsonReadable;

public abstract class NetCommand implements IJsonReadable {
    public abstract NetCommandId getId();
}


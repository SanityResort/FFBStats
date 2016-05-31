package refactored.com.balancedbytes.games.ffb.net.commands;

import refactored.com.balancedbytes.games.ffb.net.NetCommand;

public abstract class ServerCommand extends NetCommand {
    private int fCommandNr;

    public int getCommandNr() {
        return this.fCommandNr;
    }

    public void setCommandNr(int pCommandNr) {
        this.fCommandNr = pCommandNr;
    }
}


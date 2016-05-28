/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.client.ClientData;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.GameTitle;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.ui.GameTitleUpdateTask;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.option.GameOptionId;
import com.balancedbytes.games.ffb.option.UtilGameOption;
import java.util.Date;
import java.util.TimerTask;

public class TurnTimerTask
extends TimerTask {
    private FantasyFootballClient fClient;

    public TurnTimerTask(FantasyFootballClient pClient) {
        this.fClient = pClient;
    }

    @Override
    public void run() {
        Game game = this.getClient().getGame();
        UserInterface userInterface = this.getClient().getUserInterface();
        ClientData clientData = this.getClient().getClientData();
        GameTitle gameTitle = new GameTitle(userInterface.getGameTitle());
        if (game.getStarted() != null) {
            if (game.getFinished() == null) {
                game.setGameTime(game.getGameTime() + 1000);
                gameTitle.setGameTime(game.getGameTime());
            }
        } else {
            gameTitle.setGameTime(-1);
        }
        if (game.isTurnTimeEnabled() && !clientData.isTurnTimerStopped()) {
            if (!game.isWaitingForOpponent()) {
                game.setTurnTime(game.getTurnTime() + 1000);
            }
            gameTitle.setTurnTime(game.getTurnTime());
        } else {
            gameTitle.setTurnTime(-1);
        }
        userInterface.invokeAndWait(new GameTitleUpdateTask(this.getClient(), gameTitle));
        if (!(ClientMode.PLAYER != this.getClient().getMode() || !game.isTurnTimeEnabled() || clientData.isTurnTimerStopped() || game.isHomePlaying() || game.isTimeoutPossible() || game.isTimeoutEnforced() || UtilGameOption.getIntOption(game, GameOptionId.TURNTIME) <= 0 || game.getTurnTime() < (long)(UtilGameOption.getIntOption(game, GameOptionId.TURNTIME) * 1000))) {
            this.getClient().getCommunication().sendTimeoutPossible();
        }
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }
}


/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.util.StringTool;

public class GameTitle {
    private static final long _MILLISECONDS = 1;
    private static final long _SECONDS = 1000;
    private static final long _MINUTES = 60000;
    private static final long _HOURS = 3600000;
    private static final long _DAYS = 86400000;
    private ClientMode fClientMode;
    private boolean fTesting;
    private String fHomeCoach;
    private String fAwayCoach;
    private long fPingTime;
    private long fTurnTime;
    private long fGameTime;

    public GameTitle() {
        this.setPingTime(-1);
    }

    public GameTitle(GameTitle pGameTitle) {
        if (pGameTitle != null) {
            this.setClientMode(pGameTitle.getClientMode());
            this.setTesting(pGameTitle.isTesting());
            this.setHomeCoach(pGameTitle.getHomeCoach());
            this.setAwayCoach(pGameTitle.getAwayCoach());
            this.setPingTime(pGameTitle.getPingTime());
            this.setTurnTime(pGameTitle.getTurnTime());
            this.setGameTime(pGameTitle.getGameTime());
        }
    }

    public void setTesting(boolean pTesting) {
        this.fTesting = pTesting;
    }

    public boolean isTesting() {
        return this.fTesting;
    }

    public ClientMode getClientMode() {
        return this.fClientMode;
    }

    public void setClientMode(ClientMode pClientMode) {
        this.fClientMode = pClientMode;
    }

    public String getHomeCoach() {
        return this.fHomeCoach;
    }

    public void setHomeCoach(String pHomeCoach) {
        this.fHomeCoach = pHomeCoach;
    }

    public String getAwayCoach() {
        return this.fAwayCoach;
    }

    public void setAwayCoach(String pAwayCoach) {
        this.fAwayCoach = pAwayCoach;
    }

    public long getPingTime() {
        return this.fPingTime;
    }

    public void setPingTime(long pPingTime) {
        this.fPingTime = pPingTime;
    }

    public long getTurnTime() {
        return this.fTurnTime;
    }

    public void setTurnTime(long pTurnTime) {
        this.fTurnTime = pTurnTime;
    }

    public long getGameTime() {
        return this.fGameTime;
    }

    public void setGameTime(long pGameTime) {
        this.fGameTime = pGameTime;
    }

    public String toString() {
        StringBuilder title = new StringBuilder();
        title.append("FantasyFootball");
        if (StringTool.isProvided(this.getHomeCoach()) && StringTool.isProvided(this.getAwayCoach())) {
            if (this.isTesting()) {
                title.append(" test ");
            } else {
                if (ClientMode.PLAYER == this.getClientMode()) {
                    title.append(" - ");
                }
                if (ClientMode.SPECTATOR == this.getClientMode()) {
                    title.append(" spectate ");
                }
                if (ClientMode.REPLAY == this.getClientMode()) {
                    title.append(" replay ");
                }
            }
            title.append(this.getHomeCoach()).append(" vs ").append(this.getAwayCoach());
        }
        if (ClientMode.REPLAY != this.getClientMode() && this.getTurnTime() >= 0) {
            title.append(" - Turn ");
            this.appendTime(title, this.getTurnTime(), false);
        }
        if (this.getGameTime() >= 0) {
            title.append(" - Game ");
            this.appendTime(title, this.getGameTime(), true);
        }
        if (this.getPingTime() >= 0) {
            title.append(" - Ping ");
            title.append(this.getPingTime()).append("ms");
        }
        return title.toString();
    }

    private void appendTime(StringBuilder pBuffer, long pMilliseconds, boolean pShowHours) {
        long milliseconds = pMilliseconds;
        int days = 0;
        if (milliseconds >= 86400000) {
            days = (int)(milliseconds / 86400000);
            milliseconds -= (long)days * 86400000;
            pBuffer.append(days).append("d");
        }
        int hours = 0;
        if (pShowHours || days > 0 || milliseconds >= 3600000) {
            hours = (int)(milliseconds / 3600000);
            milliseconds -= (long)hours * 3600000;
            this.appendMin2Digits(pBuffer, hours).append("h");
        }
        int minutes = (int)(milliseconds / 60000);
        this.appendMin2Digits(pBuffer, minutes).append("m");
        int seconds = (int)((milliseconds -= (long)minutes * 60000) / 1000);
        milliseconds -= (long)seconds * 1000;
        this.appendMin2Digits(pBuffer, seconds).append("s");
    }

    private StringBuilder appendMin2Digits(StringBuilder pBuffer, int pValue) {
        if (pValue < 10) {
            pBuffer.append("0");
        }
        pBuffer.append(pValue);
        return pBuffer;
    }
}


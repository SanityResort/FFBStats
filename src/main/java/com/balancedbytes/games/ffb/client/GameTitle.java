/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.util.StringTool;

public class GameTitle {
    private static final long _SECONDS = 1000;
    private static final long _MINUTES = 60000;
    private static final long _HOURS = 3600000;
    private static final long _DAYS = 86400000;
    private ClientMode fClientMode;
    private Boolean fTesting;
    private String fHomeCoach;
    private String fAwayCoach;
    private long fPingTime;
    private long fTurnTime;
    private long fGameTime;

    public GameTitle() {
        this.setPingTime(-1);
        this.setTurnTime(-1);
        this.setGameTime(-1);
    }

    public void update(GameTitle gameTitle) {
        if (gameTitle != null) {
            if (gameTitle.getClientMode() != null) {
                this.setClientMode(gameTitle.getClientMode());
            }
            if (gameTitle.getTesting() != null) {
                this.setTesting(gameTitle.isTesting());
            }
            if (StringTool.isProvided(gameTitle.getHomeCoach())) {
                this.setHomeCoach(gameTitle.getHomeCoach());
            }
            if (StringTool.isProvided(gameTitle.getAwayCoach())) {
                this.setAwayCoach(gameTitle.getAwayCoach());
            }
            if (gameTitle.getPingTime() >= 0) {
                this.setPingTime(gameTitle.getPingTime());
            }
            if (gameTitle.getGameTime() >= 0) {
                this.setGameTime(gameTitle.getGameTime());
            }
            if (gameTitle.getTurnTime() >= 0) {
                this.setTurnTime(gameTitle.getTurnTime());
            }
        }
    }

    public void setTesting(boolean pTesting) {
        this.fTesting = pTesting;
    }

    public boolean isTesting() {
        return this.fTesting != null ? this.fTesting : false;
    }

    private Boolean getTesting() {
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
            this.appendPing(title);
        }
        return title.toString();
    }

    private void appendTime(StringBuilder builder, long milliseconds, boolean showHours) {
        long myMilliseconds = milliseconds > 0 ? milliseconds : 0;
        int days = 0;
        if (myMilliseconds >= 86400000) {
            days = (int)(myMilliseconds / 86400000);
            myMilliseconds -= (long)days * 86400000;
            builder.append(days).append("d");
        }
        int hours = 0;
        if (showHours || days > 0 || myMilliseconds >= 3600000) {
            hours = (int)(myMilliseconds / 3600000);
            myMilliseconds -= (long)hours * 3600000;
            this.appendMin2Digits(builder, hours).append("h");
        }
        int minutes = (int)(myMilliseconds / 60000);
        this.appendMin2Digits(builder, minutes).append("m");
        int seconds = (int)((myMilliseconds -= (long)minutes * 60000) / 1000);
        this.appendMin2Digits(builder, seconds).append("s");
    }

    private void appendPing(StringBuilder builder) {
        builder.append(StringTool.formatThousands(this.getPingTime())).append("ms");
    }

    private StringBuilder appendMin2Digits(StringBuilder pBuffer, int pValue) {
        if (pValue < 10) {
            pBuffer.append("0");
        }
        pBuffer.append(pValue);
        return pBuffer;
    }
}


/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.ClientModeFactory;
import com.balancedbytes.games.ffb.FantasyFootballException;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;

public class ClientParameters {
    public static final String USAGE = "java -jar FantasyFootballClient.jar -player -coach <coach>\njava -jar FantasyFootballClient.jar -player -coach <coach> -gameId <gameId>\njava -jar FantasyFootballClient.jar -player -coach <coach> -gameId <gameId> -teamHome <teamName> -teamAway <teamName>\njava -jar FantasyFootballClient.jar -player -coach <coach> -teamId <teamId> -teamName <teamName>\njava -jar FantasyFootballClient.jar -spectator -coach <coach>\njava -jar FantasyFootballClient.jar -spectator -coach <coach> -gameId <gameId>\njava -jar FantasyFootballClient.jar -replay -gameId <gameId>";
    private static final String _ARGUMENT_COACH = "-coach";
    private static final String _ARGUMENT_GAME_ID = "-gameId";
    private static final String _ARGUMENT_TEAM_ID = "-teamId";
    private static final String _ARGUMENT_TEAM_NAME = "-teamName";
    private static final String _ARGUMENT_TEAM_HOME = "-teamHome";
    private static final String _ARGUMENT_TEAM_AWAY = "-teamAway";
    private static final String _ARGUMENT_AUTHENTICATION = "-auth";
    private static final String _ARGUMENT_PORT = "-port";
    private ClientMode fMode;
    private String fCoach;
    private long fGameId;
    private String fTeamId;
    private String fTeamName;
    private String fTeamHome;
    private String fTeamAway;
    private String fAuthentication;
    private int fPort;

    public ClientMode getMode() {
        return this.fMode;
    }

    public String getCoach() {
        return this.fCoach;
    }

    private void setCoach(String pCoach) {
        this.fCoach = pCoach;
    }

    public long getGameId() {
        return this.fGameId;
    }

    public String getTeamName() {
        return this.fTeamName;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public String getTeamHome() {
        return this.fTeamHome;
    }

    public String getTeamAway() {
        return this.fTeamAway;
    }

    public String getAuthentication() {
        return this.fAuthentication;
    }

    public int getPort() {
        return this.fPort;
    }

    public void initFrom(String[] pArguments) {
        if (ArrayTool.isProvided(pArguments)) {
            ClientModeFactory clientModeFactory = new ClientModeFactory();
            int pos = 0;
            while (pos < pArguments.length) {
                String argument;
                if (clientModeFactory.forArgument(argument = this.fetchArgument(pArguments, pos++)) != null) {
                    this.fMode = clientModeFactory.forArgument(argument);
                    continue;
                }
                if ("-coach".equalsIgnoreCase(argument)) {
                    this.setCoach(this.fetchArgument(pArguments, pos++));
                    continue;
                }
                if ("-gameId".equalsIgnoreCase(argument)) {
                    try {
                        this.fGameId = Long.parseLong(this.fetchArgument(pArguments, pos++));
                        continue;
                    }
                    catch (NumberFormatException pNfe) {
                        throw new FantasyFootballException("GameId must be numeric.");
                    }
                }
                if ("-teamId".equalsIgnoreCase(argument)) {
                    this.fTeamId = this.fetchArgument(pArguments, pos++);
                    continue;
                }
                if ("-teamName".equalsIgnoreCase(argument)) {
                    this.fTeamName = this.fetchArgument(pArguments, pos++);
                    continue;
                }
                if ("-teamHome".equalsIgnoreCase(argument)) {
                    this.fTeamHome = this.fetchArgument(pArguments, pos++);
                    continue;
                }
                if ("-teamAway".equalsIgnoreCase(argument)) {
                    this.fTeamAway = this.fetchArgument(pArguments, pos++);
                    continue;
                }
                if ("-auth".equalsIgnoreCase(argument)) {
                    this.fAuthentication = this.fetchArgument(pArguments, pos++);
                    continue;
                }
                if ("-port".equalsIgnoreCase(argument)) {
                    try {
                        this.fPort = Integer.parseInt(this.fetchArgument(pArguments, pos++));
                        continue;
                    }
                    catch (NumberFormatException pNfe) {
                        throw new FantasyFootballException("Port must be numeric.");
                    }
                }
                throw new FantasyFootballException("Unknown argument " + argument);
            }
        }
    }

    public boolean validate() {
        if (this.getMode() == null) {
            return false;
        }
        switch (this.getMode()) {
            case PLAYER: {
                if (!StringTool.isProvided(this.getCoach())) {
                    return false;
                }
                if (this.getGameId() > 0) {
                    if (StringTool.isProvided(this.getTeamHome())) {
                        return StringTool.isProvided(this.getTeamAway());
                    }
                    if (StringTool.isProvided(this.getTeamAway())) {
                        return StringTool.isProvided(this.getTeamHome());
                    }
                } else {
                    if (StringTool.isProvided(this.getTeamId())) {
                        return StringTool.isProvided(this.getTeamName());
                    }
                    if (StringTool.isProvided(this.getTeamName())) {
                        return StringTool.isProvided(this.getTeamId());
                    }
                }
                return true;
            }
            case SPECTATOR: {
                if (!StringTool.isProvided(this.getCoach())) {
                    return false;
                }
                return true;
            }
            case REPLAY: {
                return this.getGameId() > 0;
            }
        }
        return false;
    }

    private String fetchArgument(String[] pArguments, int pPosition) {
        if (pPosition < pArguments.length) {
            return pArguments[pPosition];
        }
        throw new FantasyFootballException("Argument list too short");
    }

}


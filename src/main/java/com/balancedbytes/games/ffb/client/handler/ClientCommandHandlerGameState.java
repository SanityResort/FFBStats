/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.handler;

import com.balancedbytes.games.ffb.Weather;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.PlayerIconFactory;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.util.UtilClientThrowTeamMate;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Roster;
import com.balancedbytes.games.ffb.model.RosterPosition;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ServerCommandGameState;
import com.balancedbytes.games.ffb.option.GameOptionId;
import com.balancedbytes.games.ffb.util.StringTool;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

public class ClientCommandHandlerGameState
extends ClientCommandHandler {
    protected ClientCommandHandlerGameState(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_GAME_STATE;
    }

    @Override
    public boolean handleNetCommand(NetCommand pNetCommand, ClientCommandHandlerMode pMode) {
        ServerCommandGameState gameStateCommand = (ServerCommandGameState)pNetCommand;
        Game game = gameStateCommand.getGame();
        IconCache iconCache = this.getClient().getUserInterface().getIconCache();
        HashSet<String> iconUrls = new HashSet<String>();
        this.addIconUrl(iconUrls, IconCache.findTeamLogoUrl(game.getTeamHome()));
        this.addIconUrl(iconUrls, IconCache.findTeamLogoUrl(game.getTeamAway()));
        this.addRosterIconUrls(iconUrls, game.getTeamHome().getRoster());
        this.addRosterIconUrls(iconUrls, game.getTeamAway().getRoster());
        for (Player player : game.getPlayers()) {
            this.addIconUrl(iconUrls, PlayerIconFactory.getPortraitUrl(player));
            this.addIconUrl(iconUrls, PlayerIconFactory.getIconSetUrl(player));
        }
        this.addIconUrl(iconUrls, iconCache.buildPitchUrl(this.getClient().getProperty("pitch.url.default"), Weather.NICE));
        this.addIconUrl(iconUrls, iconCache.buildPitchUrl(this.getClient().getProperty("pitch.url.basic"), Weather.NICE));
        String pitchUrl = game.getOptions().getOptionWithDefault(GameOptionId.PITCH_URL).getValueAsString();
        if (StringTool.isProvided(pitchUrl)) {
            this.addIconUrl(iconUrls, iconCache.buildPitchUrl(pitchUrl, Weather.NICE));
        }
        HashSet<String> iconUrlsToDownload = new HashSet<String>();
        for (String iconUrl : iconUrls) {
            if (iconCache.loadIconFromArchive(iconUrl) || iconUrl.endsWith("/i/")) continue;
            iconUrlsToDownload.add(iconUrl);
        }
        int nrOfIcons = iconUrlsToDownload.size();
        if (nrOfIcons > 0) {
            int currentIconNr = 0;
            for (String iconUrl : iconUrlsToDownload) {
                System.out.println("download " + iconUrl);
                iconCache.loadIconFromUrl(iconUrl);
                Object[] arrobject = new Object[]{++currentIconNr, nrOfIcons};
                String message = String.format("Loaded icon %d of %d.", arrobject);
            }
        }
        this.getClient().setGame(game);
        UtilClientThrowTeamMate.updateThrownPlayer(this.getClient());
        if (pMode == ClientCommandHandlerMode.PLAYING) {
            SwingUtilities.invokeLater(new Runnable(){

                @Override
                public void run() {
                    UserInterface userInterface = ClientCommandHandlerGameState.this.getClient().getUserInterface();
                    userInterface.init();
                    ClientCommandHandlerGameState.this.getClient().updateClientState();
                }
            });
        }
        return true;
    }


    private void addIconUrl(Set<String> pIconUrls, String pIconUrl) {
        IconCache iconCache;
        if (StringTool.isProvided(pIconUrl) && (iconCache = this.getClient().getUserInterface().getIconCache()).getIconByUrl(pIconUrl) == null) {
            pIconUrls.add(pIconUrl);
        }
    }

    private void addRosterIconUrls(Set<String> pIconUrls, Roster pRoster) {
        for (RosterPosition position : pRoster.getPositions()) {
            this.addIconUrl(pIconUrls, PlayerIconFactory.getPortraitUrl(position));
            this.addIconUrl(pIconUrls, PlayerIconFactory.getIconSetUrl(position));
        }
    }

}


/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.handler;

import com.balancedbytes.games.ffb.Weather;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.PlayerIconFactory;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.DialogManager;
import com.balancedbytes.games.ffb.client.dialog.DialogProgressBar;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandler;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerMode;
import com.balancedbytes.games.ffb.client.state.ClientState;
import com.balancedbytes.games.ffb.client.ui.ChatComponent;
import com.balancedbytes.games.ffb.client.ui.GameMenuBar;
import com.balancedbytes.games.ffb.client.util.UtilClientThrowTeamMate;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.GameOptions;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Roster;
import com.balancedbytes.games.ffb.model.RosterPosition;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ServerCommandGameState;
import com.balancedbytes.games.ffb.option.GameOptionId;
import com.balancedbytes.games.ffb.option.IGameOption;
import com.balancedbytes.games.ffb.util.StringTool;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;
import javax.swing.SwingUtilities;

public class ClientCommandHandlerGameState
extends ClientCommandHandler
implements IDialogCloseListener {
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
            DialogProgressBar dialogProgress = new DialogProgressBar(this.getClient(), "Loading icons", 0, nrOfIcons);
            dialogProgress.showDialog(this);
            int currentIconNr = 0;
            for (String iconUrl2 : iconUrlsToDownload) {
                System.out.println("download " + iconUrl2);
                iconCache.loadIconFromUrl(iconUrl2);
                Object[] arrobject = new Object[]{++currentIconNr, nrOfIcons};
                String message = String.format("Loaded icon %d of %d.", arrobject);
                dialogProgress.updateProgress(currentIconNr, message);
            }
            dialogProgress.hideDialog();
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
                    userInterface.getDialogManager().updateDialog();
                    userInterface.getGameMenuBar().updateMissingPlayers();
                    userInterface.getGameMenuBar().updateInducements();
                    userInterface.getChat().requestChatInputFocus();
                }
            });
        }
        return true;
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.getClient().stopClient();
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


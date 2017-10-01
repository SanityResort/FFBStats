/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.handler;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.ParagraphStyle;
import com.balancedbytes.games.ffb.client.TextStyle;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandler;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerMode;
import com.balancedbytes.games.ffb.client.ui.ChatComponent;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ServerCommandTalk;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;

public class ClientCommandHandlerTalk
extends ClientCommandHandler {
    protected ClientCommandHandlerTalk(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_TALK;
    }

    @Override
    public boolean handleNetCommand(NetCommand pNetCommand, ClientCommandHandlerMode pMode) {
        ServerCommandTalk talkCommand = (ServerCommandTalk)pNetCommand;
        Game game = this.getClient().getGame();
        String coach = talkCommand.getCoach();
        Object[] allTalk = talkCommand.getTalks();
        if (ArrayTool.isProvided(allTalk)) {
            for (Object talk : allTalk) {
                StringBuilder status = new StringBuilder();
                TextStyle style = TextStyle.NONE;
                if (StringTool.isProvided(coach)) {
                    status.append("<");
                    status.append(coach);
                    status.append("> ");
                    style = coach.equals(game.getTeamHome().getCoach()) ? TextStyle.HOME : (coach.equals(game.getTeamAway().getCoach()) ? TextStyle.AWAY : TextStyle.SPECTATOR);
                }
                status.append((String)talk);
                ChatComponent chat = this.getClient().getUserInterface().getChat();
                chat.append(null, style, status.toString());
                chat.append(null, null, null);
            }
        }
        return true;
    }
}


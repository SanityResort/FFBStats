/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.handler;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogInformation;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandler;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerMode;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ServerCommandAdminMessage;

public class ClientCommandHandlerAdminMessage
extends ClientCommandHandler
implements IDialogCloseListener {
    protected ClientCommandHandlerAdminMessage(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_ADMIN_MESSAGE;
    }

    @Override
    public boolean handleNetCommand(NetCommand pNetCommand, ClientCommandHandlerMode pMode) {
        ServerCommandAdminMessage messageCommand = (ServerCommandAdminMessage)pNetCommand;
        DialogInformation messageDialog = new DialogInformation(this.getClient(), "Administrator Message", messageCommand.getMessages(), 1, false);
        messageDialog.showDialog(this);
        return true;
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        pDialog.hideDialog();
    }
}


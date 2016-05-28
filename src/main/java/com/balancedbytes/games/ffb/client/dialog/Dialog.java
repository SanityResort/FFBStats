/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.state.ClientState;
import com.balancedbytes.games.ffb.client.ui.ChatComponent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

public abstract class Dialog
extends JInternalFrame
implements IDialog,
MouseListener,
InternalFrameListener {
    private IDialogCloseListener fCloseListener;
    private FantasyFootballClient fClient;
    private boolean fChatInputFocus;

    public Dialog(FantasyFootballClient pClient, String pTitle, boolean pCloseable) {
        super(pTitle, false, pCloseable);
        this.fClient = pClient;
        this.setDefaultCloseOperation(0);
        this.addMouseListener(this);
        this.addInternalFrameListener(this);
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }

    @Override
    public void showDialog(IDialogCloseListener pCloseListener) {
        this.fCloseListener = pCloseListener;
        UserInterface userInterface = this.getClient().getUserInterface();
        this.fChatInputFocus = userInterface.getChat().hasChatInputFocus();
        userInterface.getDesktop().add(this);
        this.setVisible(true);
        this.moveToFront();
        if (this.fChatInputFocus) {
            userInterface.getChat().requestChatInputFocus();
        }
    }

    @Override
    public void hideDialog() {
        if (this.isVisible()) {
            this.setVisible(false);
            UserInterface userInterface = this.getClient().getUserInterface();
            userInterface.getDesktop().remove(this);
            if (this.fChatInputFocus) {
                userInterface.getChat().requestChatInputFocus();
            }
        }
    }

    public IDialogCloseListener getCloseListener() {
        return this.fCloseListener;
    }

    protected void setLocationToCenter() {
        Dimension dialogSize = this.getSize();
        Dimension frameSize = this.getClient().getUserInterface().getSize();
        this.setLocation((frameSize.width - dialogSize.width) / 2, (452 - dialogSize.height) / 2);
    }

    @Override
    public void mouseEntered(MouseEvent pMouseEvent) {
        if (this.getClient().getClientState() != null) {
            this.getClient().getClientState().hideSelectSquare();
        }
    }

    @Override
    public void mouseClicked(MouseEvent pMouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent pMouseEvent) {
    }

    @Override
    public void mousePressed(MouseEvent pMouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent pMouseEvent) {
    }

    @Override
    public void internalFrameActivated(InternalFrameEvent pE) {
        if (this.getClient().getClientState() != null) {
            this.getClient().getClientState().hideSelectSquare();
        }
    }

    @Override
    public void internalFrameClosed(InternalFrameEvent pE) {
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent pE) {
    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent pE) {
    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent pE) {
    }

    @Override
    public void internalFrameIconified(InternalFrameEvent pE) {
    }

    @Override
    public void internalFrameOpened(InternalFrameEvent pE) {
    }
}


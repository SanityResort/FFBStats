/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.DialogAbout;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

public class DialogAboutHandler
extends DialogHandler {
    private Timer fCloseTimer;

    public DialogAboutHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        this.setDialog(new DialogAbout(this.getClient()));
        this.getDialog().showDialog(this);
        this.getClient().getUserInterface().setGlassPane(new MyGlassPane());
        this.getClient().getUserInterface().getGlassPane().setVisible(true);
        this.getClient().getUserInterface().getGlassPane().requestFocus();
        this.fCloseTimer = new Timer();
        this.fCloseTimer.schedule(new TimerTask(){

            @Override
            public void run() {
                DialogAboutHandler.this.fCloseTimer = null;
                DialogAboutHandler.this.dialogClosed(DialogAboutHandler.this.getDialog());
            }
        }, 5000);
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        if (this.getDialog().isVisible()) {
            this.hideDialog();
            this.getClient().getUserInterface().getGlassPane().setVisible(false);
            this.getClient().startClient();
        }
    }

    private class MyGlassPane
    extends JPanel
    implements KeyListener,
    MouseListener {
        public MyGlassPane() {
            this.setOpaque(false);
            this.addMouseListener(this);
            this.addKeyListener(this);
        }

        @Override
        public void keyPressed(KeyEvent pE) {
            DialogAboutHandler.this.dialogClosed(DialogAboutHandler.this.getDialog());
        }

        @Override
        public void keyReleased(KeyEvent pE) {
        }

        @Override
        public void keyTyped(KeyEvent pE) {
        }

        @Override
        public void mouseClicked(MouseEvent pE) {
        }

        @Override
        public void mouseEntered(MouseEvent pE) {
        }

        @Override
        public void mouseExited(MouseEvent pE) {
        }

        @Override
        public void mousePressed(MouseEvent pE) {
            DialogAboutHandler.this.dialogClosed(DialogAboutHandler.this.getDialog());
        }

        @Override
        public void mouseReleased(MouseEvent pE) {
        }
    }

}


/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.Dialog;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.dialog.DialogId;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DialogCoinChoice
extends Dialog
implements ActionListener,
KeyListener {
    private JButton fButtonHeads;
    private JButton fButtonTails;
    private boolean fChoiceHeads;

    public DialogCoinChoice(FantasyFootballClient pClient) {
        super(pClient, "Coin Throw", false);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), 1));
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new FlowLayout(1));
        IconCache iconCache = this.getClient().getUserInterface().getIconCache();
        ImageIcon iconHeads = new ImageIcon(iconCache.getIconByProperty("game.coin.heads"));
        this.fButtonHeads = new JButton(iconHeads);
        this.fButtonHeads.addActionListener(this);
        this.fButtonHeads.addKeyListener(this);
        panelButtons.add(this.fButtonHeads);
        ImageIcon iconTails = new ImageIcon(iconCache.getIconByProperty("game.coin.tails"));
        this.fButtonTails = new JButton(iconTails);
        this.fButtonTails.addActionListener(this);
        this.fButtonTails.addKeyListener(this);
        panelButtons.add(this.fButtonTails);
        JPanel panelText = new JPanel();
        panelText.setLayout(new FlowLayout(1));
        JLabel label = new JLabel("Heads or Tails?");
        label.setFont(new Font(label.getFont().getName(), 1, label.getFont().getSize()));
        panelText.add(label);
        this.getContentPane().add(panelButtons);
        this.getContentPane().add(panelText);
        this.pack();
        this.setLocationToCenter();
    }

    @Override
    public void actionPerformed(ActionEvent pActionEvent) {
        boolean bl = this.fChoiceHeads = pActionEvent.getSource() == this.fButtonHeads;
        if (this.getCloseListener() != null) {
            this.getCloseListener().dialogClosed(this);
        }
    }

    public boolean isChoiceHeads() {
        return this.fChoiceHeads;
    }

    @Override
    public DialogId getId() {
        return DialogId.COIN_CHOICE;
    }

    @Override
    public void keyPressed(KeyEvent pKeyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent pKeyEvent) {
        boolean keyHandled = true;
        switch (pKeyEvent.getKeyCode()) {
            case 72: {
                this.fChoiceHeads = true;
                break;
            }
            case 84: {
                this.fChoiceHeads = false;
                break;
            }
            default: {
                keyHandled = false;
            }
        }
        if (keyHandled && this.getCloseListener() != null) {
            this.getCloseListener().dialogClosed(this);
        }
    }

    @Override
    public void keyTyped(KeyEvent pKeyEvent) {
    }
}


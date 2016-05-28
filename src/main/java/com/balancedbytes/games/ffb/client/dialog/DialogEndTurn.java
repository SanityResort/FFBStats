/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.Dialog;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.dialog.DialogId;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class DialogEndTurn
extends Dialog
implements ActionListener,
KeyListener {
    public static int YES = 1;
    public static int NO = 2;
    public static int WIZARD = 3;
    private JButton fButtonYes = new JButton("Yes");
    private JButton fButtonNo;
    private int fChoice;

    public DialogEndTurn(FantasyFootballClient pClient) {
        super(pClient, "End Turn", false);
        this.fButtonYes.addActionListener(this);
        this.fButtonYes.addKeyListener(this);
        this.fButtonYes.setMnemonic('Y');
        this.fButtonNo = new JButton("No");
        this.fButtonNo.addActionListener(this);
        this.fButtonNo.addKeyListener(this);
        this.fButtonNo.setMnemonic('N');
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, 1));
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, 0));
        JLabel messageLabel = new JLabel("Do you really want to end your turn?");
        messageLabel.setFont(new Font(messageLabel.getFont().getName(), 1, messageLabel.getFont().getSize()));
        messagePanel.add(messageLabel);
        messagePanel.add(Box.createHorizontalGlue());
        textPanel.add(messagePanel);
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, 0));
        infoPanel.add(textPanel);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, 0));
        buttonPanel.add(this.fButtonYes);
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(this.fButtonNo);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), 1));
        this.getContentPane().add(infoPanel);
        this.getContentPane().add(buttonPanel);
        this.pack();
        this.setLocationToCenter();
    }

    @Override
    public void actionPerformed(ActionEvent pActionEvent) {
        if (pActionEvent.getSource() == this.fButtonYes) {
            this.fChoice = YES;
        }
        if (pActionEvent.getSource() == this.fButtonNo) {
            this.fChoice = NO;
        }
        if (this.getCloseListener() != null) {
            this.getCloseListener().dialogClosed(this);
        }
    }

    public int getChoice() {
        return this.fChoice;
    }

    @Override
    public DialogId getId() {
        return DialogId.END_TURN;
    }

    @Override
    public void keyPressed(KeyEvent pKeyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent pKeyEvent) {
        boolean keyHandled = true;
        switch (pKeyEvent.getKeyCode()) {
            case 89: {
                this.fChoice = YES;
                break;
            }
            case 78: {
                this.fChoice = NO;
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


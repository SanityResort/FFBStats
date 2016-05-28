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
import com.balancedbytes.games.ffb.util.StringTool;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public abstract class DialogYesOrNoQuestion
extends Dialog
implements ActionListener,
KeyListener {
    private JButton fButtonYes;
    private JButton fButtonNo;
    private boolean fChoiceYes;

    public DialogYesOrNoQuestion(FantasyFootballClient pClient, String pTitle, String pMessageLine) {
        this(pClient, pTitle, new String[]{pMessageLine}, null);
    }

    public DialogYesOrNoQuestion(FantasyFootballClient pClient, String pTitle, String[] pMessages, String pIconProperty) {
        this(pClient, pTitle, pMessages, pIconProperty, "Yes", 89, "No", 78);
    }

    public DialogYesOrNoQuestion(FantasyFootballClient pClient, String pTitle, String[] pMessages, String pIconProperty, String pYesButtonText, int pYesButtonMnemonic, String pNoButtonText, int pNoButtonMnemonic) {
        super(pClient, pTitle, false);
        this.fButtonYes = new JButton(pYesButtonText);
        this.fButtonYes.addActionListener(this);
        this.fButtonYes.addKeyListener(this);
        this.fButtonYes.setMnemonic(pYesButtonMnemonic);
        this.fButtonNo = new JButton(pNoButtonText);
        this.fButtonNo.addActionListener(this);
        this.fButtonNo.addKeyListener(this);
        this.fButtonNo.setMnemonic(pNoButtonMnemonic);
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, 1));
        for (int i = 0; i < pMessages.length; ++i) {
            if (!StringTool.isProvided(pMessages[i])) {
                textPanel.add(Box.createVerticalStrut(5));
                continue;
            }
            if (i > 0) {
                textPanel.add(Box.createVerticalStrut(5));
            }
            JPanel messagePanel = new JPanel();
            messagePanel.setLayout(new BoxLayout(messagePanel, 0));
            JLabel messageLabel = new JLabel(pMessages[i]);
            if (i == 0) {
                messageLabel.setFont(new Font(messageLabel.getFont().getName(), 1, messageLabel.getFont().getSize()));
            }
            messagePanel.add(messageLabel);
            messagePanel.add(Box.createHorizontalGlue());
            textPanel.add(messagePanel);
        }
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, 0));
        if (StringTool.isProvided(pIconProperty)) {
            BufferedImage icon = this.getClient().getUserInterface().getIconCache().getIconByProperty(pIconProperty);
            infoPanel.add(new JLabel(new ImageIcon(icon)));
            infoPanel.add(Box.createHorizontalStrut(5));
        }
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
        boolean bl = this.fChoiceYes = pActionEvent.getSource() == this.fButtonYes;
        if (this.getCloseListener() != null) {
            this.getCloseListener().dialogClosed(this);
        }
    }

    public boolean isChoiceYes() {
        return this.fChoiceYes;
    }

    @Override
    public DialogId getId() {
        return DialogId.YES_OR_NO_QUESTION;
    }

    @Override
    public void keyPressed(KeyEvent pKeyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent pKeyEvent) {
        boolean keyHandled = true;
        switch (pKeyEvent.getKeyCode()) {
            case 89: {
                this.fChoiceYes = true;
                break;
            }
            case 78: {
                this.fChoiceYes = false;
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


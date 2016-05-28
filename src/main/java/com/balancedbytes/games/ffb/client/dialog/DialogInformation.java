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
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class DialogInformation
extends Dialog
implements ActionListener {
    public static final int OK_DIALOG = 1;
    public static final int CANCEL_DIALOG = 2;
    private JButton fButton;
    private int fOptionType;

    public DialogInformation(FantasyFootballClient pClient, String pTitle, String pMessage, int pOptionType) {
        this(pClient, pTitle, new String[]{pMessage}, pOptionType, false, null);
    }

    public DialogInformation(FantasyFootballClient pClient, String pTitle, String[] pMessages, int pOptionType, boolean pCenteredText) {
        this(pClient, pTitle, pMessages, pOptionType, pCenteredText, null);
    }

    public DialogInformation(FantasyFootballClient pClient, String pTitle, String[] pMessages, int pOptionType, String pIconProperty) {
        this(pClient, pTitle, pMessages, pOptionType, false, pIconProperty);
    }

    public DialogInformation(FantasyFootballClient pClient, String pTitle, String[] pMessages, int pOptionType, boolean pCenteredText, String pIconProperty) {
        super(pClient, pTitle, false);
        this.fOptionType = pOptionType;
        this.fButton = this.getOptionType() == 1 ? new JButton("Ok") : new JButton("Cancel");
        this.fButton.addActionListener(this);
        JPanel[] messagePanels = new JPanel[pMessages.length];
        for (int i = 0; i < pMessages.length; ++i) {
            messagePanels[i] = new JPanel();
            messagePanels[i].setLayout(new BoxLayout(messagePanels[i], 0));
            messagePanels[i].add(new JLabel(pMessages[i]));
            if (pCenteredText) continue;
            messagePanels[i].add(Box.createHorizontalGlue());
        }
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, 1));
        for (int i2 = 0; i2 < messagePanels.length; ++i2) {
            if (i2 > 0) {
                textPanel.add(Box.createVerticalStrut(5));
            }
            textPanel.add(messagePanels[i2]);
        }
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, 0));
        if (StringTool.isProvided(pIconProperty)) {
            BufferedImage icon = this.getClient().getUserInterface().getIconCache().getIconByProperty(pIconProperty);
            infoPanel.add(new JLabel(new ImageIcon(icon)));
            infoPanel.add(Box.createHorizontalStrut(5));
        }
        infoPanel.add(textPanel);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, 0));
        buttonPanel.add(this.fButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), 1));
        this.getContentPane().add(infoPanel);
        this.getContentPane().add(buttonPanel);
        this.pack();
        this.setLocationToCenter();
    }

    @Override
    public void actionPerformed(ActionEvent pActionEvent) {
        if (this.getCloseListener() != null) {
            this.getCloseListener().dialogClosed(this);
        }
    }

    @Override
    public DialogId getId() {
        return DialogId.INFORMATION;
    }

    public int getOptionType() {
        return this.fOptionType;
    }
}


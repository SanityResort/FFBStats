/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ReRollSource;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.Dialog;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.dialog.DialogBlockRollParameter;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.model.Game;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Paint;
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

public class DialogBlockRoll
extends Dialog
implements ActionListener,
KeyListener {
    private static final Color _COLOR_BLUE = new Color(12, 20, 136);
    private static final Color _COLOR_RED = new Color(128, 0, 0);
    private JButton[] fBlockDice;
    private JButton fButtonTeamReRoll;
    private JButton fButtonProReRoll;
    private JButton fButtonNoReRoll;
    private int fDiceIndex = -1;
    private ReRollSource fReRollSource;
    private DialogBlockRollParameter fDialogParameter;

    public DialogBlockRoll(FantasyFootballClient pClient, DialogBlockRollParameter pDialogParameter) {
        super(pClient, "Block Roll", false);
        this.fDialogParameter = pDialogParameter;
        IconCache iconCache = this.getClient().getUserInterface().getIconCache();
        BackgroundPanel centerPanel = new BackgroundPanel(this.getDialogParameter().getNrOfDice() < 0 ? _COLOR_BLUE : _COLOR_RED);
        centerPanel.setLayout(new BoxLayout(centerPanel, 1));
        JPanel blockRollPanel = new JPanel();
        blockRollPanel.setOpaque(false);
        blockRollPanel.setLayout(new BoxLayout(blockRollPanel, 0));
        blockRollPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        int[] blockRoll = this.getDialogParameter().getBlockRoll();
        this.fBlockDice = new JButton[blockRoll.length];
        boolean ownChoice = this.fDialogParameter.getNrOfDice() > 0 || !this.fDialogParameter.hasTeamReRollOption() && !this.fDialogParameter.hasProReRollOption();
        for (int i = 0; i < this.fBlockDice.length; ++i) {
            this.fBlockDice[i] = new JButton();
            this.fBlockDice[i].setOpaque(false);
            this.fBlockDice[i].setBounds(0, 0, 45, 45);
            this.fBlockDice[i].setFocusPainted(false);
            this.fBlockDice[i].setMargin(new Insets(5, 5, 5, 5));
            this.fBlockDice[i].setIcon(new ImageIcon(iconCache.getDiceIcon(blockRoll[i])));
            blockRollPanel.add(this.fBlockDice[i]);
            if (!ownChoice) continue;
            this.fBlockDice[i].addActionListener(this);
            if (i == 0) {
                this.fBlockDice[i].setMnemonic(49);
            }
            if (i == 1) {
                this.fBlockDice[i].setMnemonic(50);
            }
            if (i == 2) {
                this.fBlockDice[i].setMnemonic(51);
            }
            if (i > 0) {
                blockRollPanel.add(Box.createHorizontalStrut(5));
            }
            this.fBlockDice[i].addKeyListener(this);
        }
        centerPanel.add(blockRollPanel);
        if (!ownChoice) {
            JPanel opponentsChoicePanel = new JPanel();
            opponentsChoicePanel.setOpaque(false);
            opponentsChoicePanel.setLayout(new BoxLayout(opponentsChoicePanel, 0));
            JLabel opponentsChoiceLabel = new JLabel("Opponent's choice");
            opponentsChoiceLabel.setFont(new Font(opponentsChoiceLabel.getFont().getName(), 1, opponentsChoiceLabel.getFont().getSize()));
            opponentsChoicePanel.add(opponentsChoiceLabel);
            opponentsChoicePanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
            centerPanel.add(opponentsChoicePanel);
        }
        if (this.getDialogParameter().hasTeamReRollOption() || this.getDialogParameter().hasProReRollOption()) {
            JPanel reRollPanel = new JPanel();
            reRollPanel.setOpaque(false);
            reRollPanel.setLayout(new BoxLayout(reRollPanel, 0));
            this.fButtonTeamReRoll = new JButton("Team Re-Roll");
            this.fButtonTeamReRoll.addActionListener(this);
            this.fButtonTeamReRoll.setMnemonic(84);
            this.fButtonTeamReRoll.addKeyListener(this);
            this.fButtonProReRoll = new JButton("Pro Re-Roll");
            this.fButtonProReRoll.addActionListener(this);
            this.fButtonProReRoll.setMnemonic(80);
            this.fButtonProReRoll.addKeyListener(this);
            this.fButtonNoReRoll = new JButton("No Re-Roll");
            this.fButtonNoReRoll.addActionListener(this);
            this.fButtonNoReRoll.setMnemonic(78);
            this.fButtonNoReRoll.addKeyListener(this);
            Box.Filler verticalGlue1 = (Box.Filler)Box.createVerticalGlue();
            verticalGlue1.setOpaque(false);
            reRollPanel.add(verticalGlue1);
            if (this.getDialogParameter().hasTeamReRollOption()) {
                reRollPanel.add(this.fButtonTeamReRoll);
            }
            if (this.getDialogParameter().hasProReRollOption()) {
                reRollPanel.add(this.fButtonProReRoll);
            }
            if (this.getDialogParameter().getNrOfDice() < 0) {
                reRollPanel.add(this.fButtonNoReRoll);
            }
            Box.Filler verticalGlue2 = (Box.Filler)Box.createVerticalGlue();
            verticalGlue2.setOpaque(false);
            reRollPanel.add(verticalGlue2);
            centerPanel.add(Box.createVerticalStrut(10));
            centerPanel.add(reRollPanel);
        }
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add((Component)centerPanel, "Center");
        this.pack();
        this.setLocationToCenter();
    }

    @Override
    public DialogId getId() {
        return DialogId.BLOCK_ROLL;
    }

    @Override
    public void actionPerformed(ActionEvent pActionEvent) {
        boolean homeChoice;
        Game game = this.getClient().getGame();
        boolean bl = homeChoice = this.getDialogParameter().getNrOfDice() > 0 || !game.isHomePlaying();
        if (pActionEvent.getSource() == this.fButtonTeamReRoll) {
            this.fReRollSource = ReRollSource.TEAM_RE_ROLL;
        }
        if (pActionEvent.getSource() == this.fButtonProReRoll) {
            this.fReRollSource = ReRollSource.PRO;
        }
        if (homeChoice && this.fBlockDice.length >= 1 && pActionEvent.getSource() == this.fBlockDice[0]) {
            this.fDiceIndex = 0;
        }
        if (homeChoice && this.fBlockDice.length >= 2 && pActionEvent.getSource() == this.fBlockDice[1]) {
            this.fDiceIndex = 1;
        }
        if (homeChoice && this.fBlockDice.length >= 3 && pActionEvent.getSource() == this.fBlockDice[2]) {
            this.fDiceIndex = 2;
        }
        if ((this.fReRollSource != null || this.fDiceIndex >= 0 || pActionEvent.getSource() == this.fButtonNoReRoll) && this.getCloseListener() != null) {
            this.getCloseListener().dialogClosed(this);
        }
    }

    @Override
    public void keyTyped(KeyEvent pKeyEvent) {
    }

    public ReRollSource getReRollSource() {
        return this.fReRollSource;
    }

    public int getDiceIndex() {
        return this.fDiceIndex;
    }

    public DialogBlockRollParameter getDialogParameter() {
        return this.fDialogParameter;
    }

    @Override
    public void keyPressed(KeyEvent pKeyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent pKeyEvent) {
        Game game = this.getClient().getGame();
        boolean homeChoice = this.getDialogParameter().getNrOfDice() > 0 || !game.isHomePlaying();
        boolean keyHandled = false;
        switch (pKeyEvent.getKeyCode()) {
            case 49: {
                if (!homeChoice || this.fBlockDice.length < 1) break;
                keyHandled = true;
                this.fDiceIndex = 0;
                break;
            }
            case 50: {
                if (!homeChoice || this.fBlockDice.length < 2) break;
                keyHandled = true;
                this.fDiceIndex = 1;
                break;
            }
            case 51: {
                if (!homeChoice || this.fBlockDice.length < 3) break;
                keyHandled = true;
                this.fDiceIndex = 2;
                break;
            }
            case 84: {
                if (!this.getDialogParameter().hasTeamReRollOption()) break;
                keyHandled = true;
                this.fReRollSource = ReRollSource.TEAM_RE_ROLL;
                break;
            }
            case 80: {
                if (!this.getDialogParameter().hasProReRollOption()) break;
                keyHandled = true;
                this.fReRollSource = ReRollSource.PRO;
                break;
            }
            case 78: {
                keyHandled = (this.getDialogParameter().hasTeamReRollOption() || this.getDialogParameter().hasProReRollOption()) && this.getDialogParameter().getNrOfDice() < 0;
            }
        }
        if (keyHandled && this.getCloseListener() != null) {
            this.getCloseListener().dialogClosed(this);
        }
    }

    private class BackgroundPanel
    extends JPanel {
        private Color fColor;

        public BackgroundPanel(Color pColor) {
            this.fColor = pColor;
            this.setOpaque(true);
        }

        @Override
        protected void paintComponent(Graphics pGraphics) {
            if (!this.isOpaque()) {
                super.paintComponent(pGraphics);
            } else {
                Graphics2D g2d = (Graphics2D)pGraphics;
                Dimension size = this.getSize();
                g2d.setPaint(new GradientPaint(0.0f, 0.0f, this.fColor, size.width - 1, 0.0f, Color.WHITE, false));
                g2d.fillRect(0, 0, size.width, size.height);
                this.setOpaque(false);
                super.paintComponent(pGraphics);
                this.setOpaque(true);
            }
        }
    }

}


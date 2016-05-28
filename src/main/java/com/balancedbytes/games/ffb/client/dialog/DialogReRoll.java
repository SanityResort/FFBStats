/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ReRollSource;
import com.balancedbytes.games.ffb.ReRolledAction;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.Dialog;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogReRollParameter;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.util.UtilCards;
import java.awt.Component;
import java.awt.Container;
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

public class DialogReRoll
extends Dialog
implements ActionListener,
KeyListener {
    private JButton fButtonTeamReRoll;
    private JButton fButtonProReRoll;
    private JButton fButtonNoReRoll;
    private DialogReRollParameter fDialogParameter;
    private ReRollSource fReRollSource;

    public DialogReRoll(FantasyFootballClient pClient, DialogReRollParameter pDialogParameter) {
        super(pClient, "Use a Re-roll", false);
        Game game;
        Player reRollingPlayer;
        this.fDialogParameter = pDialogParameter;
        this.fButtonTeamReRoll = new JButton("Team Re-Roll");
        this.fButtonTeamReRoll.addActionListener(this);
        this.fButtonTeamReRoll.addKeyListener(this);
        this.fButtonTeamReRoll.setMnemonic(84);
        this.fButtonProReRoll = new JButton("Pro Re-Roll");
        this.fButtonProReRoll.addActionListener(this);
        this.fButtonProReRoll.addKeyListener(this);
        this.fButtonProReRoll.setMnemonic(80);
        this.fButtonNoReRoll = new JButton("No Re-Roll");
        this.fButtonNoReRoll.addActionListener(this);
        this.fButtonNoReRoll.addKeyListener(this);
        this.fButtonNoReRoll.setMnemonic(78);
        StringBuilder message1 = new StringBuilder();
        if (this.fDialogParameter.getMinimumRoll() > 0) {
            message1.append("Do you want to re-roll the failed ").append(this.fDialogParameter.getReRolledAction().getName()).append("?");
        } else {
            message1.append("Do you want to re-roll the ").append(this.fDialogParameter.getReRolledAction().getName()).append("?");
        }
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, 1));
        messagePanel.add(new JLabel(message1.toString()));
        if (pDialogParameter.isFumble()) {
            messagePanel.add(Box.createVerticalStrut(5));
            StringBuilder message2 = new StringBuilder();
            message2.append("Current roll is a FUMBLE.");
            messagePanel.add(new JLabel(message2.toString()));
        }
        if ((reRollingPlayer = (game = this.getClient().getGame()).getPlayerById(pDialogParameter.getPlayerId())) != null && UtilCards.hasSkill(game, reRollingPlayer, Skill.LONER)) {
            messagePanel.add(Box.createVerticalStrut(5));
            StringBuilder message3 = new StringBuilder();
            message3.append("Player is a LONER - the Re-Roll is not guaranteed to help.");
            messagePanel.add(new JLabel(message3.toString()));
        }
        if (this.fDialogParameter.getMinimumRoll() > 0) {
            messagePanel.add(Box.createVerticalStrut(5));
            StringBuilder message4 = new StringBuilder();
            message4.append("You will need a roll of ").append(this.fDialogParameter.getMinimumRoll()).append("+ to succeed.");
            messagePanel.add(new JLabel(message4.toString()));
        }
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, 0));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        BufferedImage icon = this.getClient().getUserInterface().getIconCache().getIconByProperty("game.dice.small");
        infoPanel.add(new JLabel(new ImageIcon(icon)));
        infoPanel.add(Box.createHorizontalStrut(5));
        infoPanel.add(messagePanel);
        infoPanel.add(Box.createHorizontalGlue());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, 0));
        if (this.fDialogParameter.isTeamReRollOption()) {
            buttonPanel.add(this.fButtonTeamReRoll);
            buttonPanel.add(Box.createHorizontalStrut(5));
        }
        if (this.fDialogParameter.isProReRollOption()) {
            buttonPanel.add(this.fButtonProReRoll);
            buttonPanel.add(Box.createHorizontalStrut(5));
        }
        buttonPanel.add(this.fButtonNoReRoll);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), 1));
        this.getContentPane().add(infoPanel);
        this.getContentPane().add(buttonPanel);
        this.pack();
        this.setLocationToCenter();
    }

    @Override
    public DialogId getId() {
        return DialogId.RE_ROLL;
    }

    @Override
    public void actionPerformed(ActionEvent pActionEvent) {
        if (pActionEvent.getSource() == this.fButtonTeamReRoll) {
            this.fReRollSource = ReRollSource.TEAM_RE_ROLL;
        }
        if (pActionEvent.getSource() == this.fButtonProReRoll) {
            this.fReRollSource = ReRollSource.PRO;
        }
        if (pActionEvent.getSource() == this.fButtonNoReRoll) {
            this.fReRollSource = null;
        }
        if (this.getCloseListener() != null) {
            this.getCloseListener().dialogClosed(this);
        }
    }

    public ReRollSource getReRollSource() {
        return this.fReRollSource;
    }

    public ReRolledAction getReRolledAction() {
        return this.fDialogParameter.getReRolledAction();
    }

    public DialogReRollParameter getDialogParameter() {
        return this.fDialogParameter;
    }

    @Override
    public void keyPressed(KeyEvent pKeyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent pKeyEvent) {
        boolean keyHandled = true;
        switch (pKeyEvent.getKeyCode()) {
            case 84: {
                if (!this.getDialogParameter().isTeamReRollOption()) break;
                this.fReRollSource = ReRollSource.TEAM_RE_ROLL;
                break;
            }
            case 80: {
                if (!this.getDialogParameter().isProReRollOption()) break;
                this.fReRollSource = ReRollSource.PRO;
                break;
            }
            case 78: {
                keyHandled = true;
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


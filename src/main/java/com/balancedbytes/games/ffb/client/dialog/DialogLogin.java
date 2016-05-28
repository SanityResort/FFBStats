/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.PasswordChallenge;
import com.balancedbytes.games.ffb.client.ClientParameters;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.Dialog;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.NoSuchAlgorithmException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.metal.MetalBorders;
import javax.swing.text.Document;

public class DialogLogin
extends Dialog {
    public static final int FIELD_GAME = 1;
    public static final int FIELD_COACH = 2;
    public static final int FIELD_PASSWORD = 3;
    private JTextField fFieldGame;
    private JTextField fFieldCoach;
    private JPasswordField fFieldPassword;
    private JButton fButtonCreate;
    private JButton fButtonList;
    private byte[] fEncodedPassword;
    private int fPasswordLength;
    private boolean fListGames;
    private boolean fShowGameName;
    private static final String _PASSWORD_DEFAULT = "1234567890123456789012345678901234567890";

    public DialogLogin(FantasyFootballClient pClient, byte[] pEncodedPassword, int pPasswordLength, String pTeamHomeName, String pTeamAwayName, boolean pShowGameName) {
        super(pClient, ClientMode.PLAYER == pClient.getMode() ? "Start Game as Player" : "Start Game as Spectator", false);
        this.fPasswordLength = pPasswordLength;
        this.fEncodedPassword = pEncodedPassword;
        this.fShowGameName = pShowGameName;
        boolean askForPassword = this.fPasswordLength >= 0;
        JPanel teamHomePanel = new JPanel();
        JTextField teamHomeField = new JTextField(StringTool.print(pTeamHomeName));
        teamHomeField.setEditable(false);
        teamHomePanel.setLayout(new BoxLayout(teamHomePanel, 0));
        teamHomePanel.add(teamHomeField);
        teamHomePanel.setBorder(this.createTitledBorder("Team"));
        JPanel teamAwayPanel = new JPanel();
        JTextField teamAwayField = new JTextField(StringTool.print(pTeamAwayName));
        teamAwayField.setEditable(false);
        teamAwayPanel.setLayout(new BoxLayout(teamAwayPanel, 0));
        teamAwayPanel.add(teamAwayField);
        teamAwayPanel.setBorder(this.createTitledBorder("Opponent"));
        this.fFieldCoach = new JTextField(20);
        this.fFieldCoach.setText(pClient.getParameters().getCoach());
        this.fFieldCoach.setEditable(false);
        JPanel coachPanel = new JPanel();
        coachPanel.setLayout(new BoxLayout(coachPanel, 0));
        coachPanel.add(this.fFieldCoach);
        coachPanel.setBorder(this.createTitledBorder("Coach"));
        this.fFieldPassword = new JPasswordField(20);
        if (this.fEncodedPassword != null) {
            this.fFieldPassword.setText("1234567890123456789012345678901234567890".substring(0, this.fPasswordLength));
        }
        this.fFieldPassword.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent pActionEvent) {
                if (DialogLogin.this.getClient().getParameters().getMode() == ClientMode.PLAYER || StringTool.isProvided(DialogLogin.this.getGameName())) {
                    if (DialogLogin.this.fFieldGame.isEditable()) {
                        DialogLogin.this.fFieldGame.requestFocus();
                    } else {
                        DialogLogin.this.fButtonCreate.requestFocus();
                    }
                } else {
                    DialogLogin.this.fButtonCreate.requestFocus();
                }
            }
        });
        this.fFieldPassword.addKeyListener(new KeyAdapter(){

            @Override
            public void keyReleased(KeyEvent pKeyEvent) {
                DialogLogin.this.toggleButtons();
            }
        });
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, 0));
        passwordPanel.add(this.fFieldPassword);
        passwordPanel.setBorder(this.createTitledBorder("Password"));
        this.fFieldGame = new JTextField(20);
        this.fFieldGame.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent pActionEvent) {
                DialogLogin.this.fButtonCreate.requestFocus();
            }
        });
        this.fFieldGame.addKeyListener(new KeyAdapter(){

            @Override
            public void keyReleased(KeyEvent pKeyEvent) {
                DialogLogin.this.toggleButtons();
            }
        });
        this.fFieldGame.setEditable(ClientMode.PLAYER == this.getClient().getMode());
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, 0));
        gamePanel.add(this.fFieldGame);
        gamePanel.setBorder(this.createTitledBorder("Game"));
        this.fButtonList = new JButton("List Open Games");
        this.fButtonList.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent pActionEvent) {
                DialogLogin.this.fListGames = true;
                DialogLogin.this.checkAndCloseDialog();
            }
        });
        this.fButtonCreate = ClientMode.PLAYER == this.getClient().getMode() ? new JButton(this.fShowGameName ? "Start New Game" : "Start Game") : new JButton("Spectate Game");
        this.fButtonCreate.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent pActionEvent) {
                DialogLogin.this.fListGames = false;
                DialogLogin.this.checkAndCloseDialog();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(this.fButtonCreate);
        if (this.fShowGameName) {
            buttonPanel.add(this.fButtonList);
        }
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, 1));
        if (StringTool.isProvided(pTeamHomeName)) {
            contentPanel.add(teamHomePanel);
        }
        if (StringTool.isProvided(pTeamAwayName)) {
            contentPanel.add(teamAwayPanel);
        }
        contentPanel.add(coachPanel);
        if (askForPassword) {
            contentPanel.add(passwordPanel);
        }
        if (this.fShowGameName) {
            contentPanel.add(gamePanel);
        }
        contentPanel.add(buttonPanel);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add((Component)contentPanel, "Center");
        this.pack();
        this.setLocationToCenter();
    }

    private void showDialogIntern(IDialogCloseListener pCloseListener) {
        this.fFieldGame.setBorder(MetalBorders.getTextFieldBorder());
        this.fFieldCoach.setBorder(MetalBorders.getTextFieldBorder());
        this.fFieldPassword.setBorder(MetalBorders.getTextFieldBorder());
        this.toggleButtons();
        super.showDialog(pCloseListener);
    }

    @Override
    public void showDialog(IDialogCloseListener pCloseListener) {
        this.showDialogIntern(pCloseListener);
        if (this.fFieldCoach.getDocument().getLength() > 0 && this.fPasswordLength >= 0) {
            this.fFieldPassword.requestFocus();
        } else {
            this.fFieldGame.requestFocus();
        }
    }

    public void showDialogWithError(IDialogCloseListener pCloseListener, int pErrorField) {
        this.showDialogIntern(pCloseListener);
        switch (pErrorField) {
            case 1: {
                this.fFieldGame.setBorder(BorderFactory.createLineBorder(Color.RED));
                this.fFieldGame.requestFocus();
                break;
            }
            case 2: {
                this.fFieldCoach.setBorder(BorderFactory.createLineBorder(Color.RED));
                this.fFieldCoach.requestFocus();
                break;
            }
            case 3: {
                this.fFieldPassword.setBorder(BorderFactory.createLineBorder(Color.RED));
                this.fFieldPassword.setText("");
                this.fFieldPassword.requestFocus();
            }
        }
    }

    public String getGameName() {
        return this.fFieldGame.getText();
    }

    public String getCoach() {
        return this.fFieldCoach.getText();
    }

    public void setCoach(String pCoach) {
        this.fFieldCoach.setText(pCoach);
    }

    public byte[] getEncodedPassword() {
        if (this.fEncodedPassword != null) {
            return this.fEncodedPassword;
        }
        char[] passwordChars = this.fFieldPassword.getPassword();
        if (passwordChars != null && passwordChars.length > 0) {
            byte[] password = new String(passwordChars).getBytes();
            try {
                return PasswordChallenge.md5Encode(password);
            }
            catch (NoSuchAlgorithmException pNsaE) {
                return null;
            }
        }
        return null;
    }

    public void setEncodedPassword(byte[] pEncodedPassword, int pPasswordLength) {
        this.fEncodedPassword = pEncodedPassword;
        if (this.fEncodedPassword != null) {
            this.fFieldPassword.setText("1234567890123456789012345678901234567890".substring(0, pPasswordLength));
        }
    }

    private CompoundBorder createTitledBorder(String title) {
        return BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title), BorderFactory.createEmptyBorder(0, 2, 2, 2));
    }

    private void checkAndCloseDialog() {
        if (StringTool.isProvided(this.getCoach()) && (this.fPasswordLength < 0 || ArrayTool.isProvided(this.fFieldPassword.getPassword()))) {
            this.fPasswordLength = this.fFieldPassword.getDocument().getLength();
            if (this.getCloseListener() != null) {
                this.getCloseListener().dialogClosed(this);
            }
        }
    }

    @Override
    public DialogId getId() {
        return DialogId.GAME_COACH_PASSWORD;
    }

    public boolean isListGames() {
        return this.fListGames;
    }

    public int getPasswordLength() {
        return this.fPasswordLength;
    }

    private void toggleButtons() {
        boolean hasGame = this.fFieldGame.getDocument().getLength() > 0;
        boolean hasCoach = this.fFieldCoach.getDocument().getLength() > 0;
        boolean hasPassword = this.fPasswordLength < 0 || this.fFieldPassword.getDocument().getLength() > 0;
        this.fButtonCreate.setEnabled((!this.fShowGameName || hasGame) && hasCoach && hasPassword);
        this.fButtonList.setEnabled(this.fShowGameName && hasCoach && hasPassword);
    }

}


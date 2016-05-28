/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.Dialog;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.ui.IntegerField;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.util.StringTool;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

public class DialogPettyCash
extends Dialog
implements ActionListener,
KeyListener {
    private IntegerField fIntegerFieldPettyCash;
    private JLabel fLabelTreasury;
    private JLabel fLabelTeamValue;
    private JLabel fLabelOpponentTeamValue;
    private JLabel fLabelInducements;
    private JButton fButtonTransfer;
    private int fOriginalTeamValue;
    private int fTeamValue;
    private int fOriginalTreasury;
    private int fTreasury;
    private int fOpponentTeamValue;
    private int fPettyCash;

    public DialogPettyCash(FantasyFootballClient pClient, int pTeamValue, int pTreasury, int pOpponentTeamValue) {
        super(pClient, "Transfer Gold to Petty Cash", false);
        this.fOriginalTeamValue = pTeamValue;
        this.fTeamValue = pTeamValue;
        this.fOriginalTreasury = pTreasury;
        this.fTreasury = pTreasury;
        this.fOpponentTeamValue = pOpponentTeamValue;
        JPanel panelTreasury = new JPanel();
        panelTreasury.setLayout(new BoxLayout(panelTreasury, 0));
        this.fLabelTreasury = new JLabel(this.createTreasuryText());
        panelTreasury.add(this.fLabelTreasury);
        panelTreasury.add(Box.createHorizontalGlue());
        JPanel panelTeamValue = new JPanel();
        panelTeamValue.setLayout(new BoxLayout(panelTeamValue, 0));
        this.fLabelTeamValue = new JLabel(this.createTeamValueText());
        panelTeamValue.add(this.fLabelTeamValue);
        panelTeamValue.add(Box.createHorizontalGlue());
        JPanel panelOpponentTeamValue = new JPanel();
        panelOpponentTeamValue.setLayout(new BoxLayout(panelOpponentTeamValue, 0));
        this.fLabelOpponentTeamValue = new JLabel(this.createOpponentTeamValueText());
        panelOpponentTeamValue.add(this.fLabelOpponentTeamValue);
        panelOpponentTeamValue.add(Box.createHorizontalGlue());
        this.fIntegerFieldPettyCash = new IntegerField(5);
        this.fIntegerFieldPettyCash.setText("0");
        this.fIntegerFieldPettyCash.setHorizontalAlignment(4);
        this.fIntegerFieldPettyCash.selectAll();
        this.fIntegerFieldPettyCash.setMaximumSize(this.fIntegerFieldPettyCash.getPreferredSize());
        this.fIntegerFieldPettyCash.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void removeUpdate(DocumentEvent pE) {
                DialogPettyCash.this.updatePettyCash();
            }

            @Override
            public void insertUpdate(DocumentEvent pE) {
                DialogPettyCash.this.updatePettyCash();
            }

            @Override
            public void changedUpdate(DocumentEvent pE) {
                DialogPettyCash.this.updatePettyCash();
            }
        });
        this.fIntegerFieldPettyCash.addFocusListener(new FocusAdapter(){

            @Override
            public void focusLost(FocusEvent pE) {
                if (DialogPettyCash.this.fIntegerFieldPettyCash.getInt() != DialogPettyCash.this.fPettyCash / 1000) {
                    DialogPettyCash.this.fIntegerFieldPettyCash.setInt(DialogPettyCash.this.fPettyCash / 1000);
                }
            }
        });
        JPanel panelPettyCash = new JPanel();
        panelPettyCash.setLayout(new BoxLayout(panelPettyCash, 0));
        JLabel labelLeading = new JLabel("Petty Cash");
        labelLeading.setFont(labelLeading.getFont().deriveFont(1));
        panelPettyCash.add(labelLeading);
        panelPettyCash.add(Box.createHorizontalStrut(5));
        panelPettyCash.add(this.fIntegerFieldPettyCash);
        panelPettyCash.add(Box.createHorizontalStrut(5));
        JLabel labelTrailing = new JLabel("k gold");
        labelTrailing.setFont(labelTrailing.getFont().deriveFont(1));
        panelPettyCash.add(labelTrailing);
        panelPettyCash.add(Box.createHorizontalGlue());
        JPanel panelInducements = new JPanel();
        panelInducements.setLayout(new BoxLayout(panelInducements, 0));
        this.fLabelInducements = new JLabel(this.createInducementsText());
        panelInducements.add(this.fLabelInducements);
        panelInducements.add(Box.createHorizontalGlue());
        this.fButtonTransfer = new JButton("Transfer");
        this.fButtonTransfer.addActionListener(this);
        this.fButtonTransfer.addKeyListener(this);
        this.fButtonTransfer.setMnemonic(84);
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new BoxLayout(panelButtons, 0));
        panelButtons.add(this.fButtonTransfer);
        JPanel panelContent = new JPanel();
        panelContent.setLayout(new BoxLayout(panelContent, 1));
        panelContent.add(panelTreasury);
        panelContent.add(panelTeamValue);
        panelContent.add(panelOpponentTeamValue);
        panelContent.add(Box.createVerticalStrut(5));
        panelContent.add(panelPettyCash);
        panelContent.add(Box.createVerticalStrut(5));
        panelContent.add(panelInducements);
        panelContent.add(Box.createVerticalStrut(5));
        panelContent.add(panelButtons);
        panelContent.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add((Component)panelContent, "Center");
        this.pack();
        this.setLocationToCenter();
    }

    @Override
    public DialogId getId() {
        return DialogId.PETTY_CASH;
    }

    private String createTreasuryText() {
        StringBuilder line = new StringBuilder();
        line.append("Your treasury is ").append(StringTool.formatThousands(this.fTreasury / 1000)).append("k gold.");
        return line.toString();
    }

    private String createTeamValueText() {
        StringBuilder line = new StringBuilder();
        line.append("Your team value is ").append(StringTool.formatThousands(this.fTeamValue / 1000)).append("k.");
        return line.toString();
    }

    private String createOpponentTeamValueText() {
        StringBuilder line = new StringBuilder();
        line.append("Your opponent's team value is ").append(StringTool.formatThousands(this.fOpponentTeamValue / 1000)).append("k.");
        return line.toString();
    }

    private String createInducementsText() {
        StringBuilder line = new StringBuilder();
        int inducements = this.fOpponentTeamValue - this.fTeamValue;
        if (inducements == 0) {
            line.append("You don't gain or give away any free inducements.");
        } else if (inducements > 0) {
            line.append("You gain free inducements for ").append(StringTool.formatThousands(inducements / 1000)).append("k gold.");
        } else {
            line.append("You give away free inducements for ").append(StringTool.formatThousands((- inducements) / 1000)).append("k gold.");
        }
        return line.toString();
    }

    private void updatePettyCash() {
        this.fPettyCash = Math.min(this.fIntegerFieldPettyCash.getInt() * 1000, this.fOriginalTreasury);
        this.fTreasury = this.fOriginalTreasury - this.fPettyCash;
        this.fLabelTreasury.setText(this.createTreasuryText());
        this.fTeamValue = this.fOriginalTeamValue + this.fPettyCash;
        this.fLabelTeamValue.setText(this.createTeamValueText());
        this.fLabelInducements.setText(this.createInducementsText());
    }

    public int getPettyCash() {
        return this.fPettyCash;
    }

    @Override
    public void actionPerformed(ActionEvent pActionEvent) {
        if (this.getCloseListener() != null) {
            this.getCloseListener().dialogClosed(this);
        }
    }

    @Override
    public void keyPressed(KeyEvent pKeyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent pKeyEvent) {
        boolean keyHandled = true;
        switch (pKeyEvent.getKeyCode()) {
            case 67: {
                this.fPettyCash = 0;
                break;
            }
            case 84: {
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


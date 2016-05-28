/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.SpecialEffect;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.Dialog;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.dialog.DialogId;
import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DialogWizardSpell
extends Dialog
implements ActionListener,
KeyListener {
    private JButton fButtonLightning;
    private JButton fButtonFireball;
    private JButton fButtonCancel;
    private SpecialEffect fWizardSpell;

    public DialogWizardSpell(FantasyFootballClient pClient) {
        super(pClient, "Wizard Spell", false);
        JPanel panelText = new JPanel();
        panelText.setLayout(new BoxLayout(panelText, 0));
        panelText.add(new JLabel("Which spell should your wizard cast?"));
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new BoxLayout(panelButtons, 0));
        panelButtons.add(Box.createHorizontalGlue());
        this.fButtonLightning = new JButton("Lightning");
        this.fButtonLightning.addActionListener(this);
        this.fButtonLightning.addKeyListener(this);
        panelButtons.add(this.fButtonLightning);
        this.fButtonFireball = new JButton("Fireball");
        this.fButtonFireball.addActionListener(this);
        this.fButtonFireball.addKeyListener(this);
        panelButtons.add(this.fButtonFireball);
        this.fButtonCancel = new JButton("Cancel");
        this.fButtonCancel.addActionListener(this);
        this.fButtonCancel.addKeyListener(this);
        panelButtons.add(this.fButtonCancel);
        panelButtons.add(Box.createHorizontalGlue());
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), 1));
        this.getContentPane().add(Box.createVerticalStrut(5));
        this.getContentPane().add(panelText);
        this.getContentPane().add(Box.createVerticalStrut(5));
        this.getContentPane().add(panelButtons);
        this.pack();
        this.setLocationToCenter();
    }

    @Override
    public DialogId getId() {
        return DialogId.WIZARD_SPELL;
    }

    @Override
    public void actionPerformed(ActionEvent pActionEvent) {
        if (pActionEvent.getSource() == this.fButtonLightning) {
            this.fWizardSpell = SpecialEffect.LIGHTNING;
        }
        if (pActionEvent.getSource() == this.fButtonFireball) {
            this.fWizardSpell = SpecialEffect.FIREBALL;
        }
        if (pActionEvent.getSource() == this.fButtonCancel) {
            this.fWizardSpell = null;
        }
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
            case 76: {
                this.fWizardSpell = SpecialEffect.LIGHTNING;
                break;
            }
            case 70: {
                this.fWizardSpell = SpecialEffect.FIREBALL;
                break;
            }
            case 67: {
                this.fWizardSpell = null;
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

    public SpecialEffect getWizardSpell() {
        return this.fWizardSpell;
    }
}


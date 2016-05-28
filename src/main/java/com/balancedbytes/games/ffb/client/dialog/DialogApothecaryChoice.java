/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.SeriousInjury;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.Dialog;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.dialog.DialogApothecaryChoiceParameter;
import com.balancedbytes.games.ffb.dialog.DialogId;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class DialogApothecaryChoice
extends Dialog
implements ActionListener {
    private JButton fButtonInjuryOld;
    private JButton fButtonInjuryNew;
    private boolean fChoiceNewInjury;

    public DialogApothecaryChoice(FantasyFootballClient pClient, DialogApothecaryChoiceParameter pDialogParameter) {
        super(pClient, "Choose Apothecary Result", false);
        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BoxLayout(panelMain, 1));
        JPanel panelInfo1 = new JPanel();
        panelInfo1.setLayout(new BoxLayout(panelInfo1, 0));
        panelInfo1.add(new JLabel("The apothecary gives you a choice."));
        panelInfo1.add(Box.createHorizontalGlue());
        panelMain.add(panelInfo1);
        JPanel panelInfo2 = new JPanel();
        panelInfo2.setLayout(new BoxLayout(panelInfo2, 0));
        panelInfo2.add(new JLabel("Which injury result do you want to keep?"));
        panelInfo2.add(Box.createHorizontalGlue());
        panelMain.add(Box.createVerticalStrut(5));
        panelMain.add(panelInfo2);
        JPanel panelButtonOld = new JPanel();
        panelButtonOld.setLayout(new BoxLayout(panelButtonOld, 0));
        this.fButtonInjuryOld = pDialogParameter.getSeriousInjuryOld() != null ? new JButton(pDialogParameter.getSeriousInjuryOld().getButtonText()) : new JButton(pDialogParameter.getPlayerStateOld().getButtonText());
        this.fButtonInjuryOld.setMaximumSize(new Dimension(32767, 32767));
        this.fButtonInjuryOld.addActionListener(this);
        panelButtonOld.add(this.fButtonInjuryOld);
        panelMain.add(Box.createVerticalStrut(10));
        panelMain.add(panelButtonOld);
        JPanel panelButtonNew = new JPanel();
        panelButtonNew.setLayout(new BoxLayout(panelButtonNew, 0));
        this.fButtonInjuryNew = pDialogParameter.getSeriousInjuryNew() != null ? new JButton(pDialogParameter.getSeriousInjuryNew().getButtonText()) : new JButton(pDialogParameter.getPlayerStateNew().getButtonText());
        this.fButtonInjuryNew.setMaximumSize(new Dimension(32767, 32767));
        this.fButtonInjuryNew.addActionListener(this);
        panelButtonNew.add(this.fButtonInjuryNew);
        panelMain.add(Box.createVerticalStrut(5));
        panelMain.add(panelButtonNew);
        panelMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add((Component)panelMain, "Center");
        this.pack();
        this.setLocationToCenter();
    }

    @Override
    public DialogId getId() {
        return DialogId.APOTHECARY_CHOICE;
    }

    @Override
    public void actionPerformed(ActionEvent pActionEvent) {
        if (pActionEvent.getSource() == this.fButtonInjuryOld) {
            this.fChoiceNewInjury = false;
        }
        if (pActionEvent.getSource() == this.fButtonInjuryNew) {
            this.fChoiceNewInjury = true;
        }
        if (this.getCloseListener() != null) {
            this.getCloseListener().dialogClosed(this);
        }
    }

    public boolean isChoiceNewInjury() {
        return this.fChoiceNewInjury;
    }
}


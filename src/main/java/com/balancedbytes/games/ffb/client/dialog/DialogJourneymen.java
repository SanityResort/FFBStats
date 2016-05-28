/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.Dialog;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Roster;
import com.balancedbytes.games.ffb.model.RosterPosition;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.StringTool;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class DialogJourneymen
extends Dialog
implements ActionListener,
KeyListener {
    private int fSlotsAvailable;
    private String[] fPositionIds;
    private List<JComboBox> fBoxes;
    private int[] fSlotsSelected;
    private int fOldTeamValue;
    private int fNewTeamValue;
    private JLabel fLabelNewTeamValue;
    private JButton fButtonHire;

    public DialogJourneymen(FantasyFootballClient pClient, int pSlots, String[] pPositionIds) {
        super(pClient, "Hire Journeymen", false);
        this.fSlotsAvailable = pSlots;
        this.fPositionIds = pPositionIds;
        this.fSlotsSelected = new int[this.fPositionIds.length];
        this.fBoxes = new ArrayList<JComboBox>();
        for (int i = 0; i < this.fPositionIds.length; ++i) {
            this.fBoxes.add(new JComboBox());
        }
        this.refreshModels();
        Game game = this.getClient().getGame();
        this.fNewTeamValue = this.fOldTeamValue = game.getTeamHome().getTeamValue();
        Roster roster = game.getTeamHome().getRoster();
        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new GridLayout(0, 2, 5, 5));
        for (int i2 = 0; i2 < this.fBoxes.size(); ++i2) {
            RosterPosition rosterPosition = roster.getPositionById(this.fPositionIds[i2]);
            JPanel boxLabelPanel = new JPanel();
            boxLabelPanel.setLayout(new BoxLayout(boxLabelPanel, 0));
            boxLabelPanel.add(Box.createHorizontalGlue());
            boxLabelPanel.add(new JLabel(StringTool.isProvided(rosterPosition.getDisplayName()) ? rosterPosition.getDisplayName() : rosterPosition.getName()));
            boxPanel.add(boxLabelPanel);
            JPanel boxSelectPanel = new JPanel();
            boxSelectPanel.setLayout(new BoxLayout(boxSelectPanel, 0));
            boxSelectPanel.add(this.fBoxes.get(i2));
            boxSelectPanel.add(Box.createHorizontalGlue());
            boxPanel.add(boxSelectPanel);
        }
        StringBuilder oldTeamValueText = new StringBuilder();
        oldTeamValueText.append("Current Team Value is ").append(StringTool.formatThousands(this.fOldTeamValue / 1000)).append("k.");
        JLabel labelOldTeamValue = new JLabel(oldTeamValueText.toString());
        labelOldTeamValue.setFont(new Font(labelOldTeamValue.getFont().getName(), 1, labelOldTeamValue.getFont().getSize()));
        JPanel oldTeamValuePanel = new JPanel();
        oldTeamValuePanel.setLayout(new BoxLayout(oldTeamValuePanel, 0));
        oldTeamValuePanel.add(labelOldTeamValue);
        oldTeamValuePanel.add(Box.createHorizontalGlue());
        this.fLabelNewTeamValue = new JLabel();
        this.fLabelNewTeamValue.setFont(new Font(this.fLabelNewTeamValue.getFont().getName(), 1, this.fLabelNewTeamValue.getFont().getSize()));
        this.updateLabelNewTeamValue();
        JPanel newTeamValuePanel = new JPanel();
        newTeamValuePanel.setLayout(new BoxLayout(newTeamValuePanel, 0));
        newTeamValuePanel.add(this.fLabelNewTeamValue);
        newTeamValuePanel.add(Box.createHorizontalGlue());
        StringBuilder infoText = new StringBuilder();
        infoText.append("You may hire up to ").append(this.fSlotsAvailable).append(" Journeymen.");
        JLabel infoLabel = new JLabel(infoText.toString());
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, 0));
        infoPanel.add(infoLabel);
        infoPanel.add(Box.createHorizontalGlue());
        this.fButtonHire = new JButton("Hire");
        this.fButtonHire.addActionListener(this);
        this.fButtonHire.addKeyListener(this);
        this.fButtonHire.setMnemonic(72);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, 0));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(this.fButtonHire);
        buttonPanel.add(Box.createHorizontalGlue());
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, 1));
        centerPanel.add(infoPanel);
        centerPanel.add(Box.createVerticalStrut(5));
        centerPanel.add(oldTeamValuePanel);
        centerPanel.add(Box.createVerticalStrut(5));
        centerPanel.add(newTeamValuePanel);
        centerPanel.add(Box.createVerticalStrut(5));
        centerPanel.add(boxPanel);
        centerPanel.add(Box.createVerticalStrut(5));
        centerPanel.add(buttonPanel);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add((Component)centerPanel, "Center");
        this.pack();
        this.setLocationToCenter();
    }

    @Override
    public DialogId getId() {
        return DialogId.JOURNEYMEN;
    }

    public int[] getSlotsSelected() {
        return this.fSlotsSelected;
    }

    public String[] getPositionIds() {
        return this.fPositionIds;
    }

    private void refreshModels() {
        int i;
        int freeSlots = this.fSlotsAvailable;
        for (i = 0; i < this.fSlotsSelected.length; ++i) {
            freeSlots -= this.fSlotsSelected[i];
        }
        for (i = 0; i < this.fBoxes.size(); ++i) {
            String[] selection = new String[this.fSlotsSelected[i] + freeSlots + 1];
            for (int j = 0; j < selection.length; ++j) {
                selection[j] = Integer.toString(j);
            }
            JComboBox box = this.fBoxes.get(i);
            box.removeActionListener(this);
            box.setModel(new DefaultComboBoxModel<String>(selection));
            box.setSelectedIndex(this.fSlotsSelected[i]);
            box.setPreferredSize(box.getMinimumSize());
            box.addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent pActionEvent) {
        if (pActionEvent.getSource() == this.fButtonHire) {
            if (this.getCloseListener() != null) {
                this.getCloseListener().dialogClosed(this);
            }
        } else {
            for (int i = 0; i < this.fBoxes.size(); ++i) {
                JComboBox box = this.fBoxes.get(i);
                if (pActionEvent.getSource() != box) continue;
                this.fSlotsSelected[i] = box.getSelectedIndex();
                break;
            }
            this.refreshModels();
            this.fNewTeamValue = this.fOldTeamValue;
            Roster roster = this.getClient().getGame().getTeamHome().getRoster();
            for (int i2 = 0; i2 < this.fSlotsSelected.length; ++i2) {
                RosterPosition rosterPosition = roster.getPositionById(this.fPositionIds[i2]);
                this.fNewTeamValue += rosterPosition.getCost() * this.fSlotsSelected[i2];
            }
            this.updateLabelNewTeamValue();
        }
    }

    @Override
    public void keyPressed(KeyEvent pKeyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent pKeyEvent) {
        if (pKeyEvent.getKeyCode() == 72 && this.getCloseListener() != null) {
            this.getCloseListener().dialogClosed(this);
        }
    }

    @Override
    public void keyTyped(KeyEvent pKeyEvent) {
    }

    private void updateLabelNewTeamValue() {
        StringBuilder newTeamValueText = new StringBuilder();
        newTeamValueText.append("New Team Value is ").append(StringTool.formatThousands(this.fNewTeamValue / 1000)).append("k.");
        this.fLabelNewTeamValue.setText(newTeamValueText.toString());
    }
}


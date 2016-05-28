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
import com.balancedbytes.games.ffb.util.ArrayTool;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DialogTeamSetup
extends Dialog
implements ActionListener,
ListSelectionListener {
    public static final int CHOICE_LOAD = 1;
    public static final int CHOICE_SAVE = 2;
    public static final int CHOICE_CANCEL = 3;
    public static final int CHOICE_DELETE = 4;
    private JButton fButtonLoadSave;
    private JButton fButtonCancel;
    private JButton fButtonDelete;
    private JTextField fTextfieldSetupName;
    private boolean fLoadDialog;
    private JList fSetupList;
    private DefaultListModel fSetupListModel;
    private int fUserChoice;
    private String fSetupName;

    public DialogTeamSetup(FantasyFootballClient pClient, boolean pLoadDialog, String[] pSetups) {
        super(pClient, pLoadDialog ? "Load Team Setup" : "Save Team Setup", false);
        this.fLoadDialog = pLoadDialog;
        this.fButtonLoadSave = this.isLoadDialog() ? new JButton("Load") : new JButton("Save");
        this.fButtonLoadSave.addActionListener(this);
        this.fButtonCancel = new JButton("Cancel");
        this.fButtonCancel.addActionListener(this);
        BufferedImage deleteIcon = this.getClient().getUserInterface().getIconCache().getIconByProperty("game.delete");
        this.fButtonDelete = new JButton(new ImageIcon(deleteIcon));
        this.fButtonDelete.addActionListener(this);
        this.fSetupListModel = new DefaultListModel();
        if (ArrayTool.isProvided(pSetups)) {
            for (int i = 0; i < pSetups.length; ++i) {
                this.fSetupListModel.addElement(pSetups[i]);
            }
        }
        this.fSetupList = new JList(this.fSetupListModel);
        this.fSetupList.setSelectionMode(0);
        this.fSetupList.setVisibleRowCount(Math.min(7, Math.max(3, pSetups.length)));
        this.fSetupList.addListSelectionListener(this);
        JScrollPane setupListScroller = new JScrollPane(this.fSetupList);
        setupListScroller.setHorizontalScrollBarPolicy(31);
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());
        listPanel.add((Component)setupListScroller, "Center");
        listPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, 0));
        buttonPanel.add(this.fButtonLoadSave);
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(this.fButtonDelete);
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(this.fButtonCancel);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        this.fTextfieldSetupName = new JTextField(20);
        JPanel editPanel = new JPanel();
        editPanel.setLayout(new BoxLayout(editPanel, 0));
        editPanel.add(this.fTextfieldSetupName);
        editPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), 1));
        this.getContentPane().add(listPanel);
        if (!this.isLoadDialog()) {
            this.getContentPane().add(editPanel);
        }
        this.getContentPane().add(buttonPanel);
        this.pack();
        int height = Math.max(this.getSize().height, 100);
        int width = Math.max(this.getSize().width, 200);
        this.setSize(width, height);
        this.setLocationToCenter();
    }

    @Override
    public void actionPerformed(ActionEvent pActionEvent) {
        this.hideDialog();
        if (pActionEvent.getSource() == this.fButtonLoadSave) {
            if (this.isLoadDialog()) {
                this.fUserChoice = 1;
            } else {
                this.fUserChoice = 2;
                this.fSetupName = this.fTextfieldSetupName.getText();
            }
        }
        if (pActionEvent.getSource() == this.fButtonCancel) {
            this.fUserChoice = 3;
        }
        if (pActionEvent.getSource() == this.fButtonDelete) {
            this.fUserChoice = 4;
        }
        if (this.getCloseListener() != null) {
            this.getCloseListener().dialogClosed(this);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent pSelectionEvent) {
        if (!pSelectionEvent.getValueIsAdjusting()) {
            if (this.fSetupList.getSelectedIndex() == -1) {
                this.fButtonLoadSave.setEnabled(false);
            } else {
                this.fButtonLoadSave.setEnabled(true);
                this.fSetupName = (String)this.fSetupList.getSelectedValue();
                this.fTextfieldSetupName.setText(this.fSetupName);
            }
        }
    }

    @Override
    public DialogId getId() {
        return DialogId.TEAM_SETUP;
    }

    public int getUserChoice() {
        return this.fUserChoice;
    }

    public String getSetupName() {
        return this.fSetupName;
    }

    public boolean isLoadDialog() {
        return this.fLoadDialog;
    }
}


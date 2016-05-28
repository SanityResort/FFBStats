/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog.inducements;

import com.balancedbytes.games.ffb.Inducement;
import com.balancedbytes.games.ffb.InducementType;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.SkillFactory;
import com.balancedbytes.games.ffb.client.ClientData;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.Dialog;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.dialog.inducements.DropDownPanel;
import com.balancedbytes.games.ffb.client.dialog.inducements.MercenaryTable;
import com.balancedbytes.games.ffb.client.dialog.inducements.MercenaryTableModel;
import com.balancedbytes.games.ffb.client.dialog.inducements.StarPlayerTable;
import com.balancedbytes.games.ffb.client.dialog.inducements.StarPlayerTableModel;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.InducementSet;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Roster;
import com.balancedbytes.games.ffb.model.RosterPosition;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.StringTool;
import com.balancedbytes.games.ffb.util.UtilInducements;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class DialogBuyInducements
extends Dialog
implements ActionListener,
KeyListener {
    private Set<DropDownPanel> fPanels = new HashSet<DropDownPanel>();
    private int fAvailableGold = 0;
    private Roster fRoster = null;
    private int fStartGold = 0;
    private JPanel fGoldPanel = null;
    private JLabel fGoldLabelAmount = null;
    private JButton fButtonReset = null;
    private JButton fButtonOK = null;
    private String fTeamId = null;
    private JTable fTableStarPlayers;
    private StarPlayerTableModel fTableModelStarPlayers;
    private JTable fTableMercenaries;
    private MercenaryTableModel fTableModelMercenaries;
    private Team fTeam;

    public DialogBuyInducements(FantasyFootballClient pClient, String pTeamId, int pAvailableGold) {
        super(pClient, "Buy Inducements", true);
        this.fTeamId = pTeamId;
        if (pClient.getGame().getTeamHome().getId().equals(this.fTeamId)) {
            this.fRoster = pClient.getGame().getTeamHome().getRoster();
            this.fTeam = pClient.getGame().getTeamHome();
        } else {
            this.fRoster = pClient.getGame().getTeamAway().getRoster();
            this.fTeam = pClient.getGame().getTeamAway();
        }
        this.fStartGold = this.fAvailableGold = pAvailableGold;
        this.fGoldPanel = new JPanel();
        this.fGoldPanel.setLayout(new BoxLayout(this.fGoldPanel, 0));
        JLabel goldLabel = new JLabel("Available Gold:");
        goldLabel.setFont(new Font("Sans Serif", 1, 12));
        this.fGoldPanel.add(goldLabel);
        this.fGoldPanel.add(Box.createHorizontalStrut(10));
        this.fGoldLabelAmount = new JLabel(this.formatGold(this.fAvailableGold));
        this.fGoldLabelAmount.setFont(new Font("Sans Serif", 1, 12));
        this.fGoldPanel.add(this.fGoldLabelAmount);
        this.fGoldPanel.add(Box.createHorizontalGlue());
        this.fGoldPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        JPanel leftPanel = this.buildLeftPanel();
        JPanel rightPanel = this.buildRightPanel();
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, 0));
        centerPanel.add(leftPanel);
        centerPanel.add(Box.createHorizontalStrut(10));
        centerPanel.add(rightPanel);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.fButtonReset = new JButton("Reset");
        this.fButtonReset.addActionListener(this);
        this.fButtonReset.addKeyListener(this);
        this.fButtonReset.setMnemonic(82);
        this.fButtonOK = new JButton("Buy");
        this.fButtonOK.addActionListener(this);
        this.fButtonOK.addKeyListener(this);
        this.fButtonOK.setMnemonic(66);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, 0));
        buttonPanel.add(this.fButtonOK);
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(this.fButtonReset);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), 1));
        this.getContentPane().add(this.fGoldPanel);
        this.getContentPane().add(centerPanel);
        this.getContentPane().add(buttonPanel);
        this.pack();
        this.setLocationToCenter();
        Point p = this.getLocation();
        this.setLocation(p.x, 10);
    }

    public int getFreeSlotsInRoster() {
        int freeSlots = 16 - this.fTeam.getNrOfAvailablePlayers();
        freeSlots -= this.fTableModelStarPlayers.getCheckedRows();
        if ((freeSlots -= this.fTableModelMercenaries.getCheckedRows()) < 0) {
            freeSlots = 0;
        }
        return freeSlots;
    }

    private JPanel buildLeftPanel() {
        int verticalStrut = 10;
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, 1));
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, 0));
        labelPanel.add(new JLabel("Inducements:"));
        labelPanel.add(Box.createHorizontalGlue());
        leftPanel.add(labelPanel);
        leftPanel.add(Box.createVerticalStrut(10));
        this.createPanel(InducementType.BLOODWEISER_BABES, leftPanel, verticalStrut);
        this.createPanel(InducementType.BRIBES, leftPanel, verticalStrut);
        this.createPanel(InducementType.EXTRA_TEAM_TRAINING, leftPanel, verticalStrut);
        this.createPanel(InducementType.MASTER_CHEF, leftPanel, verticalStrut);
        this.createPanel(InducementType.IGOR, leftPanel, verticalStrut);
        this.createPanel(InducementType.WANDERING_APOTHECARIES, leftPanel, verticalStrut);
        this.createPanel(InducementType.WIZARD, leftPanel, 0);
        leftPanel.add(Box.createVerticalGlue());
        return leftPanel;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public InducementSet getSelectedInducements() {
        String[] mercenaryIds;
        InducementSet indSet = new InducementSet();
        for (DropDownPanel pan : this.fPanels) {
            if (pan.getSelectedAmount() <= 0) continue;
            indSet.addInducement(new Inducement(pan.getInducementType(), pan.getSelectedAmount()));
        }
        String[] starPlayerIds = this.getSelectedStarPlayerIds();
        if (starPlayerIds.length > 0) {
            indSet.addInducement(new Inducement(InducementType.STAR_PLAYERS, starPlayerIds.length));
        }
        if ((mercenaryIds = this.getSelectedMercenaryIds()).length > 0) {
            indSet.addInducement(new Inducement(InducementType.MERCENARIES, mercenaryIds.length));
        }
        return indSet;
    }

    public String[] getSelectedStarPlayerIds() {
        ArrayList<String> starPlayerPositionIds = new ArrayList<String>();
        for (int i = 0; i < this.fTableModelStarPlayers.getRowCount(); ++i) {
            if (!((Boolean)this.fTableModelStarPlayers.getValueAt(i, 0)).booleanValue()) continue;
            Player starPlayer = (Player)this.fTableModelStarPlayers.getValueAt(i, 4);
            starPlayerPositionIds.add(starPlayer.getPositionId());
        }
        return starPlayerPositionIds.toArray(new String[starPlayerPositionIds.size()]);
    }

    public String[] getSelectedMercenaryIds() {
        ArrayList<String> mercenaryPositionIds = new ArrayList<String>();
        for (int i = 0; i < this.fTableModelMercenaries.getRowCount(); ++i) {
            if (!((Boolean)this.fTableModelMercenaries.getValueAt(i, 0)).booleanValue()) continue;
            Player mercenary = (Player)this.fTableModelMercenaries.getValueAt(i, 5);
            mercenaryPositionIds.add(mercenary.getPositionId());
        }
        return mercenaryPositionIds.toArray(new String[mercenaryPositionIds.size()]);
    }

    public Skill[] getSelectedMercenarySkills() {
        ArrayList<Skill> mercenarySkills = new ArrayList<Skill>();
        for (int i = 0; i < this.fTableModelMercenaries.getRowCount(); ++i) {
            if (!((Boolean)this.fTableModelMercenaries.getValueAt(i, 0)).booleanValue()) continue;
            Skill mercenarySkill = new SkillFactory().forName((String)this.fTableModelMercenaries.getValueAt(i, 4));
            mercenarySkills.add(mercenarySkill);
        }
        return mercenarySkills.toArray(new Skill[mercenarySkills.size()]);
    }

    public int getAvailableGold() {
        return this.fAvailableGold;
    }

    public Team getTeam() {
        return this.fTeam;
    }

    public Roster getRoster() {
        return this.fRoster;
    }

    private JPanel buildRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, 1));
        this.fTableModelStarPlayers = new StarPlayerTableModel(this);
        this.fTableStarPlayers = new StarPlayerTable(this.fTableModelStarPlayers);
        this.fTableStarPlayers.setSelectionMode(0);
        this.fTableStarPlayers.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

            @Override
            public void valueChanged(ListSelectionEvent pE) {
                int selectedRowIndex;
                if (!pE.getValueIsAdjusting() && (selectedRowIndex = DialogBuyInducements.this.fTableStarPlayers.getSelectionModel().getLeadSelectionIndex()) >= 0) {
                    DialogBuyInducements.this.getClient().getClientData().setSelectedPlayer((Player)DialogBuyInducements.this.fTableModelStarPlayers.getValueAt(selectedRowIndex, 4));
                    DialogBuyInducements.this.getClient().getUserInterface().refreshSideBars();
                }
            }
        });
        DefaultTableCellRenderer rightAlignedRenderer = new DefaultTableCellRenderer();
        rightAlignedRenderer.setHorizontalAlignment(4);
        this.fTableStarPlayers.getColumnModel().getColumn(3).setCellRenderer(rightAlignedRenderer);
        this.fTableStarPlayers.getColumnModel().getColumn(0).setPreferredWidth(30);
        this.fTableStarPlayers.getColumnModel().getColumn(1).setPreferredWidth(50);
        this.fTableStarPlayers.getColumnModel().getColumn(2).setPreferredWidth(270);
        this.fTableStarPlayers.getColumnModel().getColumn(3).setPreferredWidth(100);
        this.fTableStarPlayers.setRowHeight(42);
        this.fTableStarPlayers.setPreferredScrollableViewportSize(new Dimension(350, 148));
        JScrollPane scrollPaneStarPlayer = new JScrollPane(this.fTableStarPlayers);
        JPanel starLabel = new JPanel();
        starLabel.setLayout(new BoxLayout(starLabel, 0));
        starLabel.add(new JLabel("Star Players (varying Gold 0-2):"));
        starLabel.add(Box.createHorizontalGlue());
        rightPanel.add(starLabel);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(scrollPaneStarPlayer);
        rightPanel.add(Box.createVerticalGlue());
        this.fTableModelMercenaries = new MercenaryTableModel(this);
        this.fTableMercenaries = new MercenaryTable(this.fTableModelMercenaries);
        this.fTableMercenaries.setSelectionMode(0);
        this.fTableMercenaries.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

            @Override
            public void valueChanged(ListSelectionEvent pE) {
                int selectedRowIndex;
                if (!pE.getValueIsAdjusting() && (selectedRowIndex = DialogBuyInducements.this.fTableMercenaries.getSelectionModel().getLeadSelectionIndex()) >= 0) {
                    DialogBuyInducements.this.getClient().getClientData().setSelectedPlayer((Player)DialogBuyInducements.this.fTableModelMercenaries.getValueAt(selectedRowIndex, 5));
                    DialogBuyInducements.this.getClient().getUserInterface().refreshSideBars();
                }
            }
        });
        DefaultTableCellRenderer mercAlignedRenderer = new DefaultTableCellRenderer();
        mercAlignedRenderer.setHorizontalAlignment(4);
        this.fTableMercenaries.getColumnModel().getColumn(3).setCellRenderer(mercAlignedRenderer);
        this.fTableMercenaries.getColumnModel().getColumn(0).setPreferredWidth(30);
        this.fTableMercenaries.getColumnModel().getColumn(1).setPreferredWidth(50);
        this.fTableMercenaries.getColumnModel().getColumn(2).setPreferredWidth(150);
        this.fTableMercenaries.getColumnModel().getColumn(3).setPreferredWidth(100);
        this.fTableMercenaries.getColumnModel().getColumn(4).setPreferredWidth(120);
        this.fTableMercenaries.setRowHeight(42);
        this.fTableMercenaries.setPreferredScrollableViewportSize(new Dimension(350, 148));
        JScrollPane scrollPaneMec = new JScrollPane(this.fTableMercenaries);
        JPanel mecLabel = new JPanel();
        mecLabel.setLayout(new BoxLayout(mecLabel, 0));
        mecLabel.add(new JLabel("Mercenaries (varying Gold):"));
        mecLabel.add(Box.createHorizontalGlue());
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(mecLabel);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(scrollPaneMec);
        rightPanel.add(Box.createVerticalGlue());
        return rightPanel;
    }

    @Override
    public DialogId getId() {
        return DialogId.BUY_INDUCEMENTS;
    }

    private void setGoldValue(int pValue) {
        this.fGoldLabelAmount.setText(this.formatGold(pValue));
    }

    public String formatGold(int pAmount) {
        StringBuilder buf = new StringBuilder(Integer.toString(pAmount)).reverse();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < buf.length(); ++i) {
            result.insert(0, buf.charAt(i));
            if ((i + 1) % 3 != 0 || i + 1 >= buf.length()) continue;
            result.insert(0, ",");
        }
        return result.toString();
    }

    private DropDownPanel createPanel(InducementType pInducementType, JPanel pAddToPanel, int pVertStrut) {
        int maxCount = UtilInducements.findInducementsAvailable(this.fRoster, pInducementType);
        int cost = UtilInducements.findInducementCost(this.fRoster, pInducementType);
        DropDownPanel panel = new DropDownPanel(pInducementType, maxCount, pInducementType.getDescription(), cost, this, this.fAvailableGold);
        pAddToPanel.add(panel);
        if (pVertStrut > 0) {
            pAddToPanel.add(Box.createVerticalStrut(pVertStrut));
        }
        this.fPanels.add(panel);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent pActionEvent) {
        if (pActionEvent.getSource() == this.fButtonOK) {
            if (this.getCloseListener() != null) {
                this.getCloseListener().dialogClosed(this);
            }
        } else if (pActionEvent.getSource() == this.fButtonReset) {
            this.resetPanels();
        } else {
            this.recalculateGold();
        }
    }

    public void recalculateGold() {
        int i;
        int cost = 0;
        for (DropDownPanel pan : this.fPanels) {
            cost += pan.getCurrentCost();
        }
        for (i = 0; i < this.fTableModelStarPlayers.getRowCount(); ++i) {
            if (!((Boolean)this.fTableModelStarPlayers.getValueAt(i, 0)).booleanValue()) continue;
            cost += ((Player)this.fTableModelStarPlayers.getValueAt(i, 4)).getPosition().getCost();
        }
        for (i = 0; i < this.fTableModelMercenaries.getRowCount(); ++i) {
            if (!((Boolean)this.fTableModelMercenaries.getValueAt(i, 0)).booleanValue()) continue;
            cost += ((Player)this.fTableModelMercenaries.getValueAt(i, 5)).getPosition().getCost();
            String skillSlot = (String)this.fTableModelMercenaries.getValueAt(i, 4);
            if (StringTool.isProvided(skillSlot)) {
                cost += 80000;
                continue;
            }
            cost += 30000;
        }
        this.fAvailableGold = this.fStartGold - cost;
        for (DropDownPanel pan1 : this.fPanels) {
            pan1.availableGoldChanged(this.fAvailableGold);
        }
        this.setGoldValue(this.fAvailableGold);
    }

    public void resetPanels() {
        int i;
        for (i = 0; i < this.fTableStarPlayers.getRowCount(); ++i) {
            this.fTableModelStarPlayers.setValueAt(false, i, 0);
        }
        for (i = 0; i < this.fTableModelMercenaries.getRowCount(); ++i) {
            this.fTableModelMercenaries.setValueAt(false, i, 0);
        }
        this.fAvailableGold = this.fStartGold;
        for (DropDownPanel pan : this.fPanels) {
            pan.reset(this.fStartGold);
        }
    }

    @Override
    public void keyPressed(KeyEvent pKeyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent pKeyEvent) {
        boolean keyHandled = false;
        switch (pKeyEvent.getKeyCode()) {
            case 82: {
                this.resetPanels();
                break;
            }
            case 79: {
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


/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.TeamList;
import com.balancedbytes.games.ffb.TeamListEntry;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.Dialog;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.util.UtilClientJTable;
import com.balancedbytes.games.ffb.client.util.UtilClientReflection;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.util.StringTool;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class DialogTeamChoice
extends Dialog {
    private TeamList fTeamList;
    private int fSelectedIndex;
    private JTable fTable;
    private JButton fButtonCancel;
    private JButton fButtonOk;

    public DialogTeamChoice(FantasyFootballClient pClient, TeamList pTeamList) {
        super(pClient, "Select Team", false);
        this.fTeamList = pTeamList;
        Object[] columnNames = new String[]{"Div", "Team Name", "Race", "TV", "Treasury"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, this.fTeamList.size()){

            @Override
            public Class<?> getColumnClass(int pColumnIndex) {
                Object o = this.getValueAt(0, pColumnIndex);
                if (o == null) {
                    return Object.class;
                }
                return o.getClass();
            }
        };
        TeamListEntry[] teamListEntries = this.fTeamList.getTeamListEntries();
        for (int i = 0; i < teamListEntries.length; ++i) {
            String division = teamListEntries[i].getDivision();
            if ("1".equals(division)) {
                division = "[R]";
            }
            if ("5".equals(division)) {
                division = "[L]";
            }
            if ("7".equals(division)) {
                division = "[A]";
            }
            if ("8".equals(division)) {
                division = "[U]";
            }
            if ("9".equals(division)) {
                division = "[F]";
            }
            if ("10".equals(division)) {
                division = "[B]";
            }
            tableModel.setValueAt(division, i, 0);
            tableModel.setValueAt(teamListEntries[i].getTeamName(), i, 1);
            tableModel.setValueAt(teamListEntries[i].getRace(), i, 2);
            tableModel.setValueAt(StringTool.formatThousands(teamListEntries[i].getTeamValue() / 1000) + "k", i, 3);
            tableModel.setValueAt(StringTool.formatThousands(teamListEntries[i].getTreasury() / 1000) + "k", i, 4);
        }
        this.fTable = new JTable(tableModel);
        UtilClientReflection.setFillsViewportHeight(this.fTable, true);
        UtilClientReflection.setAutoCreateRowSorter(this.fTable, true);
        this.fTable.setSelectionMode(0);
        this.fTable.setAutoResizeMode(0);
        this.fTable.getTableHeader().setReorderingAllowed(false);
        this.fTable.getColumnModel().getColumn(0).setCellRenderer(new MyTableCellRenderer(4));
        this.fTable.getColumnModel().getColumn(3).setCellRenderer(new MyTableCellRenderer(4));
        this.fTable.getColumnModel().getColumn(4).setCellRenderer(new MyTableCellRenderer(4));
        this.fTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                int viewRow = DialogTeamChoice.this.fTable.getSelectedRow();
                if (viewRow < 0) {
                    DialogTeamChoice.this.fButtonOk.setEnabled(false);
                } else {
                    DialogTeamChoice.this.fButtonOk.setEnabled(true);
                    DialogTeamChoice.this.fSelectedIndex = UtilClientReflection.convertRowIndexToModel(DialogTeamChoice.this.fTable, viewRow);
                }
            }
        });
        for (int column = 0; column < this.fTable.getColumnCount(); ++column) {
            UtilClientJTable.packTableColumn(this.fTable, column, 5);
        }
        int nrOfVisibleRows = Math.min(10, this.fTeamList.size());
        int height = (nrOfVisibleRows + 1) * this.fTable.getRowHeight();
        this.fTable.setPreferredScrollableViewportSize(new Dimension(this.fTable.getPreferredSize().width, height));
        KeyStroke enterKeyStroke = KeyStroke.getKeyStroke(10, 0);
        AbstractAction enterKeyAction = new AbstractAction(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                String actionCommand = ae.getActionCommand();
                if (actionCommand.equals("EnterKey")) {
                    DialogTeamChoice.this.checkAndCloseDialog(false);
                }
            }
        };
        InputMap inputMap = this.fTable.getInputMap(0);
        inputMap.remove(enterKeyStroke);
        this.fTable.setInputMap(0, inputMap);
        this.fTable.unregisterKeyboardAction(enterKeyStroke);
        this.fTable.registerKeyboardAction(enterKeyAction, "EnterKey", enterKeyStroke, 0);
        JScrollPane scrollPane = nrOfVisibleRows < this.fTeamList.size() ? new JScrollPane(22, 31) : new JScrollPane(21, 31);
        scrollPane.setViewportView(this.fTable);
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(1, 1));
        inputPanel.add(scrollPane);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        this.fButtonCancel = new JButton("Cancel");
        this.fButtonCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent pActionEvent) {
                DialogTeamChoice.this.fSelectedIndex = -1;
                DialogTeamChoice.this.checkAndCloseDialog(true);
            }
        });
        this.fButtonOk = new JButton("Play");
        this.fButtonOk.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent pActionEvent) {
                DialogTeamChoice.this.checkAndCloseDialog(false);
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(1));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 3, 3, 3));
        buttonPanel.add(this.fButtonOk);
        buttonPanel.add(this.fButtonCancel);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add((Component)inputPanel, "Center");
        this.getContentPane().add((Component)buttonPanel, "South");
        this.pack();
        this.setLocationToCenter();
    }

    @Override
    public void showDialog(IDialogCloseListener pCloseListener) {
        this.fTable.getSelectionModel().setSelectionInterval(0, 0);
        super.showDialog(pCloseListener);
    }

    private void checkAndCloseDialog(boolean pCancelSelected) {
        if ((pCancelSelected || this.fSelectedIndex >= 0) && this.getCloseListener() != null) {
            this.getCloseListener().dialogClosed(this);
        }
    }

    @Override
    public DialogId getId() {
        return DialogId.TEAM_CHOICE;
    }

    public TeamList getTeamList() {
        return this.fTeamList;
    }

    public TeamListEntry getSelectedTeamEntry() {
        if (this.fSelectedIndex >= 0) {
            return this.fTeamList.getTeamListEntries()[this.fSelectedIndex];
        }
        return null;
    }

    private class MyTableCellRenderer
    extends DefaultTableCellRenderer {
        public MyTableCellRenderer(int pHorizontalAlignment) {
            this.setHorizontalAlignment(pHorizontalAlignment);
        }

        @Override
        public Component getTableCellRendererComponent(JTable pTable, Object pValue, boolean pIsSelected, boolean pHasFocus, int pRow, int pColumn) {
            return super.getTableCellRendererComponent(pTable, pValue, pIsSelected, false, pRow, pColumn);
        }
    }

}


/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.GameList;
import com.balancedbytes.games.ffb.GameListEntry;
import com.balancedbytes.games.ffb.client.ClientParameters;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.Dialog;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.util.UtilClientJTable;
import com.balancedbytes.games.ffb.client.util.UtilClientReflection;
import com.balancedbytes.games.ffb.dialog.DialogId;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class DialogGameChoice
extends Dialog {
    private static final DateFormat _TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
    private GameListEntry[] fGameListEntries;
    private int fSelectedIndex;
    private JTable fTable;
    private JButton fButtonCancel;
    private JButton fButtonOk;

    public DialogGameChoice(FantasyFootballClient pClient, GameList pGameList) {
        super(pClient, "Select Game", false);
        this.fGameListEntries = pGameList.getEntriesSorted();
        Object[] columnNames = null;
        columnNames = this.getClient().getParameters().getMode() == ClientMode.PLAYER ? new String[]{"My Team", "Opposing Team", "Opponent", "Started"} : new String[]{"Home Team", "Home Coach", "Away Team", "Away Coach", "Started"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, this.fGameListEntries.length){

            @Override
            public Class<?> getColumnClass(int pColumnIndex) {
                Object o = this.getValueAt(0, pColumnIndex);
                if (o == null) {
                    return Object.class;
                }
                return o.getClass();
            }
        };
        for (int i = 0; i < this.fGameListEntries.length; ++i) {
            String startedTimestamp;
            String string = startedTimestamp = this.fGameListEntries[i].getStarted() != null ? _TIMESTAMP_FORMAT.format(this.fGameListEntries[i].getStarted()) : "scheduled";
            if (ClientMode.PLAYER == this.getClient().getMode()) {
                if (this.getClient().getParameters().getCoach().equals(this.fGameListEntries[i].getTeamHomeCoach())) {
                    tableModel.setValueAt(this.fGameListEntries[i].getTeamHomeName(), i, 0);
                    tableModel.setValueAt(this.fGameListEntries[i].getTeamAwayName(), i, 1);
                    tableModel.setValueAt(this.fGameListEntries[i].getTeamAwayCoach(), i, 2);
                } else {
                    tableModel.setValueAt(this.fGameListEntries[i].getTeamAwayName(), i, 0);
                    tableModel.setValueAt(this.fGameListEntries[i].getTeamHomeName(), i, 1);
                    tableModel.setValueAt(this.fGameListEntries[i].getTeamHomeCoach(), i, 2);
                }
                tableModel.setValueAt(startedTimestamp, i, 3);
                continue;
            }
            tableModel.setValueAt(this.fGameListEntries[i].getTeamHomeName(), i, 0);
            tableModel.setValueAt(this.fGameListEntries[i].getTeamHomeCoach(), i, 1);
            tableModel.setValueAt(this.fGameListEntries[i].getTeamAwayName(), i, 2);
            tableModel.setValueAt(this.fGameListEntries[i].getTeamAwayCoach(), i, 3);
            tableModel.setValueAt(startedTimestamp, i, 4);
        }
        this.fTable = new JTable(tableModel);
        UtilClientReflection.setFillsViewportHeight(this.fTable, true);
        UtilClientReflection.setAutoCreateRowSorter(this.fTable, true);
        this.fTable.setSelectionMode(0);
        this.fTable.setAutoResizeMode(0);
        this.fTable.getTableHeader().setReorderingAllowed(false);
        this.fTable.setDefaultRenderer(Object.class, new MyTableCellRenderer(2));
        this.fTable.setDefaultRenderer(Integer.class, new MyTableCellRenderer(4));
        this.fTable.getColumnModel().getColumn(0).setCellRenderer(new MyTableCellRenderer(4));
        this.fTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                int viewRow = DialogGameChoice.this.fTable.getSelectedRow();
                if (viewRow < 0) {
                    DialogGameChoice.this.fButtonOk.setEnabled(false);
                } else {
                    DialogGameChoice.this.fButtonOk.setEnabled(true);
                    DialogGameChoice.this.fSelectedIndex = UtilClientReflection.convertRowIndexToModel(DialogGameChoice.this.fTable, viewRow);
                }
            }
        });
        for (int column = 0; column < this.fTable.getColumnCount(); ++column) {
            UtilClientJTable.packTableColumn(this.fTable, column, 5);
        }
        int nrOfVisibleRows = Math.min(10, this.fGameListEntries.length);
        int height = (nrOfVisibleRows + 1) * this.fTable.getRowHeight();
        this.fTable.setPreferredScrollableViewportSize(new Dimension(this.fTable.getPreferredSize().width, height));
        KeyStroke enterKeyStroke = KeyStroke.getKeyStroke(10, 0);
        AbstractAction enterKeyAction = new AbstractAction(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                String actionCommand = ae.getActionCommand();
                if (actionCommand.equals("EnterKey")) {
                    DialogGameChoice.this.checkAndCloseDialog(false);
                }
            }
        };
        InputMap inputMap = this.fTable.getInputMap(0);
        inputMap.remove(enterKeyStroke);
        this.fTable.setInputMap(0, inputMap);
        this.fTable.unregisterKeyboardAction(enterKeyStroke);
        this.fTable.registerKeyboardAction(enterKeyAction, "EnterKey", enterKeyStroke, 0);
        JScrollPane scrollPane = nrOfVisibleRows < this.fGameListEntries.length ? new JScrollPane(22, 31) : new JScrollPane(21, 31);
        scrollPane.setViewportView(this.fTable);
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(1, 1));
        inputPanel.add(scrollPane);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        this.fButtonCancel = new JButton("Cancel");
        this.fButtonCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent pActionEvent) {
                DialogGameChoice.this.fSelectedIndex = -1;
                DialogGameChoice.this.checkAndCloseDialog(true);
            }
        });
        this.fButtonOk = ClientMode.SPECTATOR == pClient.getMode() ? new JButton("Spectate") : new JButton("Play");
        this.fButtonOk.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent pActionEvent) {
                DialogGameChoice.this.checkAndCloseDialog(false);
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
        return DialogId.GAME_CHOICE;
    }

    public GameListEntry[] getGameListEntires() {
        return this.fGameListEntries;
    }

    public GameListEntry getSelectedGameEntry() {
        if (this.fSelectedIndex >= 0) {
            return this.fGameListEntries[this.fSelectedIndex];
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


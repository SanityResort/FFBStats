/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog.inducements;

import com.balancedbytes.games.ffb.PlayerType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.PlayerIconFactory;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.inducements.DialogBuyInducements;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Roster;
import com.balancedbytes.games.ffb.model.RosterPosition;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.StringTool;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

public class MercenaryTableModel
extends AbstractTableModel {
    private String[] fColumnNames;
    private Object[][] fRowData;
    private DialogBuyInducements fDialog;

    public MercenaryTableModel(DialogBuyInducements pDialog) {
        this.fDialog = pDialog;
        this.fColumnNames = new String[]{"", "Icon", "Name", "Gold", "Skill"};
        this.fRowData = this.buildRowData();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public int getRowCount() {
        return this.fRowData.length;
    }

    @Override
    public Class<?> getColumnClass(int pColumnIndex) {
        return this.getValueAt(0, pColumnIndex).getClass();
    }

    @Override
    public String getColumnName(int pColumnIndex) {
        return this.fColumnNames[pColumnIndex];
    }

    @Override
    public Object getValueAt(int pRowIndex, int pColumnIndex) {
        return this.fRowData[pRowIndex][pColumnIndex];
    }

    @Override
    public boolean isCellEditable(int pRowIndex, int pColumnIndex) {
        return pColumnIndex == 0 || pColumnIndex == 4;
    }

    public int getCheckedRows() {
        int noBoughtStarPlayers = 0;
        for (int i = 0; i < this.getRowCount(); ++i) {
            if (!((Boolean)this.getValueAt(i, 0)).booleanValue()) continue;
            ++noBoughtStarPlayers;
        }
        return noBoughtStarPlayers;
    }

    @Override
    public void setValueAt(Object pValue, int pRowIndex, int pColumnIndex) {
        int skillCost;
        Player player = (Player)this.fRowData[pRowIndex][5];
        int playerCost = player.getPosition().getCost() + 30000;
        if (pColumnIndex == 0) {
            int n = skillCost = StringTool.isProvided(this.fRowData[pRowIndex][4]) ? 50000 : 0;
            if (((Boolean)pValue).booleanValue()) {
                if (playerCost + skillCost <= this.fDialog.getAvailableGold() && this.fDialog.getFreeSlotsInRoster() > 0) {
                    this.fRowData[pRowIndex][pColumnIndex] = pValue;
                    this.fireTableCellUpdated(pRowIndex, pColumnIndex);
                }
            } else {
                this.fRowData[pRowIndex][pColumnIndex] = pValue;
                this.fireTableCellUpdated(pRowIndex, pColumnIndex);
            }
            this.fDialog.recalculateGold();
        }
        if (pColumnIndex == 4) {
            this.fRowData[pRowIndex][pColumnIndex] = pValue;
            this.fireTableCellUpdated(pRowIndex, pColumnIndex);
            skillCost = StringTool.isProvided(this.fRowData[pRowIndex][4]) ? 50000 : 0;
            this.setValueAt(this.fDialog.formatGold(playerCost + skillCost), pRowIndex, 3);
            if (((Boolean)this.fRowData[pRowIndex][0]).booleanValue() && skillCost > this.fDialog.getAvailableGold()) {
                this.fRowData[pRowIndex][0] = false;
                this.fireTableCellUpdated(pRowIndex, 0);
            }
            this.fDialog.recalculateGold();
        }
        if (pColumnIndex == 3) {
            this.fRowData[pRowIndex][pColumnIndex] = pValue;
        }
    }

    private Object[][] buildRowData() {
        PlayerIconFactory playerIconFactory = this.fDialog.getClient().getUserInterface().getPlayerIconFactory();
        ArrayList<Object[]> mercenaryList = new ArrayList<Object[]>();
        for (RosterPosition pos : this.fDialog.getRoster().getPositions()) {
            if (PlayerType.STAR == pos.getType()) continue;
            int playerInPosition = this.fDialog.getTeam().getNrOfAvailablePlayersInPosition(pos);
            for (int i = 0; i < pos.getQuantity() - playerInPosition; ++i) {
                Player player = new Player();
                player.updatePosition(pos);
                player.setName(pos.getName());
                Object[] mecenary = new Object[]{new Boolean(false), new ImageIcon(playerIconFactory.getBasicIcon(this.fDialog.getClient(), player, true, false, false, false)), pos.getName(), this.fDialog.formatGold(pos.getCost() + 30000), "", player};
                mercenaryList.add(mecenary);
            }
        }
        Object[][] mercenaries = (Object[][])mercenaryList.toArray(new Object[mercenaryList.size()][]);
        Arrays.sort(mercenaries, new Comparator<Object[]>(){

            @Override
            public int compare(Object[] o1, Object[] o2) {
                RosterPosition position1 = ((Player)o1[5]).getPosition();
                RosterPosition position2 = ((Player)o2[5]).getPosition();
                return position1.getCost() - position2.getCost();
            }
        });
        return mercenaries;
    }

}


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
import com.balancedbytes.games.ffb.util.StringTool;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

public class StarPlayerTableModel
extends AbstractTableModel {
    private String[] fColumnNames;
    private Object[][] fRowData;
    private int fNrOfCheckedRows;
    private int fMaxNrOfStars;
    private DialogBuyInducements fDialog;

    public StarPlayerTableModel(DialogBuyInducements pDialog) {
        this.fDialog = pDialog;
        this.fColumnNames = new String[]{"", "Icon", "Name", "Gold"};
        this.fRowData = this.buildRowData();
        this.setMaxNrOfStars(2);
    }

    @Override
    public int getColumnCount() {
        return 4;
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
        return pColumnIndex == 0;
    }

    public int getCheckedRows() {
        int noBoughtStarPlayers = 0;
        for (int i = 0; i < this.getRowCount(); ++i) {
            if (!((Boolean)this.getValueAt(i, 0)).booleanValue()) continue;
            ++noBoughtStarPlayers;
        }
        return noBoughtStarPlayers;
    }

    public void setMaxNrOfStars(int amount) {
        this.fMaxNrOfStars = amount;
    }

    @Override
    public void setValueAt(Object pValue, int pRowIndex, int pColumnIndex) {
        if (pColumnIndex == 0) {
            Player player = (Player)this.fRowData[pRowIndex][4];
            int cost = player.getPosition().getCost();
            String teamWithPositionId = player.getPosition().getTeamWithPositionId();
            if (StringTool.isProvided(teamWithPositionId)) {
                int partnerRowId = -1;
                for (int i = 0; i < this.getRowCount(); ++i) {
                    Player rowPlayer = (Player)this.fRowData[i][4];
                    if (!teamWithPositionId.equals(rowPlayer.getPositionId())) continue;
                    partnerRowId = i;
                    break;
                }
                if (partnerRowId >= 0) {
                    if (((Boolean)pValue).booleanValue()) {
                        if ((cost += ((Player)this.fRowData[partnerRowId][4]).getPosition().getCost()) <= this.fDialog.getAvailableGold() && this.fNrOfCheckedRows < this.fMaxNrOfStars && this.fDialog.getFreeSlotsInRoster() > 1) {
                            this.fRowData[pRowIndex][pColumnIndex] = true;
                            this.fireTableCellUpdated(pRowIndex, pColumnIndex);
                            this.fRowData[partnerRowId][pColumnIndex] = true;
                            this.fireTableCellUpdated(partnerRowId, pColumnIndex);
                            this.setMaxNrOfStars(this.fMaxNrOfStars + 1);
                            this.fDialog.recalculateGold();
                            this.fNrOfCheckedRows = this.getCheckedRows();
                        }
                    } else {
                        this.fRowData[pRowIndex][pColumnIndex] = false;
                        this.fireTableCellUpdated(pRowIndex, pColumnIndex);
                        this.fRowData[partnerRowId][pColumnIndex] = false;
                        this.fireTableCellUpdated(partnerRowId, pColumnIndex);
                        this.setMaxNrOfStars(this.fMaxNrOfStars - 1);
                        this.fDialog.recalculateGold();
                        this.fNrOfCheckedRows = this.getCheckedRows();
                    }
                }
            } else if (((Boolean)pValue).booleanValue()) {
                if (cost <= this.fDialog.getAvailableGold() && this.fNrOfCheckedRows < this.fMaxNrOfStars && this.fDialog.getFreeSlotsInRoster() > 0) {
                    this.fRowData[pRowIndex][pColumnIndex] = pValue;
                    this.fireTableCellUpdated(pRowIndex, pColumnIndex);
                    this.fDialog.recalculateGold();
                    this.fNrOfCheckedRows = this.getCheckedRows();
                }
            } else {
                this.fRowData[pRowIndex][pColumnIndex] = pValue;
                this.fireTableCellUpdated(pRowIndex, pColumnIndex);
                this.fDialog.recalculateGold();
                this.fNrOfCheckedRows = this.getCheckedRows();
            }
        }
    }

    private Object[][] buildRowData() {
        PlayerIconFactory playerIconFactory = this.fDialog.getClient().getUserInterface().getPlayerIconFactory();
        ArrayList<Object[]> starPlayerList = new ArrayList<Object[]>();
        for (RosterPosition pos : this.fDialog.getRoster().getPositions()) {
            if (PlayerType.STAR != pos.getType()) continue;
            Player player = new Player();
            player.updatePosition(pos);
            player.setName(pos.getName());
            Object[] star = new Object[]{new Boolean(false), new ImageIcon(playerIconFactory.getBasicIcon(this.fDialog.getClient(), player, true, false, false, false)), pos.getName(), this.fDialog.formatGold(pos.getCost()), player};
            starPlayerList.add(star);
        }
        Object[][] starPlayers = (Object[][])starPlayerList.toArray(new Object[starPlayerList.size()][]);
        Arrays.sort(starPlayers, new Comparator<Object[]>(){

            @Override
            public int compare(Object[] o1, Object[] o2) {
                RosterPosition position1 = ((Player)o1[4]).getPosition();
                RosterPosition position2 = ((Player)o2[4]).getPosition();
                return position1.getCost() - position2.getCost();
            }
        });
        return starPlayers;
    }

}


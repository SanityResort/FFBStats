/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.PlayerIconFactory;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.PlayerCheckListItem;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.ArrayTool;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

public class PlayerCheckList
extends JList {
    public PlayerCheckList(FantasyFootballClient pClient, String[] pPlayerIds, String[] pDescriptions, int pMaxSelects) {
        if (!ArrayTool.isProvided(pPlayerIds)) {
            throw new IllegalArgumentException("Argument players must not be empty or null.");
        }
        Game game = pClient.getGame();
        ArrayList<PlayerCheckListItem> checkListItems = new ArrayList<PlayerCheckListItem>();
        PlayerIconFactory playerIconFactory = pClient.getUserInterface().getPlayerIconFactory();
        for (int i = 0; i < pPlayerIds.length; ++i) {
            Player player = game.getPlayerById(pPlayerIds[i]);
            if (player == null) continue;
            boolean homePlayer = game.getTeamHome().hasPlayer(player);
            BufferedImage playerIcon = playerIconFactory.getBasicIcon(pClient, player, homePlayer, false, false, false);
            StringBuilder text = new StringBuilder();
            text.append(player.getName());
            if (ArrayTool.isProvided(pDescriptions)) {
                text.append(" ").append(pDescriptions[i]);
            }
            PlayerCheckListItem checkListItem = new PlayerCheckListItem(player, new ImageIcon(playerIcon), text.toString());
            checkListItem.setSelected(pPlayerIds.length == 1);
            checkListItems.add(checkListItem);
        }
        this.setListData(checkListItems.toArray(new PlayerCheckListItem[checkListItems.size()]));
        this.setCellRenderer(new PlayerCheckListRenderer());
        this.setSelectionMode(0);
        this.addMouseListener(new PlayerCheckListMouseAdapter(pMaxSelects));
    }

    public Player getPlayerAtIndex(int pIndex) {
        PlayerCheckListItem checkListItem = (PlayerCheckListItem)this.getModel().getElementAt(pIndex);
        if (checkListItem != null) {
            return checkListItem.getPlayer();
        }
        return null;
    }

    public Player[] getSelectedPlayers() {
        ArrayList<Player> selectedPlayers = new ArrayList<Player>();
        for (int i = 0; i < this.getModel().getSize(); ++i) {
            PlayerCheckListItem item = (PlayerCheckListItem)this.getModel().getElementAt(i);
            if (!item.isSelected()) continue;
            selectedPlayers.add(item.getPlayer());
        }
        return selectedPlayers.toArray(new Player[selectedPlayers.size()]);
    }

    private class PlayerCheckListMouseAdapter
    extends MouseAdapter {
        private int fMaxSelects;

        public PlayerCheckListMouseAdapter(int pMaxSelects) {
            this.fMaxSelects = pMaxSelects;
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            JList list = (JList)event.getSource();
            int index = list.locationToIndex(event.getPoint());
            PlayerCheckListItem selectedItem = (PlayerCheckListItem)list.getModel().getElementAt(index);
            if (!selectedItem.isSelected()) {
                if (this.fMaxSelects > 1) {
                    int nrOfSelectedItems = 0;
                    for (int i = 0; i < list.getModel().getSize(); ++i) {
                        PlayerCheckListItem item = (PlayerCheckListItem)list.getModel().getElementAt(i);
                        if (!item.isSelected()) continue;
                        ++nrOfSelectedItems;
                    }
                    if (nrOfSelectedItems < this.fMaxSelects) {
                        selectedItem.setSelected(true);
                    }
                } else {
                    for (int i = 0; i < list.getModel().getSize(); ++i) {
                        PlayerCheckListItem item = (PlayerCheckListItem)list.getModel().getElementAt(i);
                        if (!item.isSelected()) continue;
                        item.setSelected(false);
                        list.repaint(list.getCellBounds(i, i));
                    }
                    selectedItem.setSelected(true);
                }
            } else {
                selectedItem.setSelected(false);
            }
            list.repaint(list.getCellBounds(index, index));
        }
    }

    private class PlayerCheckListRenderer
    extends JPanel
    implements ListCellRenderer {
        private JCheckBox fCheckBox;
        private JLabel fLabel;

        public PlayerCheckListRenderer() {
            this.setLayout(new BoxLayout(this, 0));
            this.fCheckBox = new JCheckBox();
            this.add(this.fCheckBox);
            this.fLabel = new JLabel();
            this.add(this.fLabel);
        }

        public Component getListCellRendererComponent(JList pList, Object pValue, int pIndex, boolean pIsSelected, boolean pCellHasFocus) {
            this.setEnabled(pList.isEnabled());
            this.setFont(pList.getFont());
            this.setBackground(pList.getBackground());
            this.setForeground(pList.getForeground());
            this.fCheckBox.setBackground(pList.getBackground());
            PlayerCheckListItem listItem = (PlayerCheckListItem)pValue;
            this.fCheckBox.setSelected(listItem.isSelected());
            this.fLabel.setIcon(listItem.getIcon());
            this.fLabel.setText(listItem.getText());
            return this;
        }
    }

}


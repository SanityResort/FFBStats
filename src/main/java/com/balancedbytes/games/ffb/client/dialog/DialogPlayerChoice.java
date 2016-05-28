/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.client.ClientData;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.Dialog;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.dialog.PlayerCheckList;
import com.balancedbytes.games.ffb.client.state.ClientState;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

public class DialogPlayerChoice
extends Dialog
implements ActionListener {
    private PlayerCheckList fList;
    private JButton fButtonSelect = new JButton("Select");
    private JButton fButtonCancel;
    private Player[] fSelectedPlayers;

    public DialogPlayerChoice(FantasyFootballClient pClient, String pHeader, String[] pPlayerIds, String[] pDescriptions, int pMaxSelects, FieldCoordinate pPlayerCoordinate) {
        super(pClient, "Player Choice", false);
        this.fButtonSelect.setToolTipText("Select the checked player(s)");
        this.fButtonSelect.addActionListener(this);
        this.fButtonSelect.setMnemonic(83);
        this.fButtonCancel = new JButton("Cancel");
        this.fButtonCancel.setToolTipText("Do not select any player");
        this.fButtonCancel.addActionListener(this);
        this.fButtonCancel.setMnemonic(67);
        this.fList = new PlayerCheckList(pClient, pPlayerIds, pDescriptions, pMaxSelects);
        this.fList.setVisibleRowCount(Math.min(pPlayerIds.length, 5));
        this.fList.addMouseMotionListener(new MouseMotionAdapter(){

            @Override
            public void mouseMoved(MouseEvent pMouseEvent) {
                int index = DialogPlayerChoice.this.fList.locationToIndex(pMouseEvent.getPoint());
                Player player = DialogPlayerChoice.this.fList.getPlayerAtIndex(index);
                if (player != null) {
                    FieldCoordinate playerCoordinate = DialogPlayerChoice.this.getClient().getGame().getFieldModel().getPlayerCoordinate(player);
                    DialogPlayerChoice.this.getClient().getClientState().hideSelectSquare();
                    DialogPlayerChoice.this.getClient().getClientState().showSelectSquare(playerCoordinate);
                    if (player != DialogPlayerChoice.this.getClient().getClientData().getSelectedPlayer()) {
                        DialogPlayerChoice.this.getClient().getClientData().setSelectedPlayer(player);
                        DialogPlayerChoice.this.getClient().getUserInterface().refreshSideBars();
                    }
                }
            }
        });
        JScrollPane listScroller = new JScrollPane(this.fList);
        listScroller.setAlignmentX(0.0f);
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, 0));
        JLabel headerLabel = new JLabel(pHeader);
        headerLabel.setFont(new Font(headerLabel.getFont().getName(), 1, headerLabel.getFont().getSize()));
        headerPanel.add(headerLabel);
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, 0));
        listPanel.add(listScroller);
        listPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, 0));
        buttonPanel.add(this.fButtonSelect);
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(this.fButtonCancel);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        JPanel centerPane = new JPanel();
        centerPane.setLayout(new BoxLayout(centerPane, 1));
        centerPane.add(headerPanel);
        centerPane.add(listPanel);
        centerPane.add(buttonPanel);
        this.getContentPane().add((Component)centerPane, "Center");
        this.pack();
        if (pPlayerCoordinate != null) {
            int x = 116 + (pPlayerCoordinate.getX() + 1) * 30;
            int y = (pPlayerCoordinate.getY() + 1) * 30;
            this.setLocation(x, y);
        } else {
            this.setLocationToCenter();
        }
        this.addMouseListener(this);
        this.fList.setSelectedIndex(0);
    }

    @Override
    public DialogId getId() {
        return DialogId.PLAYER_CHOICE;
    }

    @Override
    public void actionPerformed(ActionEvent pActionEvent) {
        if (pActionEvent.getSource() == this.fButtonCancel) {
            this.fSelectedPlayers = new Player[0];
            this.closeDialog();
        }
        if (pActionEvent.getSource() == this.fButtonSelect) {
            this.fSelectedPlayers = this.fList.getSelectedPlayers();
            if (this.fSelectedPlayers.length > 0) {
                this.closeDialog();
            }
        }
    }

    private void closeDialog() {
        if (this.getCloseListener() != null) {
            this.getCloseListener().dialogClosed(this);
        }
    }

    public Player[] getSelectedPlayers() {
        return this.fSelectedPlayers;
    }

}


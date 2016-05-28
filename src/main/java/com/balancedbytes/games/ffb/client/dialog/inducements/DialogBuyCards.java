/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog.inducements;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.CardType;
import com.balancedbytes.games.ffb.InducementDuration;
import com.balancedbytes.games.ffb.InducementPhase;
import com.balancedbytes.games.ffb.InducementPhaseFactory;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.ParagraphStyle;
import com.balancedbytes.games.ffb.client.TextStyle;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.Dialog;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.ui.ChatLogScrollPane;
import com.balancedbytes.games.ffb.client.ui.ChatLogTextPane;
import com.balancedbytes.games.ffb.client.ui.GameMenuBar;
import com.balancedbytes.games.ffb.dialog.DialogBuyCardsParameter;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.InducementSet;
import com.balancedbytes.games.ffb.model.TurnData;
import com.balancedbytes.games.ffb.util.StringTool;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DialogBuyCards
extends Dialog
implements ActionListener,
KeyListener,
ListSelectionListener {
    private Map<CardType, Integer> fNrOfCardsPerType;
    private Map<CardType, JButton> fButtonPerType;
    private int fAvailableGold;
    private JLabel fLabelAvailableGold;
    private int fAvailableCards;
    private JLabel fLabelAvailableCards;
    private JButton fButtonMagicItem;
    private JButton fButtonDirtyTrick;
    private ChatLogScrollPane fCardLogScrollPane;
    private ChatLogTextPane fCardLogTextPane;
    private JButton fButtonContinue;

    public DialogBuyCards(FantasyFootballClient pClient, DialogBuyCardsParameter pParameter) {
        super(pClient, "Buy Cards", false);
        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BoxLayout(panelMain, 1));
        panelMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.fAvailableGold = pParameter.getAvailableGold();
        this.fLabelAvailableGold = new JLabel();
        this.updateAvailableGoldLabel();
        JPanel panelGold = new JPanel();
        panelGold.setLayout(new BoxLayout(panelGold, 0));
        panelGold.add(this.fLabelAvailableGold);
        panelGold.add(Box.createHorizontalGlue());
        panelMain.add(panelGold);
        panelMain.add(Box.createVerticalStrut(5));
        this.fAvailableCards = pParameter.getAvailableCards();
        this.fLabelAvailableCards = new JLabel();
        this.updateAvailableCardsLabel();
        JPanel panelCards = new JPanel();
        panelCards.setLayout(new BoxLayout(panelCards, 0));
        panelCards.add(this.fLabelAvailableCards);
        panelCards.add(Box.createHorizontalGlue());
        panelMain.add(panelCards);
        panelMain.add(Box.createVerticalStrut(10));
        this.fNrOfCardsPerType = new HashMap<CardType, Integer>();
        this.fButtonPerType = new HashMap<CardType, JButton>();
        this.fButtonMagicItem = new JButton();
        this.fButtonMagicItem.addActionListener(this);
        this.fButtonPerType.put(CardType.MAGIC_ITEM, this.fButtonMagicItem);
        this.fNrOfCardsPerType.put(CardType.MAGIC_ITEM, pParameter.getNrOfCards(CardType.MAGIC_ITEM));
        panelMain.add(this.createDeckPanel(CardType.MAGIC_ITEM));
        panelMain.add(Box.createVerticalStrut(5));
        this.fButtonDirtyTrick = new JButton();
        this.fButtonDirtyTrick.addActionListener(this);
        this.fButtonPerType.put(CardType.DIRTY_TRICK, this.fButtonDirtyTrick);
        this.fNrOfCardsPerType.put(CardType.DIRTY_TRICK, pParameter.getNrOfCards(CardType.DIRTY_TRICK));
        panelMain.add(this.createDeckPanel(CardType.DIRTY_TRICK));
        panelMain.add(Box.createVerticalStrut(5));
        this.fCardLogTextPane = new ChatLogTextPane();
        this.fCardLogScrollPane = new ChatLogScrollPane(this.fCardLogTextPane);
        JPanel panelCardLog = new JPanel();
        panelCardLog.setLayout(new BorderLayout());
        panelCardLog.add((Component)this.fCardLogScrollPane, "Center");
        panelCardLog.setMinimumSize(new Dimension(450, 135));
        panelCardLog.setPreferredSize(panelCardLog.getMinimumSize());
        panelMain.add(panelCardLog);
        panelMain.add(Box.createVerticalStrut(10));
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new BoxLayout(panelButtons, 0));
        this.fButtonContinue = new JButton("Continue");
        this.fButtonContinue.addActionListener(this);
        panelButtons.add(Box.createHorizontalGlue());
        panelButtons.add(this.fButtonContinue);
        panelButtons.add(Box.createHorizontalGlue());
        panelMain.add(panelButtons);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add((Component)panelMain, "Center");
        this.pack();
        this.setLocationToCenter();
    }

    public void addCard(Card pCard) {
        if (pCard == null) {
            return;
        }
        this.fCardLogTextPane.append(ParagraphStyle.INDENT_0, TextStyle.BOLD, pCard.getName());
        this.fCardLogTextPane.append(ParagraphStyle.INDENT_0, TextStyle.NONE, null);
        this.fCardLogTextPane.append(ParagraphStyle.INDENT_1, TextStyle.NONE, pCard.getDescription());
        this.fCardLogTextPane.append(ParagraphStyle.INDENT_1, TextStyle.NONE, null);
        this.fCardLogTextPane.append(ParagraphStyle.INDENT_1, TextStyle.NONE, pCard.getDuration().getDescription());
        this.fCardLogTextPane.append(ParagraphStyle.INDENT_1, TextStyle.NONE, null);
        this.fCardLogTextPane.append(ParagraphStyle.INDENT_1, TextStyle.NONE, new InducementPhaseFactory().getDescription(pCard.getPhases()));
        this.fCardLogTextPane.append(ParagraphStyle.INDENT_1, TextStyle.NONE, null);
    }

    public void updateDialog() {
        this.fCardLogTextPane.detachDocument();
        this.fCardLogTextPane.attachDocument();
        InducementSet inducementSet = this.getClient().getGame().getTurnDataHome().getInducementSet();
        for (Card card : inducementSet.getAvailableCards()) {
            this.addCard(card);
        }
    }

    private JPanel createDeckPanel(CardType pType) {
        JPanel deckPanel = new JPanel();
        deckPanel.setLayout(new BoxLayout(deckPanel, 0));
        deckPanel.add(this.updateDeckButton(pType));
        return deckPanel;
    }

    private JLabel updateAvailableGoldLabel() {
        StringBuilder gold = new StringBuilder();
        gold.append("Available Gold: ").append(StringTool.formatThousands(this.fAvailableGold));
        this.fLabelAvailableGold.setText(gold.toString());
        this.fLabelAvailableGold.setFont(new Font("Sans Serif", 1, 12));
        return this.fLabelAvailableGold;
    }

    private JLabel updateAvailableCardsLabel() {
        StringBuilder cards = new StringBuilder();
        cards.append("Available Cards: ").append(this.fAvailableCards);
        this.fLabelAvailableCards.setText(cards.toString());
        this.fLabelAvailableCards.setFont(new Font("Sans Serif", 1, 12));
        return this.fLabelAvailableCards;
    }

    private JButton updateDeckButton(CardType pType) {
        JButton button = this.fButtonPerType.get(pType);
        if (button == null) {
            return null;
        }
        StringBuilder label = new StringBuilder();
        label.append("<html><center>");
        label.append("<b>").append(pType.getDeckName()).append("</b>");
        label.append("<br>");
        int nrOfCards = this.fNrOfCardsPerType.get(pType) != null ? this.fNrOfCardsPerType.get(pType) : 0;
        label.append(nrOfCards).append(" cards for ").append(StringTool.formatThousands(pType.getPrice())).append(" gold each");
        label.append("</center></html>");
        button.setText(label.toString());
        button.setEnabled(nrOfCards > 0 && this.fAvailableGold >= pType.getPrice() && this.fAvailableCards > 0);
        return button;
    }

    @Override
    public DialogId getId() {
        return DialogId.BUY_CARDS;
    }

    @Override
    public void actionPerformed(ActionEvent pActionEvent) {
        if (pActionEvent.getSource() == this.fButtonMagicItem) {
            this.buyCard(CardType.MAGIC_ITEM);
        }
        if (pActionEvent.getSource() == this.fButtonDirtyTrick) {
            this.buyCard(CardType.DIRTY_TRICK);
        }
        if (pActionEvent.getSource() == this.fButtonContinue && this.getCloseListener() != null) {
            this.getCloseListener().dialogClosed(this);
        }
    }

    private void buyCard(CardType pType) {
        this.getClient().getCommunication().sendBuyCard(pType);
        int nrOfCards = this.fNrOfCardsPerType.get(pType);
        this.fNrOfCardsPerType.put(pType, nrOfCards - 1);
        --this.fAvailableCards;
        this.updateAvailableCardsLabel();
        this.fAvailableGold -= pType.getPrice();
        this.updateAvailableGoldLabel();
        this.updateDeckButton(CardType.MAGIC_ITEM);
        this.updateDeckButton(CardType.DIRTY_TRICK);
    }

    @Override
    public void keyPressed(KeyEvent pKeyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent pKeyEvent) {
    }

    @Override
    public void keyTyped(KeyEvent pKeyEvent) {
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
    }

    @Override
    protected void setLocationToCenter() {
        Dimension dialogSize = this.getSize();
        Dimension frameSize = this.getClient().getUserInterface().getSize();
        Dimension menuBarSize = this.getClient().getUserInterface().getGameMenuBar().getSize();
        this.setLocation((frameSize.width - dialogSize.width) / 2, (frameSize.height - dialogSize.height) / 2 - menuBarSize.height);
    }
}


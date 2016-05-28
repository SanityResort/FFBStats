/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog.inducements;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.InducementType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.Dialog;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogUseInducementParameter;
import com.balancedbytes.games.ffb.util.ArrayTool;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class DialogUseInducement
extends Dialog
implements ActionListener {
    private InducementType fInducement;
    private Card fCard;
    private JButton fButtonWizard;
    private JButton fButtonContinue;
    private Map<Card, JButton> fButtonPerCard;

    public DialogUseInducement(FantasyFootballClient pClient, DialogUseInducementParameter pDialogParameter) {
        super(pClient, "Use Inducement", false);
        HashSet<InducementType> inducementSet = new HashSet<InducementType>();
        if (ArrayTool.isProvided(pDialogParameter.getInducementTypes())) {
            for (InducementType inducement : pDialogParameter.getInducementTypes()) {
                inducementSet.add(inducement);
            }
        }
        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BoxLayout(panelMain, 1));
        panelMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel panelText = new JPanel();
        panelText.setLayout(new BoxLayout(panelText, 0));
        panelText.add(new JLabel("Which inducement do you want to use?"));
        panelText.add(Box.createHorizontalGlue());
        panelMain.add(panelText);
        panelMain.add(Box.createVerticalStrut(10));
        this.fButtonPerCard = new HashMap<Card, JButton>();
        if (ArrayTool.isProvided(pDialogParameter.getCards())) {
            for (Card card : pDialogParameter.getCards()) {
                JPanel panelCard = new JPanel();
                panelCard.setLayout(new BoxLayout(panelCard, 0));
                StringBuilder buttonText = new StringBuilder();
                buttonText.append("<html>");
                buttonText.append("<b>").append(card.getName()).append("</b>");
                buttonText.append("<br>").append(card.getHtmlDescription());
                buttonText.append("</html>");
                JButton buttonCard = new JButton(buttonText.toString());
                buttonCard.setHorizontalAlignment(2);
                buttonCard.setMaximumSize(new Dimension(32767, 32767));
                buttonCard.addActionListener(this);
                panelCard.add(buttonCard);
                this.fButtonPerCard.put(card, buttonCard);
                panelMain.add(panelCard);
                panelMain.add(Box.createVerticalStrut(5));
            }
        }
        if (inducementSet.contains(InducementType.WIZARD)) {
            JPanel panelWizard = new JPanel();
            panelWizard.setLayout(new BoxLayout(panelWizard, 0));
            StringBuilder buttonText = new StringBuilder();
            buttonText.append("<html>");
            buttonText.append("<b>Wizard</b>");
            buttonText.append("<br>Cast Fireball or Lightning spell");
            buttonText.append("</html>");
            this.fButtonWizard = new JButton(buttonText.toString());
            this.fButtonWizard.setHorizontalAlignment(2);
            this.fButtonWizard.setMaximumSize(new Dimension(32767, 32767));
            this.fButtonWizard.addActionListener(this);
            panelWizard.add(this.fButtonWizard);
            panelMain.add(panelWizard);
            panelMain.add(Box.createVerticalStrut(5));
        }
        JPanel panelContinue = new JPanel();
        panelContinue.setLayout(new BoxLayout(panelContinue, 0));
        this.fButtonContinue = new JButton("Continue");
        this.fButtonContinue.addActionListener(this);
        panelContinue.add(Box.createHorizontalGlue());
        panelContinue.add(this.fButtonContinue);
        panelContinue.add(Box.createHorizontalGlue());
        panelMain.add(Box.createVerticalStrut(10));
        panelMain.add(panelContinue);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add((Component)panelMain, "Center");
        this.pack();
        this.setLocationToCenter();
    }

    @Override
    public DialogId getId() {
        return DialogId.USE_INDUCEMENT;
    }

    @Override
    public void actionPerformed(ActionEvent pActionEvent) {
        this.fInducement = null;
        if (pActionEvent.getSource() == this.fButtonWizard) {
            this.fInducement = InducementType.WIZARD;
        }
        this.fCard = null;
        for (Card card : this.fButtonPerCard.keySet()) {
            if (pActionEvent.getSource() != this.fButtonPerCard.get(card)) continue;
            this.fCard = card;
            break;
        }
        if (pActionEvent.getSource() == this.fButtonContinue) {
            this.fInducement = null;
            this.fCard = null;
        }
        if (this.getCloseListener() != null) {
            this.getCloseListener().dialogClosed(this);
        }
    }

    public InducementType getInducement() {
        return this.fInducement;
    }

    public Card getCard() {
        return this.fCard;
    }
}


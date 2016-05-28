/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog.inducements;

import com.balancedbytes.games.ffb.InducementType;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DropDownPanel
extends JPanel
implements ActionListener {
    private static final long serialVersionUID = -1914526447650181630L;
    private int fMax;
    private String fText;
    private int fCost;
    private JComboBox fBox;
    private boolean fAvailable;
    private ActionListener fActionListener;
    private int fAmountSelected = 0;
    private boolean fHandleEvents = true;
    private InducementType fInducementType;

    public DropDownPanel(InducementType pInducementType, int pMax, String pText, int pCost, ActionListener pListener, int pAvailableGold) {
        this.fInducementType = pInducementType;
        this.fMax = pMax;
        this.fText = pText;
        this.fCost = pCost;
        this.fAvailable = pMax != 0;
        this.fActionListener = pListener;
        ArrayList<String> anzahl = new ArrayList<String>();
        for (int i = 0; i <= this.fMax && i * this.fCost <= pAvailableGold; ++i) {
            anzahl.add(Integer.toString(i));
        }
        this.fBox = new JComboBox<String>(anzahl.toArray(new String[anzahl.size()]));
        this.fBox.setSelectedIndex(0);
        this.fBox.setMaximumSize(this.fBox.getMinimumSize());
        this.fBox.addActionListener(pListener);
        this.fBox.setEnabled(this.fAvailable);
        this.fBox.addActionListener(this);
        this.setLayout(new BoxLayout(this, 0));
        this.add(this.fBox);
        this.add(Box.createHorizontalStrut(10));
        JLabel label = new JLabel(this.fText + " (Max: " + Integer.toString(this.fMax) + "  " + this.formatGold(this.fCost) + " Gold" + (pMax > 1 ? " each)" : ")"));
        this.add(label);
        this.add(Box.createHorizontalGlue());
        this.setMaximumSize(new Dimension(this.getMaximumSize().width, this.getMinimumSize().height));
    }

    public InducementType getInducementType() {
        return this.fInducementType;
    }

    public int getSelectedAmount() {
        return this.fAmountSelected;
    }

    public void reset(int pAvailableGold) {
        this.fAmountSelected = 0;
        this.fBox.setSelectedIndex(0);
        this.availableGoldChanged(pAvailableGold);
    }

    private String formatGold(int pAmount) {
        StringBuilder buf = new StringBuilder(Integer.toString(pAmount)).reverse();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < buf.length(); ++i) {
            result.insert(0, buf.charAt(i));
            if ((i + 1) % 3 != 0 || i + 1 >= buf.length()) continue;
            result.insert(0, ",");
        }
        return result.toString();
    }

    public void availableGoldChanged(int pAvailableGold) {
        this.fHandleEvents = false;
        ArrayList<String> anzahl = new ArrayList<String>();
        if (((this.fMax - this.fAmountSelected) * this.fCost > pAvailableGold || this.fBox.getItemCount() < this.fMax + 1) && this.fBox.isEnabled()) {
            int i;
            for (i = 0; i <= this.fAmountSelected; ++i) {
                anzahl.add(Integer.toString(i));
            }
            for (i = this.fAmountSelected + 1; i <= this.fMax && (i - this.fAmountSelected) * this.fCost <= pAvailableGold; ++i) {
                anzahl.add(Integer.toString(i));
            }
            if (this.fBox.getItemCount() > anzahl.size()) {
                for (i = this.fBox.getItemCount(); i > anzahl.size() && i > 0; --i) {
                    this.fBox.removeItemAt(i - 1);
                }
            } else {
                for (i = this.fBox.getItemCount() + 1; i <= anzahl.size(); ++i) {
                    this.fBox.addItem(anzahl.get(i - 1));
                }
            }
        }
        this.fHandleEvents = true;
    }

    public int getCurrentCost() {
        return this.fAmountSelected * this.fCost;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.fHandleEvents) {
            this.fAmountSelected = Integer.parseInt(this.fBox.getItemAt(this.fBox.getSelectedIndex()).toString());
            this.fActionListener.actionPerformed(e);
        }
    }
}


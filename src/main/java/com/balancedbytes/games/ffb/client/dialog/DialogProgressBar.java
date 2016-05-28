/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.Dialog;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.util.StringTool;
import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class DialogProgressBar
extends Dialog
implements ActionListener {
    private JButton fButton = new JButton("Cancel");
    private JLabel fMessageLabel;
    private JProgressBar fProgressBar;

    public DialogProgressBar(FantasyFootballClient pClient, String pTitle) {
        this(pClient, pTitle, 0, 0);
    }

    public DialogProgressBar(FantasyFootballClient pClient, String pTitle, int pMinValue, int pMaxValue) {
        super(pClient, pTitle, false);
        this.fButton.addActionListener(this);
        this.fProgressBar = new JProgressBar(pMinValue, pMaxValue);
        this.fProgressBar.setValue(pMinValue);
        this.fProgressBar.setStringPainted(true);
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, 0));
        this.fMessageLabel = new JLabel("Initializing.");
        messagePanel.add(this.fMessageLabel);
        messagePanel.add(Box.createHorizontalGlue());
        messagePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JPanel progressPanel = new JPanel();
        progressPanel.setLayout(new BoxLayout(progressPanel, 0));
        progressPanel.add(this.fProgressBar);
        progressPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, 0));
        buttonPanel.add(this.fButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), 1));
        this.getContentPane().add(messagePanel);
        this.getContentPane().add(progressPanel);
        this.getContentPane().add(buttonPanel);
        this.pack();
        this.setLocationToCenter();
    }

    @Override
    public void actionPerformed(ActionEvent pActionEvent) {
        if (this.getCloseListener() != null) {
            this.getCloseListener().dialogClosed(this);
        }
    }

    @Override
    public DialogId getId() {
        return DialogId.PROGRESS_BAR;
    }

    public int getMinimum() {
        return this.fProgressBar.getMinimum();
    }

    public void setMinimum(final int pMinimum) {
        if (pMinimum != this.getMinimum()) {
            if (SwingUtilities.isEventDispatchThread()) {
                this.fProgressBar.setMinimum(pMinimum);
            } else {
                try {
                    SwingUtilities.invokeAndWait(new Runnable(){

                        @Override
                        public void run() {
                            DialogProgressBar.this.fProgressBar.setMinimum(pMinimum);
                        }
                    });
                }
                catch (InterruptedException var2_2) {
                }
                catch (InvocationTargetException var2_3) {
                    // empty catch block
                }
            }
        }
    }

    public int getMaximum() {
        return this.fProgressBar.getMaximum();
    }

    public void setMaximum(final int pMaximum) {
        if (pMaximum != this.getMaximum()) {
            if (SwingUtilities.isEventDispatchThread()) {
                this.fProgressBar.setMaximum(pMaximum);
            } else {
                try {
                    SwingUtilities.invokeAndWait(new Runnable(){

                        @Override
                        public void run() {
                            DialogProgressBar.this.fProgressBar.setMaximum(pMaximum);
                        }
                    });
                }
                catch (InterruptedException var2_2) {
                }
                catch (InvocationTargetException var2_3) {
                    // empty catch block
                }
            }
        }
    }

    public void updateProgress(final int pProgress, final String pMessage) {
        if (SwingUtilities.isEventDispatchThread()) {
            this.fProgressBar.setValue(pProgress);
            if (StringTool.isProvided(pMessage)) {
                this.fMessageLabel.setText(pMessage);
            }
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable(){

                    @Override
                    public void run() {
                        DialogProgressBar.this.fProgressBar.setValue(pProgress);
                        if (StringTool.isProvided(pMessage)) {
                            DialogProgressBar.this.fMessageLabel.setText(pMessage);
                        }
                    }
                });
            }
            catch (InterruptedException var3_3) {
            }
            catch (InvocationTargetException var3_4) {
                // empty catch block
            }
        }
    }

}


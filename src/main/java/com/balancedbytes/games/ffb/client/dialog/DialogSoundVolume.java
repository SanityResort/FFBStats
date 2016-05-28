/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.SoundId;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.Dialog;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.sound.SoundEngine;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.util.StringTool;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameEvent;

public class DialogSoundVolume
extends Dialog
implements ChangeListener,
ActionListener {
    private JSlider fSlider;
    private int fVolume;
    private JLabel fSettingLabel;
    private JButton fTestButton;

    public DialogSoundVolume(FantasyFootballClient pClient) {
        super(pClient, "Sound Volume Setting", true);
        String volumeProperty = pClient.getProperty("setting.sound.volume");
        int n = this.fVolume = StringTool.isProvided(volumeProperty) ? Integer.parseInt(volumeProperty) : 50;
        if (this.fVolume < 1) {
            this.fVolume = 1;
        }
        if (this.fVolume > 100) {
            this.fVolume = 100;
        }
        this.fSlider = new JSlider();
        this.fSlider.setMinimum(1);
        this.fSlider.setMaximum(100);
        this.fSlider.setValue(this.fVolume);
        this.fSlider.addChangeListener(this);
        this.fSlider.setMinimumSize(this.fSlider.getPreferredSize());
        this.fSlider.setMaximumSize(this.fSlider.getPreferredSize());
        this.fSettingLabel = new JLabel("100%");
        this.fSettingLabel.setMinimumSize(this.fSettingLabel.getPreferredSize());
        this.fSettingLabel.setMaximumSize(this.fSettingLabel.getPreferredSize());
        this.fSettingLabel.setHorizontalAlignment(4);
        this.fTestButton = new JButton("Test");
        this.fTestButton.addActionListener(this);
        JPanel settingPanel = new JPanel();
        settingPanel.setLayout(new BoxLayout(settingPanel, 0));
        settingPanel.add(this.fSlider);
        settingPanel.add(Box.createHorizontalStrut(5));
        settingPanel.add(this.fSettingLabel);
        settingPanel.add(Box.createHorizontalStrut(5));
        settingPanel.add(this.fTestButton);
        settingPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), 1));
        this.getContentPane().add(settingPanel);
        this.pack();
        this.setLocationToCenter();
        this.updateSettingLabel();
    }

    @Override
    public DialogId getId() {
        return DialogId.SOUND_VOLUME;
    }

    @Override
    public void stateChanged(ChangeEvent pE) {
        this.fVolume = this.fSlider.getValue();
        this.updateSettingLabel();
    }

    private void updateSettingLabel() {
        this.fSettingLabel.setText("" + this.fVolume + "%");
    }

    @Override
    public void actionPerformed(ActionEvent pE) {
        SoundEngine soundEngine = this.getClient().getUserInterface().getSoundEngine();
        soundEngine.setVolume(this.getVolume());
        soundEngine.playSound(SoundId.DING);
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent pE) {
        if (this.getCloseListener() != null) {
            this.getCloseListener().dialogClosed(this);
        }
    }

    public int getVolume() {
        return this.fVolume;
    }
}


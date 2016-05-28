/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.Dialog;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.ui.GameMenuBar;
import com.balancedbytes.games.ffb.dialog.DialogId;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.event.InternalFrameEvent;

public class DialogAbout
extends Dialog {
    private static final String[] _PLAYTESTERS = new String[]{"Ale1972, Asharak, Avantar, Avien, Ballcrusher, Balle2000, Barre, Benjysmyth, BiggieB, Brainsaw, Calthor, Carnis, CircularLogic, Chavo,", "Clarkin, Cmelchior, Cyrus-Havoc, Don Tomaso, DanTitan76, DukeTyrion, Dynamo380, Ebenezer, Ehlers, Flix, Floppeditbackwards,", "Freak_in_a_Frock, Freppa, Gandresch, Garaygos, Gjopie, Hangar18, Happygrue, Hitonagashi, Howlett, Janekt, JanMattys, Janzki,", "Jarvis_Pants, JoeMalik, Koigokoro, LeBlanc, Lerysh, Lewdgrip, Loraxwolfsbane, Louky, LoxleyAndy, Magistern, Malitrius, Mickael, Mtknight,", "MxFr, Nazgob, Nighteye, On1, PhrollikK, Purplegoo, RamonSalazar, Ravenmore, Razin, RedDevilCG, Reisender, Relezite, Shadow46x2, Sl8,", "Stej, Steve, Svemole, SvenS, Tarabaralla, Teluriel, Tensai, Thul, Tortured-Robot, Treborius, Ulrik, Ultwe, Uomotigre3, Uuni, Vesikannu,", "Woodstock, XZCion, Zakatan"};
    private static final int _WIDTH = 813;
    private static final int _HEIGHT = 542;

    public DialogAbout(FantasyFootballClient pClient) {
        super(pClient, "About Fantasy Football", true);
        JLabel aboutLabel = new JLabel(this.createAboutImageIcon(pClient));
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add((Component)aboutLabel, "Center");
        this.pack();
        this.setLocationToCenter();
    }

    @Override
    public DialogId getId() {
        return DialogId.ABOUT;
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent pE) {
        if (this.getCloseListener() != null) {
            this.getCloseListener().dialogClosed(this);
        }
    }

    private ImageIcon createAboutImageIcon(FantasyFootballClient pClient) {
        BufferedImage aboutImage = new BufferedImage(813, 542, 2);
        Graphics2D g2d = aboutImage.createGraphics();
        g2d.drawImage(pClient.getUserInterface().getIconCache().getIconByProperty("game.splashscreen"), 0, 0, null);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Sans Serif", 1, 17));
        String versionInfo = "Java Client Version 1.2.5";
        Rectangle2D versionBounds = g2d.getFontMetrics().getStringBounds(versionInfo, g2d);
        g2d.drawString(versionInfo, 788 - (int)versionBounds.getWidth(), 155);
        int y = 150;
        this.drawBold(g2d, 10, y += 0, "Headcoach: BattleLore");
        this.drawText(g2d, 20, y += 20, "thank you for providing ideas, encouragement and the occasional kick in the butt.");
        this.drawBold(g2d, 10, y += 25, "Assistant Coaches: WhatBall, Garion and Lakrillo");
        this.drawText(g2d, 20, y += 20, "thank you for helping to to pull the cart along.");
        this.drawBold(g2d, 10, y += 25, "Sports Director: Christer");
        this.drawText(g2d, 20, y += 20, "thank you for the patience and energy to tackle the long road with me.");
        this.drawBold(g2d, 10, y += 25, "Lifetime Luxury Suite Owner: SkiJunkie");
        this.drawText(g2d, 20, y += 20, "thank you doing it first and giving a vision to follow.");
        this.drawBold(g2d, 10, y += 25, "Light Show by: Cowhead, F_alk, FreeRange, Harvestmouse, Knut_Rockie, MisterFurious and Ryanfitz");
        this.drawBold(g2d, 10, y += 20, "Playing the Stadium Organ: VocalVoodoo and Minenbonnie");
        this.drawBold(g2d, 10, y += 20, "Official supplier of game balls: Qaz");
        this.drawText(g2d, 20, y += 20, "thank you all for making FFB look and sound great.");
        this.drawBold(g2d, 10, y += 25, "Cheerleaders & Pest Control:");
        y += 5;
        for (String playtesters : _PLAYTESTERS) {
            this.drawText(g2d, 20, y += 15, playtesters);
        }
        g2d.dispose();
        return new ImageIcon(aboutImage);
    }

    private void drawText(Graphics2D pG2d, int pX, int pY, String pText) {
        pG2d.setFont(new Font("Sans Serif", 0, 12));
        pG2d.drawString(pText, pX, pY);
    }

    private void drawBold(Graphics2D pG2d, int pX, int pY, String pText) {
        pG2d.setFont(new Font("Sans Serif", 1, 12));
        pG2d.drawString(pText, pX, pY);
    }

    @Override
    protected void setLocationToCenter() {
        Dimension dialogSize = this.getSize();
        Dimension frameSize = this.getClient().getUserInterface().getSize();
        Dimension menuBarSize = this.getClient().getUserInterface().getGameMenuBar().getSize();
        this.setLocation((frameSize.width - dialogSize.width) / 2, (frameSize.height - dialogSize.height) / 2 - menuBarSize.height);
    }
}


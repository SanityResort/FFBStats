/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.animation;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.layer.FieldLayer;
import com.balancedbytes.games.ffb.model.Animation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class AnimationSequenceCard
implements IAnimationSequence,
ActionListener {
    private static final int _TIMER_DELAY = 100;
    private AnimationFrame[] fFrames;
    private int fPosition;
    private Timer fTimer;
    private FieldLayer fFieldLayer;
    private int fX;
    private int fY;
    private int fDelay;
    private IAnimationListener fListener;

    public static AnimationSequenceCard createAnimationSequence(FantasyFootballClient pClient, Animation pAnimation) {
        String cardBackProperty = AnimationSequenceCard.getCardBackProperty(pAnimation.getCard());
        BufferedImage cardFront = AnimationSequenceCard.createCardFront(pClient, pAnimation.getCard());
        return new AnimationSequenceCard(new AnimationFrame[]{new AnimationFrame(cardBackProperty, 0.5f, 0.3, 100), new AnimationFrame(cardBackProperty, 0.6f, 0.4, 100), new AnimationFrame(cardBackProperty, 0.7f, 0.5, 100), new AnimationFrame(cardBackProperty, 0.8f, 0.6, 100), new AnimationFrame(cardBackProperty, 0.9f, 0.7, 100), new AnimationFrame(cardBackProperty, 1.0f, 0.8, 100), new AnimationFrame(cardBackProperty, 1.0f, 0.9, 100), new AnimationFrame(cardBackProperty, 1.0f, 1.0, 1.0, 500), new AnimationFrame(cardBackProperty, 1.0f, 0.8, 1.0, 100), new AnimationFrame(cardBackProperty, 1.0f, 0.6, 1.0, 100), new AnimationFrame(cardBackProperty, 1.0f, 0.4, 1.0, 100), new AnimationFrame(cardBackProperty, 1.0f, 0.2, 1.0, 100), new AnimationFrame(cardBackProperty, 1.0f, 0.0, 1.0, 100), new AnimationFrame(cardFront, 1.0f, 0.2, 1.0, 100), new AnimationFrame(cardFront, 1.0f, 0.4, 1.0, 100), new AnimationFrame(cardFront, 1.0f, 0.6, 1.0, 100), new AnimationFrame(cardFront, 1.0f, 0.8, 1.0, 100), new AnimationFrame(cardFront, 1.0f, 1.0, 1.0, 2000)});
    }

    private static String getCardFrontProperty(Card pCard) {
        if (pCard != null) {
            switch (pCard.getType()) {
                case DIRTY_TRICK: {
                    return "animation.card.dirtyTrick.front";
                }
                case MAGIC_ITEM: {
                    return "animation.card.magicItem.front";
                }
            }
        }
        return null;
    }

    private static String getCardBackProperty(Card pCard) {
        if (pCard != null) {
            switch (pCard.getType()) {
                case DIRTY_TRICK: {
                    return "animation.card.dirtyTrick.back";
                }
                case MAGIC_ITEM: {
                    return "animation.card.magicItem.back";
                }
            }
        }
        return null;
    }

    private static BufferedImage createCardFront(FantasyFootballClient pClient, Card pCard) {
        IconCache iconCache = pClient.getUserInterface().getIconCache();
        BufferedImage frontIcon = iconCache.getIconByProperty(AnimationSequenceCard.getCardFrontProperty(pCard));
        if (frontIcon == null) {
            return null;
        }
        String[] lines = pCard.getShortName().split(" ");
        BufferedImage cardIcon = new BufferedImage(frontIcon.getWidth(), frontIcon.getHeight(), 2);
        Graphics2D g2d = cardIcon.createGraphics();
        g2d.drawImage(frontIcon, 0, 0, null);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Sans Serif", 1, 22));
        FontMetrics metrics = g2d.getFontMetrics();
        for (int i = 0; i < lines.length; ++i) {
            Rectangle2D textBounds = metrics.getStringBounds(lines[i], g2d);
            int x = frontIcon.getWidth() / 2 - (int)(textBounds.getWidth() / 2.0) + 1;
            int y = frontIcon.getHeight() / 2 - lines.length * 28 / 2 + i * 28 + (int)(textBounds.getHeight() / 2.0) - 2;
            g2d.drawString(lines[i], x, y);
        }
        g2d.dispose();
        return cardIcon;
    }

    protected AnimationSequenceCard(AnimationFrame[] pFrames) {
        this.fFrames = pFrames;
        this.fX = 391;
        this.fY = 226;
        this.fTimer = new Timer(100, this);
    }

    @Override
    public void play(FieldLayer pFieldLayer, IAnimationListener pListener) {
        this.fFieldLayer = pFieldLayer;
        this.fListener = pListener;
        this.fPosition = -1;
        this.fDelay = 0;
        this.fTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent pE) {
        this.fDelay -= 100;
        if (this.fDelay > 0) {
            return;
        }
        if (this.fPosition >= 0) {
            this.fFrames[this.fPosition].clear();
        }
        if (this.fPosition < this.fFrames.length - 1) {
            ++this.fPosition;
            this.fDelay = this.fFrames[this.fPosition].getTime();
            this.fFrames[this.fPosition].drawCenteredAndScaled(this.fFieldLayer, this.fX, this.fY);
            if (this.fFrames[this.fPosition].getSound() != null) {
                String soundSetting = this.fFieldLayer.getClient().getProperty("setting.sound.mode");
                if ("soundOn".equals(soundSetting) || "muteSpectators".equals(soundSetting)) {
                }
            }
        } else {
            this.fTimer.stop();
            this.fPosition = -1;
        }
        this.fFieldLayer.getClient().getUserInterface().getFieldComponent().refresh();
        if (this.fPosition < 0 && this.fListener != null) {
            this.fListener.animationFinished();
        }
    }

}


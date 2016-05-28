/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.animation;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.SoundId;
import com.balancedbytes.games.ffb.client.layer.FieldLayer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimationSequenceSpecialEffect
implements IAnimationSequence,
ActionListener {
    private static final int _TIMER_DELAY = 100;
    private AnimationFrame[] fFrames;
    private int fPosition;
    private Timer fTimer;
    private FieldLayer fFieldLayer;
    private FieldCoordinate fCoordinate;
    private int fDelay;
    private IAnimationListener fListener;

    public static final AnimationSequenceSpecialEffect createAnimationSequenceBomb(FieldCoordinate pCoordinate) {
        return new AnimationSequenceSpecialEffect(pCoordinate, new AnimationFrame[]{new AnimationFrame("animation.fireball.explosion.1", 1.0f, 100, SoundId.EXPLODE), new AnimationFrame("animation.fireball.explosion.2", 1.0f, 100), new AnimationFrame("animation.fireball.explosion.3", 1.0f, 100), new AnimationFrame("animation.fireball.explosion.4", 1.0f, 100), new AnimationFrame("animation.fireball.explosion.5", 1.0f, 100), new AnimationFrame("animation.fireball.explosion.6", 1.0f, 200), new AnimationFrame("animation.fireball.explosion.6", 0.7f, 100), new AnimationFrame("animation.fireball.explosion.6", 0.5f, 100), new AnimationFrame("animation.fireball.explosion.6", 0.2f, 100)});
    }

    public static final AnimationSequenceSpecialEffect createAnimationSequenceFireball(FieldCoordinate pCoordinate) {
        return new AnimationSequenceSpecialEffect(pCoordinate, new AnimationFrame[]{new AnimationFrame("animation.fireball.explosion.1", 1.0f, 100, SoundId.FIREBALL), new AnimationFrame("animation.fireball.explosion.2", 1.0f, 100), new AnimationFrame("animation.fireball.explosion.3", 1.0f, 100), new AnimationFrame("animation.fireball.explosion.4", 1.0f, 100), new AnimationFrame("animation.fireball.explosion.5", 1.0f, 100), new AnimationFrame("animation.fireball.explosion.6", 1.0f, 100), new AnimationFrame("animation.fireball.explosion.7", 1.0f, 100), new AnimationFrame("animation.fireball.explosion.8", 1.0f, "animation.fireball.smoke.1", 1.0f, 100), new AnimationFrame("animation.fireball.explosion.8", 0.7f, "animation.fireball.smoke.2", 1.0f, 100), new AnimationFrame("animation.fireball.explosion.8", 0.5f, "animation.fireball.smoke.3", 1.0f, 100), new AnimationFrame("animation.fireball.smoke.3", 1.0f, 100), new AnimationFrame("animation.fireball.smoke.3", 0.7f, 100), new AnimationFrame("animation.fireball.smoke.3", 0.5f, 100)});
    }

    public static final AnimationSequenceSpecialEffect createAnimationSequenceLightning(FieldCoordinate pCoordinate) {
        return new AnimationSequenceSpecialEffect(pCoordinate, new AnimationFrame[]{new AnimationFrame("animation.lightning.01", 1.0f, 100), new AnimationFrame("animation.lightning.02", 1.0f, 100), new AnimationFrame("animation.lightning.03", 1.0f, 100), new AnimationFrame("animation.lightning.04", 1.0f, 100), new AnimationFrame("animation.lightning.05", 1.0f, 100), new AnimationFrame("animation.lightning.06", 1.0f, 200), new AnimationFrame("animation.lightning.07", 1.0f, 200, SoundId.LIGHTNING), new AnimationFrame("animation.lightning.06", 1.0f, 200), new AnimationFrame("animation.lightning.07", 1.0f, 200, SoundId.LIGHTNING), new AnimationFrame("animation.lightning.06", 1.0f, 200), new AnimationFrame("animation.lightning.08", 1.0f, 100), new AnimationFrame("animation.lightning.09", 1.0f, 100), new AnimationFrame("animation.lightning.10", 1.0f, 100), new AnimationFrame("animation.lightning.11", 1.0f, 100), new AnimationFrame("animation.lightning.12", 1.0f, 100)});
    }

    protected AnimationSequenceSpecialEffect(FieldCoordinate pCoordinate, AnimationFrame[] pFrames) {
        this.fCoordinate = pCoordinate;
        this.fFrames = pFrames;
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
            this.fFrames[this.fPosition].draw(this.fFieldLayer, this.fCoordinate);
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


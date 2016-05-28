/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.animation;

import com.balancedbytes.games.ffb.SoundId;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.FieldComponent;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.animation.AnimationFrame;
import com.balancedbytes.games.ffb.client.animation.IAnimationListener;
import com.balancedbytes.games.ffb.client.animation.IAnimationSequence;
import com.balancedbytes.games.ffb.client.layer.FieldLayer;
import com.balancedbytes.games.ffb.client.sound.SoundEngine;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class AnimationSequenceKickoff
implements IAnimationSequence,
ActionListener {
    public static final AnimationSequenceKickoff KICKOFF_BLITZ = AnimationSequenceKickoff.createAnimationSequence("animation.kickoff.blitz");
    public static final AnimationSequenceKickoff KICKOFF_BLIZZARD = AnimationSequenceKickoff.createAnimationSequence("animation.kickoff.blizzard");
    public static final AnimationSequenceKickoff KICKOFF_BRILLIANT_COACHING = AnimationSequenceKickoff.createAnimationSequence("animation.kickoff.brilliantCoaching");
    public static final AnimationSequenceKickoff KICKOFF_CHEERING_FANS = AnimationSequenceKickoff.createAnimationSequence("animation.kickoff.cheeringFans");
    public static final AnimationSequenceKickoff KICKOFF_GET_THE_REF = AnimationSequenceKickoff.createAnimationSequence("animation.kickoff.getTheRef");
    public static final AnimationSequenceKickoff KICKOFF_HIGH_KICK = AnimationSequenceKickoff.createAnimationSequence("animation.kickoff.highKick");
    public static final AnimationSequenceKickoff KICKOFF_NICE = AnimationSequenceKickoff.createAnimationSequence("animation.kickoff.nice");
    public static final AnimationSequenceKickoff KICKOFF_PERFECT_DEFENSE = AnimationSequenceKickoff.createAnimationSequence("animation.kickoff.perfectDefense");
    public static final AnimationSequenceKickoff KICKOFF_PITCH_INVASION = AnimationSequenceKickoff.createAnimationSequence("animation.kickoff.pitchInvasion");
    public static final AnimationSequenceKickoff KICKOFF_POURING_RAIN = AnimationSequenceKickoff.createAnimationSequence("animation.kickoff.pouringRain");
    public static final AnimationSequenceKickoff KICKOFF_QUICK_SNAP = AnimationSequenceKickoff.createAnimationSequence("animation.kickoff.quickSnap");
    public static final AnimationSequenceKickoff KICKOFF_RIOT = AnimationSequenceKickoff.createAnimationSequence("animation.kickoff.riot");
    public static final AnimationSequenceKickoff KICKOFF_SWELTERING_HEAT = AnimationSequenceKickoff.createAnimationSequence("animation.kickoff.swelteringHeat");
    public static final AnimationSequenceKickoff KICKOFF_THROW_A_ROCK = AnimationSequenceKickoff.createAnimationSequence("animation.kickoff.throwARock");
    public static final AnimationSequenceKickoff KICKOFF_VERY_SUNNY = AnimationSequenceKickoff.createAnimationSequence("animation.kickoff.verySunny");
    private static final int _TIMER_DELAY = 100;
    private AnimationFrame[] fFrames;
    private int fPosition;
    private Timer fTimer;
    private FieldLayer fFieldLayer;
    private int fX;
    private int fY;
    private int fDelay;
    private IAnimationListener fListener;

    private static AnimationSequenceKickoff createAnimationSequence(String pIconProperty) {
        return new AnimationSequenceKickoff(new AnimationFrame[]{new AnimationFrame(pIconProperty, 0.5f, 0.3, 100), new AnimationFrame(pIconProperty, 0.6f, 0.4, 100), new AnimationFrame(pIconProperty, 0.7f, 0.5, 100), new AnimationFrame(pIconProperty, 0.8f, 0.6, 100), new AnimationFrame(pIconProperty, 0.9f, 0.7, 100), new AnimationFrame(pIconProperty, 1.0f, 0.8, 100), new AnimationFrame(pIconProperty, 1.0f, 0.9, 100), new AnimationFrame(pIconProperty, 1.0f, 1.0, 2000), new AnimationFrame(pIconProperty, 0.8f, 1.0, 100), new AnimationFrame(pIconProperty, 0.6f, 1.0, 100), new AnimationFrame(pIconProperty, 0.4f, 1.0, 100), new AnimationFrame(pIconProperty, 0.2f, 1.0, 100)});
    }

    protected AnimationSequenceKickoff(AnimationFrame[] pFrames) {
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
                SoundEngine soundEngine = this.fFieldLayer.getClient().getUserInterface().getSoundEngine();
                String soundSetting = this.fFieldLayer.getClient().getProperty("setting.sound.mode");
                if ("soundOn".equals(soundSetting) || "muteSpectators".equals(soundSetting)) {
                    soundEngine.playSound(this.fFrames[this.fPosition].getSound());
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


/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.animation;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.SoundId;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.FieldComponent;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.PlayerIconFactory;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.animation.IAnimationListener;
import com.balancedbytes.games.ffb.client.animation.IAnimationSequence;
import com.balancedbytes.games.ffb.client.layer.FieldLayer;
import com.balancedbytes.games.ffb.client.sound.SoundEngine;
import com.balancedbytes.games.ffb.model.Animation;
import com.balancedbytes.games.ffb.model.AnimationType;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.Timer;

public class AnimationSequenceThrowing
implements IAnimationSequence,
ActionListener {
    private AnimationType fAnimationType;
    private BufferedImage fAnimatedIcon;
    private FieldCoordinate fStartCoordinate;
    private FieldCoordinate fEndCoordinate;
    private FieldCoordinate fInterceptorCoordinate;
    private SoundId fSound;
    private int fStartX;
    private int fStartY;
    private int fEndX;
    private int fEndY;
    private int fInterceptorX;
    private int fInterceptorY;
    private int fPositionX;
    private int fPositionY;
    private Rectangle fLastIconBounds;
    private FieldLayer fFieldLayer;
    private IAnimationListener fListener;
    private Timer fTimer;

    public static AnimationSequenceThrowing createAnimationSequencePass(FantasyFootballClient pClient, Animation pAnimation) {
        return new AnimationSequenceThrowing(AnimationType.PASS, pClient.getUserInterface().getIconCache().getIconByProperty("game.ball"), pAnimation.getStartCoordinate(), pAnimation.getEndCoordinate(), pAnimation.getInterceptorCoordinate(), SoundId.THROW);
    }

    public static AnimationSequenceThrowing createAnimationSequenceThrowBomb(FantasyFootballClient pClient, Animation pAnimation) {
        return new AnimationSequenceThrowing(AnimationType.THROW_BOMB, pClient.getUserInterface().getIconCache().getIconByProperty("game.bomb"), pAnimation.getStartCoordinate(), pAnimation.getEndCoordinate(), pAnimation.getInterceptorCoordinate(), SoundId.THROW);
    }

    public static AnimationSequenceThrowing createAnimationSequenceThrowARock(FantasyFootballClient pClient, Animation pAnimation) {
        return new AnimationSequenceThrowing(AnimationType.THROW_A_ROCK, pClient.getUserInterface().getIconCache().getIconByProperty("game.rock"), pAnimation.getStartCoordinate(), pAnimation.getEndCoordinate(), pAnimation.getInterceptorCoordinate(), SoundId.THROW);
    }

    public static AnimationSequenceThrowing createAnimationSequenceKick(FantasyFootballClient pClient, Animation pAnimation) {
        return new AnimationSequenceThrowing(AnimationType.KICK, pClient.getUserInterface().getIconCache().getIconByProperty("game.ball.big"), pAnimation.getStartCoordinate(), pAnimation.getEndCoordinate(), pAnimation.getInterceptorCoordinate(), SoundId.KICK);
    }

    public static AnimationSequenceThrowing createAnimationSequenceHailMaryPass(FantasyFootballClient pClient, Animation pAnimation) {
        return new AnimationSequenceThrowing(AnimationType.HAIL_MARY_PASS, pClient.getUserInterface().getIconCache().getIconByProperty("game.ball.big"), pAnimation.getStartCoordinate(), pAnimation.getEndCoordinate(), pAnimation.getInterceptorCoordinate(), SoundId.THROW);
    }

    public static AnimationSequenceThrowing createAnimationSequenceHailMaryBomb(FantasyFootballClient pClient, Animation pAnimation) {
        return new AnimationSequenceThrowing(AnimationType.HAIL_MARY_BOMB, pClient.getUserInterface().getIconCache().getIconByProperty("game.bomb.big"), pAnimation.getStartCoordinate(), pAnimation.getEndCoordinate(), pAnimation.getInterceptorCoordinate(), SoundId.THROW);
    }

    public static AnimationSequenceThrowing createAnimationSequenceThrowTeamMate(FantasyFootballClient pClient, Animation pAnimation) {
        Player thrownPlayer = pClient.getGame().getPlayerById(pAnimation.getThrownPlayerId());
        boolean homePlayer = pClient.getGame().getTeamHome().hasPlayer(thrownPlayer);
        PlayerIconFactory playerIconFactory = pClient.getUserInterface().getPlayerIconFactory();
        BufferedImage playerIcon = playerIconFactory.getBasicIcon(pClient, thrownPlayer, homePlayer, false, pAnimation.isWithBall(), false);
        return new AnimationSequenceThrowing(AnimationType.THROW_TEAM_MATE, playerIcon, pAnimation.getStartCoordinate(), pAnimation.getEndCoordinate(), null, SoundId.WOOOAAAH);
    }

    protected AnimationSequenceThrowing(AnimationType pAnimationType, BufferedImage pAnimatedIcon, FieldCoordinate pStartCoordinate, FieldCoordinate pEndCoordinate, FieldCoordinate pInterceptorCoordinate, SoundId pSound) {
        this.fAnimationType = pAnimationType;
        this.fAnimatedIcon = pAnimatedIcon;
        this.fStartCoordinate = pStartCoordinate;
        this.fEndCoordinate = pEndCoordinate;
        this.fInterceptorCoordinate = pInterceptorCoordinate;
        this.fSound = pSound;
        this.fTimer = new Timer(20, this);
    }

    @Override
    public void play(FieldLayer pFieldLayer, IAnimationListener pListener) {
        this.fFieldLayer = pFieldLayer;
        this.fListener = pListener;
        this.fLastIconBounds = null;
        this.fStartX = this.fStartCoordinate.getX() * 30 + 15;
        this.fStartY = this.fStartCoordinate.getY() * 30 + 15;
        this.fEndX = this.fEndCoordinate.getX() * 30 + 15;
        this.fEndY = this.fEndCoordinate.getY() * 30 + 15;
        this.fInterceptorX = this.fEndX;
        this.fInterceptorY = this.fEndY;
        if (this.fInterceptorCoordinate != null) {
            this.fInterceptorX = this.fInterceptorCoordinate.getX() * 30 + 15;
            this.fInterceptorY = this.fInterceptorCoordinate.getY() * 30 + 15;
        }
        this.fPositionX = this.fStartX;
        this.fPositionY = this.fStartY;
        String soundSetting = this.fFieldLayer.getClient().getProperty("setting.sound.mode");
        if (this.fSound != null && ("soundOn".equals(soundSetting) || "muteSpectators".equals(soundSetting))) {
            SoundEngine soundEngine = this.fFieldLayer.getClient().getUserInterface().getSoundEngine();
            soundEngine.playSound(this.fSound);
        }
        this.fTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent pE) {
        boolean xAxisAnimation;
        this.fFieldLayer.clear(this.fLastIconBounds, true);
        double scale = 0.0;
        boolean stopAnimation = false;
        boolean bl = xAxisAnimation = Math.abs(this.fEndX - this.fStartX) > Math.abs(this.fEndY - this.fStartY);
        if (xAxisAnimation) {
            this.fPositionY = this.fStartY + (int)((double)(this.fEndY - this.fStartY) / (double)(this.fEndX - this.fStartX) * (double)(this.fPositionX - this.fStartX));
            scale = this.findScale((double)(this.fPositionX - this.fStartX) / (double)(this.fEndX - this.fStartX) * 2.0);
        } else {
            this.fPositionX = this.fStartX + (int)((double)(this.fEndX - this.fStartX) / (double)(this.fEndY - this.fStartY) * (double)(this.fPositionY - this.fStartY));
            scale = this.findScale((double)(this.fPositionY - this.fStartY) / (double)(this.fEndY - this.fStartY) * 2.0);
        }
        this.fLastIconBounds = this.fFieldLayer.drawCenteredAndScaled(this.fAnimatedIcon, this.fPositionX, this.fPositionY, 1.0f, scale, scale);
        this.fFieldLayer.getClient().getUserInterface().getFieldComponent().refresh();
        int stepping = this.findStepping();
        if (xAxisAnimation) {
            if (this.fStartX < this.fEndX) {
                this.fPositionX += stepping;
                stopAnimation = this.fPositionX >= this.fInterceptorX;
            } else {
                this.fPositionX -= stepping;
                stopAnimation = this.fPositionX <= this.fInterceptorX;
            }
        } else if (this.fStartY < this.fEndY) {
            this.fPositionY += stepping;
            stopAnimation = this.fPositionY >= this.fInterceptorY;
        } else {
            this.fPositionY -= stepping;
            boolean bl2 = stopAnimation = this.fPositionY <= this.fInterceptorY;
        }
        if (stopAnimation) {
            this.fTimer.stop();
            this.fFieldLayer.clear(this.fLastIconBounds, true);
            this.fListener.animationFinished();
        }
    }

    private int findStepping() {
        int deltaY;
        if (this.fStartCoordinate == null || this.fEndCoordinate == null) {
            return 0;
        }
        int deltaX = Math.abs(this.fEndCoordinate.getX() - this.fStartCoordinate.getX());
        int deltaMax = Math.max(deltaX, deltaY = Math.abs(this.fEndCoordinate.getY() - this.fStartCoordinate.getY()));
        if (deltaMax <= 7) {
            return 2;
        }
        return 3;
    }

    private double findScale(double pX) {
        if (AnimationType.PASS == this.fAnimationType || AnimationType.THROW_A_ROCK == this.fAnimationType || AnimationType.THROW_BOMB == this.fAnimationType) {
            return 1.0 - (pX - 1.0) * (pX - 1.0) * 0.5;
        }
        if (AnimationType.THROW_TEAM_MATE == this.fAnimationType) {
            return 1.5 - (pX - 1.0) * (pX - 1.0) * 0.5;
        }
        if (AnimationType.KICK == this.fAnimationType || AnimationType.HAIL_MARY_PASS == this.fAnimationType || AnimationType.HAIL_MARY_BOMB == this.fAnimationType) {
            return 1.0 - (pX - 1.0) * (pX - 1.0) * 0.75;
        }
        return 0.0;
    }
}


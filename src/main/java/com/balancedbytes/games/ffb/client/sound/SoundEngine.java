/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  kuusisto.tinysound.Sound
 *  kuusisto.tinysound.TinySound
 */
package com.balancedbytes.games.ffb.client.sound;

import com.balancedbytes.games.ffb.SoundId;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.util.StringTool;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

public class SoundEngine {
    private static Map<SoundId, String> _SOUND_PROPERTY_KEYS = Collections.synchronizedMap(new HashMap());
    private FantasyFootballClient fClient;
    private Map<SoundId, Sound> fSoundById;
    private int fVolume;

    public SoundEngine(FantasyFootballClient pClient) {
        this.fClient = pClient;
        this.fSoundById = new HashMap<SoundId, Sound>();
    }

    public void init() {
        TinySound.init();
        TinySound.setGlobalVolume((double)0.5);
    }

    public void playSound(SoundId pSoundId) {
        String soundPropertyKey;
        Sound sound = this.fSoundById.get(pSoundId);
        if (sound == null && StringTool.isProvided(soundPropertyKey = _SOUND_PROPERTY_KEYS.get(pSoundId))) {
            String fileProperty = soundPropertyKey + ".file";
            String soundResource = "/sounds/" + this.getClient().getProperty(fileProperty);
            sound = TinySound.loadSound((String)soundResource);
            this.fSoundById.put(pSoundId, sound);
        }
        if (sound != null) {
            sound.play((double)this.fVolume / 100.0);
        }
    }

    public long getSoundLength(SoundId pSoundId) {
        StringBuilder lengthProperty = new StringBuilder().append(_SOUND_PROPERTY_KEYS.get(pSoundId)).append(".length");
        String length = this.getClient().getProperty(lengthProperty.toString());
        if (StringTool.isProvided(length)) {
            return Long.parseLong(length);
        }
        return 0;
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }

    public void setVolume(int pVolume) {
        this.fVolume = pVolume;
    }

    public int getVolume() {
        return this.fVolume;
    }

    static {
        _SOUND_PROPERTY_KEYS.put(SoundId.BLOCK, "sound.block");
        _SOUND_PROPERTY_KEYS.put(SoundId.BLUNDER, "sound.blunder");
        _SOUND_PROPERTY_KEYS.put(SoundId.BOUNCE, "sound.bounce");
        _SOUND_PROPERTY_KEYS.put(SoundId.CATCH, "sound.catch");
        _SOUND_PROPERTY_KEYS.put(SoundId.CHAINSAW, "sound.chainsaw");
        _SOUND_PROPERTY_KEYS.put(SoundId.CLICK, "sound.click");
        _SOUND_PROPERTY_KEYS.put(SoundId.DING, "sound.ding");
        _SOUND_PROPERTY_KEYS.put(SoundId.DODGE, "sound.dodge");
        _SOUND_PROPERTY_KEYS.put(SoundId.DUH, "sound.duh");
        _SOUND_PROPERTY_KEYS.put(SoundId.EW, "sound.ew");
        _SOUND_PROPERTY_KEYS.put(SoundId.EXPLODE, "sound.explode");
        _SOUND_PROPERTY_KEYS.put(SoundId.FALL, "sound.fall");
        _SOUND_PROPERTY_KEYS.put(SoundId.FIREBALL, "sound.fireball");
        _SOUND_PROPERTY_KEYS.put(SoundId.FOUL, "sound.foul");
        _SOUND_PROPERTY_KEYS.put(SoundId.HYPNO, "sound.hypno");
        _SOUND_PROPERTY_KEYS.put(SoundId.INJURY, "sound.injury");
        _SOUND_PROPERTY_KEYS.put(SoundId.KICK, "sound.kick");
        _SOUND_PROPERTY_KEYS.put(SoundId.KO, "sound.ko");
        _SOUND_PROPERTY_KEYS.put(SoundId.LIGHTNING, "sound.lightning");
        _SOUND_PROPERTY_KEYS.put(SoundId.METAL, "sound.metal");
        _SOUND_PROPERTY_KEYS.put(SoundId.NOMNOM, "sound.nomnom");
        _SOUND_PROPERTY_KEYS.put(SoundId.ORGAN, "sound.organ");
        _SOUND_PROPERTY_KEYS.put(SoundId.PICKUP, "sound.pickup");
        _SOUND_PROPERTY_KEYS.put(SoundId.QUESTION, "sound.question");
        _SOUND_PROPERTY_KEYS.put(SoundId.RIP, "sound.rip");
        _SOUND_PROPERTY_KEYS.put(SoundId.ROAR, "sound.roar");
        _SOUND_PROPERTY_KEYS.put(SoundId.ROOT, "sound.root");
        _SOUND_PROPERTY_KEYS.put(SoundId.SLURP, "sound.slurp");
        _SOUND_PROPERTY_KEYS.put(SoundId.SPEC_AAH, "sound.specAah");
        _SOUND_PROPERTY_KEYS.put(SoundId.SPEC_BOO, "sound.specBoo");
        _SOUND_PROPERTY_KEYS.put(SoundId.SPEC_CHEER, "sound.specCheer");
        _SOUND_PROPERTY_KEYS.put(SoundId.SPEC_CLAP, "sound.specClap");
        _SOUND_PROPERTY_KEYS.put(SoundId.SPEC_CRICKETS, "sound.specCrickets");
        _SOUND_PROPERTY_KEYS.put(SoundId.SPEC_LAUGH, "sound.specLaugh");
        _SOUND_PROPERTY_KEYS.put(SoundId.SPEC_OOH, "sound.specOoh");
        _SOUND_PROPERTY_KEYS.put(SoundId.SPEC_SHOCK, "sound.specShock");
        _SOUND_PROPERTY_KEYS.put(SoundId.SPEC_STOMP, "sound.specStomp");
        _SOUND_PROPERTY_KEYS.put(SoundId.STEP, "sound.step");
        _SOUND_PROPERTY_KEYS.put(SoundId.STAB, "sound.stab");
        _SOUND_PROPERTY_KEYS.put(SoundId.THROW, "sound.throw");
        _SOUND_PROPERTY_KEYS.put(SoundId.TOUCHDOWN, "sound.touchdown");
        _SOUND_PROPERTY_KEYS.put(SoundId.WHISTLE, "sound.whistle");
        _SOUND_PROPERTY_KEYS.put(SoundId.WOOOAAAH, "sound.woooaaah");
    }
}


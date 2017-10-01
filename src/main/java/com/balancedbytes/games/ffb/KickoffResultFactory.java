/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.KickoffResult;

public class KickoffResultFactory
implements INamedObjectFactory {
    @Override
    public KickoffResult forName(String pName) {
        for (KickoffResult kickoff : KickoffResult.values()) {
            if (!kickoff.getName().equalsIgnoreCase(pName)) continue;
            return kickoff;
        }
        return null;
    }

    public KickoffResult forRoll(int pRoll) {
        switch (pRoll) {
            case 2: {
                return KickoffResult.GET_THE_REF;
            }
            case 3: {
                return KickoffResult.RIOT;
            }
            case 4: {
                return KickoffResult.PERFECT_DEFENCE;
            }
            case 5: {
                return KickoffResult.HIGH_KICK;
            }
            case 6: {
                return KickoffResult.CHEERING_FANS;
            }
            case 7: {
                return KickoffResult.WEATHER_CHANGE;
            }
            case 8: {
                return KickoffResult.BRILLIANT_COACHING;
            }
            case 9: {
                return KickoffResult.QUICK_SNAP;
            }
            case 10: {
                return KickoffResult.BLITZ;
            }
            case 11: {
                return KickoffResult.THROW_A_ROCK;
            }
            case 12: {
                return KickoffResult.PITCH_INVASION;
            }
        }
        return null;
    }
}


/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.INamedObject;

public enum AnimationType implements INamedObject
{
    PASS("pass"),
    THROW_TEAM_MATE("throwTeamMate"),
    KICK("kick"),
    SPELL_FIREBALL("spellFireball"),
    SPELL_LIGHTNING("spellLightning"),
    KICKOFF_BLITZ("kickoffBlitz"),
    KICKOFF_BLIZZARD("kickoffBlizzard"),
    KICKOFF_BRILLIANT_COACHING("kickoffBrilliantCoaching"),
    KICKOFF_CHEERING_FANS("kickoffCheeringFans"),
    KICKOFF_GET_THE_REF("kickoffGetTheRef"),
    KICKOFF_HIGH_KICK("kickoffHighKick"),
    KICKOFF_NICE("kickoffNice"),
    KICKOFF_PERFECT_DEFENSE("kickoffPerfectDefense"),
    KICKOFF_PITCH_INVASION("kickoffPitchInvasion"),
    KICKOFF_POURING_RAIN("kickoffPouringRain"),
    KICKOFF_QUICK_SNAP("kickoffQuickSnap"),
    KICKOFF_RIOT("kickoffRiot"),
    KICKOFF_SWELTERING_HEAT("kickoffSwelteringHeat"),
    KICKOFF_THROW_A_ROCK("kickoffThrowARock"),
    KICKOFF_VERY_SUNNY("kickoffVerySunny"),
    HAIL_MARY_PASS("hailMaryPass"),
    THROW_A_ROCK("throwARock"),
    THROW_BOMB("throwBomb"),
    HAIL_MARY_BOMB("hailMaryBomb"),
    BOMB_EXLOSION("bombExplosion"),
    CARD("card");
    
    private String fName;

    private AnimationType(String pName) {
        this.fName = pName;
    }

    @Override
    public String getName() {
        return this.fName;
    }
}


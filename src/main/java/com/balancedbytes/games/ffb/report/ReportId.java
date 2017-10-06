/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.INamedObject;
import org.butterbrot.ffb.stats.adapter.DummyReport;

public enum ReportId implements INamedObject
{
    ALWAYS_HUNGRY_ROLL("alwaysHungryRoll"),
    ARGUE_THE_CALL("argueTheCall"),
    CATCH_ROLL("catchRoll"),
    CONFUSION_ROLL("confusionRoll"),
    DAUNTLESS_ROLL("dauntlessRoll"),
    DODGE_ROLL("dodgeRoll"),
    ESCAPE_ROLL("escapeRoll"),
    FOUL_APPEARANCE_ROLL("foulAppearanceRoll"),
    GO_FOR_IT_ROLL("goForItRoll"),
    INTERCEPTION_ROLL("interceptionRoll"),
    LEAP_ROLL("leapRoll"),
    PASS_ROLL( "passRoll"),
    PICK_UP_ROLL("pickUpRoll"),
    RIGHT_STUFF_ROLL("rightStuffRoll"),
    REGENERATION_ROLL("regenerationRoll"),
    SAFE_THROW_ROLL("safeThrowRoll"),
    TENTACLES_SHADOWING_ROLL("tentaclesShadowingRoll"),
    SKILL_USE("skillUse"),
    RE_ROLL("reRoll"),
    TURN_END("turnEnd"),
    PLAYER_ACTION("playerAction"),
    FOUL("foul"),
    HAND_OVER("handOver"),
    INJURY("injury"),
    APOTHECARY_ROLL("apothecaryRoll"),
    APOTHECARY_CHOICE("apothecaryChoice"),
    THROW_IN("throwIn"),
    SCATTER_BALL("scatterBall"),
    BLOCK("block"),
    BLOCK_CHOICE("blockChoice"),
    SPECTATORS("spectators"),
    WEATHER("weather"),
    COIN_THROW("coinThrow"),
    RECEIVE_CHOICE("receiveChoice"),
    KICKOFF_RESULT("kickoffResult"),
    KICKOFF_SCATTER("kickoffScatter"),
    KICKOFF_EXTRA_REROLL("extraReRoll"),
    KICKOFF_RIOT("kickoffRiot"),
    KICKOFF_THROW_A_ROCK("kickoffThrowARock"),
    PUSHBACK("pushback"),
    REFEREE("referee"),
    KICKOFF_PITCH_INVASION("kickoffPitchInvasion"),
    THROW_TEAM_MATE_ROLL("throwTeamMateRoll"),
    SCATTER_PLAYER("scatterPlayer"),
    TIMEOUT_ENFORCED("timeoutEnforced"),
    WINNINGS_ROLL("winningsRoll"),
    FUMBBL_RESULT_UPLOAD("fumbblResultUpload"),
    FAN_FACTOR_ROLL("fanFactorRoll"),
    MOST_VALUABLE_PLAYERS("mostValuablePlayers"),
    DEFECTING_PLAYERS("defectingPlayers"),
    JUMP_UP_ROLL("jumpUpRoll"),
    STAND_UP_ROLL("standUpRoll"),
    BRIBES_ROLL("bribesRoll"),
    MASTER_CHEF_ROLL("masterChefRoll"),
    START_HALF("startHalf"),
    INDUCEMENT("inducement"),
    PILING_ON("pilingOn"),
    CHAINSAW_ROLL("chainsawRoll"),
    LEADER("leader"),
    SECRET_WEAPON_BAN("secretWeaponBan"),
    BLOOD_LUST_ROLL("bloodLustRoll"),
    HYPNOTIC_GAZE_ROLL("hypnoticGazeRoll"),
    BITE_SPECTATOR("biteSpectator"),
    ANIMOSITY_ROLL("animosityRoll"),
    RAISE_DEAD("raiseDead"),
    BLOCK_ROLL("blockRoll"),
    PENALTY_SHOOTOUT("penaltyShootout"),
    DOUBLE_HIRED_STAR_PLAYER("doubleHiredStarPlayer"),
    SPELL_EFFECT_ROLL("spellEffectRoll"),
    WIZARD_USE("wizardUse"),
    GAME_OPTIONS("gameOptions"),
    PASS_BLOCK("passBlock"),
    NO_PLAYERS_TO_FIELD("noPlayersToField"),
    PLAY_CARD("playCard"),
    CARD_DEACTIVATED("cardDeactivated"),
    BOMB_OUT_OF_BOUNDS("bombOutOfBounds"),
    PETTY_CASH("pettyCash"),
    INDUCEMENTS_BOUGHT("inducementsBought"),
    CARDS_BOUGHT("cardsBought"),
    CARD_EFFECT_ROLL("cardEffectRoll"),
    WEEPING_DAGGER_ROLL("weepingDaggerRoll"),
    DUMMY("dummy" );

    private String fName;

    ReportId(String pName) {
        this.fName = pName;
    }


    @Override
    public String getName() {
        return this.fName;
    }

    public IReport createReport() {
        switch (this) {
            case ALWAYS_HUNGRY_ROLL: {
                return new ReportSkillRoll(ALWAYS_HUNGRY_ROLL);
            }
            case CATCH_ROLL: {
                return new ReportSkillRoll(CATCH_ROLL);
            }
            case CONFUSION_ROLL: {
                return new ReportConfusionRoll();
            }
            case DAUNTLESS_ROLL: {
                return new ReportSkillRoll(DAUNTLESS_ROLL);
            }
            case DODGE_ROLL: {
                return new ReportSkillRoll(DODGE_ROLL);
            }
            case ESCAPE_ROLL: {
                return new ReportSkillRoll(ESCAPE_ROLL);
            }
            case FAN_FACTOR_ROLL: {
                return new ReportFanFactorRoll();
            }
            case FOUL_APPEARANCE_ROLL: {
                return new ReportSkillRoll(FOUL_APPEARANCE_ROLL);
            }
            case GO_FOR_IT_ROLL: {
                return new ReportSkillRoll(GO_FOR_IT_ROLL);
            }
            case INJURY: {
                return new ReportInjury();
            }
            case INTERCEPTION_ROLL: {
                return new ReportInterceptionRoll();
            }
            case LEAP_ROLL: {
                return new ReportSkillRoll(LEAP_ROLL);
            }
            case PASS_ROLL: {
                return new ReportPassRoll();
            }
            case PICK_UP_ROLL: {
                return new ReportSkillRoll(PICK_UP_ROLL);
            }
            case PLAYER_ACTION: {
                return new ReportPlayerAction();
            }
            case RE_ROLL: {
                return new ReportReRoll();
            }
            case REGENERATION_ROLL: {
                return new ReportSkillRoll(REGENERATION_ROLL);
            }
            case RIGHT_STUFF_ROLL: {
                return new ReportSkillRoll(RIGHT_STUFF_ROLL);
            }
            case SAFE_THROW_ROLL: {
                return new ReportSkillRoll(SAFE_THROW_ROLL);
            }
            case TENTACLES_SHADOWING_ROLL: {
                return new ReportTentaclesShadowingRoll();
            }
            case TURN_END: {
                return new ReportTurnEnd();
            }
            case APOTHECARY_ROLL: {
                return new ReportApothecaryRoll();
            }
            case SPECTATORS: {
                return new ReportSpectators();
            }
            case KICKOFF_THROW_A_ROCK: {
                return new ReportKickoffThrowARock();
            }
            case KICKOFF_PITCH_INVASION: {
                return new ReportKickoffPitchInvasion();
            }
            case THROW_TEAM_MATE_ROLL: {
                return new ReportSkillRoll(THROW_TEAM_MATE_ROLL);
            }
            case WINNINGS_ROLL: {
                return new ReportWinningsRoll();
            }
            case JUMP_UP_ROLL: {
                return new ReportSkillRoll(JUMP_UP_ROLL);
            }
            case STAND_UP_ROLL: {
                return new ReportStandUpRoll();
            }
            case BRIBES_ROLL: {
                return new ReportBribesRoll();
            }
            case MASTER_CHEF_ROLL: {
                return new ReportMasterChefRoll();
            }
            case CHAINSAW_ROLL: {
                return new ReportSkillRoll(CHAINSAW_ROLL);
            }
            case BLOOD_LUST_ROLL: {
                return new ReportSkillRoll(BLOOD_LUST_ROLL);
            }
            case HYPNOTIC_GAZE_ROLL: {
                return new ReportSkillRoll(HYPNOTIC_GAZE_ROLL);
            }
            case ANIMOSITY_ROLL: {
                return new ReportSkillRoll(ANIMOSITY_ROLL);
            }
            case BLOCK_ROLL: {
                return new ReportBlockRoll();
            }
            case PENALTY_SHOOTOUT: {
                return new ReportPenaltyShootout();
            }
            case SPELL_EFFECT_ROLL: {
                return new ReportSpecialEffectRoll();
            }
            case PILING_ON: {
                return new ReportPilingOn();
            }
            case WEATHER: {
                return new ReportWeather();
            }
            case KICKOFF_EXTRA_REROLL: {
                return new ReportKickoffExtraReRoll();
            }
            case KICKOFF_RESULT: {
                return new ReportKickoffResult();
            }
            case START_HALF: {
                return new ReportStartHalf();
            }
            case SCATTER_BALL: {
                return new ReportScatterBall();
            }
            case SCATTER_PLAYER: {
                return new ReportScatterPlayer();
            }
            case REFEREE: {
                return new ReportReferee();
            }
            case BITE_SPECTATOR: {
                return new ReportBiteSpectator();
            }
            case TIMEOUT_ENFORCED: {
                return new ReportTimeoutEnforced();
            }
            case WIZARD_USE: {
                return new ReportWizardUse();
            }
            default:
                return new DummyReport();
        }
    }
}


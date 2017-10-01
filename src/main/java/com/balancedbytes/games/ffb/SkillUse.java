/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.PlayerGender;
import com.balancedbytes.games.ffb.model.Player;

public enum SkillUse implements INamedObject
{
    WOULD_NOT_HELP("wouldNotHelp", "because it would not help"),
    NO_TEAM_MATE_IN_RANGE("noTeamMateInRange", "because no team-mate is in range"),
    STOP_OPPONENT("stopOpponent", "to stop %g opponent"),
    PUSH_BACK_OPPONENT("pushBackOpponent", "to push %g opponent back"),
    BRING_DOWN_OPPONENT("bringDownOppponent", "to bring %g opponent down"),
    AVOID_PUSH("avoidPush", "to avoid being pushed"),
    CANCEL_FEND("cancelFend", "to cancel %g opponent's Fend skill"),
    CANCEL_STAND_FIRM("cancelStandFirm", "to cancel %g opponent's Stand Firm skill"),
    STAY_AWAY_FROM_OPPONENT("stayAwayFromOpponent", "to stay away from %g opponent"),
    CATCH_BALL("catchBall", "to catch the ball"),
    STEAL_BALL("stealBall", "to steal the ball"),
    CANCEL_STRIP_BALL("cancelStripBall", "to cancel %g opponent's Strip Ball skill"),
    HALVE_KICKOFF_SCATTER("halveKickoffScatter", "to halve the kickoff scatter"),
    CANCEL_DODGE("cancelDodge", "to cancel %g opponent's Dodge skill"),
    AVOID_FALLING("avoidFalling", "to avoid falling"),
    CANCEL_TACKLE("cancelTackle", "to cancel %g opponent's Tackle skill"),
    INCREASE_STRENGTH_BY_1("increaseStrengthBy1", "to increase %g strength by 1"),
    CANCEL_DIVING_CATCH("cancelDivingCatch", "because players from both teams hinder each other");
    
    private String fName;
    private String fDescription;
    private static final String _PARAMETER_GENITIVE = "%g";

    private SkillUse(String pName, String pDescription) {
        this.fName = pName;
        this.fDescription = pDescription;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public String getDescription(Player pPlayer) {
        if (pPlayer != null) {
            return this.fDescription.replaceAll("%g", pPlayer.getPlayerGender().getGenitive());
        }
        return this.fDescription;
    }
}


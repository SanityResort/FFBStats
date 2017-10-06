/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.option;

import com.balancedbytes.games.ffb.model.Game;

public class UtilGameOption {
    public static boolean isOptionEnabled(Game pGame, GameOptionId pOptionId) {
        if (pGame == null || pOptionId == null) {
            return false;
        }
        GameOptionBoolean gameOption = (GameOptionBoolean)pGame.getOptions().getOptionWithDefault(pOptionId);
        return gameOption != null ? gameOption.isEnabled() : false;
    }

    public static int getIntOption(Game pGame, GameOptionId pOptionId) {
        if (pGame == null || pOptionId == null) {
            return 0;
        }
        GameOptionInt gameOption = (GameOptionInt)pGame.getOptions().getOptionWithDefault(pOptionId);
        return gameOption != null ? gameOption.getValue() : 0;
    }
}

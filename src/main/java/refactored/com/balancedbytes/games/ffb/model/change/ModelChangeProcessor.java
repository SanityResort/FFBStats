/*
 * Decompiled with CFR 0_114.
 */
package refactored.com.balancedbytes.games.ffb.model.change;


public class ModelChangeProcessor {

    public ModelChange transform(ModelChange pModelChange) {
        if (pModelChange == null || pModelChange.getChangeId() == null) {
            return null;
        }
        switch (pModelChange.getChangeId()) {

            case GAME_SET_HOME_FIRST_OFFENSE:
            case GAME_SET_HOME_PLAYING: {
                return new ModelChange(pModelChange.getChangeId(), pModelChange.getKey(), !((Boolean) pModelChange.getValue()));
            }
            case TURN_DATA_SET_TURN_NR:  {
                return new ModelChange(pModelChange.getChangeId(), this.isHomeData(pModelChange) ? "away" : "home", pModelChange.getValue());
            }
        }
        return new ModelChange(pModelChange.getChangeId(), pModelChange.getKey(), pModelChange.getValue());
    }

    private boolean isHomeData(ModelChange pChange) {
        return "home".equals(pChange.getKey());
    }
}


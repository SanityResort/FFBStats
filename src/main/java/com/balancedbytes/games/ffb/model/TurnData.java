/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.LeaderState;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonObjectOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.InducementSet;
import com.balancedbytes.games.ffb.model.change.ModelChange;
import com.balancedbytes.games.ffb.model.change.ModelChangeId;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class TurnData
implements IJsonSerializable {
    private boolean fHomeData;
    private int fTurnNr;
    private boolean fFirstTurnAfterKickoff;
    private boolean fTurnStarted;
    private int fReRolls;
    private int fApothecaries;
    private boolean fBlitzUsed;
    private boolean fFoulUsed;
    private boolean fReRollUsed;
    private boolean fHandOverUsed;
    private boolean fPassUsed;
    private boolean fCoachBanned;
    private InducementSet fInducementSet;
    private LeaderState fLeaderState;
    private transient Game fGame;

    public TurnData(Game pGame, boolean pHomeData) {
        this.fGame = pGame;
        this.fHomeData = pHomeData;
        this.fInducementSet = new InducementSet(this);
        this.fLeaderState = LeaderState.NONE;
    }

    public InducementSet getInducementSet() {
        return this.fInducementSet;
    }

    public int getTurnNr() {
        return this.fTurnNr;
    }

    public void setTurnNr(int pTurnNr) {
        if (pTurnNr == this.fTurnNr) {
            return;
        }
        this.fTurnNr = pTurnNr;
        this.notifyObservers(ModelChangeId.TURN_DATA_SET_TURN_NR, this.fTurnNr);
    }

    public boolean isTurnStarted() {
        return this.fTurnStarted;
    }

    public void setTurnStarted(boolean pTurnStarted) {
        if (pTurnStarted == this.fTurnStarted) {
            return;
        }
        this.fTurnStarted = pTurnStarted;
        this.notifyObservers(ModelChangeId.TURN_DATA_SET_TURN_STARTED, this.fTurnStarted);
    }

    public boolean isFirstTurnAfterKickoff() {
        return this.fFirstTurnAfterKickoff;
    }

    public void setFirstTurnAfterKickoff(boolean pFirstTurnAfterKickoff) {
        if (pFirstTurnAfterKickoff == this.fFirstTurnAfterKickoff) {
            return;
        }
        this.fFirstTurnAfterKickoff = pFirstTurnAfterKickoff;
        this.notifyObservers(ModelChangeId.TURN_DATA_SET_FIRST_TURN_AFTER_KICKOFF, this.fFirstTurnAfterKickoff);
    }

    public int getReRolls() {
        return this.fReRolls;
    }

    public void setReRolls(int pReRolls) {
        if (pReRolls == this.fReRolls) {
            return;
        }
        this.fReRolls = pReRolls;
        this.notifyObservers(ModelChangeId.TURN_DATA_SET_RE_ROLLS, this.fReRolls);
    }

    public boolean isBlitzUsed() {
        return this.fBlitzUsed;
    }

    public void setBlitzUsed(boolean pBlitzUsed) {
        if (pBlitzUsed == this.fBlitzUsed) {
            return;
        }
        this.fBlitzUsed = pBlitzUsed;
        this.notifyObservers(ModelChangeId.TURN_DATA_SET_BLITZ_USED, this.fBlitzUsed);
    }

    public boolean isFoulUsed() {
        return this.fFoulUsed;
    }

    public void setFoulUsed(boolean pFoulUsed) {
        if (pFoulUsed == this.fFoulUsed) {
            return;
        }
        this.fFoulUsed = pFoulUsed;
        this.notifyObservers(ModelChangeId.TURN_DATA_SET_FOUL_USED, this.fFoulUsed);
    }

    public boolean isReRollUsed() {
        return this.fReRollUsed;
    }

    public void setReRollUsed(boolean pReRollUsed) {
        if (pReRollUsed == this.fReRollUsed) {
            return;
        }
        this.fReRollUsed = pReRollUsed;
        this.notifyObservers(ModelChangeId.TURN_DATA_SET_RE_ROLL_USED, this.fReRollUsed);
    }

    public boolean isHandOverUsed() {
        return this.fHandOverUsed;
    }

    public void setHandOverUsed(boolean pHandOverUsed) {
        if (pHandOverUsed == this.fHandOverUsed) {
            return;
        }
        this.fHandOverUsed = pHandOverUsed;
        this.notifyObservers(ModelChangeId.TURN_DATA_SET_HAND_OVER_USED, this.fHandOverUsed);
    }

    public boolean isPassUsed() {
        return this.fPassUsed;
    }

    public void setPassUsed(boolean passUsed) {
        if (passUsed == this.fPassUsed) {
            return;
        }
        this.fPassUsed = passUsed;
        this.notifyObservers(ModelChangeId.TURN_DATA_SET_PASS_USED, this.fPassUsed);
    }

    public boolean isCoachBanned() {
        return this.fCoachBanned;
    }

    public void setCoachBanned(boolean coachBanned) {
        if (coachBanned == this.fCoachBanned) {
            return;
        }
        this.fCoachBanned = coachBanned;
        this.notifyObservers(ModelChangeId.TURN_DATA_SET_COACH_BANNED, this.fCoachBanned);
    }

    public int getApothecaries() {
        return this.fApothecaries;
    }

    public void setApothecaries(int apothecaries) {
        if (apothecaries == this.fApothecaries) {
            return;
        }
        this.fApothecaries = apothecaries;
        this.notifyObservers(ModelChangeId.TURN_DATA_SET_APOTHECARIES, this.fApothecaries);
    }

    public boolean isHomeData() {
        return this.fHomeData;
    }

    public Game getGame() {
        return this.fGame;
    }

    public void setGame(Game pGame) {
        this.fGame = pGame;
    }

    public boolean isApothecaryAvailable() {
        return this.getApothecaries() > 0;
    }

    public void useApothecary() {
        if (this.isApothecaryAvailable()) {
            this.setApothecaries(this.getApothecaries() - 1);
        }
    }

    public LeaderState getLeaderState() {
        return this.fLeaderState;
    }

    public void setLeaderState(LeaderState pLeaderState) {
        this.fLeaderState = pLeaderState;
    }

    public void startTurn() {
        this.setBlitzUsed(false);
        this.setHandOverUsed(false);
        this.setPassUsed(false);
        this.setFoulUsed(false);
        this.setReRollUsed(false);
    }

    public void init(TurnData pTurnData) {
        if (pTurnData != null) {
            this.fTurnNr = pTurnData.getTurnNr();
            this.fReRolls = pTurnData.getReRolls();
            this.fApothecaries = pTurnData.getApothecaries();
            this.fBlitzUsed = pTurnData.isBlitzUsed();
            this.fFoulUsed = pTurnData.isFoulUsed();
            this.fReRollUsed = pTurnData.isReRollUsed();
            this.fHandOverUsed = pTurnData.isHandOverUsed();
            this.fPassUsed = pTurnData.isPassUsed();
            this.fInducementSet.clear();
            this.fInducementSet.add(pTurnData.getInducementSet());
            this.fLeaderState = pTurnData.getLeaderState();
            this.fFirstTurnAfterKickoff = pTurnData.isFirstTurnAfterKickoff();
            this.fTurnStarted = pTurnData.isTurnStarted();
            this.fCoachBanned = pTurnData.isCoachBanned();
        }
    }

    private void notifyObservers(ModelChangeId pChangeId, Object pValue) {
        if (this.getGame() == null || pChangeId == null) {
            return;
        }
        String key = this.isHomeData() ? "home" : "away";
        ModelChange modelChange = new ModelChange(pChangeId, key, pValue);
        this.getGame().notifyObservers(modelChange);
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.HOME_DATA.addTo(jsonObject, this.fHomeData);
        IJsonOption.TURN_STARTED.addTo(jsonObject, this.fTurnStarted);
        IJsonOption.TURN_NR.addTo(jsonObject, this.fTurnNr);
        IJsonOption.FIRST_TURN_AFTER_KICKOFF.addTo(jsonObject, this.fFirstTurnAfterKickoff);
        IJsonOption.RE_ROLLS.addTo(jsonObject, this.fReRolls);
        IJsonOption.APOTHECARIES.addTo(jsonObject, this.fApothecaries);
        IJsonOption.BLITZ_USED.addTo(jsonObject, this.fBlitzUsed);
        IJsonOption.FOUL_USED.addTo(jsonObject, this.fFoulUsed);
        IJsonOption.RE_ROLL_USED.addTo(jsonObject, this.fReRollUsed);
        IJsonOption.HAND_OVER_USED.addTo(jsonObject, this.fHandOverUsed);
        IJsonOption.PASS_USED.addTo(jsonObject, this.fPassUsed);
        IJsonOption.COACH_BANNED.addTo(jsonObject, this.fCoachBanned);
        IJsonOption.LEADER_STATE.addTo(jsonObject, this.fLeaderState);
        if (this.fInducementSet != null) {
            IJsonOption.INDUCEMENT_SET.addTo(jsonObject, this.fInducementSet.toJsonValue());
        }
        return jsonObject;
    }

    @Override
    public TurnData initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fHomeData = IJsonOption.HOME_DATA.getFrom(jsonObject);
        this.fTurnStarted = IJsonOption.TURN_STARTED.getFrom(jsonObject);
        this.fTurnNr = IJsonOption.TURN_NR.getFrom(jsonObject);
        this.fFirstTurnAfterKickoff = IJsonOption.FIRST_TURN_AFTER_KICKOFF.getFrom(jsonObject);
        this.fReRolls = IJsonOption.RE_ROLLS.getFrom(jsonObject);
        this.fApothecaries = IJsonOption.APOTHECARIES.getFrom(jsonObject);
        this.fBlitzUsed = IJsonOption.BLITZ_USED.getFrom(jsonObject);
        this.fFoulUsed = IJsonOption.FOUL_USED.getFrom(jsonObject);
        this.fReRollUsed = IJsonOption.RE_ROLL_USED.getFrom(jsonObject);
        this.fHandOverUsed = IJsonOption.HAND_OVER_USED.getFrom(jsonObject);
        this.fPassUsed = IJsonOption.PASS_USED.getFrom(jsonObject);
        Boolean coachBanned = IJsonOption.COACH_BANNED.getFrom(jsonObject);
        this.fCoachBanned = coachBanned != null ? coachBanned : false;
        this.fLeaderState = (LeaderState)IJsonOption.LEADER_STATE.getFrom(jsonObject);
        this.fInducementSet = new InducementSet(this);
        this.fInducementSet.initFrom(IJsonOption.INDUCEMENT_SET.getFrom(jsonObject));
        return this;
    }
}


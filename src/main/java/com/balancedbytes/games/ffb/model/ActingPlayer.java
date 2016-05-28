/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.SkillFactory;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.JsonArrayOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.change.ModelChange;
import com.balancedbytes.games.ffb.model.change.ModelChangeId;
import com.balancedbytes.games.ffb.util.StringTool;
import com.balancedbytes.games.ffb.util.UtilCards;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.HashSet;
import java.util.Set;

public class ActingPlayer
implements IJsonSerializable {
    private String fPlayerId;
    private int fStrength;
    private int fCurrentMove;
    private boolean fGoingForIt;
    private boolean fDodging;
    private boolean fLeaping;
    private boolean fHasBlocked;
    private boolean fHasFouled;
    private boolean fHasPassed;
    private boolean fHasMoved;
    private boolean fHasFed;
    private PlayerAction fPlayerAction;
    private Set<Skill> fUsedSkills;
    private boolean fStandingUp;
    private boolean fSufferingBloodLust;
    private boolean fSufferingAnimosity;
    private transient Game fGame;

    public ActingPlayer(Game pGame) {
        this.fGame = pGame;
        this.fUsedSkills = new HashSet<Skill>();
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public void setPlayerId(String pPlayerId) {
        if (StringTool.isEqual(pPlayerId, this.fPlayerId)) {
            return;
        }
        this.fPlayerId = pPlayerId;
        this.fUsedSkills.clear();
        this.fCurrentMove = 0;
        this.fGoingForIt = false;
        this.fDodging = false;
        this.fHasBlocked = false;
        this.fHasFouled = false;
        this.fHasPassed = false;
        this.fHasMoved = false;
        this.fHasFed = false;
        this.fLeaping = false;
        this.fPlayerAction = null;
        this.fStandingUp = false;
        this.fSufferingBloodLust = false;
        this.fSufferingAnimosity = false;
        Player player = this.getGame().getPlayerById(this.getPlayerId());
        this.setStrength(player != null ? UtilCards.getPlayerStrength(this.getGame(), player) : 0);
        this.notifyObservers(ModelChangeId.ACTING_PLAYER_SET_PLAYER_ID, this.fPlayerId);
    }

    public Player getPlayer() {
        return this.getGame().getPlayerById(this.getPlayerId());
    }

    public void setPlayer(Player pPlayer) {
        if (pPlayer != null) {
            this.setPlayerId(pPlayer.getId());
        } else {
            this.setPlayerId(null);
        }
    }

    public int getCurrentMove() {
        return this.fCurrentMove;
    }

    public void setCurrentMove(int pCurrentMove) {
        if (pCurrentMove == this.fCurrentMove) {
            return;
        }
        this.fCurrentMove = pCurrentMove;
        this.notifyObservers(ModelChangeId.ACTING_PLAYER_SET_CURRENT_MOVE, this.fCurrentMove);
    }

    public boolean isGoingForIt() {
        return this.fGoingForIt;
    }

    public void setGoingForIt(boolean pGoingForIt) {
        if (pGoingForIt == this.fGoingForIt) {
            return;
        }
        this.fGoingForIt = pGoingForIt;
        this.notifyObservers(ModelChangeId.ACTING_PLAYER_SET_GOING_FOR_IT, this.fGoingForIt);
    }

    public PlayerAction getPlayerAction() {
        return this.fPlayerAction;
    }

    public void setPlayerAction(PlayerAction pPlayerAction) {
        if (pPlayerAction == this.fPlayerAction) {
            return;
        }
        this.fPlayerAction = pPlayerAction;
        this.notifyObservers(ModelChangeId.ACTING_PLAYER_SET_PLAYER_ACTION, this.fPlayerAction);
    }

    public boolean isSkillUsed(Skill pSkill) {
        return this.fUsedSkills.contains(pSkill);
    }

    public void markSkillUsed(Skill pSkill) {
        if (pSkill == null || this.isSkillUsed(pSkill)) {
            return;
        }
        this.fUsedSkills.add(pSkill);
        this.notifyObservers(ModelChangeId.ACTING_PLAYER_MARK_SKILL_USED, pSkill);
    }

    public String getRace() {
        if (this.getPlayer() != null) {
            return this.getPlayer().getRace();
        }
        return null;
    }

    public Skill[] getUsedSkills() {
        return this.fUsedSkills.toArray(new Skill[this.fUsedSkills.size()]);
    }

    public boolean hasBlocked() {
        return this.fHasBlocked;
    }

    public void setHasBlocked(boolean pHasBlocked) {
        if (pHasBlocked == this.fHasBlocked) {
            return;
        }
        this.fHasBlocked = pHasBlocked;
        this.notifyObservers(ModelChangeId.ACTING_PLAYER_SET_HAS_BLOCKED, this.fHasBlocked);
    }

    public boolean hasPassed() {
        return this.fHasPassed;
    }

    public void setHasPassed(boolean pHasPassed) {
        if (pHasPassed == this.fHasPassed) {
            return;
        }
        this.fHasPassed = pHasPassed;
        this.notifyObservers(ModelChangeId.ACTING_PLAYER_SET_HAS_PASSED, this.fHasPassed);
    }

    public boolean isDodging() {
        return this.fDodging;
    }

    public void setDodging(boolean pDodging) {
        if (pDodging == this.fDodging) {
            return;
        }
        this.fDodging = pDodging;
        this.notifyObservers(ModelChangeId.ACTING_PLAYER_SET_DODGING, this.fDodging);
    }

    public int getStrength() {
        return this.fStrength;
    }

    public void setStrength(int pStrength) {
        if (pStrength == this.fStrength) {
            return;
        }
        this.fStrength = pStrength;
        this.notifyObservers(ModelChangeId.ACTING_PLAYER_SET_STRENGTH, this.fStrength);
    }

    public boolean hasMoved() {
        return this.fHasMoved;
    }

    public void setHasMoved(boolean pHasMoved) {
        if (pHasMoved == this.fHasMoved) {
            return;
        }
        this.fHasMoved = pHasMoved;
        this.notifyObservers(ModelChangeId.ACTING_PLAYER_SET_HAS_MOVED, this.fHasMoved);
    }

    public boolean isLeaping() {
        return this.fLeaping;
    }

    public void setLeaping(boolean pLeaping) {
        if (pLeaping == this.fLeaping) {
            return;
        }
        this.fLeaping = pLeaping;
        this.notifyObservers(ModelChangeId.ACTING_PLAYER_SET_LEAPING, this.fLeaping);
    }

    public void setStandingUp(boolean pStandingUp) {
        if (pStandingUp == this.fStandingUp) {
            return;
        }
        this.fStandingUp = pStandingUp;
        this.notifyObservers(ModelChangeId.ACTING_PLAYER_SET_STANDING_UP, this.fStandingUp);
    }

    public boolean isStandingUp() {
        return this.fStandingUp;
    }

    public void setSufferingBloodLust(boolean pSufferingBloodLust) {
        if (pSufferingBloodLust == this.fSufferingBloodLust) {
            return;
        }
        this.fSufferingBloodLust = pSufferingBloodLust;
        this.notifyObservers(ModelChangeId.ACTING_PLAYER_SET_SUFFERING_BLOOD_LUST, this.fSufferingBloodLust);
    }

    public boolean isSufferingBloodLust() {
        return this.fSufferingBloodLust;
    }

    public void setSufferingAnimosity(boolean pSufferingAnimosity) {
        if (pSufferingAnimosity == this.fSufferingAnimosity) {
            return;
        }
        this.fSufferingAnimosity = pSufferingAnimosity;
        this.notifyObservers(ModelChangeId.ACTING_PLAYER_SET_SUFFERING_ANIMOSITY, this.fSufferingAnimosity);
    }

    public boolean isSufferingAnimosity() {
        return this.fSufferingAnimosity;
    }

    public boolean hasFed() {
        return this.fHasFed;
    }

    public void setHasFed(boolean pHasFed) {
        if (pHasFed == this.fHasFed) {
            return;
        }
        this.fHasFed = pHasFed;
        this.notifyObservers(ModelChangeId.ACTING_PLAYER_SET_HAS_FED, this.fHasFed);
    }

    public boolean hasFouled() {
        return this.fHasFouled;
    }

    public void setHasFouled(boolean pHasFouled) {
        if (pHasFouled == this.fHasFouled) {
            return;
        }
        this.fHasFouled = pHasFouled;
        this.notifyObservers(ModelChangeId.ACTING_PLAYER_SET_HAS_FOULED, this.fHasFouled);
    }

    public Game getGame() {
        return this.fGame;
    }

    public boolean hasActed() {
        return this.hasMoved() || this.hasFouled() || this.hasBlocked() || this.hasPassed() || this.fUsedSkills.size() > 0;
    }

    private void notifyObservers(ModelChangeId pChangeId, Object pValue) {
        if (this.getGame() == null || pChangeId == null) {
            return;
        }
        this.getGame().notifyObservers(new ModelChange(pChangeId, null, pValue));
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.CURRENT_MOVE.addTo(jsonObject, this.fCurrentMove);
        IJsonOption.GOING_FOR_IT.addTo(jsonObject, this.fGoingForIt);
        IJsonOption.HAS_BLOCKED.addTo(jsonObject, this.fHasBlocked);
        IJsonOption.HAS_FED.addTo(jsonObject, this.fHasFed);
        IJsonOption.HAS_FOULED.addTo(jsonObject, this.fHasFouled);
        IJsonOption.HAS_MOVED.addTo(jsonObject, this.fHasMoved);
        IJsonOption.HAS_PASSED.addTo(jsonObject, this.fHasPassed);
        IJsonOption.PLAYER_ACTION.addTo(jsonObject, this.fPlayerAction);
        IJsonOption.STANDING_UP.addTo(jsonObject, this.fStandingUp);
        IJsonOption.SUFFERING_ANIMOSITY.addTo(jsonObject, this.fSufferingAnimosity);
        IJsonOption.SUFFERING_BLOODLUST.addTo(jsonObject, this.fSufferingBloodLust);
        JsonArray usedSkillsArray = new JsonArray();
        for (Skill skill : this.getUsedSkills()) {
            usedSkillsArray.add(UtilJson.toJsonValue(skill));
        }
        IJsonOption.USED_SKILLS.addTo(jsonObject, usedSkillsArray);
        return jsonObject;
    }

    @Override
    public ActingPlayer initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fCurrentMove = IJsonOption.CURRENT_MOVE.getFrom(jsonObject);
        this.fGoingForIt = IJsonOption.GOING_FOR_IT.getFrom(jsonObject);
        this.fHasBlocked = IJsonOption.HAS_BLOCKED.getFrom(jsonObject);
        this.fHasFed = IJsonOption.HAS_FED.getFrom(jsonObject);
        this.fHasFouled = IJsonOption.HAS_FOULED.getFrom(jsonObject);
        this.fHasMoved = IJsonOption.HAS_MOVED.getFrom(jsonObject);
        this.fHasPassed = IJsonOption.HAS_PASSED.getFrom(jsonObject);
        this.fPlayerAction = (PlayerAction)IJsonOption.PLAYER_ACTION.getFrom(jsonObject);
        this.fStandingUp = IJsonOption.STANDING_UP.getFrom(jsonObject);
        this.fSufferingAnimosity = IJsonOption.SUFFERING_ANIMOSITY.getFrom(jsonObject);
        this.fSufferingBloodLust = IJsonOption.SUFFERING_BLOODLUST.getFrom(jsonObject);
        JsonArray usedSkillsArray = IJsonOption.USED_SKILLS.getFrom(jsonObject);
        this.fUsedSkills.clear();
        if (usedSkillsArray != null) {
            for (int i = 0; i < usedSkillsArray.size(); ++i) {
                this.fUsedSkills.add((Skill)UtilJson.toEnumWithName(new SkillFactory(), usedSkillsArray.get(i)));
            }
        }
        return this;
    }
}


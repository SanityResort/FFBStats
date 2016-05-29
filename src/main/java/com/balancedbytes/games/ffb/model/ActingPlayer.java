/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.SkillFactory;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.util.StringTool;
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

    public boolean isGoingForIt() {
        return this.fGoingForIt;
    }



    public PlayerAction getPlayerAction() {
        return this.fPlayerAction;
    }



    public boolean isSkillUsed(Skill pSkill) {
        return this.fUsedSkills.contains(pSkill);
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

    public boolean hasPassed() {
        return this.fHasPassed;
    }

    public int getStrength() {
        return this.fStrength;
    }

    public boolean hasMoved() {
        return this.fHasMoved;
    }

    public boolean isLeaping() {
        return this.fLeaping;
    }

    public boolean isStandingUp() {
        return this.fStandingUp;
    }

    public boolean hasFouled() {
        return this.fHasFouled;
    }

    public Game getGame() {
        return this.fGame;
    }

    public boolean hasActed() {
        return this.hasMoved() || this.hasFouled() || this.hasBlocked() || this.hasPassed() || this.fUsedSkills.size() > 0;
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


/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.PlayerGender;
import com.balancedbytes.games.ffb.PlayerType;
import com.balancedbytes.games.ffb.SeriousInjury;
import com.balancedbytes.games.ffb.SeriousInjuryFactory;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.SkillCategory;
import com.balancedbytes.games.ffb.SkillFactory;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class Player implements IJsonSerializable {

    private String fId;
    private int fNr;
    private Team fTeam;
    private String fName;
    private PlayerType fPlayerType;
    private PlayerGender fPlayerGender;
    private int fMovement;
    private int fStrength;
    private int fAgility;
    private int fArmour;
    private String fUrlPortrait;
    private String fUrlIconSet;
    private int fNrOfIcons;
    private String fPositionId;
    private transient int fIconSetIndex;
    private List<Skill> fSkills = new ArrayList<Skill>();
    private List<SeriousInjury> fLastingInjuries = new ArrayList<SeriousInjury>();
    private SeriousInjury fRecoveringInjury;
    private transient int fCurrentSpps;
    private transient boolean fInsideSkillList;
    private transient boolean fInsideInjuryList;
    private transient boolean fInjuryCurrent;

    public Player() {
        this.setGender(PlayerGender.MALE);
        this.fIconSetIndex = 0;
    }

    public String getName() {
        return this.fName;
    }

    public PlayerType getPlayerType() {
        return this.fPlayerType;
    }

    public void setType(PlayerType pType) {
        this.fPlayerType = pType;
    }

    public int getNr() {
        return this.fNr;
    }

    public int getAgility() {
        return this.fAgility;
    }

    public void setAgility(int pAgility) {
        this.fAgility = pAgility;
    }

    public int getArmour() {
        return this.fArmour;
    }

    public void setArmour(int pArmour) {
        this.fArmour = pArmour;
    }

    public int getMovement() {
        return this.fMovement;
    }

    public void setMovement(int pMovement) {
        this.fMovement = pMovement;
    }

    public int getStrength() {
        return this.fStrength;
    }

    public void setStrength(int pStrength) {
        this.fStrength = pStrength;
    }

    public void addLastingInjury(SeriousInjury pLastingInjury) {
        if (pLastingInjury != null) {
            this.fLastingInjuries.add(pLastingInjury);
        }
    }

    public SeriousInjury[] getLastingInjuries() {
        return this.fLastingInjuries.toArray(new SeriousInjury[this.fLastingInjuries.size()]);
    }

    public void addSkill(Skill pSkill) {
        if (!(pSkill == null || pSkill.getCategory() != SkillCategory.STAT_INCREASE && pSkill.getCategory() != SkillCategory.STAT_DECREASE && this.fSkills.contains(pSkill))) {
            this.fSkills.add(pSkill);
        }
    }

    public boolean removeSkill(Skill pSkill) {
        return this.fSkills.remove(pSkill);
    }

    public boolean hasSkill(Skill pSkill) {
        return this.fSkills.contains(pSkill);
    }

    public Skill[] getSkills() {
        return this.fSkills.toArray(new Skill[this.fSkills.size()]);
    }

    public String getUrlPortrait() {
        return this.fUrlPortrait;
    }

    public void setUrlPortrait(String pUrlPortrait) {
        this.fUrlPortrait = pUrlPortrait;
    }

    public String getUrlIconSet() {
        return this.fUrlIconSet;
    }

    public void setUrlIconSet(String pUrlIconSet) {
        this.fUrlIconSet = pUrlIconSet;
    }

    public int getNrOfIcons() {
        return this.fNrOfIcons;
    }

    public void setNrOfIcons(int pNrOfIcons) {
        this.fNrOfIcons = pNrOfIcons;
    }

    public Team getTeam() {
        return this.fTeam;
    }

    public void setTeam(Team pTeam) {
        this.fTeam = pTeam;
    }

    public String getId() {
        return this.fId;
    }

    public void setId(String pId) {
        this.fId = pId;
    }

    public PlayerGender getPlayerGender() {
        return this.fPlayerGender;
    }

    public SeriousInjury getRecoveringInjury() {
        return this.fRecoveringInjury;
    }

    public void setRecoveringInjury(SeriousInjury pCurrentInjury) {
        this.fRecoveringInjury = pCurrentInjury;
    }

    public int getCurrentSpps() {
        return this.fCurrentSpps;
    }

    public void setCurrentSpps(int pCurrentSpps) {
        this.fCurrentSpps = pCurrentSpps;
    }

    public void setName(String name) {
        this.fName = name;
    }

    public void setGender(PlayerGender gender) {
        this.fPlayerGender = gender;
    }

    public void setNr(int nr) {
        this.fNr = nr;
    }

    public int getIconSetIndex() {
        return this.fIconSetIndex;
    }

    public String getPositionId() {
        return this.fPositionId;
    }

    public void setPositionId(String pPositionId) {
        this.fPositionId = pPositionId;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Player other = (Player)obj;
        return this.getId().equals(other.getId());
    }

    public void init(Player pPlayer) {
        if (pPlayer == null) {
            return;
        }
        this.setMovement(pPlayer.getMovement());
        this.setStrength(pPlayer.getStrength());
        this.setAgility(pPlayer.getAgility());
        this.setArmour(pPlayer.getArmour());
        this.fLastingInjuries.clear();
        for (SeriousInjury injury : pPlayer.getLastingInjuries()) {
            this.addLastingInjury(injury);
        }
        this.setRecoveringInjury(pPlayer.getRecoveringInjury());
        this.setUrlPortrait(pPlayer.getUrlPortrait());
        this.setUrlIconSet(pPlayer.getUrlIconSet());
        this.setNrOfIcons(pPlayer.getNrOfIcons());
        this.fSkills.clear();
        for (Skill skill : pPlayer.getSkills()) {
            this.addSkill(skill);
        }
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fId);
        IJsonOption.PLAYER_NR.addTo(jsonObject, this.fNr);
        IJsonOption.POSITION_ID.addTo(jsonObject, this.fPositionId);
        IJsonOption.PLAYER_NAME.addTo(jsonObject, this.fName);
        IJsonOption.PLAYER_GENDER.addTo(jsonObject, this.fPlayerGender);
        IJsonOption.PLAYER_TYPE.addTo(jsonObject, this.fPlayerType);
        IJsonOption.MOVEMENT.addTo(jsonObject, this.fMovement);
        IJsonOption.STRENGTH.addTo(jsonObject, this.fStrength);
        IJsonOption.AGILITY.addTo(jsonObject, this.fAgility);
        IJsonOption.ARMOUR.addTo(jsonObject, this.fArmour);
        JsonArray lastingInjuries = new JsonArray();
        for (SeriousInjury injury : this.fLastingInjuries) {
            lastingInjuries.add(UtilJson.toJsonValue(injury));
        }
        IJsonOption.LASTING_INJURIES.addTo(jsonObject, lastingInjuries);
        IJsonOption.RECOVERING_INJURY.addTo(jsonObject, this.fRecoveringInjury);
        IJsonOption.URL_PORTRAIT.addTo(jsonObject, this.fUrlPortrait);
        IJsonOption.URL_ICON_SET.addTo(jsonObject, this.fUrlIconSet);
        IJsonOption.NR_OF_ICONS.addTo(jsonObject, this.fNrOfIcons);
        IJsonOption.POSITION_ICON_INDEX.addTo(jsonObject, this.fIconSetIndex);
        JsonArray skillArray = new JsonArray();
        for (Skill skill : this.fSkills) {
            skillArray.add(UtilJson.toJsonValue(skill));
        }
        IJsonOption.SKILL_ARRAY.addTo(jsonObject, skillArray);
        return jsonObject;
    }

    @Override
    public Player initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fNr = IJsonOption.PLAYER_NR.getFrom(jsonObject);
        this.fPositionId = IJsonOption.POSITION_ID.getFrom(jsonObject);
        this.fName = IJsonOption.PLAYER_NAME.getFrom(jsonObject);
        this.fPlayerGender = (PlayerGender)IJsonOption.PLAYER_GENDER.getFrom(jsonObject);
        this.fPlayerType = (PlayerType)IJsonOption.PLAYER_TYPE.getFrom(jsonObject);
        this.fMovement = IJsonOption.MOVEMENT.getFrom(jsonObject);
        this.fStrength = IJsonOption.STRENGTH.getFrom(jsonObject);
        this.fAgility = IJsonOption.AGILITY.getFrom(jsonObject);
        this.fArmour = IJsonOption.ARMOUR.getFrom(jsonObject);
        SeriousInjuryFactory seriousInjuryFactory = new SeriousInjuryFactory();
        this.fLastingInjuries.clear();
        JsonArray lastingInjuries = IJsonOption.LASTING_INJURIES.getFrom(jsonObject);
        for (int i = 0; i < lastingInjuries.size(); ++i) {
            this.fLastingInjuries.add((SeriousInjury)UtilJson.toEnumWithName(seriousInjuryFactory, lastingInjuries.get(i)));
        }
        this.fRecoveringInjury = (SeriousInjury)IJsonOption.RECOVERING_INJURY.getFrom(jsonObject);
        this.fUrlPortrait = IJsonOption.URL_PORTRAIT.getFrom(jsonObject);
        this.fUrlIconSet = IJsonOption.URL_ICON_SET.getFrom(jsonObject);
        this.fNrOfIcons = IJsonOption.NR_OF_ICONS.getFrom(jsonObject);
        this.fIconSetIndex = IJsonOption.POSITION_ICON_INDEX.getFrom(jsonObject);
        SkillFactory skillFactory = new SkillFactory();
        this.fSkills.clear();
        JsonArray skillArray = IJsonOption.SKILL_ARRAY.getFrom(jsonObject);
        for (int i2 = 0; i2 < skillArray.size(); ++i2) {
            this.fSkills.add((Skill)UtilJson.toEnumWithName(skillFactory, skillArray.get(i2)));
        }
        return this;
    }

}


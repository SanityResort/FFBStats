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
    public static final String XML_TAG = "player";
    private static final String _XML_ATTRIBUTE_ID = "id";
    private static final String _XML_ATTRIBUTE_NR = "nr";
    private static final String _XML_ATTRIBUTE_SIZE = "size";
    private static final String _XML_TAG_NAME = "name";
    private static final String _XML_TAG_TYPE = "type";
    private static final String _XML_TAG_GENDER = "gender";
    private static final String _XML_TAG_POSITION_ID = "positionId";
    private static final String _XML_TAG_SKILL_LIST = "skillList";
    private static final String _XML_TAG_SKILL = "skill";
    private static final String _XML_TAG_ICON_SET = "iconSet";
    private static final String _XML_TAG_PORTRAIT = "portrait";
    private static final String _XML_TAG_INJURY_LIST = "injuryList";
    private static final String _XML_TAG_INJURY = "injury";
    private static final String _XML_ATTRIBUTE_RECOVERING = "recovering";
    private static final String _XML_TAG_PLAYER_STATISTICS = "playerStatistics";
    private static final String _XML_ATTRIBUTE_CURRENT_SPPS = "currentSpps";
    private static final String _XML_TAG_MOVEMENT = "movement";
    private static final String _XML_TAG_STRENGTH = "strength";
    private static final String _XML_TAG_AGILITY = "agility";
    private static final String _XML_TAG_ARMOUR = "armour";
    private static final String _XML_TAG_SHORTHAND = "shorthand";
    private static final String _XML_TAG_RACE = "race";
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
    private transient RosterPosition fPosition;
    private transient int fCurrentSpps;
    private transient boolean fInsideSkillList;
    private transient boolean fInsideInjuryList;
    private transient boolean fInjuryCurrent;

    public Player() {
        this.setGender(PlayerGender.MALE);
        this.fIconSetIndex = 0;
        this.fPosition = new RosterPosition(null);
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

    public RosterPosition getPosition() {
        return this.fPosition;
    }

    public void updatePosition(RosterPosition pPosition) {
        this.fPosition = pPosition;
        if (this.fPosition != null) {
            this.setPositionId(this.fPosition.getId());
            if (this.getPlayerType() == null) {
                this.setType(this.fPosition.getType());
            }
            if (this.fPosition.getGender() != null) {
                this.setGender(this.fPosition.getGender());
            }
            this.setMovement(this.fPosition.getMovement());
            this.setStrength(this.fPosition.getStrength());
            this.setAgility(this.fPosition.getAgility());
            this.setArmour(this.fPosition.getArmour());
            this.fIconSetIndex = pPosition.findNextIconSetIndex();
            for (Skill skill2 : this.fPosition.getSkills()) {
                this.addSkill(skill2);
            }
            block13 : for (Skill skill2 : this.getSkills()) {
                switch (skill2) {
                    case MOVEMENT_INCREASE: {
                        ++this.fMovement;
                        continue block13;
                    }
                    case STRENGTH_INCREASE: {
                        ++this.fStrength;
                        continue block13;
                    }
                    case AGILITY_INCREASE: {
                        ++this.fAgility;
                        continue block13;
                    }
                    case ARMOUR_INCREASE: {
                        ++this.fArmour;
                        break;
                    }
                }
            }
            int oldMovement = this.getMovement();
            int oldArmour = this.getArmour();
            int oldAgility = this.getAgility();
            int oldStrength = this.getStrength();
            block14 : for (SeriousInjury injury : this.getLastingInjuries()) {
                switch (injury) {
                    case SMASHED_HIP: 
                    case SMASHED_ANKLE: {
                        if (this.fMovement <= 1 || oldMovement - this.fMovement >= 2) continue block14;
                        --this.fMovement;
                        continue block14;
                    }
                    case SERIOUS_CONCUSSION: 
                    case FRACTURED_SKULL: {
                        if (this.fArmour <= 1 || oldArmour - this.fArmour >= 2) continue block14;
                        --this.fArmour;
                        continue block14;
                    }
                    case BROKEN_NECK: {
                        if (this.fAgility <= 1 || oldAgility - this.fAgility >= 2) continue block14;
                        --this.fAgility;
                        continue block14;
                    }
                    case SMASHED_COLLAR_BONE: {
                        if (this.fStrength <= 1 || oldStrength - this.fStrength >= 2) continue block14;
                        --this.fStrength;
                        break;
                    }
                }
            }
        }
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

    public String getRace() {
        return this.getPosition().getRace();
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


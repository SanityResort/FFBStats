/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.PlayerGender;
import com.balancedbytes.games.ffb.PlayerGenderFactory;
import com.balancedbytes.games.ffb.PlayerType;
import com.balancedbytes.games.ffb.PlayerTypeFactory;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.SkillCategory;
import com.balancedbytes.games.ffb.SkillCategoryFactory;
import com.balancedbytes.games.ffb.SkillFactory;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.JsonArrayOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntArrayOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.Roster;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import com.balancedbytes.games.ffb.xml.IXmlReadable;
import com.balancedbytes.games.ffb.xml.IXmlSerializable;
import com.balancedbytes.games.ffb.xml.UtilXml;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.xml.transform.sax.TransformerHandler;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

public class RosterPosition
implements IXmlSerializable,
IJsonSerializable {
    public static final String XML_TAG = "position";
    private static final String _XML_ATTRIBUTE_ID = "id";
    private static final String _XML_ATTRIBUTE_VALUE = "value";
    private static final String _XML_ATTRIBUTE_SIZE = "size";
    private static final String _XML_TAG_QUANTITY = "quantity";
    private static final String _XML_TAG_NAME = "name";
    private static final String _XML_TAG_DISPLAY_NAME = "displayName";
    private static final String _XML_TAG_TYPE = "type";
    private static final String _XML_TAG_GENDER = "gender";
    private static final String _XML_TAG_COST = "cost";
    private static final String _XML_TAG_MOVEMENT = "movement";
    private static final String _XML_TAG_STRENGTH = "strength";
    private static final String _XML_TAG_AGILITY = "agility";
    private static final String _XML_TAG_ARMOUR = "armour";
    private static final String _XML_TAG_SHORTHAND = "shorthand";
    private static final String _XML_TAG_RACE = "race";
    private static final String _XML_TAG_UNDEAD = "undead";
    private static final String _XML_TAG_THRALL = "thrall";
    private static final String _XML_TAG_TEAM_WITH_POSITION_ID = "teamWithPositionId";
    private static final String _XML_TAG_SKILL_LIST = "skillList";
    private static final String _XML_TAG_SKILL = "skill";
    private static final String _XML_TAG_SKILLCATEGORY_LIST = "skillCategoryList";
    private static final String _XML_TAG_NORMAL = "normal";
    private static final String _XML_TAG_DOUBLE = "double";
    private static final String _XML_TAG_PORTRAIT = "portrait";
    private static final String _XML_TAG_ICON_SET = "iconSet";
    private String fId;
    private Roster fRoster;
    private String fName;
    private String fDisplayName;
    private String fShorthand;
    private PlayerType fType;
    private PlayerGender fGender;
    private int fQuantity;
    private int fCost;
    private int fMovement;
    private int fStrength;
    private int fAgility;
    private int fArmour;
    private String fRace;
    private boolean fUndead;
    private boolean fThrall;
    private String fTeamWithPositionId;
    private String fUrlPortrait;
    private String fUrlIconSet;
    private int fNrOfIcons;
    private int fCurrentIconSetIndex;
    private Map<Skill, Integer> fSkillValues;
    private Set<SkillCategory> fSkillCategoriesOnNormalRoll;
    private Set<SkillCategory> fSkillCategoriesOnDoubleRoll;
    private transient boolean fInsideSkillListTag;
    private transient boolean fInsideSkillCategoryListTag;
    private transient Integer fCurrentSkillValue;

    public RosterPosition() {
        this(null);
    }

    public RosterPosition(String pId) {
        this.fId = pId;
        this.fSkillValues = new LinkedHashMap<Skill, Integer>();
        this.fSkillCategoriesOnNormalRoll = new HashSet<SkillCategory>();
        this.fSkillCategoriesOnDoubleRoll = new HashSet<SkillCategory>();
        this.fCurrentIconSetIndex = -1;
    }

    public PlayerType getType() {
        return this.fType;
    }

    public void setGender(PlayerGender pGender) {
        this.fGender = pGender;
    }

    public PlayerGender getGender() {
        return this.fGender;
    }

    public int getAgility() {
        return this.fAgility;
    }

    public int getArmour() {
        return this.fArmour;
    }

    public int getMovement() {
        return this.fMovement;
    }

    public int getCost() {
        return this.fCost;
    }

    public String getName() {
        return this.fName;
    }

    public void setName(String name) {
        this.fName = name;
    }

    public void setShorthand(String pShorthand) {
        this.fShorthand = pShorthand;
    }

    public String getShorthand() {
        return this.fShorthand;
    }

    public int getStrength() {
        return this.fStrength;
    }

    public SkillCategory[] getSkillCategories(boolean pOnDouble) {
        if (pOnDouble) {
            return this.fSkillCategoriesOnDoubleRoll.toArray(new SkillCategory[this.fSkillCategoriesOnDoubleRoll.size()]);
        }
        return this.fSkillCategoriesOnNormalRoll.toArray(new SkillCategory[this.fSkillCategoriesOnNormalRoll.size()]);
    }

    public boolean isDoubleCategory(SkillCategory pSkillCategory) {
        return this.fSkillCategoriesOnDoubleRoll.contains(pSkillCategory);
    }

    public boolean hasSkill(Skill pSkill) {
        return this.fSkillValues.containsKey(pSkill);
    }

    public Skill[] getSkills() {
        return this.fSkillValues.keySet().toArray(new Skill[this.fSkillValues.size()]);
    }

    public int getSkillValue(Skill pSkill) {
        Integer value = this.fSkillValues.get(pSkill);
        return value != null ? value : 0;
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

    public int getQuantity() {
        return this.fQuantity;
    }

    public Roster getRoster() {
        return this.fRoster;
    }

    protected void setRoster(Roster pRoster) {
        this.fRoster = pRoster;
    }

    public String getId() {
        return this.fId;
    }

    public int getNrOfIcons() {
        return this.fNrOfIcons;
    }

    public void setNrOfIcons(int pNrOfIcons) {
        this.fNrOfIcons = pNrOfIcons;
    }

    public int findNextIconSetIndex() {
        if (this.fNrOfIcons > 0) {
            ++this.fCurrentIconSetIndex;
            if (this.fCurrentIconSetIndex >= this.fNrOfIcons) {
                this.fCurrentIconSetIndex = 0;
            }
        }
        return this.fCurrentIconSetIndex;
    }

    public void setType(PlayerType type) {
        this.fType = type;
    }

    public void setCost(int cost) {
        this.fCost = cost;
    }

    public void setMovement(int movement) {
        this.fMovement = movement;
    }

    public void setStrength(int strength) {
        this.fStrength = strength;
    }

    public void setAgility(int agility) {
        this.fAgility = agility;
    }

    public void setArmour(int armour) {
        this.fArmour = armour;
    }

    public void setQuantity(int quantity) {
        this.fQuantity = quantity;
    }

    public String getDisplayName() {
        return this.fDisplayName;
    }

    public void setDisplayName(String pDisplayName) {
        this.fDisplayName = pDisplayName;
    }

    public String getRace() {
        return this.fRace;
    }

    public void setRace(String pRace) {
        this.fRace = pRace;
    }

    public boolean isUndead() {
        return this.fUndead;
    }

    public void setUndead(boolean pUndead) {
        this.fUndead = pUndead;
    }

    public boolean isThrall() {
        return this.fThrall;
    }

    public void setThrall(boolean pThrall) {
        this.fThrall = pThrall;
    }

    public void setTeamWithPositionId(String pTeamWithPositionId) {
        this.fTeamWithPositionId = pTeamWithPositionId;
    }

    public String getTeamWithPositionId() {
        return this.fTeamWithPositionId;
    }

    @Override
    public void addToXml(TransformerHandler pHandler) {
        AttributesImpl attributes = new AttributesImpl();
        UtilXml.addAttribute(attributes, "id", this.getId());
        UtilXml.startElement(pHandler, "position", attributes);
        UtilXml.addValueElement(pHandler, "quantity", this.getQuantity());
        UtilXml.addValueElement(pHandler, "name", this.getName());
        UtilXml.addValueElement(pHandler, "shorthand", this.getShorthand());
        UtilXml.addValueElement(pHandler, "type", this.getType() != null ? this.getType().getName() : null);
        UtilXml.addValueElement(pHandler, "gender", this.getGender() != null ? this.getGender().getName() : null);
        UtilXml.addValueElement(pHandler, "displayName", this.getDisplayName());
        UtilXml.addValueElement(pHandler, "cost", this.getCost());
        UtilXml.addValueElement(pHandler, "movement", this.getMovement());
        UtilXml.addValueElement(pHandler, "strength", this.getStrength());
        UtilXml.addValueElement(pHandler, "agility", this.getAgility());
        UtilXml.addValueElement(pHandler, "armour", this.getArmour());
        UtilXml.addValueElement(pHandler, "race", this.getRace());
        UtilXml.addValueElement(pHandler, "undead", this.isUndead());
        UtilXml.addValueElement(pHandler, "thrall", this.isThrall());
        UtilXml.addValueElement(pHandler, "teamWithPositionId", this.getTeamWithPositionId());
        UtilXml.addValueElement(pHandler, "portrait", this.getUrlPortrait());
        attributes = new AttributesImpl();
        UtilXml.addAttribute(attributes, "size", this.getNrOfIcons());
        UtilXml.startElement(pHandler, "iconSet", attributes);
        UtilXml.addCharacters(pHandler, this.getUrlIconSet());
        UtilXml.endElement(pHandler, "iconSet");
        UtilXml.addValueElement(pHandler, "teamWithPositionId", this.getTeamWithPositionId());
        UtilXml.startElement(pHandler, "skillCategoryList");
        for (SkillCategory skillCategory : this.getSkillCategories(false)) {
            UtilXml.addValueElement(pHandler, "normal", skillCategory.getName());
        }
        for (SkillCategory skillCategory : this.getSkillCategories(true)) {
            UtilXml.addValueElement(pHandler, "double", skillCategory.getName());
        }
        UtilXml.endElement(pHandler, "skillCategoryList");
        UtilXml.startElement(pHandler, "skillList");
        UtilXml.endElement(pHandler, "skillList");
        UtilXml.endElement(pHandler, "position");
    }

    @Override
    public String toXml(boolean pIndent) {
        return UtilXml.toXml(this, pIndent);
    }

    @Override
    public IXmlReadable startXmlElement(String pXmlTag, Attributes pXmlAttributes) {
        if (this.fInsideSkillListTag) {
            if ("skill".equals(pXmlTag)) {
                String skillValue = UtilXml.getStringAttribute(pXmlAttributes, "value");
                this.fCurrentSkillValue = StringTool.isProvided(skillValue) ? Integer.valueOf(Integer.parseInt(skillValue)) : null;
            }
        } else {
            if ("position".equals(pXmlTag)) {
                this.fId = pXmlAttributes.getValue("id").trim();
            }
            if ("skillCategoryList".equals(pXmlTag)) {
                this.fInsideSkillCategoryListTag = true;
            }
            if ("iconSet".equals(pXmlTag)) {
                this.setNrOfIcons(UtilXml.getIntAttribute(pXmlAttributes, "size"));
            }
            if ("skillList".equals(pXmlTag)) {
                this.fInsideSkillListTag = true;
            }
        }
        return this;
    }

    @Override
    public boolean endXmlElement(String pTag, String pValue) {
        boolean complete = "position".equals(pTag);
        if (complete) {
            if (!StringTool.isProvided(this.getShorthand()) && StringTool.isProvided(this.getName())) {
                this.setShorthand(this.getName().substring(0, 1));
            }
        } else if (this.fInsideSkillListTag) {
            Skill skill;
            if ("skillList".equals(pTag)) {
                this.fInsideSkillListTag = false;
            }
            if ("skill".equals(pTag) && (skill = new SkillFactory().forName(pValue)) != null) {
                this.fSkillValues.put(skill, this.fCurrentSkillValue);
            }
        } else if (this.fInsideSkillCategoryListTag) {
            SkillCategory pSkillCategory;
            if ("skillCategoryList".equals(pTag)) {
                this.fInsideSkillCategoryListTag = false;
            }
            if ("normal".equals(pTag) && (pSkillCategory = new SkillCategoryFactory().forName(pValue)) != null) {
                this.fSkillCategoriesOnNormalRoll.add(pSkillCategory);
            }
            if ("double".equals(pTag) && (pSkillCategory = new SkillCategoryFactory().forName(pValue)) != null) {
                this.fSkillCategoriesOnDoubleRoll.add(pSkillCategory);
            }
        } else {
            if ("portrait".equals(pTag)) {
                this.setUrlPortrait(pValue);
            }
            if ("iconSet".equals(pTag)) {
                this.setUrlIconSet(pValue);
                if (this.getNrOfIcons() < 1) {
                    this.setNrOfIcons(1);
                }
            }
            if ("quantity".equals(pTag)) {
                this.setQuantity(Integer.parseInt(pValue));
            }
            if ("name".equals(pTag)) {
                this.setName(pValue);
            }
            if ("displayName".equals(pTag)) {
                this.setDisplayName(pValue);
            }
            if ("shorthand".equals(pTag)) {
                this.setShorthand(pValue);
            }
            if ("type".equals(pTag)) {
                this.setType(new PlayerTypeFactory().forName(pValue));
            }
            if ("gender".equals(pTag)) {
                this.setGender(new PlayerGenderFactory().forName(pValue));
            }
            if ("cost".equals(pTag)) {
                this.setCost(Integer.parseInt(pValue));
            }
            if ("movement".equals(pTag)) {
                this.setMovement(Integer.parseInt(pValue));
            }
            if ("strength".equals(pTag)) {
                this.setStrength(Integer.parseInt(pValue));
            }
            if ("agility".equals(pTag)) {
                this.setAgility(Integer.parseInt(pValue));
            }
            if ("armour".equals(pTag)) {
                this.setArmour(Integer.parseInt(pValue));
            }
            if ("race".equals(pTag)) {
                this.setRace(pValue);
            }
            if ("undead".equals(pTag)) {
                this.setUndead(Boolean.parseBoolean(pValue));
            }
            if ("thrall".equals(pTag)) {
                this.setThrall(Boolean.parseBoolean(pValue));
            }
            if ("teamWithPositionId".equals(pTag)) {
                this.setTeamWithPositionId(pValue);
            }
        }
        return complete;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.POSITION_ID.addTo(jsonObject, this.fId);
        IJsonOption.POSITION_NAME.addTo(jsonObject, this.fName);
        IJsonOption.SHORTHAND.addTo(jsonObject, this.fShorthand);
        IJsonOption.DISPLAY_NAME.addTo(jsonObject, this.fDisplayName);
        IJsonOption.PLAYER_TYPE.addTo(jsonObject, this.fType);
        IJsonOption.PLAYER_GENDER.addTo(jsonObject, this.fGender);
        IJsonOption.QUANTITY.addTo(jsonObject, this.fQuantity);
        IJsonOption.MOVEMENT.addTo(jsonObject, this.fMovement);
        IJsonOption.STRENGTH.addTo(jsonObject, this.fStrength);
        IJsonOption.AGILITY.addTo(jsonObject, this.fAgility);
        IJsonOption.ARMOUR.addTo(jsonObject, this.fArmour);
        IJsonOption.COST.addTo(jsonObject, this.fCost);
        IJsonOption.RACE.addTo(jsonObject, this.fRace);
        IJsonOption.UNDEAD.addTo(jsonObject, this.fUndead);
        IJsonOption.THRALL.addTo(jsonObject, this.fThrall);
        IJsonOption.TEAM_WITH_POSITION_ID.addTo(jsonObject, this.fTeamWithPositionId);
        IJsonOption.URL_PORTRAIT.addTo(jsonObject, this.fUrlPortrait);
        IJsonOption.URL_ICON_SET.addTo(jsonObject, this.fUrlIconSet);
        IJsonOption.NR_OF_ICONS.addTo(jsonObject, this.fNrOfIcons);
        JsonArray skillCategoriesNormal = new JsonArray();
        for (SkillCategory skillCategory : this.getSkillCategories(false)) {
            skillCategoriesNormal.add(UtilJson.toJsonValue(skillCategory));
        }
        IJsonOption.SKILL_CATEGORIES_NORMAL.addTo(jsonObject, skillCategoriesNormal);
        JsonArray skillCategoriesDouble = new JsonArray();
        for (SkillCategory skillCategory : this.getSkillCategories(true)) {
            skillCategoriesDouble.add(UtilJson.toJsonValue(skillCategory));
        }
        IJsonOption.SKILL_CATEGORIES_DOUBLE.addTo(jsonObject, skillCategoriesDouble);
        JsonArray skillArray = new JsonArray();
        ArrayList<Integer> skillValues = new ArrayList<Integer>();
        for (Skill skill : this.getSkills()) {
            skillArray.add(UtilJson.toJsonValue(skill));
            skillValues.add(this.getSkillValue(skill));
        }
        if (skillArray.size() > 0) {
            IJsonOption.SKILL_ARRAY.addTo(jsonObject, skillArray);
        }
        if (skillValues.size() > 0) {
            IJsonOption.SKILL_VALUES.addTo(jsonObject, skillValues);
        }
        return jsonObject;
    }

    @Override
    public RosterPosition initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fId = IJsonOption.POSITION_ID.getFrom(jsonObject);
        this.fName = IJsonOption.POSITION_NAME.getFrom(jsonObject);
        this.fShorthand = IJsonOption.SHORTHAND.getFrom(jsonObject);
        this.fDisplayName = IJsonOption.DISPLAY_NAME.getFrom(jsonObject);
        this.fType = (PlayerType)IJsonOption.PLAYER_TYPE.getFrom(jsonObject);
        this.fGender = (PlayerGender)IJsonOption.PLAYER_GENDER.getFrom(jsonObject);
        this.fQuantity = IJsonOption.QUANTITY.getFrom(jsonObject);
        this.fMovement = IJsonOption.MOVEMENT.getFrom(jsonObject);
        this.fStrength = IJsonOption.STRENGTH.getFrom(jsonObject);
        this.fAgility = IJsonOption.AGILITY.getFrom(jsonObject);
        this.fArmour = IJsonOption.ARMOUR.getFrom(jsonObject);
        this.fCost = IJsonOption.COST.getFrom(jsonObject);
        this.fRace = IJsonOption.RACE.getFrom(jsonObject);
        this.fUndead = IJsonOption.UNDEAD.getFrom(jsonObject);
        this.fThrall = IJsonOption.THRALL.getFrom(jsonObject);
        this.fTeamWithPositionId = IJsonOption.TEAM_WITH_POSITION_ID.getFrom(jsonObject);
        this.fUrlPortrait = IJsonOption.URL_PORTRAIT.getFrom(jsonObject);
        this.fUrlIconSet = IJsonOption.URL_ICON_SET.getFrom(jsonObject);
        this.fNrOfIcons = IJsonOption.NR_OF_ICONS.getFrom(jsonObject);
        SkillCategoryFactory skillCategoryFactory = new SkillCategoryFactory();
        this.fSkillCategoriesOnNormalRoll.clear();
        JsonArray skillCategoriesNormal = IJsonOption.SKILL_CATEGORIES_NORMAL.getFrom(jsonObject);
        for (int i = 0; i < skillCategoriesNormal.size(); ++i) {
            this.fSkillCategoriesOnNormalRoll.add((SkillCategory)UtilJson.toEnumWithName(skillCategoryFactory, skillCategoriesNormal.get(i)));
        }
        this.fSkillCategoriesOnDoubleRoll.clear();
        JsonArray skillCategoriesDouble = IJsonOption.SKILL_CATEGORIES_DOUBLE.getFrom(jsonObject);
        for (int i = 0; i < skillCategoriesDouble.size(); ++i) {
            this.fSkillCategoriesOnDoubleRoll.add((SkillCategory)UtilJson.toEnumWithName(skillCategoryFactory, skillCategoriesDouble.get(i)));
        }
        this.fSkillValues.clear();
        JsonArray skillArray = IJsonOption.SKILL_ARRAY.getFrom(jsonObject);
        int[] skillValues = IJsonOption.SKILL_VALUES.getFrom(jsonObject);
        if (skillArray != null && skillArray.size() > 0 && ArrayTool.isProvided(skillValues)) {
            SkillFactory skillFactory = new SkillFactory();
            for (int i = 0; i < skillArray.size(); ++i) {
                Skill skill = (Skill)UtilJson.toEnumWithName(skillFactory, skillArray.get(i));
                this.fSkillValues.put(skill, skillValues[i]);
            }
        }
        return this;
    }
}


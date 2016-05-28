/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.SkillFactory;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonObjectOption;
import com.balancedbytes.games.ffb.json.JsonStringArrayOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.InducementSet;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClientCommandBuyInducements
extends NetCommand {
    private String fTeamId;
    private int fAvailableGold;
    private InducementSet fInducementSet;
    private List<String> fStarPlayerPositionIds = new ArrayList<String>();
    private List<String> fMercenaryPositionIds = new ArrayList<String>();
    private List<Skill> fMercenarySkills = new ArrayList<Skill>();

    public ClientCommandBuyInducements() {
    }

    public ClientCommandBuyInducements(String pTeamId, int pAvailableGold, InducementSet pInducementSet, String[] pStarPlayerPositionIds, String[] pMercenaryPositionIds, Skill[] pMercenarySkills) {
        this();
        this.fTeamId = pTeamId;
        this.fAvailableGold = pAvailableGold;
        this.fInducementSet = pInducementSet;
        if (ArrayTool.isProvided(pStarPlayerPositionIds)) {
            for (String starPlayerPositionId : pStarPlayerPositionIds) {
                this.addStarPlayerPositionId(starPlayerPositionId);
            }
        }
        if (ArrayTool.isProvided(pMercenaryPositionIds) && ArrayTool.isProvided(pMercenarySkills)) {
            for (int i = 0; i < pMercenaryPositionIds.length; ++i) {
                this.addMercenaryPosition(pMercenaryPositionIds[i], pMercenarySkills[i]);
            }
        }
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_BUY_INDUCEMENTS;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public InducementSet getInducementSet() {
        return this.fInducementSet;
    }

    public String[] getStarPlayerPositionIds() {
        return this.fStarPlayerPositionIds.toArray(new String[this.fStarPlayerPositionIds.size()]);
    }

    public int getNrOfStarPlayerPositions() {
        return this.fStarPlayerPositionIds.size();
    }

    public void addStarPlayerPositionId(String pStarPlayerPositionId) {
        this.fStarPlayerPositionIds.add(pStarPlayerPositionId);
    }

    public void addMercenaryPosition(String pMercenaryPositionId, Skill pMercenarySkill) {
        this.fMercenaryPositionIds.add(pMercenaryPositionId);
        this.fMercenarySkills.add(pMercenarySkill);
    }

    public int getNrOfMercenaryPositions() {
        return this.fMercenaryPositionIds.size();
    }

    public String[] getMercenaryPositionIds() {
        return this.fMercenaryPositionIds.toArray(new String[this.fMercenaryPositionIds.size()]);
    }

    public Skill[] getMercenarySkills() {
        return this.fMercenarySkills.toArray(new Skill[this.fMercenarySkills.size()]);
    }

    public int getAvailableGold() {
        return this.fAvailableGold;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        if (this.fInducementSet != null) {
            IJsonOption.INDUCEMENT_SET.addTo(jsonObject, this.fInducementSet.toJsonValue());
        }
        IJsonOption.STAR_PLAYER_POSTION_IDS.addTo(jsonObject, this.fStarPlayerPositionIds);
        IJsonOption.AVAILABLE_GOLD.addTo(jsonObject, this.fAvailableGold);
        IJsonOption.MERCENARY_POSTION_IDS.addTo(jsonObject, this.fMercenaryPositionIds);
        String[] mercenarySkillNames = new String[this.fMercenarySkills.size()];
        for (int i = 0; i < mercenarySkillNames.length; ++i) {
            Skill skill = this.fMercenarySkills.get(i);
            mercenarySkillNames[i] = skill != null ? skill.getName() : "";
        }
        IJsonOption.MERCENARY_SKILLS.addTo(jsonObject, mercenarySkillNames);
        return jsonObject;
    }

    @Override
    public ClientCommandBuyInducements initFrom(JsonValue pJsonValue) {
        String[] starPlayerPositionIds;
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fInducementSet = new InducementSet();
        JsonObject inducementSetObject = IJsonOption.INDUCEMENT_SET.getFrom(jsonObject);
        if (inducementSetObject != null) {
            this.fInducementSet.initFrom(inducementSetObject);
        }
        for (String positionId : starPlayerPositionIds = IJsonOption.STAR_PLAYER_POSTION_IDS.getFrom(jsonObject)) {
            this.addStarPlayerPositionId(positionId);
        }
        this.fAvailableGold = IJsonOption.AVAILABLE_GOLD.getFrom(jsonObject);
        String[] mercenaryPositionIds = IJsonOption.MERCENARY_POSTION_IDS.getFrom(jsonObject);
        String[] mercenarySkillNames = IJsonOption.MERCENARY_SKILLS.getFrom(jsonObject);
        if (StringTool.isProvided(mercenaryPositionIds) && StringTool.isProvided(mercenarySkillNames)) {
            SkillFactory skillFactory = new SkillFactory();
            for (int i = 0; i < mercenaryPositionIds.length; ++i) {
                this.addMercenaryPosition(mercenaryPositionIds[i], skillFactory.forName(mercenarySkillNames[i]));
            }
        }
        return this;
    }
}


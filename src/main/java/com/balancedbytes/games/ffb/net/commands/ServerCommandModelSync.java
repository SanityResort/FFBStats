/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.SoundId;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonLongOption;
import com.balancedbytes.games.ffb.json.JsonObjectOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.Animation;
import com.balancedbytes.games.ffb.model.change.ModelChangeList;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ServerCommand;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.balancedbytes.games.ffb.report.ReportList;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ServerCommandModelSync
extends ServerCommand {
    private ModelChangeList fModelChanges = new ModelChangeList();
    private ReportList fReportList = new ReportList();
    private Animation fAnimation;
    private SoundId fSound;
    private long fGameTime;
    private long fTurnTime;

    public ServerCommandModelSync() {
    }

    public ServerCommandModelSync(ModelChangeList pModelChanges, ReportList pReportList, Animation pAnimation, SoundId pSound, long pGameTime, long pTurnTime) {
        this();
        this.fModelChanges.add(pModelChanges);
        this.fReportList.add(pReportList);
        this.fAnimation = pAnimation;
        this.fSound = pSound;
        this.fGameTime = pGameTime;
        this.fTurnTime = pTurnTime;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_MODEL_SYNC;
    }

    public ModelChangeList getModelChanges() {
        return this.fModelChanges;
    }

    public ReportList getReportList() {
        return this.fReportList;
    }

    public Animation getAnimation() {
        return this.fAnimation;
    }

    public SoundId getSound() {
        return this.fSound;
    }

    public long getGameTime() {
        return this.fGameTime;
    }

    public long getTurnTime() {
        return this.fTurnTime;
    }

    public ServerCommandModelSync transform() {
        Animation transformedAnimation = this.getAnimation() != null ? this.getAnimation().transform() : null;
        ServerCommandModelSync transformedCommand = new ServerCommandModelSync(this.getModelChanges(), this.getReportList().transform(), transformedAnimation, this.getSound(), this.getGameTime(), this.getTurnTime());
        transformedCommand.setCommandNr(this.getCommandNr());
        return transformedCommand;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.COMMAND_NR.addTo(jsonObject, this.getCommandNr());
        if (this.fModelChanges != null) {
            IJsonOption.MODEL_CHANGE_LIST.addTo(jsonObject, this.fModelChanges.toJsonValue());
        }
        if (this.fReportList != null) {
            IJsonOption.REPORT_LIST.addTo(jsonObject, this.fReportList.toJsonValue());
        }
        if (this.fAnimation != null) {
            IJsonOption.ANIMATION.addTo(jsonObject, this.fAnimation.toJsonValue());
        }
        IJsonOption.SOUND.addTo(jsonObject, this.fSound);
        IJsonOption.GAME_TIME.addTo(jsonObject, this.fGameTime);
        IJsonOption.TURN_TIME.addTo(jsonObject, this.fTurnTime);
        return jsonObject;
    }

    @Override
    public ServerCommandModelSync initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.setCommandNr(IJsonOption.COMMAND_NR.getFrom(jsonObject));
        JsonObject modelChangeListObject = IJsonOption.MODEL_CHANGE_LIST.getFrom(jsonObject);
        this.fModelChanges = new ModelChangeList();
        if (modelChangeListObject != null) {
            this.fModelChanges.initFrom(modelChangeListObject);
        }
        this.fReportList = new ReportList();
        JsonObject reportListObject = IJsonOption.REPORT_LIST.getFrom(jsonObject);
        if (reportListObject != null) {
            this.fReportList.initFrom(reportListObject);
        }
        this.fAnimation = null;
        JsonObject animationObject = IJsonOption.ANIMATION.getFrom(jsonObject);
        if (animationObject != null) {
            this.fAnimation = new Animation().initFrom(animationObject);
        }
        this.fSound = (SoundId)IJsonOption.SOUND.getFrom(jsonObject);
        this.fGameTime = IJsonOption.GAME_TIME.getFrom(jsonObject);
        this.fTurnTime = IJsonOption.TURN_TIME.getFrom(jsonObject);
        return this;
    }
}


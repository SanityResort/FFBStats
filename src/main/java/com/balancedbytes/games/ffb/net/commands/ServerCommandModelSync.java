/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.SoundId;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.report.ReportList;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ServerCommandModelSync
extends ServerCommand {
    private ReportList fReportList = new ReportList();
    private SoundId fSound;
    private long fGameTime;
    private long fTurnTime;

    public ServerCommandModelSync() {
    }

    public ServerCommandModelSync(ReportList pReportList, SoundId pSound, long pGameTime, long pTurnTime) {
        this();
        this.fReportList.add(pReportList);
        this.fSound = pSound;
        this.fGameTime = pGameTime;
        this.fTurnTime = pTurnTime;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_MODEL_SYNC;
    }

    public ReportList getReportList() {
        return this.fReportList;
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
        ServerCommandModelSync transformedCommand = new ServerCommandModelSync(this.getReportList().transform(), this.getSound(), this.getGameTime(), this.getTurnTime());
        transformedCommand.setCommandNr(this.getCommandNr());
        return transformedCommand;
    }

    @Override
    public ServerCommandModelSync initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.setCommandNr(IJsonOption.COMMAND_NR.getFrom(jsonObject));
        this.fReportList = new ReportList();
        JsonObject reportListObject = IJsonOption.REPORT_LIST.getFrom(jsonObject);
        if (reportListObject != null) {
            this.fReportList.initFrom(reportListObject);
        }
        JsonObject animationObject = IJsonOption.ANIMATION.getFrom(jsonObject);

        this.fSound = (SoundId)IJsonOption.SOUND.getFrom(jsonObject);
        this.fGameTime = IJsonOption.GAME_TIME.getFrom(jsonObject);
        this.fTurnTime = IJsonOption.TURN_TIME.getFrom(jsonObject);
        return this;
    }
}


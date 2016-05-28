/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.BlockResult;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntArrayOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.UtilReport;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportBlockChoice
implements IReport {
    private int fNrOfDice;
    private int[] fBlockRoll;
    private int fDiceIndex;
    private BlockResult fBlockResult;
    private String fDefenderId;

    public ReportBlockChoice() {
    }

    public ReportBlockChoice(int pNrOfDice, int[] pBlockRoll, int pDiceIndex, BlockResult pBlockResult, String pDefenderId) {
        this.fNrOfDice = pNrOfDice;
        this.fBlockRoll = pBlockRoll;
        this.fDiceIndex = pDiceIndex;
        this.fBlockResult = pBlockResult;
        this.fDefenderId = pDefenderId;
    }

    @Override
    public ReportId getId() {
        return ReportId.BLOCK_CHOICE;
    }

    public int getNrOfDice() {
        return this.fNrOfDice;
    }

    public int[] getBlockRoll() {
        return this.fBlockRoll;
    }

    public int getDiceIndex() {
        return this.fDiceIndex;
    }

    public BlockResult getBlockResult() {
        return this.fBlockResult;
    }

    public String getDefenderId() {
        return this.fDefenderId;
    }

    @Override
    public IReport transform() {
        return new ReportBlockChoice(this.getNrOfDice(), this.getBlockRoll(), this.getDiceIndex(), this.getBlockResult(), this.getDefenderId());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.NR_OF_DICE.addTo(jsonObject, this.fNrOfDice);
        IJsonOption.BLOCK_ROLL.addTo(jsonObject, this.fBlockRoll);
        IJsonOption.DICE_INDEX.addTo(jsonObject, this.fDiceIndex);
        IJsonOption.BLOCK_RESULT.addTo(jsonObject, this.fBlockResult);
        IJsonOption.DEFENDER_ID.addTo(jsonObject, this.fDefenderId);
        return jsonObject;
    }

    @Override
    public ReportBlockChoice initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fNrOfDice = IJsonOption.NR_OF_DICE.getFrom(jsonObject);
        this.fBlockRoll = IJsonOption.BLOCK_ROLL.getFrom(jsonObject);
        this.fDiceIndex = IJsonOption.DICE_INDEX.getFrom(jsonObject);
        this.fBlockResult = (BlockResult)IJsonOption.BLOCK_RESULT.getFrom(jsonObject);
        this.fDefenderId = IJsonOption.DEFENDER_ID.getFrom(jsonObject);
        return this;
    }
}


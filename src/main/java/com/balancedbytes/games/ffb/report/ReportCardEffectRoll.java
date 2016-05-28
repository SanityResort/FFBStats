/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.CardEffect;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.UtilReport;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportCardEffectRoll
implements IReport {
    private Card fCard;
    private CardEffect fCardEffect;
    private int fRoll;

    public ReportCardEffectRoll() {
    }

    public ReportCardEffectRoll(Card pCard, int pRoll) {
        this.fCard = pCard;
        this.fRoll = pRoll;
    }

    @Override
    public ReportId getId() {
        return ReportId.CARD_EFFECT_ROLL;
    }

    public Card getCard() {
        return this.fCard;
    }

    public int getRoll() {
        return this.fRoll;
    }

    public void setCardEffect(CardEffect pCardEffect) {
        this.fCardEffect = pCardEffect;
    }

    public CardEffect getCardEffect() {
        return this.fCardEffect;
    }

    @Override
    public IReport transform() {
        ReportCardEffectRoll transformedReport = new ReportCardEffectRoll(this.getCard(), this.getRoll());
        transformedReport.setCardEffect(this.getCardEffect());
        return transformedReport;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.CARD.addTo(jsonObject, this.fCard);
        IJsonOption.ROLL.addTo(jsonObject, this.fRoll);
        if (this.fCardEffect != null) {
            IJsonOption.CARD_EFFECT.addTo(jsonObject, this.fCardEffect);
        }
        return jsonObject;
    }

    @Override
    public ReportCardEffectRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fCard = (Card)IJsonOption.CARD.getFrom(jsonObject);
        this.fRoll = IJsonOption.ROLL.getFrom(jsonObject);
        this.fCardEffect = (CardEffect)IJsonOption.CARD_EFFECT.getFrom(jsonObject);
        return this;
    }
}


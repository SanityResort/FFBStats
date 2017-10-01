/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.Weather;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportWeather
implements IReport {
    private Weather fWeather;
    private int[] fWeatherRoll;

    public ReportWeather() {
    }

    public ReportWeather(Weather pWeather, int[] pRoll) {
        this.fWeather = pWeather;
        this.fWeatherRoll = pRoll;
    }

    @Override
    public ReportId getId() {
        return ReportId.WEATHER;
    }

    public Weather getWeather() {
        return this.fWeather;
    }

    public int[] getWeatherRoll() {
        return this.fWeatherRoll;
    }

    @Override
    public IReport transform() {
        return new ReportWeather(this.getWeather(), this.getWeatherRoll());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.WEATHER.addTo(jsonObject, this.fWeather);
        IJsonOption.WEATHER_ROLL.addTo(jsonObject, this.fWeatherRoll);
        return jsonObject;
    }

    @Override
    public ReportWeather initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fWeather = (Weather)IJsonOption.WEATHER.getFrom(jsonObject);
        this.fWeatherRoll = IJsonOption.WEATHER_ROLL.getFrom(jsonObject);
        return this;
    }
}


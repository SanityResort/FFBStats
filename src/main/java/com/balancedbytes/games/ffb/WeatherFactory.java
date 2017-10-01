/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.Weather;

public class WeatherFactory
implements INamedObjectFactory {
    @Override
    public Weather forName(String pName) {
        for (Weather weather : Weather.values()) {
            if (!weather.getName().equalsIgnoreCase(pName)) continue;
            return weather;
        }
        return null;
    }

    public Weather forShortName(String pShortName) {
        for (Weather weather : Weather.values()) {
            if (!weather.getShortName().equalsIgnoreCase(pShortName)) continue;
            return weather;
        }
        return null;
    }
}


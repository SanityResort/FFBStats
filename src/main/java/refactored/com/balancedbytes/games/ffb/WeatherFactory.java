/*
 * Decompiled with CFR 0_114.
 */
package refactored.com.balancedbytes.games.ffb;

public class WeatherFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public Weather forId(int pId) {
        if (pId > 0) {
            for (Weather weather : Weather.values()) {
                if (pId != weather.getId()) continue;
                return weather;
            }
        }
        return null;
    }

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


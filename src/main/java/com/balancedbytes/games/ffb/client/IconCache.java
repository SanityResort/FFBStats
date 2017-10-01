/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client;

import com.balancedbytes.games.ffb.BloodSpot;
import com.balancedbytes.games.ffb.DiceDecoration;
import com.balancedbytes.games.ffb.PushbackSquare;
import com.balancedbytes.games.ffb.Weather;
import com.balancedbytes.games.ffb.WeatherFactory;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.option.GameOptionId;
import com.balancedbytes.games.ffb.util.StringTool;
import com.balancedbytes.games.ffb.util.UtilUrl;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class IconCache {
    private static final Pattern _PATTERN_PITCH = Pattern.compile("\\?pitch=([a-z]+)$");
    private Map<String, BufferedImage> fIconByKey;
    private Properties fIconUrlProperties;
    private Map<String, Integer> fCurrentIndexPerKey;
    private FantasyFootballClient fClient;

    public IconCache(FantasyFootballClient pClient) {
        this.fClient = pClient;
        this.fIconByKey = new HashMap<String, BufferedImage>();
        this.fCurrentIndexPerKey = new HashMap<String, Integer>();
    }

    public void init() {
        this.fIconUrlProperties = new Properties();
        try {
            InputStream propertyInputStream = this.getClass().getResourceAsStream("/icons.ini");
            this.fIconUrlProperties.load(propertyInputStream);
            propertyInputStream.close();
        }
        catch (IOException propertyInputStream) {
            // empty catch block
        }
    }

    public boolean loadIconFromArchive(String pUrl) {
        boolean cached;
        String iconPath;
        if (!StringTool.isProvided(pUrl)) {
            return false;
        }
        String myUrl = pUrl;
        Weather pitchWeather = this.findPitchWeather(myUrl);
        if (pitchWeather != null) {
            myUrl = myUrl.substring(0, myUrl.length() - 7 - pitchWeather.getShortName().length());
        }
        if (!(cached = StringTool.isProvided(iconPath = this.fIconUrlProperties.getProperty(myUrl)))) {
            iconPath = myUrl;
        }
        if (!iconPath.startsWith("/")) {
            iconPath = "/" + iconPath;
        }
        if (cached && !iconPath.startsWith("/icons/cached")) {
            iconPath = "/icons/cached" + iconPath;
        }
        if (!cached && !iconPath.startsWith("/icons")) {
            iconPath = "/icons" + iconPath;
        }
        try {
            InputStream iconInputStream = this.getClass().getResourceAsStream(iconPath);
            if (iconInputStream != null) {
                if (pitchWeather != null) {
                    return this.loadPitchFromStream(new ZipInputStream(iconInputStream), myUrl);
                }
                BufferedImage icon = ImageIO.read(iconInputStream);
                iconInputStream.close();
                if (icon != null) {
                    this.fIconByKey.put(pUrl, icon);
                    return true;
                }
            }
        }
        catch (IOException iconInputStream) {
            // empty catch block
        }
        return false;
    }

    public BufferedImage getIconByProperty(String pIconProperty) {
        if (!StringTool.isProvided(pIconProperty)) {
            return null;
        }
        String iconUrl = this.getClient().getProperty(pIconProperty);
        BufferedImage icon = this.getIconByUrl(iconUrl);
        if (icon == null && this.loadIconFromArchive(iconUrl)) {
            icon = this.getIconByUrl(iconUrl);
        }
        return icon;
    }

    public BufferedImage getIconByUrl(String pUrl) {
        return this.fIconByKey.get(pUrl);
    }

    public BufferedImage getPitch(Game pGame, Weather pWeather) {
        if (pWeather == Weather.INTRO) {
            return this.getIconByProperty("pitch.intro");
        }
        return this.getIconByUrl(this.findPitchUrl(pGame, pWeather));
    }

    public void loadIconFromUrl(String pUrl) {
        if (!StringTool.isProvided(pUrl)) {
            return;
        }
        Weather weather = this.findPitchWeather(pUrl);
        if (weather != null) {
            this.loadPitchFromUrl(pUrl.substring(0, pUrl.length() - 7 - weather.getShortName().length()));
        } else {
            URL iconUrl = null;
            try {
                iconUrl = new URL(pUrl);
                BufferedImage icon = ImageIO.read(iconUrl);
                this.fIconByKey.put(pUrl, icon);
            }
            catch (Exception pAny) {
                this.getClient().getUserInterface().getStatusReport().reportIconLoadFailure(iconUrl);
            }
        }
    }

    private Weather findPitchWeather(String pUrl) {
        Matcher pitchMatcher = _PATTERN_PITCH.matcher(pUrl);
        if (pitchMatcher.find()) {
            return new WeatherFactory().forShortName(pitchMatcher.group(1));
        }
        return null;
    }

    public String getNextProperty(String pIconProperty) {
        String nextKey = null;
        int index = 1;
        Integer currentIndex = this.fCurrentIndexPerKey.get(pIconProperty);
        if (currentIndex != null) {
            index = currentIndex + 1;
        }
        this.fCurrentIndexPerKey.put(pIconProperty, index);
        StringBuilder indexedProperty = new StringBuilder();
        indexedProperty.append(pIconProperty);
        indexedProperty.append(".");
        if (index < 10) {
            indexedProperty.append("0");
        }
        indexedProperty.append(index);
        nextKey = indexedProperty.toString();
        if (!StringTool.isProvided(this.getClient().getProperty(nextKey)) && index > 1) {
            this.fCurrentIndexPerKey.remove(pIconProperty);
            nextKey = this.getNextProperty(pIconProperty);
        }
        return nextKey;
    }

    public BufferedImage getIcon(PushbackSquare pPushbackSquare) {
        if (pPushbackSquare.isSelected()) {
            switch (pPushbackSquare.getDirection()) {
                case NORTH: {
                    return this.getIconByProperty("game.pushback.north.selected");
                }
                case NORTHEAST: {
                    return this.getIconByProperty("game.pushback.northeast.selected");
                }
                case EAST: {
                    return this.getIconByProperty("game.pushback.east.selected");
                }
                case SOUTHEAST: {
                    return this.getIconByProperty("game.pushback.southeast.selected");
                }
                case SOUTH: {
                    return this.getIconByProperty("game.pushback.south.selected");
                }
                case SOUTHWEST: {
                    return this.getIconByProperty("game.pushback.southwest.selected");
                }
                case WEST: {
                    return this.getIconByProperty("game.pushback.west.selected");
                }
                case NORTHWEST: {
                    return this.getIconByProperty("game.pushback.northwest.selected");
                }
            }
            return null;
        }
        switch (pPushbackSquare.getDirection()) {
            case NORTH: {
                return this.getIconByProperty("game.pushback.north");
            }
            case NORTHEAST: {
                return this.getIconByProperty("game.pushback.northeast");
            }
            case EAST: {
                return this.getIconByProperty("game.pushback.east");
            }
            case SOUTHEAST: {
                return this.getIconByProperty("game.pushback.southeast");
            }
            case SOUTH: {
                return this.getIconByProperty("game.pushback.south");
            }
            case SOUTHWEST: {
                return this.getIconByProperty("game.pushback.southwest");
            }
            case WEST: {
                return this.getIconByProperty("game.pushback.west");
            }
            case NORTHWEST: {
                return this.getIconByProperty("game.pushback.northwest");
            }
        }
        return null;
    }

    public BufferedImage getIcon(BloodSpot pBloodspot) {
        String iconProperty = pBloodspot.getIconProperty();
        if (iconProperty == null) {
            switch (pBloodspot.getInjury().getBase()) {
                case 5: {
                    iconProperty = this.getNextProperty("bloodspot.ko");
                    break;
                }
                case 6: {
                    iconProperty = this.getNextProperty("bloodspot.bh");
                    break;
                }
                case 7: {
                    iconProperty = this.getNextProperty("bloodspot.si");
                    break;
                }
                case 8: {
                    iconProperty = this.getNextProperty("bloodspot.rip");
                    break;
                }
                case 19: {
                    iconProperty = this.getNextProperty("bloodspot.bomb");
                    break;
                }
                case 17: {
                    iconProperty = "bloodspot.fireball";
                    break;
                }
                case 18: {
                    iconProperty = "bloodspot.lightning";
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Cannot get icon for Bloodspot with injury " + pBloodspot.getInjury() + ".");
                }
            }
            pBloodspot.setIconProperty(iconProperty);
        }
        return this.getIconByProperty(iconProperty);
    }

    private String findPitchUrl(Game pGame, Weather pWeather) {
        String pitchUrl;
        if (pGame == null || pWeather == null) {
            return null;
        }
        Weather myWeather = pWeather;
        if ("pitchWeatherOff".equals(this.getClient().getProperty("setting.pitch.weather"))) {
            myWeather = Weather.NICE;
        }
        if (!StringTool.isProvided(pitchUrl = pGame.getOptions().getOptionWithDefault(GameOptionId.PITCH_URL).getValueAsString()) || "pitchDefault".equals(this.getClient().getProperty("setting.pitch.customization"))) {
            pitchUrl = this.getClient().getProperty("pitch.url.default");
        }
        if ("pitchBasic".equals(this.getClient().getProperty("setting.pitch.customization"))) {
            pitchUrl = this.getClient().getProperty("pitch.url.basic");
        }
        return this.buildPitchUrl(pitchUrl, myWeather);
    }

    public String buildPitchUrl(String pUrl, Weather pWeather) {
        if (!StringTool.isProvided(pUrl) || pWeather == null) {
            return null;
        }
        return pUrl + "?pitch=" + pWeather.getShortName();
    }

    private void loadPitchFromUrl(String pUrl) {
        URL pitchUrl = null;
        try {
            pitchUrl = new URL(pUrl);
            HttpURLConnection connection = (HttpURLConnection)pitchUrl.openConnection();
            connection.setRequestMethod("GET");
            this.loadPitchFromStream(new ZipInputStream(connection.getInputStream()), pUrl);
        }
        catch (Exception pAny) {
            this.getClient().getUserInterface().getStatusReport().reportIconLoadFailure(pitchUrl);
        }
    }

    private boolean loadPitchFromStream(ZipInputStream pZipIn, String pUrl) {
        URL pitchUrl = null;
        boolean pitchLoaded = false;
        try {
            pitchUrl = new URL(pUrl);
            Properties pitchProperties = new Properties();
            HashMap<String, BufferedImage> iconByName = new HashMap<String, BufferedImage>();
            ZipEntry entry = null;
            while ((entry = pZipIn.getNextEntry()) != null) {
                if ("pitch.ini".equals(entry.getName())) {
                    pitchProperties.load(pZipIn);
                    continue;
                }
                iconByName.put(entry.getName(), ImageIO.read(pZipIn));
            }
            pZipIn.close();
            for (Weather weather : Weather.values()) {
                BufferedImage pitchIcon;
                String iconName = pitchProperties.getProperty(weather.getShortName());
                if (!StringTool.isProvided(iconName) || (pitchIcon = (BufferedImage)iconByName.get(iconName)) == null) continue;
                this.fIconByKey.put(this.buildPitchUrl(pUrl, weather), pitchIcon);
                pitchLoaded = true;
            }
        }
        catch (Exception pAny) {
            this.getClient().getUserInterface().getStatusReport().reportIconLoadFailure(pitchUrl);
        }
        return pitchLoaded;
    }

    public BufferedImage getIcon(DiceDecoration pDiceDecoration) {
        String iconProperty = null;
        switch (pDiceDecoration.getNrOfDice()) {
            case -3: {
                iconProperty = "decoration.dice3against";
                break;
            }
            case -2: {
                iconProperty = "decoration.dice2against";
                break;
            }
            case 1: {
                iconProperty = "decoration.dice1";
                break;
            }
            case 2: {
                iconProperty = "decoration.dice2";
                break;
            }
            case 3: {
                iconProperty = "decoration.dice3";
            }
        }
        if (iconProperty != null) {
            return this.getIconByProperty(iconProperty);
        }
        return null;
    }

    public BufferedImage getDiceIcon(int pRoll) {
        switch (pRoll) {
            case 1: {
                return this.getIconByProperty("dice.block.1");
            }
            case 2: {
                return this.getIconByProperty("dice.block.2");
            }
            case 3: {
                return this.getIconByProperty("dice.block.3");
            }
            case 4: {
                return this.getIconByProperty("dice.block.4");
            }
            case 5: {
                return this.getIconByProperty("dice.block.5");
            }
            case 6: {
                return this.getIconByProperty("dice.block.6");
            }
        }
        return null;
    }

    public static String findTeamLogoUrl(Team pTeam) {
        String iconUrl = null;
        if (pTeam != null && StringTool.isProvided(pTeam.getLogoUrl())) {
            iconUrl = StringTool.isProvided(pTeam.getBaseIconPath()) ? UtilUrl.createUrl(pTeam.getBaseIconPath(), pTeam.getLogoUrl()) : pTeam.getLogoUrl();
        }
        return iconUrl;
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }

}


/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.xml.IXmlReadable;
import com.balancedbytes.games.ffb.xml.IXmlSerializable;
import com.balancedbytes.games.ffb.xml.UtilXml;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import org.xml.sax.Attributes;

import javax.xml.transform.sax.TransformerHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class GameList
implements IXmlSerializable,
IJsonSerializable {
    public static final String XML_TAG = "gameList";
    private List<GameListEntry> fEntries = new ArrayList<GameListEntry>();

    public GameList() {
    }

    public GameList(GameListEntry[] pGameListEntries) {
        this();
        this.add(pGameListEntries);
    }

    public GameListEntry[] getEntries() {
        return this.fEntries.toArray(new GameListEntry[this.fEntries.size()]);
    }

    public GameListEntry[] getEntriesSorted() {
        GameListEntry[] result = this.getEntries();
        Arrays.sort(result, new Comparator<GameListEntry>(){

            @Override
            public int compare(GameListEntry pEntry1, GameListEntry pEntry2) {
                Date date1 = pEntry1.getStarted() != null ? pEntry1.getStarted() : new Date(0);
                Date date2 = pEntry2.getStarted() != null ? pEntry2.getStarted() : new Date(0);
                return date2.compareTo(date1);
            }
        });
        return result;
    }

    public void add(GameListEntry pGameListEntry) {
        if (pGameListEntry != null) {
            this.fEntries.add(pGameListEntry);
        }
    }

    private void add(GameListEntry[] pGameListEntries) {
        if (ArrayTool.isProvided(pGameListEntries)) {
            for (GameListEntry gameListEntry : pGameListEntries) {
                this.add(gameListEntry);
            }
        }
    }

    public int size() {
        return this.fEntries.size();
    }

    @Override
    public void addToXml(TransformerHandler pHandler) {
        GameListEntry[] entries;
        UtilXml.startElement(pHandler, "gameList");
        for (GameListEntry gameListEntry : entries = this.getEntries()) {
            gameListEntry.addToXml(pHandler);
        }
        UtilXml.endElement(pHandler, "gameList");
    }

    @Override
    public String toXml(boolean pIndent) {
        return UtilXml.toXml(this, pIndent);
    }

    @Override
    public IXmlReadable startXmlElement(String pXmlTag, Attributes pXmlAttributes) {
        IXmlSerializable xmlElement = this;
        if ("game".equals(pXmlTag)) {
            GameListEntry gameListEntry = new GameListEntry();
            gameListEntry.startXmlElement(pXmlTag, pXmlAttributes);
            this.add(gameListEntry);
            xmlElement = gameListEntry;
        }
        return xmlElement;
    }

    @Override
    public boolean endXmlElement(String pXmlTag, String pValue) {
        return "gameList".equals(pXmlTag);
    }

    @Override
    public JsonObject toJsonValue() {
        GameListEntry[] gameListEntries;
        JsonObject jsonObject = new JsonObject();
        JsonArray gameListArray = new JsonArray();
        for (GameListEntry gameListEntry : gameListEntries = this.getEntries()) {
            gameListArray.add(gameListEntry.toJsonValue());
        }
        IJsonOption.GAME_LIST_ENTRIES.addTo(jsonObject, gameListArray);
        return jsonObject;
    }

    @Override
    public GameList initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        JsonArray gameListArray = IJsonOption.GAME_LIST_ENTRIES.getFrom(jsonObject);
        for (int i = 0; i < gameListArray.size(); ++i) {
            GameListEntry gameListEntry = new GameListEntry();
            gameListEntry.initFrom(gameListArray.get(i));
            this.add(gameListEntry);
        }
        return this;
    }

}


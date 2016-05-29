/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class GameList implements IJsonSerializable {
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


/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.SendToBoxReason;
import com.balancedbytes.games.ffb.SeriousInjury;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.util.StringTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class PlayerResult
implements IJsonSerializable {
    private int fCompletions;
    private int fTouchdowns;
    private int fInterceptions;
    private int fCasualties;
    private int fPlayerAwards;
    private int fBlocks;
    private int fFouls;
    private int fRushing;
    private int fPassing;
    private int fTurnsPlayed;
    private int fCurrentSpps;
    private boolean fDefecting;
    private SeriousInjury fSeriousInjury;
    private SendToBoxReason fSendToBoxReason;
    private int fSendToBoxTurn;
    private int fSendToBoxHalf;
    private String fSendToBoxByPlayerId;
    private boolean fHasUsedSecretWeapon;
    private transient TeamResult fTeamResult;
    private transient Player fPlayer;

    public PlayerResult(TeamResult pTeamResult) {
        this(pTeamResult, null);
    }

    public PlayerResult(TeamResult pTeamResult, Player pPlayer) {
        this.fTeamResult = pTeamResult;
        this.fPlayer = pPlayer;
    }

    public TeamResult getTeamResult() {
        return this.fTeamResult;
    }

    public Player getPlayer() {
        return this.fPlayer;
    }

    public String getPlayerId() {
        return this.getPlayer() != null ? this.getPlayer().getId() : null;
    }

    public SeriousInjury getSeriousInjury() {
        return this.fSeriousInjury;
    }


    public SendToBoxReason getSendToBoxReason() {
        return this.fSendToBoxReason;
    }


    public int getSendToBoxTurn() {
        return this.fSendToBoxTurn;
    }


    public int getSendToBoxHalf() {
        return this.fSendToBoxHalf;
    }



    public int getTurnsPlayed() {
        return this.fTurnsPlayed;
    }


    public String getSendToBoxByPlayerId() {
        return this.fSendToBoxByPlayerId;
    }




    public boolean hasUsedSecretWeapon() {
        return this.fHasUsedSecretWeapon;
    }

    public int getCompletions() {
        return this.fCompletions;
    }


    public int getTouchdowns() {
        return this.fTouchdowns;
    }



    public int getInterceptions() {
        return this.fInterceptions;
    }


    public int getCasualties() {
        return this.fCasualties;
    }



    public int getPlayerAwards() {
        return this.fPlayerAwards;
    }


    public int getBlocks() {
        return this.fBlocks;
    }



    public int getFouls() {
        return this.fFouls;
    }



    public int getRushing() {
        return this.fRushing;
    }



    public int getPassing() {
        return this.fPassing;
    }


    public int getCurrentSpps() {
        return this.fCurrentSpps;
    }


    public boolean isDefecting() {
        return this.fDefecting;
    }


    public int totalEarnedSpps() {
        return this.getPlayerAwards() * 5 + this.getTouchdowns() * 3 + this.getCasualties() * 2 + this.getInterceptions() * 2 + this.getCompletions();
    }

    public Game getGame() {
        return this.getTeamResult().getGame();
    }

    public void init(PlayerResult pPlayerResult) {
        if (pPlayerResult != null) {
            this.fPlayer = pPlayerResult.getPlayer();
            this.fCompletions = pPlayerResult.getCompletions();
            this.fTouchdowns = pPlayerResult.getTouchdowns();
            this.fInterceptions = pPlayerResult.getInterceptions();
            this.fCasualties = pPlayerResult.getCasualties();
            this.fPlayerAwards = pPlayerResult.getPlayerAwards();
            this.fBlocks = pPlayerResult.getBlocks();
            this.fFouls = pPlayerResult.getFouls();
            this.fRushing = pPlayerResult.getRushing();
            this.fPassing = pPlayerResult.getPassing();
            this.fTurnsPlayed = pPlayerResult.getTurnsPlayed();
            this.fCurrentSpps = pPlayerResult.getCurrentSpps();
            this.fDefecting = pPlayerResult.isDefecting();
            this.fSeriousInjury = pPlayerResult.getSeriousInjury();
            this.fSendToBoxReason = pPlayerResult.getSendToBoxReason();
            this.fSendToBoxTurn = pPlayerResult.getSendToBoxTurn();
            this.fSendToBoxHalf = pPlayerResult.getSendToBoxHalf();
            this.fSendToBoxByPlayerId = pPlayerResult.getSendToBoxByPlayerId();
            this.fHasUsedSecretWeapon = pPlayerResult.hasUsedSecretWeapon();
        }
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.getPlayerId());
        IJsonOption.COMPLETIONS.addTo(jsonObject, this.fCompletions);
        IJsonOption.TOUCHDOWNS.addTo(jsonObject, this.fTouchdowns);
        IJsonOption.INTERCEPTIONS.addTo(jsonObject, this.fInterceptions);
        IJsonOption.CASUALTIES.addTo(jsonObject, this.fCasualties);
        IJsonOption.PLAYER_AWARDS.addTo(jsonObject, this.fPlayerAwards);
        IJsonOption.BLOCKS.addTo(jsonObject, this.fBlocks);
        IJsonOption.FOULS.addTo(jsonObject, this.fFouls);
        IJsonOption.RUSHING.addTo(jsonObject, this.fRushing);
        IJsonOption.PASSING.addTo(jsonObject, this.fPassing);
        IJsonOption.CURRENT_SPPS.addTo(jsonObject, this.fCurrentSpps);
        IJsonOption.SERIOUS_INJURY.addTo(jsonObject, this.fSeriousInjury);
        IJsonOption.SEND_TO_BOX_REASON.addTo(jsonObject, this.fSendToBoxReason);
        IJsonOption.SEND_TO_BOX_TURN.addTo(jsonObject, this.fSendToBoxTurn);
        IJsonOption.SEND_TO_BOX_HALF.addTo(jsonObject, this.fSendToBoxHalf);
        IJsonOption.SEND_TO_BOX_BY_PLAYER_ID.addTo(jsonObject, this.fSendToBoxByPlayerId);
        IJsonOption.TURNS_PLAYED.addTo(jsonObject, this.fTurnsPlayed);
        IJsonOption.HAS_USED_SECRET_WEAPON.addTo(jsonObject, this.fHasUsedSecretWeapon);
        IJsonOption.DEFECTING.addTo(jsonObject, this.fDefecting);
        return jsonObject;
    }

    @Override
    public PlayerResult initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        String playerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fPlayer = this.getTeamResult().getTeam().getPlayerById(playerId);
        this.fCompletions = IJsonOption.COMPLETIONS.getFrom(jsonObject);
        this.fTouchdowns = IJsonOption.TOUCHDOWNS.getFrom(jsonObject);
        this.fInterceptions = IJsonOption.INTERCEPTIONS.getFrom(jsonObject);
        this.fCasualties = IJsonOption.CASUALTIES.getFrom(jsonObject);
        this.fPlayerAwards = IJsonOption.PLAYER_AWARDS.getFrom(jsonObject);
        this.fBlocks = IJsonOption.BLOCKS.getFrom(jsonObject);
        this.fFouls = IJsonOption.FOULS.getFrom(jsonObject);
        this.fRushing = IJsonOption.RUSHING.getFrom(jsonObject);
        this.fPassing = IJsonOption.PASSING.getFrom(jsonObject);
        this.fCurrentSpps = IJsonOption.CURRENT_SPPS.getFrom(jsonObject);
        this.fSeriousInjury = (SeriousInjury)IJsonOption.SERIOUS_INJURY.getFrom(jsonObject);
        this.fSendToBoxReason = (SendToBoxReason)IJsonOption.SEND_TO_BOX_REASON.getFrom(jsonObject);
        this.fSendToBoxTurn = IJsonOption.SEND_TO_BOX_TURN.getFrom(jsonObject);
        this.fSendToBoxHalf = IJsonOption.SEND_TO_BOX_HALF.getFrom(jsonObject);
        this.fSendToBoxByPlayerId = IJsonOption.SEND_TO_BOX_BY_PLAYER_ID.getFrom(jsonObject);
        this.fTurnsPlayed = IJsonOption.TURNS_PLAYED.getFrom(jsonObject);
        this.fHasUsedSecretWeapon = IJsonOption.HAS_USED_SECRET_WEAPON.getFrom(jsonObject);
        this.fDefecting = IJsonOption.DEFECTING.getFrom(jsonObject);
        return this;
    }
}


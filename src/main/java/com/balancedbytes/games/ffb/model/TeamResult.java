/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.JsonArrayOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.GameResult;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.PlayerResult;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.model.change.ModelChange;
import com.balancedbytes.games.ffb.model.change.ModelChangeId;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.HashMap;
import java.util.Map;

public class TeamResult
implements IJsonSerializable {
    private int fScore;
    private int fFame;
    private int fSpectators;
    private int fWinnings;
    private int fFanFactorModifier;
    private int fSpirallingExpenses;
    private int fBadlyHurtSuffered;
    private int fSeriousInjurySuffered;
    private int fRipSuffered;
    private boolean fConceded;
    private int fRaisedDead;
    private int fPettyCashTransferred;
    private int fPettyCashUsed;
    private int fTeamValue;
    private Map<String, PlayerResult> fPlayerResultByPlayerId;
    private transient GameResult fGameResult;
    private transient Team fTeam;
    private transient boolean fHomeData;

    public TeamResult(GameResult pGameResult, boolean pHomeData) {
        this.fGameResult = pGameResult;
        this.fHomeData = pHomeData;
        this.fPlayerResultByPlayerId = new HashMap<String, PlayerResult>();
    }

    public GameResult getGameResult() {
        return this.fGameResult;
    }

    public boolean isHomeData() {
        return this.fHomeData;
    }

    public void setTeam(Team pTeam) {
        this.fTeam = pTeam;
    }

    public Team getTeam() {
        return this.fTeam;
    }

    public void setConceded(boolean pConceded) {
        if (pConceded == this.fConceded) {
            return;
        }
        this.fConceded = pConceded;
        this.notifyObservers(ModelChangeId.TEAM_RESULT_SET_CONCEDED, this.fConceded);
    }

    public boolean hasConceded() {
        return this.fConceded;
    }

    public void setRaisedDead(int pRaisedDead) {
        if (pRaisedDead == this.fRaisedDead) {
            return;
        }
        this.fRaisedDead = pRaisedDead;
        this.notifyObservers(ModelChangeId.TEAM_RESULT_SET_RAISED_DEAD, this.fRaisedDead);
    }

    public int getRaisedDead() {
        return this.fRaisedDead;
    }

    public int getFame() {
        return this.fFame;
    }

    public void setFame(int pFame) {
        if (pFame == this.fFame) {
            return;
        }
        this.fFame = pFame;
        this.notifyObservers(ModelChangeId.TEAM_RESULT_SET_FAME, this.fFame);
    }

    public int getSpectators() {
        return this.fSpectators;
    }

    public void setSpectators(int pSpectators) {
        if (pSpectators == this.fSpectators) {
            return;
        }
        this.fSpectators = pSpectators;
        this.notifyObservers(ModelChangeId.TEAM_RESULT_SET_SPECTATORS, this.fSpectators);
    }

    public int getWinnings() {
        return this.fWinnings;
    }

    public void setWinnings(int pWinnings) {
        if (pWinnings == this.fWinnings) {
            return;
        }
        this.fWinnings = pWinnings;
        this.notifyObservers(ModelChangeId.TEAM_RESULT_SET_WINNINGS, this.fWinnings);
    }

    public int getFanFactorModifier() {
        return this.fFanFactorModifier;
    }

    public void setFanFactorModifier(int pFanFactorModifier) {
        if (pFanFactorModifier == this.fFanFactorModifier) {
            return;
        }
        this.fFanFactorModifier = pFanFactorModifier;
        this.notifyObservers(ModelChangeId.TEAM_RESULT_SET_FAN_FACTOR_MODIFIER, this.fFanFactorModifier);
    }

    public int getScore() {
        return this.fScore;
    }

    public void setScore(int pScore) {
        if (pScore == this.fScore) {
            return;
        }
        this.fScore = pScore;
        this.notifyObservers(ModelChangeId.TEAM_RESULT_SET_SCORE, this.fScore);
    }

    public void sufferInjury(PlayerState pPlayerState) {
        if (pPlayerState != null) {
            switch (pPlayerState.getBase()) {
                case 6: {
                    this.setBadlyHurtSuffered(this.getBadlyHurtSuffered() + 1);
                    break;
                }
                case 7: {
                    this.setSeriousInjurySuffered(this.getSeriousInjurySuffered() + 1);
                    break;
                }
                case 8: {
                    this.setRipSuffered(this.getRipSuffered() + 1);
                }
            }
        }
    }

    public int getBadlyHurtSuffered() {
        return this.fBadlyHurtSuffered;
    }

    public void setBadlyHurtSuffered(int pBadlyHurtSuffered) {
        if (pBadlyHurtSuffered == this.fBadlyHurtSuffered) {
            return;
        }
        this.fBadlyHurtSuffered = pBadlyHurtSuffered;
    }

    public int getSeriousInjurySuffered() {
        return this.fSeriousInjurySuffered;
    }

    public void setSeriousInjurySuffered(int pSeriousInjurySuffered) {
        if (pSeriousInjurySuffered == this.fSeriousInjurySuffered) {
            return;
        }
        this.fSeriousInjurySuffered = pSeriousInjurySuffered;
        this.notifyObservers(ModelChangeId.TEAM_RESULT_SET_SERIOUS_INJURY_SUFFERED, this.fSeriousInjurySuffered);
    }

    public int getRipSuffered() {
        return this.fRipSuffered;
    }

    public void setRipSuffered(int pRipSuffered) {
        if (pRipSuffered == this.fRipSuffered) {
            return;
        }
        this.fRipSuffered = pRipSuffered;
        this.notifyObservers(ModelChangeId.TEAM_RESULT_SET_RIP_SUFFERED, this.fRipSuffered);
    }

    public int getSpirallingExpenses() {
        return this.fSpirallingExpenses;
    }

    public void setSpirallingExpenses(int pSpirallingExpenses) {
        if (pSpirallingExpenses == this.fSpirallingExpenses) {
            return;
        }
        this.fSpirallingExpenses = pSpirallingExpenses;
        this.notifyObservers(ModelChangeId.TEAM_RESULT_SET_SPIRALLING_EXPENSES, this.fSpirallingExpenses);
    }

    public int getPettyCashTransferred() {
        return this.fPettyCashTransferred;
    }

    public void setPettyCashTransferred(int pPettyCash) {
        if (pPettyCash == this.fPettyCashTransferred) {
            return;
        }
        this.fPettyCashTransferred = pPettyCash;
        this.notifyObservers(ModelChangeId.TEAM_RESULT_SET_PETTY_CASH_TRANSFERRED, this.fPettyCashTransferred);
    }

    public int getPettyCashUsed() {
        return this.fPettyCashUsed;
    }

    public void setPettyCashUsed(int pPettyCash) {
        if (pPettyCash == this.fPettyCashUsed) {
            return;
        }
        this.fPettyCashUsed = pPettyCash;
        this.notifyObservers(ModelChangeId.TEAM_RESULT_SET_PETTY_CASH_USED, this.fPettyCashUsed);
    }

    public int getTeamValue() {
        return this.fTeamValue;
    }

    public void setTeamValue(int pTeamValue) {
        if (pTeamValue == this.fTeamValue) {
            return;
        }
        this.fTeamValue = pTeamValue;
        this.notifyObservers(ModelChangeId.TEAM_RESULT_SET_TEAM_VALUE, this.fTeamValue);
    }

    public int totalCompletions() {
        int completions = 0;
        for (Player player : this.getTeam().getPlayers()) {
            completions += this.getPlayerResult(player).getCompletions();
        }
        return completions;
    }

    public int totalInterceptions() {
        int interceptions = 0;
        for (Player player : this.getTeam().getPlayers()) {
            interceptions += this.getPlayerResult(player).getInterceptions();
        }
        return interceptions;
    }

    public int totalCasualties() {
        int casualties = 0;
        for (Player player : this.getTeam().getPlayers()) {
            casualties += this.getPlayerResult(player).getCasualties();
        }
        return casualties;
    }

    public int totalBlocks() {
        int blocks = 0;
        for (Player player : this.getTeam().getPlayers()) {
            blocks += this.getPlayerResult(player).getBlocks();
        }
        return blocks;
    }

    public int totalFouls() {
        int fouls = 0;
        for (Player player : this.getTeam().getPlayers()) {
            fouls += this.getPlayerResult(player).getFouls();
        }
        return fouls;
    }

    public int totalRushing() {
        int rushing = 0;
        for (Player player : this.getTeam().getPlayers()) {
            rushing += this.getPlayerResult(player).getRushing();
        }
        return rushing;
    }

    public int totalPassing() {
        int passing = 0;
        for (Player player : this.getTeam().getPlayers()) {
            passing += this.getPlayerResult(player).getPassing();
        }
        return passing;
    }

    public int totalEarnedSpps() {
        int earnedSpps = 0;
        for (Player player : this.getTeam().getPlayers()) {
            earnedSpps += this.getPlayerResult(player).totalEarnedSpps();
        }
        return earnedSpps;
    }

    public PlayerResult getPlayerResult(Player pPlayer) {
        String playerId = pPlayer != null ? pPlayer.getId() : null;
        PlayerResult playerResult = this.fPlayerResultByPlayerId.get(playerId);
        if (playerResult == null && this.getTeam().hasPlayer(pPlayer)) {
            playerResult = new PlayerResult(this, pPlayer);
            this.fPlayerResultByPlayerId.put(playerResult.getPlayerId(), playerResult);
        }
        return playerResult;
    }

    public void removePlayerResult(Player pPlayer) {
        String playerId = pPlayer != null ? pPlayer.getId() : null;
        this.fPlayerResultByPlayerId.remove(playerId);
    }

    public Game getGame() {
        return this.getGameResult().getGame();
    }

    public void init(TeamResult pTeamResult) {
        if (pTeamResult != null) {
            this.fScore = pTeamResult.getScore();
            this.fFame = pTeamResult.getFame();
            this.fSpectators = pTeamResult.getSpectators();
            this.fWinnings = pTeamResult.getWinnings();
            this.fFanFactorModifier = pTeamResult.getFanFactorModifier();
            this.fSpirallingExpenses = pTeamResult.getSpirallingExpenses();
            this.fBadlyHurtSuffered = pTeamResult.getBadlyHurtSuffered();
            this.fSeriousInjurySuffered = pTeamResult.getSeriousInjurySuffered();
            this.fRipSuffered = pTeamResult.getRipSuffered();
            this.fConceded = pTeamResult.hasConceded();
            this.fRaisedDead = pTeamResult.getRaisedDead();
            this.fPettyCashTransferred = pTeamResult.getPettyCashTransferred();
            this.fPettyCashUsed = pTeamResult.getPettyCashUsed();
            this.fTeamValue = pTeamResult.getTeamValue();
            for (Player player : this.fTeam.getPlayers()) {
                PlayerResult oldPlayerResult = pTeamResult.getPlayerResult(player);
                PlayerResult newPlayerResult = new PlayerResult(this);
                newPlayerResult.init(oldPlayerResult);
                this.fPlayerResultByPlayerId.put(player.getId(), newPlayerResult);
            }
        }
    }

    private void notifyObservers(ModelChangeId pChangeId, Object pValue) {
        if (this.getGame() == null || pChangeId == null) {
            return;
        }
        String key = this.isHomeData() ? "home" : "away";
        ModelChange modelChange = new ModelChange(pChangeId, key, pValue);
        this.getGame().notifyObservers(modelChange);
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.SCORE.addTo(jsonObject, this.fScore);
        IJsonOption.CONCEDED.addTo(jsonObject, this.fConceded);
        IJsonOption.RAISED_DEAD.addTo(jsonObject, this.fRaisedDead);
        IJsonOption.SPECTATORS.addTo(jsonObject, this.fSpectators);
        IJsonOption.FAME.addTo(jsonObject, this.fFame);
        IJsonOption.WINNINGS.addTo(jsonObject, this.fWinnings);
        IJsonOption.FAN_FACTOR_MODIFIER.addTo(jsonObject, this.fFanFactorModifier);
        IJsonOption.BADLY_HURT_SUFFERED.addTo(jsonObject, this.fBadlyHurtSuffered);
        IJsonOption.SERIOUS_INJURY_SUFFERED.addTo(jsonObject, this.fSeriousInjurySuffered);
        IJsonOption.RIP_SUFFERED.addTo(jsonObject, this.fRipSuffered);
        IJsonOption.SPIRALLING_EXPENSES.addTo(jsonObject, this.fSpirallingExpenses);
        if (this.getTeam() != null) {
            JsonArray playerResultArray = new JsonArray();
            for (Player player : this.getTeam().getPlayers()) {
                playerResultArray.add(this.getPlayerResult(player).toJsonValue());
            }
            IJsonOption.PLAYER_RESULTS.addTo(jsonObject, playerResultArray);
        }
        IJsonOption.PETTY_CASH_TRANSFERRED.addTo(jsonObject, this.fPettyCashTransferred);
        IJsonOption.PETTY_CASH_USED.addTo(jsonObject, this.fPettyCashUsed);
        IJsonOption.TEAM_VALUE.addTo(jsonObject, this.fTeamValue);
        return jsonObject;
    }

    @Override
    public TeamResult initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fScore = IJsonOption.SCORE.getFrom(jsonObject);
        this.fConceded = IJsonOption.CONCEDED.getFrom(jsonObject);
        this.fRaisedDead = IJsonOption.RAISED_DEAD.getFrom(jsonObject);
        this.fSpectators = IJsonOption.SPECTATORS.getFrom(jsonObject);
        this.fFame = IJsonOption.FAME.getFrom(jsonObject);
        this.fWinnings = IJsonOption.WINNINGS.getFrom(jsonObject);
        this.fFanFactorModifier = IJsonOption.FAN_FACTOR_MODIFIER.getFrom(jsonObject);
        this.fBadlyHurtSuffered = IJsonOption.BADLY_HURT_SUFFERED.getFrom(jsonObject);
        this.fSeriousInjurySuffered = IJsonOption.SERIOUS_INJURY_SUFFERED.getFrom(jsonObject);
        this.fRipSuffered = IJsonOption.RIP_SUFFERED.getFrom(jsonObject);
        this.fSpirallingExpenses = IJsonOption.SPIRALLING_EXPENSES.getFrom(jsonObject);
        this.fPlayerResultByPlayerId.clear();
        JsonArray playerResultArray = IJsonOption.PLAYER_RESULTS.getFrom(jsonObject);
        if (playerResultArray != null) {
            for (int i = 0; i < playerResultArray.size(); ++i) {
                PlayerResult playerResult = new PlayerResult(this);
                playerResult.initFrom(playerResultArray.get(i));
                this.fPlayerResultByPlayerId.put(playerResult.getPlayer().getId(), playerResult);
            }
        }
        this.fPettyCashTransferred = IJsonOption.PETTY_CASH_TRANSFERRED.getFrom(jsonObject);
        this.fPettyCashUsed = IJsonOption.PETTY_CASH_USED.getFrom(jsonObject);
        this.fTeamValue = IJsonOption.TEAM_VALUE.getFrom(jsonObject);
        return this;
    }
}


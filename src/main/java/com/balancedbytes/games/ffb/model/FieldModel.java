/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.BloodSpot;
import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.CardEffect;
import com.balancedbytes.games.ffb.CardEffectFactory;
import com.balancedbytes.games.ffb.CardFactory;
import com.balancedbytes.games.ffb.DiceDecoration;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.FieldCoordinateBounds;
import com.balancedbytes.games.ffb.FieldMarker;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.MoveSquare;
import com.balancedbytes.games.ffb.PlayerMarker;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.PushbackSquare;
import com.balancedbytes.games.ffb.RangeRuler;
import com.balancedbytes.games.ffb.TrackNumber;
import com.balancedbytes.games.ffb.Weather;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.JsonArrayOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonFieldCoordinateOption;
import com.balancedbytes.games.ffb.json.JsonPlayerStateOption;
import com.balancedbytes.games.ffb.json.JsonStringArrayOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.model.change.ModelChange;
import com.balancedbytes.games.ffb.model.change.ModelChangeId;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FieldModel
implements IJsonSerializable {
    private boolean fBallMoving;
    private boolean fBallInPlay;
    private FieldCoordinate fBallCoordinate;
    private FieldCoordinate fBombCoordinate;
    private boolean fBombMoving;
    private Weather fWeather;
    private RangeRuler fRangeRuler;
    private Map<String, FieldCoordinate> fCoordinateByPlayerId;
    private Map<String, PlayerState> fStateByPlayerId;
    private List<BloodSpot> fBloodspots;
    private Set<PushbackSquare> fPushbackSquares;
    private Set<MoveSquare> fMoveSquares;
    private Set<TrackNumber> fTrackNumbers;
    private Set<DiceDecoration> fDiceDecorations;
    private Set<FieldMarker> fFieldMarkers;
    private Set<PlayerMarker> fPlayerMarkers;
    private Map<String, Set<Card>> fCardsByPlayerId;
    private Map<String, Set<CardEffect>> fCardEffectsByPlayerId;
    private transient Map<FieldCoordinate, String> fPlayerIdByCoordinate;
    private transient Game fGame;

    public FieldModel(Game pGame) {
        this.fGame = pGame;
        this.fPlayerIdByCoordinate = new HashMap<FieldCoordinate, String>();
        this.fCoordinateByPlayerId = new HashMap<String, FieldCoordinate>();
        this.fBloodspots = new ArrayList<BloodSpot>();
        this.fPushbackSquares = new HashSet<PushbackSquare>();
        this.fMoveSquares = new HashSet<MoveSquare>();
        this.fTrackNumbers = new HashSet<TrackNumber>();
        this.fDiceDecorations = new HashSet<DiceDecoration>();
        this.fStateByPlayerId = new HashMap<String, PlayerState>();
        this.fFieldMarkers = new HashSet<FieldMarker>();
        this.fPlayerMarkers = new HashSet<PlayerMarker>();
        this.fCardsByPlayerId = new HashMap<String, Set<Card>>();
        this.fCardEffectsByPlayerId = new HashMap<String, Set<CardEffect>>();
    }

    public Player getPlayer(FieldCoordinate pPlayerPosition) {
        String playerId = pPlayerPosition != null ? this.fPlayerIdByCoordinate.get(pPlayerPosition) : null;
        return this.getGame().getPlayerById(playerId);
    }

    public void remove(Player pPlayer) {
        if (pPlayer == null) {
            return;
        }
        FieldCoordinate coordinate = this.getPlayerCoordinate(pPlayer);
        this.fPlayerIdByCoordinate.remove(coordinate);
        this.fCoordinateByPlayerId.remove(pPlayer.getId());
        this.notifyObservers(ModelChangeId.FIELD_MODEL_REMOVE_PLAYER, pPlayer.getId(), coordinate);
    }

    public void remove(Team pTeam) {
        if (pTeam != null) {
            for (Player player : pTeam.getPlayers()) {
                this.remove(player);
                this.fStateByPlayerId.remove(player);
            }
        }
    }

    public FieldCoordinate getPlayerCoordinate(Player pPlayer) {
        if (pPlayer == null) {
            return null;
        }
        return this.fCoordinateByPlayerId.get(pPlayer.getId());
    }

    public void setPlayerCoordinate(Player pPlayer, FieldCoordinate pCoordinate) {
        if (pCoordinate == null || pPlayer == null) {
            return;
        }
        FieldCoordinate oldCoordinate = this.getPlayerCoordinate(pPlayer);
        if (!FieldCoordinate.equals(pCoordinate, oldCoordinate)) {
            String oldPlayerId;
            this.fCoordinateByPlayerId.put(pPlayer.getId(), pCoordinate);
            if (oldCoordinate != null) {
                this.fPlayerIdByCoordinate.remove(oldCoordinate);
            }
            if (StringTool.isProvided(oldPlayerId = this.fPlayerIdByCoordinate.get(pCoordinate))) {
                this.fCoordinateByPlayerId.remove(oldPlayerId);
            }
            this.fPlayerIdByCoordinate.put(pCoordinate, pPlayer.getId());
            this.notifyObservers(ModelChangeId.FIELD_MODEL_SET_PLAYER_COORDINATE, pPlayer.getId(), pCoordinate);
        }
    }

    public FieldCoordinate[] getPlayerCoordinates() {
        return this.fPlayerIdByCoordinate.keySet().toArray(new FieldCoordinate[this.fPlayerIdByCoordinate.size()]);
    }

    public void setPlayerState(Player pPlayer, PlayerState pState) {
        if (pPlayer == null) {
            return;
        }
        PlayerState oldState = this.fStateByPlayerId.get(pPlayer.getId());
        if (oldState == null || pState != null && pState.getId() != oldState.getId()) {
            this.fStateByPlayerId.put(pPlayer.getId(), pState);
            this.notifyObservers(ModelChangeId.FIELD_MODEL_SET_PLAYER_STATE, pPlayer.getId(), pState);
        }
    }

    public PlayerState getPlayerState(Player pPlayer) {
        if (pPlayer == null) {
            return null;
        }
        PlayerState playerState = this.fStateByPlayerId.get(pPlayer.getId());
        return playerState != null ? playerState : new PlayerState(0);
    }

    public void addCard(Player pPlayer, Card pCard) {
        if (pPlayer == null || pCard == null) {
            return;
        }
        Set<Card> cards = this.fCardsByPlayerId.get(pPlayer.getId());
        if (cards == null) {
            cards = new HashSet<Card>();
            this.fCardsByPlayerId.put(pPlayer.getId(), cards);
        }
        cards.add(pCard);
        this.notifyObservers(ModelChangeId.FIELD_MODEL_ADD_CARD, pPlayer.getId(), pCard);
    }

    public boolean removeCard(Player pPlayer, Card pCard) {
        if (pPlayer == null || pCard == null) {
            return false;
        }
        boolean removed = false;
        Set<Card> cards = this.fCardsByPlayerId.get(pPlayer.getId());
        if (cards != null) {
            removed = cards.remove(pCard);
        }
        if (removed) {
            this.notifyObservers(ModelChangeId.FIELD_MODEL_REMOVE_CARD, pPlayer.getId(), pCard);
        }
        return removed;
    }

    public Card[] getCards(Player pPlayer) {
        if (pPlayer == null) {
            return null;
        }
        Set<Card> cards = this.fCardsByPlayerId.get(pPlayer.getId());
        if (cards == null) {
            return new Card[0];
        }
        return cards.toArray(new Card[cards.size()]);
    }

    public Player findPlayer(Card pCard) {
        for (String playerId : this.fCardsByPlayerId.keySet()) {
            for (Card card : this.fCardsByPlayerId.get(playerId)) {
                if (card != pCard) continue;
                return this.getGame().getPlayerById(playerId);
            }
        }
        return null;
    }

    public void addCardEffect(Player pPlayer, CardEffect pCardEffect) {
        if (pPlayer == null || pCardEffect == null) {
            return;
        }
        Set<CardEffect> cardEffects = this.fCardEffectsByPlayerId.get(pPlayer.getId());
        if (cardEffects == null) {
            cardEffects = new HashSet<CardEffect>();
            this.fCardEffectsByPlayerId.put(pPlayer.getId(), cardEffects);
        }
        cardEffects.add(pCardEffect);
        this.notifyObservers(ModelChangeId.FIELD_MODEL_ADD_CARD_EFFECT, pPlayer.getId(), pCardEffect);
    }

    public boolean removeCardEffect(Player pPlayer, CardEffect pCardEffect) {
        if (pPlayer == null || pCardEffect == null) {
            return false;
        }
        boolean removed = false;
        Set<CardEffect> cardEffects = this.fCardEffectsByPlayerId.get(pPlayer.getId());
        if (cardEffects != null) {
            removed = cardEffects.remove(pCardEffect);
        }
        if (removed) {
            this.notifyObservers(ModelChangeId.FIELD_MODEL_REMOVE_CARD_EFFECT, pPlayer.getId(), pCardEffect);
        }
        return removed;
    }

    public CardEffect[] getCardEffects(Player pPlayer) {
        if (pPlayer == null) {
            return null;
        }
        Set<CardEffect> cardEffects = this.fCardEffectsByPlayerId.get(pPlayer.getId());
        if (cardEffects == null) {
            return new CardEffect[0];
        }
        return cardEffects.toArray(new CardEffect[cardEffects.size()]);
    }

    public boolean hasCardEffect(Player pPlayer, CardEffect pCardEffect) {
        if (pPlayer == null || pCardEffect == null) {
            return false;
        }
        Set<CardEffect> cardEffects = this.fCardEffectsByPlayerId.get(pPlayer.getId());
        if (cardEffects == null) {
            return false;
        }
        return cardEffects.contains(pCardEffect);
    }

    public Player[] findPlayers(CardEffect pCardEffect) {
        HashSet<Player> players = new HashSet<Player>();
        block0 : for (String playerId : this.fCardEffectsByPlayerId.keySet()) {
            for (CardEffect cardEffect : this.fCardEffectsByPlayerId.get(playerId)) {
                if (cardEffect != pCardEffect) continue;
                players.add(this.getGame().getPlayerById(playerId));
                continue block0;
            }
        }
        return players.toArray(new Player[players.size()]);
    }

    public FieldCoordinate getBallCoordinate() {
        return this.fBallCoordinate;
    }

    public void setBallCoordinate(FieldCoordinate pBallCoordinate) {
        if (FieldCoordinate.equals(pBallCoordinate, this.fBallCoordinate)) {
            return;
        }
        this.fBallCoordinate = pBallCoordinate;
        this.notifyObservers(ModelChangeId.FIELD_MODEL_SET_BALL_COORDINATE, null, this.fBallCoordinate);
    }

    public FieldCoordinate getBombCoordinate() {
        return this.fBombCoordinate;
    }

    public void setBombCoordinate(FieldCoordinate pBombCoordinate) {
        if (FieldCoordinate.equals(pBombCoordinate, this.fBombCoordinate)) {
            return;
        }
        this.fBombCoordinate = pBombCoordinate;
        this.notifyObservers(ModelChangeId.FIELD_MODEL_SET_BOMB_COORDINATE, null, this.fBombCoordinate);
    }

    public FieldCoordinate[] findAdjacentCoordinates(FieldCoordinate pCoordinate, FieldCoordinateBounds pBounds, int pSteps, boolean pWithStartCoordinate) {
        ArrayList<FieldCoordinate> adjacentCoordinates = new ArrayList<FieldCoordinate>();
        if (pCoordinate != null && pBounds != null) {
            for (int y = - pSteps; y <= pSteps; ++y) {
                for (int x = - pSteps; x <= pSteps; ++x) {
                    FieldCoordinate adjacentCoordinate;
                    if (x == 0 && y == 0 && !pWithStartCoordinate || !pBounds.isInBounds(adjacentCoordinate = new FieldCoordinate(pCoordinate.getX() + x, pCoordinate.getY() + y))) continue;
                    adjacentCoordinates.add(adjacentCoordinate);
                }
            }
        }
        return adjacentCoordinates.toArray(new FieldCoordinate[adjacentCoordinates.size()]);
    }

    public void setBallMoving(boolean pBallMoving) {
        if (pBallMoving == this.fBallMoving) {
            return;
        }
        this.fBallMoving = pBallMoving;
        this.notifyObservers(ModelChangeId.FIELD_MODEL_SET_BALL_MOVING, null, this.fBallMoving);
    }

    public boolean isBallMoving() {
        return this.fBallMoving;
    }

    public void setBombMoving(boolean pBombMoving) {
        if (pBombMoving == this.fBombMoving) {
            return;
        }
        this.fBombMoving = pBombMoving;
        this.notifyObservers(ModelChangeId.FIELD_MODEL_SET_BOMB_MOVING, null, this.fBombMoving);
    }

    public boolean isBombMoving() {
        return this.fBombMoving;
    }

    public void setBallInPlay(boolean pBallInPlay) {
        if (pBallInPlay == this.fBallInPlay) {
            return;
        }
        this.fBallInPlay = pBallInPlay;
        this.notifyObservers(ModelChangeId.FIELD_MODEL_SET_BALL_IN_PLAY, null, this.fBallInPlay);
    }

    public boolean isBallInPlay() {
        return this.fBallInPlay;
    }

    public void add(BloodSpot pBloodspot) {
        if (pBloodspot == null) {
            return;
        }
        this.fBloodspots.add(pBloodspot);
        this.notifyObservers(ModelChangeId.FIELD_MODEL_ADD_BLOOD_SPOT, null, pBloodspot);
    }

    public BloodSpot[] getBloodSpots() {
        return this.fBloodspots.toArray(new BloodSpot[this.fBloodspots.size()]);
    }

    public void add(TrackNumber pTrackNumber) {
        if (pTrackNumber == null) {
            return;
        }
        this.fTrackNumbers.add(pTrackNumber);
        this.notifyObservers(ModelChangeId.FIELD_MODEL_ADD_TRACK_NUMBER, null, pTrackNumber);
    }

    public boolean remove(TrackNumber pTrackNumber) {
        if (this.fTrackNumbers.remove(pTrackNumber)) {
            this.notifyObservers(ModelChangeId.FIELD_MODEL_REMOVE_TRACK_NUMBER, null, pTrackNumber);
            return true;
        }
        return false;
    }

    public void clearTrackNumbers() {
        for (TrackNumber trackNumber : this.getTrackNumbers()) {
            this.remove(trackNumber);
        }
    }

    public TrackNumber[] getTrackNumbers() {
        return this.fTrackNumbers.toArray(new TrackNumber[this.fTrackNumbers.size()]);
    }

    public TrackNumber getTrackNumber(FieldCoordinate pCoordinate) {
        for (TrackNumber trackNumber : this.fTrackNumbers) {
            if (!trackNumber.getCoordinate().equals(pCoordinate)) continue;
            return trackNumber;
        }
        return null;
    }

    public void add(PushbackSquare[] pPushbackSquares) {
        if (ArrayTool.isProvided(pPushbackSquares)) {
            for (int i = 0; i < pPushbackSquares.length; ++i) {
                this.add(pPushbackSquares[i]);
            }
        }
    }

    public void add(PushbackSquare pPushbackSquare) {
        if (pPushbackSquare == null) {
            return;
        }
        this.fPushbackSquares.add(pPushbackSquare);
        this.notifyObservers(ModelChangeId.FIELD_MODEL_ADD_PUSHBACK_SQUARE, null, pPushbackSquare);
    }

    public boolean remove(PushbackSquare pPushbackSquare) {
        if (this.fPushbackSquares.remove(pPushbackSquare)) {
            this.notifyObservers(ModelChangeId.FIELD_MODEL_REMOVE_PUSHBACK_SQUARE, null, pPushbackSquare);
            return true;
        }
        return false;
    }

    public void clearPushbackSquares() {
        for (PushbackSquare pushbackSquare : this.getPushbackSquares()) {
            this.remove(pushbackSquare);
        }
    }

    public PushbackSquare[] getPushbackSquares() {
        return this.fPushbackSquares.toArray(new PushbackSquare[this.fPushbackSquares.size()]);
    }

    public void add(MoveSquare pMoveSquare) {
        if (pMoveSquare == null) {
            return;
        }
        this.fMoveSquares.add(pMoveSquare);
        this.notifyObservers(ModelChangeId.FIELD_MODEL_ADD_MOVE_SQUARE, null, pMoveSquare);
    }

    public void add(MoveSquare[] pMoveSquares) {
        if (ArrayTool.isProvided(pMoveSquares)) {
            for (MoveSquare moveSquare : pMoveSquares) {
                this.add(moveSquare);
            }
        }
    }

    public boolean remove(MoveSquare pMoveSquare) {
        if (this.fMoveSquares.remove(pMoveSquare)) {
            this.notifyObservers(ModelChangeId.FIELD_MODEL_REMOVE_MOVE_SQUARE, null, pMoveSquare);
            return true;
        }
        return false;
    }

    public void clearMoveSquares() {
        for (MoveSquare moveSquare : this.getMoveSquares()) {
            this.remove(moveSquare);
        }
    }

    public MoveSquare[] getMoveSquares() {
        return this.fMoveSquares.toArray(new MoveSquare[this.fMoveSquares.size()]);
    }

    public MoveSquare getMoveSquare(FieldCoordinate pCoordinate) {
        for (MoveSquare moveSquare : this.fMoveSquares) {
            if (!moveSquare.getCoordinate().equals(pCoordinate)) continue;
            return moveSquare;
        }
        return null;
    }

    public void add(DiceDecoration pDiceDecoration) {
        if (pDiceDecoration == null) {
            return;
        }
        this.fDiceDecorations.add(pDiceDecoration);
        this.notifyObservers(ModelChangeId.FIELD_MODEL_ADD_DICE_DECORATION, null, pDiceDecoration);
    }

    public boolean remove(DiceDecoration pDiceDecoration) {
        if (this.fDiceDecorations.remove(pDiceDecoration)) {
            this.notifyObservers(ModelChangeId.FIELD_MODEL_REMOVE_DICE_DECORATION, null, pDiceDecoration);
            return true;
        }
        return false;
    }

    public void clearDiceDecorations() {
        for (DiceDecoration diceDecoration : this.getDiceDecorations()) {
            this.remove(diceDecoration);
        }
    }

    public DiceDecoration[] getDiceDecorations() {
        return this.fDiceDecorations.toArray(new DiceDecoration[this.fDiceDecorations.size()]);
    }

    public DiceDecoration getDiceDecoration(FieldCoordinate pCoordinate) {
        for (DiceDecoration diceDecoration : this.fDiceDecorations) {
            if (!diceDecoration.getCoordinate().equals(pCoordinate)) continue;
            return diceDecoration;
        }
        return null;
    }

    public void add(FieldMarker pFieldMarker) {
        if (pFieldMarker == null) {
            return;
        }
        this.fFieldMarkers.remove(pFieldMarker);
        this.fFieldMarkers.add(pFieldMarker);
        this.notifyObservers(ModelChangeId.FIELD_MODEL_ADD_FIELD_MARKER, null, pFieldMarker);
    }

    public boolean remove(FieldMarker pFieldMarker) {
        if (this.fFieldMarkers.remove(pFieldMarker)) {
            this.notifyObservers(ModelChangeId.FIELD_MODEL_REMOVE_FIELD_MARKER, null, pFieldMarker);
            return true;
        }
        return false;
    }

    public void clearFieldMarkers() {
        for (FieldMarker fieldMarker : this.getFieldMarkers()) {
            this.remove(fieldMarker);
        }
    }

    public FieldMarker[] getFieldMarkers() {
        return this.fFieldMarkers.toArray(new FieldMarker[this.fFieldMarkers.size()]);
    }

    public FieldMarker getFieldMarker(FieldCoordinate pCoordinate) {
        for (FieldMarker fieldMarker : this.fFieldMarkers) {
            if (!fieldMarker.getCoordinate().equals(pCoordinate)) continue;
            return fieldMarker;
        }
        return null;
    }

    public void add(PlayerMarker pPlayerMarker) {
        if (pPlayerMarker == null) {
            return;
        }
        this.fPlayerMarkers.remove(pPlayerMarker);
        this.fPlayerMarkers.add(pPlayerMarker);
        this.notifyObservers(ModelChangeId.FIELD_MODEL_ADD_PLAYER_MARKER, null, pPlayerMarker);
    }

    public boolean remove(PlayerMarker pPlayerMarker) {
        if (this.fPlayerMarkers.remove(pPlayerMarker)) {
            this.notifyObservers(ModelChangeId.FIELD_MODEL_REMOVE_PLAYER_MARKER, null, pPlayerMarker);
            return true;
        }
        return false;
    }

    public void clearPlayerMarkers() {
        for (PlayerMarker playerMarker : this.getPlayerMarkers()) {
            this.remove(playerMarker);
        }
    }

    public PlayerMarker[] getPlayerMarkers() {
        return this.fPlayerMarkers.toArray(new PlayerMarker[this.fPlayerMarkers.size()]);
    }

    public PlayerMarker getPlayerMarker(String pPlayerId) {
        for (PlayerMarker playerMarker : this.fPlayerMarkers) {
            if (!playerMarker.getPlayerId().equals(pPlayerId)) continue;
            return playerMarker;
        }
        return null;
    }

    public Weather getWeather() {
        return this.fWeather;
    }

    public void setWeather(Weather pWeather) {
        if (pWeather == this.fWeather) {
            return;
        }
        this.fWeather = pWeather;
        this.notifyObservers(ModelChangeId.FIELD_MODEL_SET_WEATHER, null, this.fWeather);
    }

    public RangeRuler getRangeRuler() {
        return this.fRangeRuler;
    }

    public void setRangeRuler(RangeRuler pRangeRuler) {
        if (pRangeRuler != null ? pRangeRuler.equals(this.fRangeRuler) : this.fRangeRuler == null) {
            return;
        }
        this.fRangeRuler = pRangeRuler;
        this.notifyObservers(ModelChangeId.FIELD_MODEL_SET_RANGE_RULER, null, this.fRangeRuler);
    }

    public boolean updatePlayerAndBallPosition(Player pPlayer, FieldCoordinate pCoordinate) {
        boolean ballPositionUpdated = false;
        FieldCoordinate oldPosition = this.getPlayerCoordinate(pPlayer);
        if (!this.isBallMoving() && oldPosition != null && oldPosition.equals(this.getBallCoordinate())) {
            this.setBallCoordinate(pCoordinate);
            ballPositionUpdated = true;
        }
        this.setPlayerCoordinate(pPlayer, pCoordinate);
        return ballPositionUpdated;
    }

    public void setGame(Game pGame) {
        this.fGame = pGame;
    }

    public Game getGame() {
        return this.fGame;
    }

    public FieldModel transform() {
        FieldModel transformedModel = new FieldModel(this.getGame());
        transformedModel.setBallInPlay(this.isBallInPlay());
        transformedModel.setBallMoving(this.isBallMoving());
        transformedModel.setWeather(this.getWeather());
        for (String playerId : this.fStateByPlayerId.keySet()) {
            Player player = this.getGame().getPlayerById(playerId);
            transformedModel.setPlayerState(player, this.getPlayerState(player));
            Object[] cards = this.getCards(player);
            if (!ArrayTool.isProvided(cards)) continue;
            for (Card card : this.getCards(player)) {
                transformedModel.addCard(player, card);
            }
        }
        if (this.getBallCoordinate() != null) {
            transformedModel.setBallCoordinate(this.getBallCoordinate().transform());
        }
        for (FieldCoordinate playerCoordinate : this.getPlayerCoordinates()) {
            transformedModel.setPlayerCoordinate(this.getPlayer(playerCoordinate), playerCoordinate.transform());
        }
        for (BloodSpot bloodspot : this.getBloodSpots()) {
            transformedModel.add(bloodspot.transform());
        }
        for (PushbackSquare pushbackSquare : this.getPushbackSquares()) {
            transformedModel.add(pushbackSquare.transform());
        }
        for (MoveSquare moveSquare : this.getMoveSquares()) {
            transformedModel.add(moveSquare.transform());
        }
        for (TrackNumber trackNumber : this.getTrackNumbers()) {
            transformedModel.add(trackNumber.transform());
        }
        transformedModel.setRangeRuler(RangeRuler.transform(this.getRangeRuler()));
        for (FieldMarker fieldMarker : this.getFieldMarkers()) {
            transformedModel.add(fieldMarker.transform());
        }
        for (PlayerMarker playerMarker : this.getPlayerMarkers()) {
            transformedModel.add(playerMarker.transform());
        }
        return transformedModel;
    }

    private void notifyObservers(ModelChangeId pChangeId, String pKey, Object pValue) {
        if (this.getGame() == null || pChangeId == null) {
            return;
        }
        this.getGame().notifyObservers(new ModelChange(pChangeId, pKey, pValue));
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.WEATHER.addTo(jsonObject, this.fWeather);
        IJsonOption.BALL_COORDINATE.addTo(jsonObject, this.fBallCoordinate);
        IJsonOption.BALL_IN_PLAY.addTo(jsonObject, this.fBallInPlay);
        IJsonOption.BALL_MOVING.addTo(jsonObject, this.fBallMoving);
        IJsonOption.BOMB_COORDINATE.addTo(jsonObject, this.fBombCoordinate);
        IJsonOption.BOMB_MOVING.addTo(jsonObject, this.fBombMoving);
        JsonArray bloodspotArray = new JsonArray();
        for (BloodSpot bloodSpot : this.fBloodspots) {
            bloodspotArray.add(bloodSpot.toJsonValue());
        }
        IJsonOption.BLOODSPOT_ARRAY.addTo(jsonObject, bloodspotArray);
        JsonArray pushbackSquareArray = new JsonArray();
        for (PushbackSquare pushbackSquare : this.fPushbackSquares) {
            pushbackSquareArray.add(pushbackSquare.toJsonValue());
        }
        IJsonOption.PUSHBACK_SQUARE_ARRAY.addTo(jsonObject, pushbackSquareArray);
        JsonArray moveSquareArray = new JsonArray();
        for (MoveSquare moveSquare : this.fMoveSquares) {
            moveSquareArray.add(moveSquare.toJsonValue());
        }
        IJsonOption.MOVE_SQUARE_ARRAY.addTo(jsonObject, moveSquareArray);
        JsonArray trackNumberArray = new JsonArray();
        for (TrackNumber trackNumber : this.fTrackNumbers) {
            trackNumberArray.add(trackNumber.toJsonValue());
        }
        IJsonOption.TRACK_NUMBER_ARRAY.addTo(jsonObject, trackNumberArray);
        JsonArray diceDecorationArray = new JsonArray();
        for (DiceDecoration diceDecoration : this.fDiceDecorations) {
            diceDecorationArray.add(diceDecoration.toJsonValue());
        }
        IJsonOption.DICE_DECORATION_ARRAY.addTo(jsonObject, diceDecorationArray);
        JsonArray fieldMarkerArray = new JsonArray();
        for (FieldMarker fieldMarker : this.fFieldMarkers) {
            fieldMarkerArray.add(fieldMarker.toJsonValue());
        }
        IJsonOption.FIELD_MARKER_ARRAY.addTo(jsonObject, fieldMarkerArray);
        JsonArray playerMarkerArray = new JsonArray();
        for (PlayerMarker playerMarker : this.fPlayerMarkers) {
            playerMarkerArray.add(playerMarker.toJsonValue());
        }
        IJsonOption.PLAYER_MARKER_ARRAY.addTo(jsonObject, playerMarkerArray);
        JsonArray playerDataArray = new JsonArray();
        for (Player player : this.getGame().getPlayers()) {
            JsonObject playerDataObject = new JsonObject();
            IJsonOption.PLAYER_ID.addTo(playerDataObject, player.getId());
            IJsonOption.PLAYER_COORDINATE.addTo(playerDataObject, this.getPlayerCoordinate(player));
            IJsonOption.PLAYER_STATE.addTo(playerDataObject, this.getPlayerState(player));
            ArrayList<String> cards = new ArrayList<String>();
            for (Card card : this.getCards(player)) {
                cards.add(card.getName());
            }
            IJsonOption.CARDS.addTo(playerDataObject, cards);
            ArrayList<String> cardEffects = new ArrayList<String>();
            for (CardEffect cardEffect : this.getCardEffects(player)) {
                cardEffects.add(cardEffect.getName());
            }
            IJsonOption.CARD_EFFECTS.addTo(playerDataObject, cardEffects);
            playerDataArray.add(playerDataObject);
        }
        IJsonOption.PLAYER_DATA_ARRAY.addTo(jsonObject, playerDataArray);
        return jsonObject;
    }

    @Override
    public FieldModel initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fWeather = (Weather)IJsonOption.WEATHER.getFrom(jsonObject);
        this.fBallCoordinate = IJsonOption.BALL_COORDINATE.getFrom(jsonObject);
        this.fBallInPlay = IJsonOption.BALL_IN_PLAY.getFrom(jsonObject);
        this.fBallMoving = IJsonOption.BALL_MOVING.getFrom(jsonObject);
        this.fBombCoordinate = IJsonOption.BOMB_COORDINATE.getFrom(jsonObject);
        this.fBombMoving = IJsonOption.BOMB_MOVING.getFrom(jsonObject);
        this.fBloodspots.clear();
        JsonArray bloodspotArray = IJsonOption.BLOODSPOT_ARRAY.getFrom(jsonObject);
        for (int i = 0; i < bloodspotArray.size(); ++i) {
            this.fBloodspots.add(new BloodSpot().initFrom(bloodspotArray.get(i)));
        }
        this.fPushbackSquares.clear();
        JsonArray pushbackSquareArray = IJsonOption.PUSHBACK_SQUARE_ARRAY.getFrom(jsonObject);
        for (int i2 = 0; i2 < pushbackSquareArray.size(); ++i2) {
            this.fPushbackSquares.add(new PushbackSquare().initFrom(pushbackSquareArray.get(i2)));
        }
        this.fMoveSquares.clear();
        JsonArray moveSquareArray = IJsonOption.MOVE_SQUARE_ARRAY.getFrom(jsonObject);
        for (int i3 = 0; i3 < moveSquareArray.size(); ++i3) {
            this.fMoveSquares.add(new MoveSquare().initFrom(moveSquareArray.get(i3)));
        }
        this.fTrackNumbers.clear();
        JsonArray trackNumberArray = IJsonOption.TRACK_NUMBER_ARRAY.getFrom(jsonObject);
        for (int i4 = 0; i4 < trackNumberArray.size(); ++i4) {
            this.fTrackNumbers.add(new TrackNumber().initFrom(trackNumberArray.get(i4)));
        }
        this.fDiceDecorations.clear();
        JsonArray diceDecorationArray = IJsonOption.DICE_DECORATION_ARRAY.getFrom(jsonObject);
        for (int i5 = 0; i5 < diceDecorationArray.size(); ++i5) {
            this.fDiceDecorations.add(new DiceDecoration().initFrom(diceDecorationArray.get(i5)));
        }
        this.fFieldMarkers.clear();
        JsonArray fieldMarkerArray = IJsonOption.FIELD_MARKER_ARRAY.getFrom(jsonObject);
        for (int i6 = 0; i6 < fieldMarkerArray.size(); ++i6) {
            this.fFieldMarkers.add(new FieldMarker().initFrom(fieldMarkerArray.get(i6)));
        }
        this.fPlayerMarkers.clear();
        JsonArray playerMarkerArray = IJsonOption.PLAYER_MARKER_ARRAY.getFrom(jsonObject);
        for (int i7 = 0; i7 < playerMarkerArray.size(); ++i7) {
            this.fPlayerMarkers.add(new PlayerMarker().initFrom(playerMarkerArray.get(i7)));
        }
        this.fPlayerIdByCoordinate.clear();
        this.fCoordinateByPlayerId.clear();
        this.fStateByPlayerId.clear();
        this.fCardsByPlayerId.clear();
        CardFactory cardFactory = new CardFactory();
        CardEffectFactory cardEffectFactory = new CardEffectFactory();
        JsonArray playerDataArray = IJsonOption.PLAYER_DATA_ARRAY.getFrom(jsonObject);
        for (int i8 = 0; i8 < playerDataArray.size(); ++i8) {
            Object[] cardEffects;
            JsonObject playerDataObject = UtilJson.toJsonObject(playerDataArray.get(i8));
            String playerId = IJsonOption.PLAYER_ID.getFrom(playerDataObject);
            Player player = this.getGame().getPlayerById(playerId);
            FieldCoordinate playerCoordinate = IJsonOption.PLAYER_COORDINATE.getFrom(playerDataObject);
            this.setPlayerCoordinate(player, playerCoordinate);
            PlayerState playerState = IJsonOption.PLAYER_STATE.getFrom(playerDataObject);
            this.setPlayerState(player, playerState);
            Object[] cards = IJsonOption.CARDS.getFrom(playerDataObject);
            if (ArrayTool.isProvided(cards)) {
                for (int j = 0; j < cards.length; ++j) {
                    this.addCard(player, cardFactory.forName((String)cards[j]));
                }
            }
            if (!ArrayTool.isProvided(cardEffects = IJsonOption.CARD_EFFECTS.getFrom(playerDataObject))) continue;
            for (int j = 0; j < cardEffects.length; ++j) {
                this.addCardEffect(player, cardEffectFactory.forName((String)cardEffects[j]));
            }
        }
        return this;
    }
}


/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.FieldCoordinateBounds;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.SpecialEffect;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.FieldComponent;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;

public class ClientStateWizard
extends ClientState {
    private boolean fShowMarker;

    protected ClientStateWizard(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.WIZARD;
    }

    @Override
    public void enterState() {
        super.enterState();
        this.setSelectable(true);
        this.setClickable(true);
        this.fShowMarker = true;
    }

    @Override
    protected boolean mouseOverField(FieldCoordinate pCoordinate) {
        SpecialEffect wizardSpell = this.getClient().getClientData().getWizardSpell();
        if (pCoordinate != null && wizardSpell != null) {
            return this.handleMouseOver(pCoordinate);
        }
        return super.mouseOverField(pCoordinate);
    }

    @Override
    protected boolean mouseOverPlayer(Player pPlayer) {
        SpecialEffect wizardSpell = this.getClient().getClientData().getWizardSpell();
        FieldCoordinate playerCoordinate = this.getClient().getGame().getFieldModel().getPlayerCoordinate(pPlayer);
        if (playerCoordinate != null && wizardSpell != null) {
            return this.handleMouseOver(playerCoordinate);
        }
        return super.mouseOverPlayer(pPlayer);
    }

    private boolean handleMouseOver(FieldCoordinate pCoordinate) {
        if (this.fShowMarker) {
            SpecialEffect wizardSpell = this.getClient().getClientData().getWizardSpell();
            FieldComponent fieldComponent = this.getClient().getUserInterface().getFieldComponent();
            if (SpecialEffect.LIGHTNING == wizardSpell) {
                fieldComponent.getLayerOverPlayers().clearLightningMarker();
                fieldComponent.getLayerOverPlayers().drawLightningMarker(pCoordinate, !this.isValidLightningTarget(pCoordinate));
                fieldComponent.refresh();
            }
            if (SpecialEffect.FIREBALL == wizardSpell) {
                fieldComponent.getLayerOverPlayers().clearFireballMarker();
                fieldComponent.getLayerOverPlayers().drawFireballMarker(pCoordinate, !this.isValidFireballTarget(pCoordinate));
                fieldComponent.refresh();
            }
        }
        return !this.fShowMarker;
    }

    @Override
    protected void clickOnField(FieldCoordinate pCoordinate) {
        this.handleClick(pCoordinate);
    }

    @Override
    protected void clickOnPlayer(Player pPlayer) {
        FieldCoordinate playerCoordinate = this.getClient().getGame().getFieldModel().getPlayerCoordinate(pPlayer);
        this.handleClick(playerCoordinate);
    }

    private void handleClick(FieldCoordinate pCoordinate) {
        if (pCoordinate != null) {
            SpecialEffect wizardSpell = this.getClient().getClientData().getWizardSpell();
            FieldComponent fieldComponent = this.getClient().getUserInterface().getFieldComponent();
            if (SpecialEffect.LIGHTNING == wizardSpell) {
                fieldComponent.getLayerOverPlayers().clearLightningMarker();
                fieldComponent.refresh();
                if (this.isValidLightningTarget(pCoordinate)) {
                    this.getClient().getCommunication().sendWizardSpell(wizardSpell, pCoordinate);
                    this.fShowMarker = false;
                } else {
                    this.redisplaySpellDialog();
                }
            }
            if (SpecialEffect.FIREBALL == wizardSpell) {
                fieldComponent.getLayerOverPlayers().clearFireballMarker();
                fieldComponent.refresh();
                if (this.isValidFireballTarget(pCoordinate)) {
                    this.getClient().getCommunication().sendWizardSpell(wizardSpell, pCoordinate);
                    this.fShowMarker = false;
                } else {
                    this.redisplaySpellDialog();
                }
            }
        }
    }

    private void redisplaySpellDialog() {
        this.getClient().getClientData().setWizardSpell(null);
    }

    private boolean isValidLightningTarget(FieldCoordinate pCoordinate) {
        boolean valid = false;
        Game game = this.getClient().getGame();
        Player player = game.getFieldModel().getPlayer(pCoordinate);
        if (player != null && game.getTeamAway().hasPlayer(player)) {
            PlayerState playerState = game.getFieldModel().getPlayerState(player);
            valid = playerState.getBase() != 4 && playerState.getBase() != 3;
        }
        return valid;
    }

    private boolean isValidFireballTarget(FieldCoordinate pCoordinate) {
        FieldCoordinate[] fireballSquares;
        boolean valid = false;
        Game game = this.getClient().getGame();
        for (FieldCoordinate square : fireballSquares = game.getFieldModel().findAdjacentCoordinates(pCoordinate, FieldCoordinateBounds.FIELD, 1, true)) {
            PlayerState playerState;
            Player player = game.getFieldModel().getPlayer(square);
            if (player == null || !game.getTeamAway().hasPlayer(player) || (playerState = game.getFieldModel().getPlayerState(player)).getBase() == 4 || playerState.getBase() == 3) continue;
            valid = true;
        }
        return valid;
    }
}


/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog.inducements;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.CardType;
import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.dialog.inducements.DialogBuyCards;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogBuyCardsParameter;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.InducementSet;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.model.TurnData;
import java.util.HashSet;
import java.util.Set;

public class DialogBuyCardsHandler
extends DialogHandler {
    private Set<Card> fCardsDrawn;

    public DialogBuyCardsHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        this.fCardsDrawn = new HashSet<Card>();
        Game game = this.getClient().getGame();
        DialogBuyCardsParameter dialogParameter = (DialogBuyCardsParameter)game.getDialogParameter();
        if (dialogParameter != null) {
            if (ClientMode.PLAYER == this.getClient().getMode() && game.getTeamHome().getId().equals(dialogParameter.getTeamId())) {
                this.setDialog(new DialogBuyCards(this.getClient(), dialogParameter));
                this.getDialog().showDialog(this);
            } else {
                this.showStatus("Buy Cards", "Waiting for coach to buy Cards.", StatusType.WAITING);
            }
        }
    }

    @Override
    public void updateDialog() {
        Game game = this.getClient().getGame();
        DialogBuyCards buyCardsDialog = (DialogBuyCards)this.getDialog();
        if (buyCardsDialog != null) {
            for (Card card : game.getTurnDataHome().getInducementSet().getAvailableCards()) {
                if (this.fCardsDrawn.contains(card)) continue;
                this.fCardsDrawn.add(card);
                buyCardsDialog.addCard(card);
                break;
            }
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (this.testDialogHasId(pDialog, DialogId.BUY_CARDS)) {
            this.getClient().getCommunication().sendBuyCard(null);
        }
    }
}


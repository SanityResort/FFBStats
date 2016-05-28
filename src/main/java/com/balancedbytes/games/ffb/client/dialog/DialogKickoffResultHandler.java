/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.KickoffResult;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogInformation;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogKickoffResultParameter;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Team;
import java.util.ArrayList;

public class DialogKickoffResultHandler
extends DialogHandler {
    public DialogKickoffResultHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        DialogKickoffResultParameter dialogKickoffResultParameter = (DialogKickoffResultParameter)game.getDialogParameter();
        if (dialogKickoffResultParameter.getKickoffResult() != null) {
            ArrayList<String> lines = new ArrayList<String>();
            switch (dialogKickoffResultParameter.getKickoffResult()) {
                case PERFECT_DEFENCE: {
                    if (ClientMode.PLAYER == this.getClient().getMode()) {
                        if (game.isHomePlaying()) {
                            lines.add("You may re-arrange your players into any other legal defence.");
                            break;
                        }
                        lines.add("Your opponent may re-arrange his players into any other legal defence.");
                        break;
                    }
                    StringBuilder message = new StringBuilder();
                    message.append("Coach ");
                    message.append(game.isHomePlaying() ? game.getTeamHome().getCoach() : game.getTeamAway().getCoach());
                    message.append(" may re-arrange his players into any other legal defence.");
                    lines.add(message.toString());
                    break;
                }
                case HIGH_KICK: {
                    if (ClientMode.PLAYER == this.getClient().getMode()) {
                        if (!game.isHomePlaying()) {
                            lines.add("You may click on one of your players to move him to the square the ball is kicked to.");
                            lines.add("Players standing in a tacklezone may not move.");
                            break;
                        }
                        lines.add("Your opponent may position a player for the catch.");
                        break;
                    }
                    StringBuilder message = new StringBuilder();
                    message.append("Coach ");
                    message.append(game.isHomePlaying() ? game.getTeamAway().getCoach() : game.getTeamHome().getCoach());
                    message.append(" may position a player for the catch.");
                    lines.add(message.toString());
                    break;
                }
                case QUICK_SNAP: {
                    if (ClientMode.PLAYER == this.getClient().getMode()) {
                        if (!game.isHomePlaying()) {
                            lines.add("You may move all of your players a single square.");
                            lines.add("Players may enter opponent's half.");
                            break;
                        }
                        lines.add("Your opponent may move all of his players a single square.");
                        break;
                    }
                    StringBuilder message = new StringBuilder();
                    message.append("Coach ");
                    message.append(game.isHomePlaying() ? game.getTeamAway().getCoach() : game.getTeamHome().getCoach());
                    message.append(" may move all of his players a single square.");
                    lines.add(message.toString());
                    break;
                }
                case BLITZ: {
                    if (ClientMode.PLAYER == this.getClient().getMode()) {
                        if (game.isHomePlaying()) {
                            lines.add("You receive an extra turn for moving and blitzing.");
                            lines.add("Players standing in a tacklezone may not move.");
                            break;
                        }
                        lines.add("Your opponent receives an extra turn for moving and blitzing.");
                        break;
                    }
                    StringBuilder message = new StringBuilder();
                    message.append("Coach ");
                    message.append(game.isHomePlaying() ? game.getTeamHome().getCoach() : game.getTeamAway().getCoach());
                    message.append(" receives an extra turn for moving and blitzing.");
                    lines.add(message.toString());
                    break;
                }
                case GET_THE_REF: 
                case RIOT: 
                case CHEERING_FANS: 
                case WEATHER_CHANGE: 
                case BRILLIANT_COACHING: 
                case THROW_A_ROCK: 
                case PITCH_INVASION: {
                    lines.add(dialogKickoffResultParameter.getKickoffResult().getDescription());
                }
            }
            if (lines.size() > 0) {
                this.setDialog(new DialogInformation(this.getClient(), dialogKickoffResultParameter.getKickoffResult().getTitle(), lines.toArray(new String[lines.size()]), 1, "game.ref"));
                this.getDialog().showDialog(this);
            }
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (this.testDialogHasId(pDialog, DialogId.INFORMATION) && ClientMode.PLAYER == this.getClient().getMode()) {
            this.getClient().getCommunication().sendConfirm();
        }
    }

}


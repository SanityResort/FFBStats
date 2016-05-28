/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogApothecaryChoiceHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogBlockRollHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogBribesHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogCoinChoiceHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogDefenderActionHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogFollowupChoiceHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogGameConcessionHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogGameStatisticsHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogInterceptionHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogJoinHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogJourneymenHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogKickSkillHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogKickoffResultHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogKickoffReturnHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogPassBlockHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogPettyCashHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogPilingOnHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogPlayerChoiceHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogReRollHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogReceiveChoiceHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogSetupErrorHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogSkillUseHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogStartGameHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogTeamSetupHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogTouchbackHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogUseApothecaryHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogUseIgorHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogWinningsReRollHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogWizardSpellHandler;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.inducements.DialogBuyCardsHandler;
import com.balancedbytes.games.ffb.client.dialog.inducements.DialogBuyInducementsHandler;
import com.balancedbytes.games.ffb.client.dialog.inducements.DialogUseInducementHandler;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.model.Game;

public class DialogManager {
    private FantasyFootballClient fClient;
    private DialogHandler fDialogHandler;
    private IDialogParameter fShownDialogParameter;

    public DialogManager(FantasyFootballClient pClient) {
        this.fClient = pClient;
    }

    public void updateDialog() {
        Game game = this.getClient().getGame();
        if (this.getShownDialogParameter() != null && this.getShownDialogParameter() == game.getDialogParameter()) {
            if (this.getDialogHandler() != null) {
                this.getDialogHandler().updateDialog();
            }
        } else {
            if (this.getDialogHandler() != null) {
                this.getDialogHandler().hideDialog();
            }
            this.setShownDialogParameter(game.getDialogParameter());
            this.setDialogHandler(null);
            if (game.getDialogParameter() != null) {
                switch (game.getDialogParameter().getId()) {
                    case RE_ROLL: {
                        this.setDialogHandler(new DialogReRollHandler(this.getClient()));
                        break;
                    }
                    case SKILL_USE: {
                        this.setDialogHandler(new DialogSkillUseHandler(this.getClient()));
                        break;
                    }
                    case USE_APOTHECARY: {
                        this.setDialogHandler(new DialogUseApothecaryHandler(this.getClient()));
                        break;
                    }
                    case APOTHECARY_CHOICE: {
                        this.setDialogHandler(new DialogApothecaryChoiceHandler(this.getClient()));
                        break;
                    }
                    case COIN_CHOICE: {
                        this.setDialogHandler(new DialogCoinChoiceHandler(this.getClient()));
                        break;
                    }
                    case INTERCEPTION: {
                        this.setDialogHandler(new DialogInterceptionHandler(this.getClient()));
                        break;
                    }
                    case RECEIVE_CHOICE: {
                        this.setDialogHandler(new DialogReceiveChoiceHandler(this.getClient()));
                        break;
                    }
                    case FOLLOWUP_CHOICE: {
                        this.setDialogHandler(new DialogFollowupChoiceHandler(this.getClient()));
                        break;
                    }
                    case TOUCHBACK: {
                        this.setDialogHandler(new DialogTouchbackHandler(this.getClient()));
                        break;
                    }
                    case KICKOFF_RESULT: {
                        this.setDialogHandler(new DialogKickoffResultHandler(this.getClient()));
                        break;
                    }
                    case SETUP_ERROR: {
                        this.setDialogHandler(new DialogSetupErrorHandler(this.getClient()));
                        break;
                    }
                    case START_GAME: {
                        this.setDialogHandler(new DialogStartGameHandler(this.getClient()));
                        break;
                    }
                    case TEAM_SETUP: {
                        this.setDialogHandler(new DialogTeamSetupHandler(this.getClient()));
                        break;
                    }
                    case WINNINGS_RE_ROLL: {
                        this.setDialogHandler(new DialogWinningsReRollHandler(this.getClient()));
                        break;
                    }
                    case BLOCK_ROLL: {
                        this.setDialogHandler(new DialogBlockRollHandler(this.getClient()));
                        break;
                    }
                    case PLAYER_CHOICE: {
                        this.setDialogHandler(new DialogPlayerChoiceHandler(this.getClient()));
                        break;
                    }
                    case DEFENDER_ACTION: {
                        this.setDialogHandler(new DialogDefenderActionHandler(this.getClient()));
                        break;
                    }
                    case JOIN: {
                        this.setDialogHandler(new DialogJoinHandler(this.getClient()));
                        break;
                    }
                    case CONCEDE_GAME: {
                        this.setDialogHandler(new DialogGameConcessionHandler(this.getClient()));
                        break;
                    }
                    case GAME_STATISTICS: {
                        this.setDialogHandler(new DialogGameStatisticsHandler(this.getClient()));
                        break;
                    }
                    case PILING_ON: {
                        this.setDialogHandler(new DialogPilingOnHandler(this.getClient()));
                        break;
                    }
                    case BRIBES: {
                        this.setDialogHandler(new DialogBribesHandler(this.getClient()));
                        break;
                    }
                    case BUY_INDUCEMENTS: {
                        this.setDialogHandler(new DialogBuyInducementsHandler(this.getClient()));
                        break;
                    }
                    case JOURNEYMEN: {
                        this.setDialogHandler(new DialogJourneymenHandler(this.getClient()));
                        break;
                    }
                    case KICK_SKILL: {
                        this.setDialogHandler(new DialogKickSkillHandler(this.getClient()));
                        break;
                    }
                    case USE_IGOR: {
                        this.setDialogHandler(new DialogUseIgorHandler(this.getClient()));
                        break;
                    }
                    case KICKOFF_RETURN: {
                        this.setDialogHandler(new DialogKickoffReturnHandler(this.getClient()));
                        break;
                    }
                    case PETTY_CASH: {
                        this.setDialogHandler(new DialogPettyCashHandler(this.getClient()));
                        break;
                    }
                    case WIZARD_SPELL: {
                        this.setDialogHandler(new DialogWizardSpellHandler(this.getClient()));
                        break;
                    }
                    case USE_INDUCEMENT: {
                        this.setDialogHandler(new DialogUseInducementHandler(this.getClient()));
                        break;
                    }
                    case PASS_BLOCK: {
                        this.setDialogHandler(new DialogPassBlockHandler(this.getClient()));
                        break;
                    }
                    case BUY_CARDS: {
                        this.setDialogHandler(new DialogBuyCardsHandler(this.getClient()));
                        break;
                    }
                }
                if (this.getDialogHandler() != null) {
                    this.getDialogHandler().showDialog();
                }
            }
        }
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }

    public DialogHandler getDialogHandler() {
        return this.fDialogHandler;
    }

    private void setDialogHandler(DialogHandler pDialogHandler) {
        this.fDialogHandler = pDialogHandler;
    }

    public boolean isDialogVisible() {
        return this.getDialogHandler() != null && this.getDialogHandler().getDialog() != null && this.getDialogHandler().getDialog().isVisible();
    }

    public boolean isEndTurnAllowed() {
        return !this.isDialogVisible() || this.getDialogHandler().isEndTurnAllowedWhileDialogVisible();
    }

    public IDialogParameter getShownDialogParameter() {
        return this.fShownDialogParameter;
    }

    public void setShownDialogParameter(IDialogParameter pShownDialogParameter) {
        this.fShownDialogParameter = pShownDialogParameter;
    }

}


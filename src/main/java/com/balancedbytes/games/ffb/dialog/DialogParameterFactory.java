/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.dialog.DialogApothecaryChoiceParameter;
import com.balancedbytes.games.ffb.dialog.DialogBlockRollParameter;
import com.balancedbytes.games.ffb.dialog.DialogBribesParameter;
import com.balancedbytes.games.ffb.dialog.DialogBuyCardsParameter;
import com.balancedbytes.games.ffb.dialog.DialogBuyInducementsParameter;
import com.balancedbytes.games.ffb.dialog.DialogCoinChoiceParameter;
import com.balancedbytes.games.ffb.dialog.DialogConcedeGameParameter;
import com.balancedbytes.games.ffb.dialog.DialogDefenderActionParameter;
import com.balancedbytes.games.ffb.dialog.DialogFollowupChoiceParameter;
import com.balancedbytes.games.ffb.dialog.DialogGameStatisticsParameter;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogInterceptionParameter;
import com.balancedbytes.games.ffb.dialog.DialogJoinParameter;
import com.balancedbytes.games.ffb.dialog.DialogJourneymenParameter;
import com.balancedbytes.games.ffb.dialog.DialogKickSkillParameter;
import com.balancedbytes.games.ffb.dialog.DialogKickoffResultParameter;
import com.balancedbytes.games.ffb.dialog.DialogKickoffReturnParameter;
import com.balancedbytes.games.ffb.dialog.DialogPassBlockParameter;
import com.balancedbytes.games.ffb.dialog.DialogPettyCashParameter;
import com.balancedbytes.games.ffb.dialog.DialogPilingOnParameter;
import com.balancedbytes.games.ffb.dialog.DialogPlayerChoiceParameter;
import com.balancedbytes.games.ffb.dialog.DialogReRollParameter;
import com.balancedbytes.games.ffb.dialog.DialogReceiveChoiceParameter;
import com.balancedbytes.games.ffb.dialog.DialogSetupErrorParameter;
import com.balancedbytes.games.ffb.dialog.DialogSkillUseParameter;
import com.balancedbytes.games.ffb.dialog.DialogStartGameParameter;
import com.balancedbytes.games.ffb.dialog.DialogTeamSetupParameter;
import com.balancedbytes.games.ffb.dialog.DialogTouchbackParameter;
import com.balancedbytes.games.ffb.dialog.DialogTransferPettyCashParameter;
import com.balancedbytes.games.ffb.dialog.DialogUseApothecaryParameter;
import com.balancedbytes.games.ffb.dialog.DialogUseIgorParameter;
import com.balancedbytes.games.ffb.dialog.DialogUseInducementParameter;
import com.balancedbytes.games.ffb.dialog.DialogWinningsReRollParameter;
import com.balancedbytes.games.ffb.dialog.DialogWizardSpellParameter;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class DialogParameterFactory {
    public IDialogParameter createDialogParameter(DialogId pDialogId) {
        if (pDialogId == null) {
            return null;
        }
        switch (pDialogId) {
            case APOTHECARY_CHOICE: {
                return new DialogApothecaryChoiceParameter();
            }
            case RECEIVE_CHOICE: {
                return new DialogReceiveChoiceParameter();
            }
            case RE_ROLL: {
                return new DialogReRollParameter();
            }
            case SKILL_USE: {
                return new DialogSkillUseParameter();
            }
            case USE_APOTHECARY: {
                return new DialogUseApothecaryParameter();
            }
            case BLOCK_ROLL: {
                return new DialogBlockRollParameter();
            }
            case PLAYER_CHOICE: {
                return new DialogPlayerChoiceParameter();
            }
            case INTERCEPTION: {
                return new DialogInterceptionParameter();
            }
            case WINNINGS_RE_ROLL: {
                return new DialogWinningsReRollParameter();
            }
            case BRIBES: {
                return new DialogBribesParameter();
            }
            case GAME_STATISTICS: {
                return new DialogGameStatisticsParameter();
            }
            case JOIN: {
                return new DialogJoinParameter();
            }
            case START_GAME: {
                return new DialogStartGameParameter();
            }
            case TEAM_SETUP: {
                return new DialogTeamSetupParameter();
            }
            case SETUP_ERROR: {
                return new DialogSetupErrorParameter();
            }
            case TOUCHBACK: {
                return new DialogTouchbackParameter();
            }
            case DEFENDER_ACTION: {
                return new DialogDefenderActionParameter();
            }
            case COIN_CHOICE: {
                return new DialogCoinChoiceParameter();
            }
            case FOLLOWUP_CHOICE: {
                return new DialogFollowupChoiceParameter();
            }
            case CONCEDE_GAME: {
                return new DialogConcedeGameParameter();
            }
            case PILING_ON: {
                return new DialogPilingOnParameter();
            }
            case BUY_INDUCEMENTS: {
                return new DialogBuyInducementsParameter();
            }
            case TRANSFER_PETTY_CASH: {
                return new DialogTransferPettyCashParameter();
            }
            case JOURNEYMEN: {
                return new DialogJourneymenParameter();
            }
            case KICKOFF_RESULT: {
                return new DialogKickoffResultParameter();
            }
            case KICK_SKILL: {
                return new DialogKickSkillParameter();
            }
            case USE_IGOR: {
                return new DialogUseIgorParameter();
            }
            case KICKOFF_RETURN: {
                return new DialogKickoffReturnParameter();
            }
            case PETTY_CASH: {
                return new DialogPettyCashParameter();
            }
            case WIZARD_SPELL: {
                return new DialogWizardSpellParameter();
            }
            case USE_INDUCEMENT: {
                return new DialogUseInducementParameter();
            }
            case PASS_BLOCK: {
                return new DialogPassBlockParameter();
            }
            case BUY_CARDS: {
                return new DialogBuyCardsParameter();
            }
        }
        return null;
    }

    public IDialogParameter forJsonValue(JsonValue pJsonValue) {
        if (pJsonValue == null || pJsonValue.isNull()) {
            return null;
        }
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        DialogId dialogId = (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject);
        IDialogParameter dialogParameter = this.createDialogParameter(dialogId);
        if (dialogParameter != null) {
            dialogParameter.initFrom(pJsonValue);
        }
        return dialogParameter;
    }

}


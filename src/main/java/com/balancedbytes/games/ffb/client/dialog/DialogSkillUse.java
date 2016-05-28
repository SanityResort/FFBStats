/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogYesOrNoQuestion;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogSkillUseParameter;

public class DialogSkillUse
extends DialogYesOrNoQuestion {
    private DialogSkillUseParameter fDialogParameter;

    public DialogSkillUse(FantasyFootballClient pClient, DialogSkillUseParameter pDialogParameter) {
        super(pClient, "Use a skill", DialogSkillUse.createMessages(pDialogParameter), null);
        this.fDialogParameter = pDialogParameter;
    }

    @Override
    public DialogId getId() {
        return DialogId.SKILL_USE;
    }

    public Skill getSkill() {
        return this.fDialogParameter != null ? this.fDialogParameter.getSkill() : null;
    }

    private static String[] createMessages(DialogSkillUseParameter pDialogParameter) {
        String[] messages = new String[]{};
        if (pDialogParameter != null && pDialogParameter.getSkill() != null) {
            switch (pDialogParameter.getSkill()) {
                case SIDE_STEP: {
                    messages = new String[]{DialogSkillUse.createDefaultQuestion(pDialogParameter), "Using SideStep will allow you to chose the square you are pushed to."};
                    break;
                }
                case JUGGERNAUT: {
                    messages = new String[]{DialogSkillUse.createDefaultQuestion(pDialogParameter), "Using Juggernaut will convert the BOTH DOWN Block Result into a PUSHBACK."};
                    break;
                }
                case WRESTLE: {
                    messages = new String[]{DialogSkillUse.createDefaultQuestion(pDialogParameter), "Using Wrestle will put down both you and your opponent.", "No Armor Roll is made. The ball carrier drops the ball."};
                    break;
                }
                default: {
                    if (pDialogParameter.getMinimumRoll() > 0) {
                        messages = new String[2];
                        messages[1] = DialogSkillUse.createDefaultMinimumRoll(pDialogParameter);
                        break;
                    }
                    messages = new String[]{DialogSkillUse.createDefaultQuestion(pDialogParameter)};
                }
            }
        }
        return messages;
    }

    private static String createDefaultQuestion(DialogSkillUseParameter pDialogParameter) {
        StringBuilder useSkillQuestion = new StringBuilder();
        String skillName = pDialogParameter.getSkill() != null ? pDialogParameter.getSkill().getName() : null;
        useSkillQuestion.append("Do you want to use the ").append(skillName).append(" skill ?");
        return useSkillQuestion.toString();
    }

    private static String createDefaultMinimumRoll(DialogSkillUseParameter pDialogParameter) {
        StringBuilder minimumRollMessage = new StringBuilder();
        minimumRollMessage.append("You will need a roll of ").append(pDialogParameter.getMinimumRoll()).append("+ to succeed.");
        return minimumRollMessage.toString();
    }

}


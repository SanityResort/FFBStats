/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.util;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.CardEffect;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.InducementSet;
import com.balancedbytes.games.ffb.model.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class UtilCards {
    public static boolean hasSkill(Game pGame, Player pPlayer, Skill pSkill) {
        if (pGame == null || pPlayer == null || pSkill == null) {
            return false;
        }
        Set<Skill> cardSkills = UtilCards.findSkillsProvidedByCardsAndEffects(pGame, pPlayer);
        return pPlayer.hasSkill(pSkill) || cardSkills.contains(pSkill);
    }

    public static boolean hasSkill(Game pGame, ActingPlayer pActingPlayer, Skill pSkill) {
        if (pActingPlayer == null) {
            return false;
        }
        return UtilCards.hasSkill(pGame, pActingPlayer.getPlayer(), pSkill);
    }

    public static boolean hasUnusedSkill(Game pGame, ActingPlayer pActingPlayer, Skill pSkill) {
        if (pActingPlayer == null) {
            return false;
        }
        return UtilCards.hasSkill(pGame, pActingPlayer.getPlayer(), pSkill) && !pActingPlayer.isSkillUsed(pSkill);
    }

    private static Set<Skill> findSkillsProvidedByCardsAndEffects(Game pGame, Player pPlayer) {
        Card[] cards;
        CardEffect[] cardEffects;
        HashSet<Skill> cardSkills = new HashSet<Skill>();
        if (pGame == null || pPlayer == null) {
            return cardSkills;
        }
        block18 : for (Card card : cards = pGame.getFieldModel().getCards(pPlayer)) {
            switch (card) {
                case BEGUILING_BRACERS: {
                    cardSkills.add(Skill.BONE_HEAD);
                    cardSkills.add(Skill.HYPNOTIC_GAZE);
                    cardSkills.add(Skill.SIDE_STEP);
                    continue block18;
                }
                case FAWNDOUGHS_HEADBAND: {
                    cardSkills.add(Skill.ACCURATE);
                    cardSkills.add(Skill.PASS);
                    continue block18;
                }
                case FORCE_SHIELD: {
                    cardSkills.add(Skill.FEND);
                    cardSkills.add(Skill.SURE_HANDS);
                    continue block18;
                }
                case GLOVES_OF_HOLDING: {
                    cardSkills.add(Skill.CATCH);
                    cardSkills.add(Skill.SURE_HANDS);
                    continue block18;
                }
                case MAGIC_GLOVES_OF_JARK_LONGARM: {
                    cardSkills.add(Skill.PASS_BLOCK);
                    continue block18;
                }
                case RABBITS_FOOT: {
                    cardSkills.add(Skill.PRO);
                    continue block18;
                }
                case WAND_OF_SMASHING: {
                    cardSkills.add(Skill.MIGHTY_BLOW);
                    continue block18;
                }
                case GROMSKULLS_EXPLODING_RUNES: {
                    cardSkills.add(Skill.BOMBARDIER);
                    cardSkills.add(Skill.NO_HANDS);
                    cardSkills.add(Skill.SECRET_WEAPON);
                    continue block18;
                }
                case DISTRACT: {
                    cardSkills.add(Skill.DISTURBING_PRESENCE);
                    continue block18;
                }
                case STOLEN_PLAYBOOK: {
                    cardSkills.add(Skill.PASS_BLOCK);
                    cardSkills.add(Skill.SHADOWING);
                    continue block18;
                }
                case KICKING_BOOTS: {
                    cardSkills.add(Skill.KICK);
                    cardSkills.add(Skill.DIRTY_PLAYER);
                }
            }
        }
        block19 : for (CardEffect cardEffect : cardEffects = pGame.getFieldModel().getCardEffects(pPlayer)) {
            switch (cardEffect) {
                case DISTRACTED: {
                    cardSkills.add(Skill.BONE_HEAD);
                    continue block19;
                }
                case SEDATIVE: {
                    cardSkills.add(Skill.REALLY_STUPID);
                    continue block19;
                }
                case MAD_CAP_MUSHROOM_POTION: {
                    cardSkills.add(Skill.JUMP_UP);
                    cardSkills.add(Skill.NO_HANDS);
                    break;
                }
            }
        }
        return cardSkills;
    }

    public static int getPlayerStrength(Game pGame, Player pPlayer) {
        if (pGame == null || pPlayer == null) {
            return 0;
        }
        int strength = pPlayer.getStrength();
        InducementSet inducementSet = pPlayer.getTeam() == pGame.getTeamHome() ? pGame.getTurnDataHome().getInducementSet() : pGame.getTurnDataAway().getInducementSet();
        block4 : for (Card card : pGame.getFieldModel().getCards(pPlayer)) {
            switch (card) {
                case GIKTAS_STRENGTH_OF_DA_BEAR: {
                    if (inducementSet.isActive(card)) {
                        ++strength;
                        continue block4;
                    }
                    --strength;
                    continue block4;
                }
                case WAND_OF_SMASHING: {
                    ++strength;
                    break;
                }
            }
        }
        return strength;
    }

    public static int getPlayerMovement(Game pGame, Player pPlayer) {
        if (pGame == null || pPlayer == null) {
            return 0;
        }
        int movement = pPlayer.getMovement();
        InducementSet inducementSet = pPlayer.getTeam() == pGame.getTeamHome() ? pGame.getTurnDataHome().getInducementSet() : pGame.getTurnDataAway().getInducementSet();
        block3 : for (Card card : pGame.getFieldModel().getCards(pPlayer)) {
            switch (card) {
                case KICKING_BOOTS: {
                    if (!inducementSet.isActive(card)) continue block3;
                    --movement;
                    break;
                }
            }
        }
        return movement;
    }

    public static Skill[] findAllSkills(Game pGame, Player pPlayer) {
        Set<Skill> allSkills = UtilCards.findSkillsProvidedByCardsAndEffects(pGame, pPlayer);
        for (Skill skill : pPlayer.getSkills()) {
            allSkills.add(skill);
        }
        return allSkills.toArray(new Skill[allSkills.size()]);
    }

    public static Card[] findAllActiveCards(Game pGame) {
        ArrayList<Card> allActiveCards = new ArrayList<Card>();
        for (Card card : pGame.getTurnDataHome().getInducementSet().getActiveCards()) {
            allActiveCards.add(card);
        }
        for (Card card : pGame.getTurnDataAway().getInducementSet().getActiveCards()) {
            allActiveCards.add(card);
        }
        return allActiveCards.toArray(new Card[allActiveCards.size()]);
    }

    public static boolean isCardActive(Game pGame, Card pCard) {
        for (Card card : UtilCards.findAllActiveCards(pGame)) {
            if (card != pCard) continue;
            return true;
        }
        return false;
    }

    public static boolean hasCard(Game pGame, Player pPlayer, Card pCard) {
        if (pGame == null || pPlayer == null || pCard == null) {
            return false;
        }
        for (Card card : pGame.getFieldModel().getCards(pPlayer)) {
            if (card != pCard) continue;
            return true;
        }
        return false;
    }

}


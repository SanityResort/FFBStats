/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.util;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.DiceDecoration;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.FieldCoordinateBounds;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.TurnMode;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.RosterPosition;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.model.TurnData;
import com.balancedbytes.games.ffb.option.GameOptionId;
import com.balancedbytes.games.ffb.option.UtilGameOption;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.UtilCards;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

public class UtilPlayer {
    public static Player[] findPlayersOnPitchWithSkill(Game pGame, Team pTeam, Skill pSkill) {
        ArrayList<Player> result = new ArrayList<Player>();
        for (Player player : pTeam.getPlayers()) {
            if (!UtilCards.hasSkill(pGame, player, pSkill) || !FieldCoordinateBounds.FIELD.isInBounds(pGame.getFieldModel().getPlayerCoordinate(player))) continue;
            result.add(player);
        }
        return result.toArray(new Player[result.size()]);
    }

    public static Player[] findAdjacentOpposingPlayersWithSkill(Game pGame, FieldCoordinate pCenterCoordinate, Skill pSkill, boolean pCheckAbleToMove) {
        ActingPlayer actingPlayer = pGame.getActingPlayer();
        Team otherTeam = UtilPlayer.findOtherTeam(pGame, actingPlayer.getPlayer());
        Player[] opponents = UtilPlayer.findAdjacentPlayersWithTacklezones(pGame, otherTeam, pCenterCoordinate, false);
        HashSet<Player> shadowingPlayers = new HashSet<Player>();
        for (Player opponent : opponents) {
            PlayerState opponentState = pGame.getFieldModel().getPlayerState(opponent);
            if (opponentState == null || !opponentState.hasTacklezones() || !UtilCards.hasSkill(pGame, opponent, pSkill) || pCheckAbleToMove && !opponentState.isAbleToMove()) continue;
            shadowingPlayers.add(opponent);
        }
        Player[] playerArray = shadowingPlayers.toArray(new Player[shadowingPlayers.size()]);
        UtilPlayer.sortByPlayerNr(playerArray);
        return playerArray;
    }

    public static Player[] findAdjacentPronePlayers(Game pGame, Team pTeam, FieldCoordinate pCoordinate) {
        ArrayList<Player> adjacentPlayers = new ArrayList<Player>();
        FieldModel fieldModel = pGame.getFieldModel();
        FieldCoordinate[] adjacentCoordinates = fieldModel.findAdjacentCoordinates(pCoordinate, FieldCoordinateBounds.FIELD, 1, false);
        for (int i = 0; i < adjacentCoordinates.length; ++i) {
            PlayerState playerState;
            Player player = fieldModel.getPlayer(adjacentCoordinates[i]);
            if (player == null || player.getTeam() != pTeam || (playerState = fieldModel.getPlayerState(player)).getBase() != 3 && playerState.getBase() != 4) continue;
            adjacentPlayers.add(player);
        }
        return adjacentPlayers.toArray(new Player[adjacentPlayers.size()]);
    }

    public static Player[] findAdjacentBlockablePlayers(Game pGame, Team pTeam, FieldCoordinate pCoordinate) {
        ArrayList<Player> adjacentPlayers = new ArrayList<Player>();
        FieldModel fieldModel = pGame.getFieldModel();
        FieldCoordinate[] adjacentCoordinates = fieldModel.findAdjacentCoordinates(pCoordinate, FieldCoordinateBounds.FIELD, 1, false);
        for (int i = 0; i < adjacentCoordinates.length; ++i) {
            PlayerState playerState;
            Player player = fieldModel.getPlayer(adjacentCoordinates[i]);
            if (player == null || player.getTeam() != pTeam || !(playerState = fieldModel.getPlayerState(player)).canBeBlocked()) continue;
            adjacentPlayers.add(player);
        }
        return adjacentPlayers.toArray(new Player[adjacentPlayers.size()]);
    }

    public static Player[] findAdjacentStandingOrPronePlayers(Game pGame, Team pTeam, FieldCoordinate pCoordinate) {
        ArrayList<Player> adjacentPlayers = new ArrayList<Player>();
        FieldModel fieldModel = pGame.getFieldModel();
        FieldCoordinate[] adjacentCoordinates = fieldModel.findAdjacentCoordinates(pCoordinate, FieldCoordinateBounds.FIELD, 1, false);
        for (int i = 0; i < adjacentCoordinates.length; ++i) {
            PlayerState playerState;
            Player player = fieldModel.getPlayer(adjacentCoordinates[i]);
            if (player == null || player.getTeam() != pTeam || (playerState = fieldModel.getPlayerState(player)).isStunned()) continue;
            adjacentPlayers.add(player);
        }
        return adjacentPlayers.toArray(new Player[adjacentPlayers.size()]);
    }

    public static Player[] findAdjacentPlayersWithTacklezones(Game pGame, Team pTeam, FieldCoordinate pCoordinate, boolean pWithStartCoordinate) {
        ArrayList<Player> adjacentPlayers = new ArrayList<Player>();
        FieldModel fieldModel = pGame.getFieldModel();
        FieldCoordinate[] adjacentCoordinates = fieldModel.findAdjacentCoordinates(pCoordinate, FieldCoordinateBounds.FIELD, 1, pWithStartCoordinate);
        for (int i = 0; i < adjacentCoordinates.length; ++i) {
            PlayerState playerState;
            Player player = fieldModel.getPlayer(adjacentCoordinates[i]);
            if (player == null || player.getTeam() != pTeam || !(playerState = fieldModel.getPlayerState(player)).hasTacklezones()) continue;
            adjacentPlayers.add(player);
        }
        return adjacentPlayers.toArray(new Player[adjacentPlayers.size()]);
    }

    public static Player[] findAdjacentPlayersToFeedOn(Game pGame, Team pTeam, FieldCoordinate pCoordinate) {
        ArrayList<Player> adjacentPlayers = new ArrayList<Player>();
        FieldModel fieldModel = pGame.getFieldModel();
        FieldCoordinate[] adjacentCoordinates = fieldModel.findAdjacentCoordinates(pCoordinate, FieldCoordinateBounds.FIELD, 1, false);
        for (int i = 0; i < adjacentCoordinates.length; ++i) {
            Player player = fieldModel.getPlayer(adjacentCoordinates[i]);
            if (player == null || player.getTeam() != pTeam || !player.getPosition().isThrall()) continue;
            adjacentPlayers.add(player);
        }
        return adjacentPlayers.toArray(new Player[adjacentPlayers.size()]);
    }

    public static Player[] filterThrower(Game pGame, Player[] pPlayers) {
        ArrayList<Player> playerList = new ArrayList<Player>();
        if (ArrayTool.isProvided(pPlayers)) {
            for (Player player : pPlayers) {
                if (player == pGame.getThrower()) continue;
                playerList.add(player);
            }
        }
        return playerList.toArray(new Player[playerList.size()]);
    }

    public static Player[] filterAttackerAndDefender(Game pGame, Player[] pPlayers) {
        ArrayList<Player> playerList = new ArrayList<Player>();
        if (ArrayTool.isProvided(pPlayers)) {
            for (Player player : pPlayers) {
                if (player == pGame.getActingPlayer().getPlayer() || player == pGame.getDefender()) continue;
                playerList.add(player);
            }
        }
        return playerList.toArray(new Player[playerList.size()]);
    }

    public static int findBlockStrength(Game pGame, Player pAttacker, int pAttackerStrength, Player pDefender) {
        Team defenderTeam = pDefender.getTeam();
        if (UtilCards.hasSkill(pGame, pAttacker, Skill.BALL_AND_CHAIN) && pAttacker.getTeam() == pDefender.getTeam()) {
            defenderTeam = UtilPlayer.findOtherTeam(pGame, pDefender);
        }
        int blockStrength = pAttackerStrength;
        FieldCoordinate coordinateDefender = pGame.getFieldModel().getPlayerCoordinate(pDefender);
        Player[] offensiveAssists = UtilPlayer.findAdjacentPlayersWithTacklezones(pGame, pAttacker.getTeam(), coordinateDefender, false);
        for (int i = 0; i < offensiveAssists.length; ++i) {
            if (offensiveAssists[i] == pAttacker) continue;
            FieldCoordinate coordinateAssist = pGame.getFieldModel().getPlayerCoordinate(offensiveAssists[i]);
            Player[] defensiveAssists = UtilPlayer.findAdjacentPlayersWithTacklezones(pGame, defenderTeam, coordinateAssist, false);
            int defendingPlayersOtherThanBlocker = 0;
            for (int y = 0; y < defensiveAssists.length; ++y) {
                if (defensiveAssists[y] == pDefender) continue;
                ++defendingPlayersOtherThanBlocker;
            }
            if (!UtilCards.hasSkill(pGame, offensiveAssists[i], Skill.GUARD) && defendingPlayersOtherThanBlocker != 0) continue;
            ++blockStrength;
        }
        return blockStrength;
    }

    public static int findFoulAssists(Game pGame, Player pAttacker, Player pDefender) {
        int foulAssists = 0;
        FieldCoordinate coordinateDefender = pGame.getFieldModel().getPlayerCoordinate(pDefender);
        for (Player offensiveAssist : UtilPlayer.findAdjacentPlayersWithTacklezones(pGame, pAttacker.getTeam(), coordinateDefender, false)) {
            if (offensiveAssist == pAttacker) continue;
            FieldCoordinate coordinateAssist = pGame.getFieldModel().getPlayerCoordinate(offensiveAssist);
            if (UtilPlayer.findAdjacentPlayersWithTacklezones(pGame, pDefender.getTeam(), coordinateAssist, false).length >= 1 && (!UtilGameOption.isOptionEnabled(pGame, GameOptionId.SNEAKY_GIT_AS_FOUL_GUARD) || !UtilCards.hasSkill(pGame, offensiveAssist, Skill.SNEAKY_GIT))) continue;
            ++foulAssists;
        }
        FieldCoordinate coordinateAttacker = pGame.getFieldModel().getPlayerCoordinate(pAttacker);
        for (Player defensiveAssist : UtilPlayer.findAdjacentPlayersWithTacklezones(pGame, pDefender.getTeam(), coordinateAttacker, false)) {
            if (defensiveAssist == pDefender) continue;
            FieldCoordinate coordinateAssist = pGame.getFieldModel().getPlayerCoordinate(defensiveAssist);
            if (UtilPlayer.findAdjacentPlayersWithTacklezones(pGame, pAttacker.getTeam(), coordinateAssist, false).length >= 2) continue;
            --foulAssists;
        }
        return foulAssists;
    }

    public static Team findOtherTeam(Game pGame, Player pPlayer) {
        Team ownTeam = pPlayer.getTeam();
        if (pGame.getTeamHome() == ownTeam) {
            return pGame.getTeamAway();
        }
        return pGame.getTeamHome();
    }

    public static int findTacklezones(Game pGame, Player pPlayer) {
        Team otherTeam = UtilPlayer.findOtherTeam(pGame, pPlayer);
        FieldCoordinate playerCoordinate = pGame.getFieldModel().getPlayerCoordinate(pPlayer);
        return UtilPlayer.findAdjacentPlayersWithTacklezones(pGame, otherTeam, playerCoordinate, false).length;
    }

    public static void refreshPlayersForTurnStart(Game pGame) {
        FieldModel fieldModel = pGame.getFieldModel();
        Player[] players = pGame.getPlayers();
        for (int i = 0; i < players.length; ++i) {
            PlayerState newPlayerState = null;
            PlayerState oldPlayerState = fieldModel.getPlayerState(players[i]);
            switch (oldPlayerState.getBase()) {
                case 2: 
                case 11: 
                case 12: {
                    newPlayerState = oldPlayerState.changeBase(1).changeActive(true);
                    break;
                }
                case 1: 
                case 3: {
                    newPlayerState = oldPlayerState.changeActive(true);
                    break;
                }
                case 4: {
                    if ((!pGame.isHomePlaying() || !pGame.getTeamHome().hasPlayer(players[i])) && (pGame.isHomePlaying() || !pGame.getTeamAway().hasPlayer(players[i]))) break;
                    newPlayerState = oldPlayerState.changeBase(3).changeActive(false);
                }
            }
            if (newPlayerState != null && newPlayerState.hasUsedPro()) {
                newPlayerState = newPlayerState.changeUsedPro(false);
            } else if (oldPlayerState.hasUsedPro()) {
                newPlayerState = oldPlayerState.changeUsedPro(false);
            }
        }
    }

    public static boolean canHandOver(Game pGame, Player pThrower) {
        FieldModel fieldModel = pGame.getFieldModel();
        FieldCoordinate throwerCoordinate = fieldModel.getPlayerCoordinate(pThrower);
        Team throwerTeam = pGame.getTeamHome().hasPlayer(pThrower) ? pGame.getTeamHome() : pGame.getTeamAway();
        return throwerCoordinate.equals(fieldModel.getBallCoordinate()) && !fieldModel.isBallMoving() && UtilPlayer.findAdjacentPlayersWithTacklezones(pGame, throwerTeam, throwerCoordinate, false).length > 0;
    }

    public static boolean canGaze(Game pGame, Player pPlayer) {
        FieldCoordinate playerCoordinate = pGame.getFieldModel().getPlayerCoordinate(pPlayer);
        Team otherTeam = UtilPlayer.findOtherTeam(pGame, pPlayer);
        if (!UtilCards.hasSkill(pGame, pPlayer, Skill.HYPNOTIC_GAZE)) {
            return false;
        }
        return UtilPlayer.findAdjacentPlayersWithTacklezones(pGame, otherTeam, playerCoordinate, false).length > 0;
    }

    public static boolean canFoul(Game pGame, Player pPlayer) {
        FieldModel fieldModel = pGame.getFieldModel();
        FieldCoordinate playerCoordinate = fieldModel.getPlayerCoordinate(pPlayer);
        Team otherTeam = UtilPlayer.findOtherTeam(pGame, pPlayer);
        return UtilPlayer.findAdjacentPronePlayers(pGame, otherTeam, playerCoordinate).length > 0;
    }

    public static boolean isBallAvailable(Game pGame, Player pPlayer) {
        return pPlayer != null && pGame != null && pGame.getFieldModel().isBallInPlay() && (pGame.getFieldModel().isBallMoving() || pGame.getFieldModel().getBallCoordinate().equals(pGame.getFieldModel().getPlayerCoordinate(pPlayer)));
    }

    public static boolean hasBall(Game pGame, Player pPlayer) {
        return pPlayer != null && pGame != null && pGame.getFieldModel().isBallInPlay() && !pGame.getFieldModel().isBallMoving() && pGame.getFieldModel().getBallCoordinate().equals(pGame.getFieldModel().getPlayerCoordinate(pPlayer));
    }

    public static Player[] findThrowableTeamMates(Game pGame, Player pThrower) {
        Player[] adjacentPlayers;
        ArrayList<Player> throwablePlayers = new ArrayList<Player>();
        FieldModel fieldModel = pGame.getFieldModel();
        FieldCoordinate throwerCoordinate = fieldModel.getPlayerCoordinate(pThrower);
        for (Player adjacentPlayer : adjacentPlayers = UtilPlayer.findAdjacentPlayersWithTacklezones(pGame, pThrower.getTeam(), throwerCoordinate, false)) {
            if (!UtilCards.hasSkill(pGame, adjacentPlayer, Skill.RIGHT_STUFF)) continue;
            throwablePlayers.add(adjacentPlayer);
        }
        return throwablePlayers.toArray(new Player[throwablePlayers.size()]);
    }

    public static boolean canThrowTeamMate(Game pGame, Player pThrower, boolean pCheckPassUsed) {
        return pThrower != null && (!pCheckPassUsed || !pGame.getTurnData().isPassUsed()) && UtilCards.hasSkill(pGame, pThrower, Skill.THROW_TEAM_MATE) && UtilPlayer.findThrowableTeamMates(pGame, pThrower).length > 0;
    }

    public static boolean isBlockable(Game pGame, Player pPlayer) {
        ActingPlayer actingPlayer = pGame.getActingPlayer();
        if (pPlayer != null) {
            PlayerState defenderState = pGame.getFieldModel().getPlayerState(pPlayer);
            FieldCoordinate defenderCoordinate = pGame.getFieldModel().getPlayerCoordinate(pPlayer);
            FieldCoordinate attackerCoordinate = pGame.getFieldModel().getPlayerCoordinate(actingPlayer.getPlayer());
            return defenderState.canBeBlocked() && pGame.getTeamAway().hasPlayer(pPlayer) && defenderCoordinate != null && defenderCoordinate.isAdjacent(attackerCoordinate) && pGame.getFieldModel().getDiceDecoration(defenderCoordinate) != null;
        }
        return false;
    }

    public static boolean isFoulable(Game pGame, Player pPlayer) {
        boolean foulable = false;
        ActingPlayer actingPlayer = pGame.getActingPlayer();
        if (pPlayer != null) {
            PlayerState defenderState = pGame.getFieldModel().getPlayerState(pPlayer);
            FieldCoordinate defenderCoordinate = pGame.getFieldModel().getPlayerCoordinate(pPlayer);
            FieldCoordinate attackerCoordinate = pGame.getFieldModel().getPlayerCoordinate(actingPlayer.getPlayer());
            foulable = (defenderState.getBase() == 3 || defenderState.getBase() == 4) && pGame.getTeamAway().hasPlayer(pPlayer) && defenderCoordinate != null && defenderCoordinate.isAdjacent(attackerCoordinate) && !UtilCards.hasCard(pGame, pPlayer, Card.GOOD_OLD_MAGIC_CODPIECE);
        }
        return foulable;
    }

    public static boolean isPickUp(Game pGame) {
        ActingPlayer actingPlayer = pGame.getActingPlayer();
        FieldCoordinate playerCoordinate = pGame.getFieldModel().getPlayerCoordinate(actingPlayer.getPlayer());
        return pGame.getFieldModel().isBallInPlay() && pGame.getFieldModel().isBallMoving() && playerCoordinate.equals(pGame.getFieldModel().getBallCoordinate());
    }

    public static void sortByPlayerNr(Player[] pPlayerArray) {
        Arrays.sort(pPlayerArray, new Comparator<Player>(){

            @Override
            public int compare(Player pPlayer1, Player pPlayer2) {
                return pPlayer1.getNr() - pPlayer2.getNr();
            }
        });
    }

    public static boolean isNextMoveGoingForIt(Game pGame) {
        boolean nextMoveGoingForIt = false;
        ActingPlayer actingPlayer = pGame.getActingPlayer();
        Player player = actingPlayer.getPlayer();
        if (player != null) {
            if (pGame.getTurnMode() == TurnMode.KICKOFF_RETURN || pGame.getTurnMode() == TurnMode.PASS_BLOCK) {
                return false;
            }
            nextMoveGoingForIt = actingPlayer.isStandingUp() && !actingPlayer.hasActed() && !UtilCards.hasSkill(pGame, actingPlayer, Skill.JUMP_UP) ? 3 >= UtilCards.getPlayerMovement(pGame, player) : (actingPlayer.isLeaping() ? actingPlayer.getCurrentMove() + 1 >= UtilCards.getPlayerMovement(pGame, player) : actingPlayer.getCurrentMove() >= UtilCards.getPlayerMovement(pGame, player));
        }
        return nextMoveGoingForIt;
    }

    public static boolean isNextMoveDodge(Game pGame) {
        Player player;
        boolean nextMoveDodge = false;
        if (pGame.getActingPlayer() != null && (player = pGame.getActingPlayer().getPlayer()) != null) {
            FieldCoordinate playerCoordinate;
            Team otherTeam = UtilPlayer.findOtherTeam(pGame, player);
            nextMoveDodge = UtilPlayer.findAdjacentPlayersWithTacklezones(pGame, otherTeam, playerCoordinate = pGame.getFieldModel().getPlayerCoordinate(player), false).length > 0;
        }
        return nextMoveDodge;
    }

    public static boolean isNextMovePossible(Game pGame, boolean pLeaping) {
        boolean movePossible = false;
        ActingPlayer actingPlayer = pGame.getActingPlayer();
        PlayerState playerState = pGame.getFieldModel().getPlayerState(actingPlayer.getPlayer());
        if (playerState != null && playerState.isAbleToMove()) {
            if (pGame.getTurnMode() == TurnMode.KICKOFF_RETURN || pGame.getTurnMode() == TurnMode.PASS_BLOCK) {
                movePossible = pLeaping ? actingPlayer.getCurrentMove() < 2 : actingPlayer.getCurrentMove() < 3;
            } else {
                int extraMove = 0;
                if (actingPlayer.isGoingForIt()) {
                    int n = extraMove = UtilCards.hasSkill(pGame, actingPlayer, Skill.SPRINT) ? 3 : 2;
                    if (pLeaping) {
                        --extraMove;
                    }
                }
                movePossible = actingPlayer.getCurrentMove() < UtilCards.getPlayerMovement(pGame, actingPlayer.getPlayer()) + extraMove;
            }
        }
        return movePossible;
    }

    public static boolean isPickup(Game pGame) {
        Player player = pGame.getActingPlayer().getPlayer();
        FieldCoordinate playerCoordinate = pGame.getFieldModel().getPlayerCoordinate(player);
        return pGame.getFieldModel().isBallMoving() && playerCoordinate.equals(pGame.getFieldModel().getBallCoordinate());
    }

    public static boolean testPlayersAbleToAct(Game pGame, Team pTeam) {
        for (Player player : pTeam.getPlayers()) {
            FieldCoordinate playerCoordinate = pGame.getFieldModel().getPlayerCoordinate(player);
            PlayerState playerState = pGame.getFieldModel().getPlayerState(player);
            if (playerCoordinate == null || playerCoordinate.isBoxCoordinate() || playerState == null || !playerState.isActive()) continue;
            return true;
        }
        return false;
    }

    public static Player[] findPlayersInReserveOrField(Game pGame, Team pTeam) {
        ArrayList<Player> playersInBoxOrField = new ArrayList<Player>();
        for (Player player : pTeam.getPlayers()) {
            PlayerState playerState;
            FieldCoordinate playerCoordinate = pGame.getFieldModel().getPlayerCoordinate(player);
            if (playerCoordinate != null && !playerCoordinate.isBoxCoordinate()) {
                playersInBoxOrField.add(player);
            }
            if ((playerState = pGame.getFieldModel().getPlayerState(player)) == null || playerState.getBase() != 9) continue;
            playersInBoxOrField.add(player);
        }
        return playersInBoxOrField.toArray(new Player[playersInBoxOrField.size()]);
    }

}


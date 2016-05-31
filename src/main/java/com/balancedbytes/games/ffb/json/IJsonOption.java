package com.balancedbytes.games.ffb.json;

import com.balancedbytes.games.ffb.PlayerActionFactory;
import com.balancedbytes.games.ffb.net.NetCommandIdFactory;
import com.balancedbytes.games.ffb.report.ReportIdFactory;

public interface IJsonOption {
    JsonStringOption ACTING_PLAYER_ID = new JsonStringOption("actingPlayerId");
    JsonBooleanOption ARMOR_BROKEN = new JsonBooleanOption("armorBroken");
    JsonIntArrayOption ARMOR_ROLL = new JsonIntArrayOption("armorRoll");
    JsonIntArrayOption BLOCK_ROLL = new JsonIntArrayOption("blockRoll");
    JsonIntOption BLOODWEISER_BABES = new JsonIntOption("bloodweiserBabes");
    JsonIntArrayOption CASUALTY_ROLL = new JsonIntArrayOption("casualtyRoll");
    JsonIntArrayOption CASUALTY_ROLL_DECAY = new JsonIntArrayOption("casualtyRollDecay");
    JsonStringOption CHOOSING_TEAM_ID = new JsonStringOption("choosingTeamId");
    JsonStringOption COACH = new JsonStringOption("coach");
    JsonArrayOption COMMAND_ARRAY = new JsonArrayOption("commandArray");
    JsonIntOption COMMAND_NR = new JsonIntOption("commandNr");
    JsonStringOption DEFENDER_ID = new JsonStringOption("defenderId");
    JsonBooleanOption EXHAUSTED = new JsonBooleanOption("exhausted");
    JsonIntArrayOption FAN_FACTOR_ROLL_AWAY = new JsonIntArrayOption("fanFactorRollAway");
    JsonIntArrayOption FAN_FACTOR_ROLL_HOME = new JsonIntArrayOption("fanFactorRollHome");
    JsonObjectOption GAME = new JsonObjectOption("game");
    JsonLongOption GAME_ID = new JsonLongOption("gameId");
    JsonArrayOption HEAT_EXHAUSTION_ARRAY = new JsonArrayOption("heatExhaustionArray");
    JsonIntArrayOption INJURY_ROLL = new JsonIntArrayOption("injuryRoll");
    JsonArrayOption KNOCKOUT_RECOVERY_ARRAY = new JsonArrayOption("knockoutRecoveryArray");
    JsonIntArrayOption MASTER_CHEF_ROLL = new JsonIntArrayOption("masterChefRoll");
    JsonEnumWithNameOption NET_COMMAND_ID = new JsonEnumWithNameOption("netCommandId", new NetCommandIdFactory());
    JsonEnumWithNameOption PLAYER_ACTION = new JsonEnumWithNameOption("playerAction", new PlayerActionFactory());
    JsonArrayOption PLAYER_ARRAY = new JsonArrayOption("playerArray");
    JsonStringOption PLAYER_ID = new JsonStringOption("playerId");
    JsonStringOption RACE = new JsonStringOption("race");
    JsonBooleanOption RECOVERING = new JsonBooleanOption("recovering");
    JsonIntOption REPLAY_TO_COMMAND_NR = new JsonIntOption("replayToCommandNr");
    JsonEnumWithNameOption REPORT_ID = new JsonEnumWithNameOption("reportId", new ReportIdFactory());
    JsonObjectOption REPORT_LIST = new JsonObjectOption("reportList");
    JsonArrayOption REPORTS = new JsonArrayOption("reports");
    JsonIntOption ROLL = new JsonIntOption("roll");
    JsonIntOption ROLL_AWAY = new JsonIntOption("rollAway");
    JsonIntOption ROLL_HOME = new JsonIntOption("rollHome");
    JsonIntArrayOption ROLLS_AWAY = new JsonIntArrayOption("rollsAway");
    JsonIntArrayOption ROLLS_HOME = new JsonIntArrayOption("rollsHome");
    JsonIntArrayOption SPECTATOR_ROLL_AWAY = new JsonIntArrayOption("spectatorRollAway");
    JsonIntArrayOption SPECTATOR_ROLL_HOME = new JsonIntArrayOption("spectatorRollHome");
    JsonObjectOption TEAM_AWAY = new JsonObjectOption("teamAway");
    JsonObjectOption TEAM_HOME = new JsonObjectOption("teamHome");
    JsonStringOption TEAM_ID = new JsonStringOption("teamId");
    JsonStringOption TEAM_NAME = new JsonStringOption("teamName");
    JsonIntArrayOption TENTACLE_ROLL = new JsonIntArrayOption("tentacleRoll");
    JsonIntOption TOTAL_NR_OF_COMMANDS = new JsonIntOption("totalNrOfCommands");
    JsonIntOption WINNINGS_ROLL_AWAY = new JsonIntOption("winningsRollAway");
    JsonIntOption WINNINGS_ROLL_HOME = new JsonIntOption("winningsRollHome");
    JsonBooleanOption RE_ROLL_INJURY = new JsonBooleanOption("reRollInjury");
}


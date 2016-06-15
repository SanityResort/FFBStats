package org.butterbrot.ffb.stats.collections;

import refactored.com.balancedbytes.games.ffb.BlockResult;
import refactored.com.balancedbytes.games.ffb.BlockResultFactory;
import refactored.com.balancedbytes.games.ffb.report.ReportId;

import java.util.HashMap;
import java.util.Map;

public class TeamStatsCollection {

    private transient BlockResultFactory factory = new BlockResultFactory();

    private Map<Integer, Integer> singleRolls = initNonBlockStatsMap(6);
    private Map<Integer, Integer> successfulSingleRolls = initNonBlockStatsMap(2, 6);
    private Map<Integer, Integer> successfulDodgeRolls = initNonBlockStatsMap(2, 6);
    private Map<Integer, Integer> successfulGfiRolls = initNonBlockStatsMap(2, 3);
    private Map<Integer, Integer> failedDodgeRolls = initNonBlockStatsMap(2, 6);
    private Map<Integer, Integer> failedGfiRolls = initNonBlockStatsMap(2, 3);
    private Map<Integer, Integer> totalSingleRolls = initNonBlockStatsMap(6);
    private Map<Integer, Integer> doubleRolls = initNonBlockStatsMap(2, 12);
    private Map<Integer, Integer> totalDoubleRolls = initNonBlockStatsMap(2, 12);
    private Map<Integer, Integer> armourRolls = initNonBlockStatsMap(2, 12);
    private Map<Integer, Integer> injuryRolls = initNonBlockStatsMap(2, 12);
    private Map<BlockResult, Integer> blockDice = initBlockDiceMap();
    private Map<Integer, Integer> totalBlocks = initBlockStatsMap();
    private Map<Integer, Integer> rerolledBlocks = initBlockStatsMap();
    private Map<Integer, Integer> successfulBlocks = initBlockStatsMap();
    private Map<Integer, Integer> failedBlocks = initBlockStatsMap();

    private String teamName;
    private String coach;
    private String race;

    public TeamStatsCollection(String teamName, String coach, String race) {
        this.teamName = teamName;
        this.coach = coach;
        this.race = race;
    }

    public Map<Integer, Integer> getSuccessfulDodgeRolls() {
        return successfulDodgeRolls;
    }

    public Map<Integer, Integer> getSuccessfulGfiRolls() {
        return successfulGfiRolls;
    }

    public Map<Integer, Integer> getFailedDodgeRolls() {
        return failedDodgeRolls;
    }

    public Map<Integer, Integer> getFailedGfiRolls() {
        return failedGfiRolls;
    }

    public Map<Integer, Integer> getSuccessfulSingleRolls() {
        return successfulSingleRolls;
    }

    public Map<Integer, Integer> getSingleRolls() {
        return singleRolls;
    }

    public Map<Integer, Integer> getTotalSingleRolls() {
        return totalSingleRolls;
    }

    public Map<Integer, Integer> getDoubleRolls() {
        return doubleRolls;
    }

    public Map<Integer, Integer> getTotalDoubleRolls() {
        return totalDoubleRolls;
    }

    public Map<Integer, Integer> getArmourRolls() {
        return armourRolls;
    }

    public Map<Integer, Integer> getInjuryRolls() {
        return injuryRolls;
    }

    public Map<BlockResult, Integer> getBlockDice() {
        return blockDice;
    }

    public Map<Integer, Integer> getTotalBlocks() {
        return totalBlocks;
    }

    public Map<Integer, Integer> getRerolledBlocks() {
        return rerolledBlocks;
    }

    public Map<Integer, Integer> getSuccessfulBlocks() {
        return successfulBlocks;
    }

    public Map<Integer, Integer> getFailedBlocks() {
        return failedBlocks;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getCoach() {
        return coach;
    }

    public String getRace() {
        return race;
    }

    private Map<BlockResult, Integer> initBlockDiceMap() {
        Map<BlockResult, Integer> blockDice = new HashMap<>();
        for (BlockResult blockResult : BlockResult.values()) {
            blockDice.put(blockResult, 0);
        }
        return blockDice;
    }

    private Map<Integer, Integer> initBlockStatsMap() {
        Map<Integer, Integer> stats = new HashMap<>();
        stats.put(-3, 0);
        stats.put(-2, 0);
        stats.put(1, 0);
        stats.put(2, 0);
        stats.put(3, 0);
        return stats;
    }

    private Map<Integer, Integer> initNonBlockStatsMap(int max) {
        return initNonBlockStatsMap(1, max);
    }

    private Map<Integer, Integer> initNonBlockStatsMap(int min, int max) {
        Map<Integer, Integer> stats = new HashMap<>();
        int i = min;
        while (i <= max) {
            stats.put(i++, 0);
        }
        return stats;
    }

    public void addBlockDice(int[] rolls) {
        for (int roll : rolls) {
            BlockResult blockResult = factory.forRoll(roll);
            blockDice.put(blockResult, blockDice.get(blockResult) + 1);
        }
    }

    public void addBlock(int count) {
        increment(totalBlocks, count);
    }

    public void addRerolledBlock(int count) {
        increment(rerolledBlocks, count);
    }

    public void removeBlock(int count) {
        decrement(totalBlocks, count);
    }

    public void addSuccessfulBlock(int count) {
        increment(successfulBlocks, count);
    }

    public void addFailedBlock(int count) {
        increment(failedBlocks, count);
    }

    public void addSuccessRoll(ReportId reportId, int minimumRoll) {
        if (minimumRoll > 1) {
            int maxedRolled = Math.min(6, minimumRoll);
            increment(successfulSingleRolls, maxedRolled);
            if (ReportId.DODGE_ROLL == reportId) {
                increment(successfulDodgeRolls, maxedRolled);
            } else if (ReportId.GO_FOR_IT_ROLL == reportId) {
                increment(successfulGfiRolls, Math.min(3, minimumRoll));
            }
        }
    }

    public void addFailedRoll(ReportId reportId, int minimumRoll){
        if (minimumRoll > 1) {
            if (ReportId.DODGE_ROLL == reportId) {
                increment(failedDodgeRolls, Math.min(6, minimumRoll));
            } else if (ReportId.GO_FOR_IT_ROLL == reportId) {
                increment(failedGfiRolls, Math.min(3, minimumRoll));
            }
        }
    }

    public void addSingleRoll(int roll) {
        increment(singleRolls, roll);
    }

    public void addDoubleRoll(int[] rolls) {
        increment(doubleRolls, rolls[0] + rolls[1]);
        increment(totalDoubleRolls, rolls[0] + rolls[1]);
        increment(totalSingleRolls, rolls[0]);
        increment(totalSingleRolls, rolls[1]);
    }

    public void addArmourRoll(int[] rolls) {
        increment(armourRolls, rolls[0] + rolls[1]);
        increment(totalDoubleRolls, rolls[0] + rolls[1]);
        increment(totalSingleRolls, rolls[0]);
        increment(totalSingleRolls, rolls[1]);
    }

    public void addInjuryRoll(int[] rolls) {
        increment(injuryRolls, rolls[0] + rolls[1]);
        increment(totalDoubleRolls, rolls[0] + rolls[1]);
        increment(totalSingleRolls, rolls[0]);
        increment(totalSingleRolls, rolls[1]);
    }

    private void increment(Map<Integer, Integer> rolls, int roll) {
        rolls.put(roll, rolls.get(roll) + 1);
    }


    private void decrement(Map<Integer, Integer> rolls, int roll) {
        rolls.put(roll, rolls.get(roll) - 1);
    }
}

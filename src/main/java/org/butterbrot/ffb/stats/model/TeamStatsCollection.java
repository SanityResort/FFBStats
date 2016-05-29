package org.butterbrot.ffb.stats.model;

import com.balancedbytes.games.ffb.BlockResult;
import com.balancedbytes.games.ffb.BlockResultFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class TeamStatsCollection {

    private BlockResultFactory factory = new BlockResultFactory();

    private Map<Integer, Integer> singleRolls = initNonBlockStatsMap(6);
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

    private  Map<BlockResult, Integer> initBlockDiceMap() {
        Map<BlockResult, Integer>  blockDice = new HashMap<>();
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
        return initNonBlockStatsMap(1,max);
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

    public void addSuccessfulBlock(int count) {
        increment(successfulBlocks, count);
    }

    public void addFailedBlock(int count) {
        increment(failedBlocks, count);
    }

    public void addSingleRoll(int roll) {
        increment(singleRolls, roll);
        increment(totalSingleRolls, roll);
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

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Total single rolls: \n");
        builder.append(mapToString(totalSingleRolls));
        builder.append("Single rolls: \n");
        builder.append(mapToString(singleRolls));
        builder.append("Total double rolls: \n");
        builder.append(mapToString(totalDoubleRolls));
        builder.append("Double rolls: \n");
        builder.append(mapToString(doubleRolls));
        builder.append("Armour rolls: \n");
        builder.append(mapToString(armourRolls));
        builder.append("Injury rolls: \n");
        builder.append(mapToString(injuryRolls));
        builder.append("Total blocks: \n");
        builder.append(mapToString(totalBlocks));
        builder.append("Rerolled blocks: \n");
        builder.append(mapToString(rerolledBlocks));
        builder.append("Successful blocks: \n");
        builder.append(mapToString(successfulBlocks));
        builder.append("Failed blocks: \n");
        builder.append(mapToString(failedBlocks));
        builder.append("Block dice: \n");
        builder.append(blockMapToString(blockDice));
        return builder.toString();
    }

    private String mapToString(Map<Integer, Integer> map) {
        StringBuilder builder = new StringBuilder();
        Set<Integer> keys = new TreeSet<>();
        keys.addAll(map.keySet());
        for (Integer key : keys) {
            builder.append("Amount of " + key + "s: " + map.get(key) + "\n");
        }
        return builder.toString();
    }

    private String blockMapToString(Map<BlockResult, Integer> map) {
        StringBuilder builder = new StringBuilder();
        Set<BlockResult> keys = new TreeSet<>();
        keys.addAll(map.keySet());
        for (BlockResult key : keys) {
            builder.append("Amount of " + key.getName() + "s: " + map.get(key) + "\n");
        }
        return builder.toString();
    }
}

package org.butterbrot.ffb.stats.collections;

import org.butterbrot.ffb.stats.Turnover;
import refactored.com.balancedbytes.games.ffb.BlockResult;
import refactored.com.balancedbytes.games.ffb.BlockResultFactory;
import refactored.com.balancedbytes.games.ffb.report.ReportId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamStatsCollection {

    private transient BlockResultFactory factory = new BlockResultFactory();

    private Map<Integer, Integer> singleRolls = initNonBlockStatsMap(6);
    private Map<String, Integer> successfulSingleRolls = initSkillRollsMap(2, 6);
    private Map<String, Integer> successfulDodgeRolls = initSkillRollsMap(2, 6);
    private Map<String, Integer> successfulGfiRolls = initSkillRollsMap(2, 3);
    private Map<String, Integer> failedDodgeRolls = initSkillRollsMap(2, 6);
    private Map<String, Integer> failedGfiRolls = initSkillRollsMap(2, 3);
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
    private Map<String, ArmourBreaks> armourBreaks = new HashMap<>();
    private List<Injury> causedInjuries = new ArrayList<>();
    private List<Turnover> turnOvers = new ArrayList<>();

    private String teamName;
    private String coach;
    private String race;
    public TeamStatsCollection() {
    }

    public TeamStatsCollection(String teamName, String coach, String race) {
        this.teamName = teamName;
        this.coach = coach;
        this.race = race;
    }

    public Map<String, Integer> getSuccessfulDodgeRolls() {
        return successfulDodgeRolls;
    }

    public Map<String, Integer> getSuccessfulGfiRolls() {
        return successfulGfiRolls;
    }

    public Map<String, Integer> getFailedDodgeRolls() {
        return failedDodgeRolls;
    }

    public Map<String, Integer> getFailedGfiRolls() {
        return failedGfiRolls;
    }

    public Map<String, Integer> getSuccessfulSingleRolls() {
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

    private Map<String, Integer> initSkillRollsMap(int min, int max) {
        Map<String, Integer> stats = new HashMap<>();
        int i = min;
        while (i <= max) {
            stats.put(i++ + "+", 0);
        }
        return stats;
    }

    public void addTurnOver(Turnover turnOver) {
        turnOvers.add(turnOver);
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
            incrementSkillRolls(successfulSingleRolls, maxedRolled);
            if (ReportId.DODGE_ROLL == reportId) {
                incrementSkillRolls(successfulDodgeRolls, maxedRolled);
            } else if (ReportId.GO_FOR_IT_ROLL == reportId) {
                incrementSkillRolls(successfulGfiRolls, Math.min(3, minimumRoll));
            }
        }
    }

    public void removeSuccessRoll(ReportId reportId, int minimumRoll) {
        if (minimumRoll > 1) {
            int maxedRolled = Math.min(6, minimumRoll);
            decrementSkillRolls(successfulSingleRolls, maxedRolled);
            if (ReportId.DODGE_ROLL == reportId) {
                decrementSkillRolls(successfulDodgeRolls, maxedRolled);
            } else if (ReportId.GO_FOR_IT_ROLL == reportId) {
                decrementSkillRolls(successfulGfiRolls, Math.min(3, minimumRoll));
            }
        }
    }

    public void addFailedRoll(ReportId reportId, int minimumRoll){
        if (minimumRoll > 1) {
            if (ReportId.DODGE_ROLL == reportId) {
                incrementSkillRolls(failedDodgeRolls, Math.min(6, minimumRoll));
            } else if (ReportId.GO_FOR_IT_ROLL == reportId) {
                incrementSkillRolls(failedGfiRolls, Math.min(3, minimumRoll));
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

    public void addArmourBreak(int effectiveAV, boolean mbUsed, boolean poUsed, boolean dpUsed) {
        String key = "AV"+effectiveAV;
        if (!armourBreaks.containsKey(key)) {
            armourBreaks.put(key, new ArmourBreaks());
        }
        if (dpUsed) {
            armourBreaks.get(key).addDpArmourBreak();
        } else {
            armourBreaks.get(key).addArmourBreak(mbUsed, poUsed);
        }
    }

    public void addCausedInjury(String playerId, InjuryState state) {
        causedInjuries.add(new Injury(playerId,state));
    }

    private void increment(Map<Integer, Integer> rolls, int roll) {
        rolls.put(roll, rolls.get(roll) + 1);
    }

    private void incrementSkillRolls(Map<String, Integer> rolls, int roll) {
        String key = roll+"+";
        rolls.put(key, rolls.get(key) + 1);
    }

    private void decrementSkillRolls(Map<String, Integer> rolls, int roll) {
        String key = roll+"+";
        rolls.put(key, rolls.get(key) - 1);
    }

    private void decrement(Map<Integer, Integer> rolls, int roll) {
        rolls.put(roll, rolls.get(roll) - 1);
    }
}

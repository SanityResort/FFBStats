package org.butterbrot.ffb.stats.model;

import com.fumbbl.ffb.BlockResult;
import com.fumbbl.ffb.PlayerAction;
import com.fumbbl.ffb.factory.BlockResultFactory;
import com.fumbbl.ffb.report.ReportId;
import com.fumbbl.ffb.stats.DicePoolStat;
import com.fumbbl.ffb.stats.DieBase;
import com.fumbbl.ffb.stats.DoubleDiceStat;
import com.fumbbl.ffb.stats.SingleDieStat;
import org.butterbrot.ffb.stats.adapter.PlayerActionMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TeamStatsCollection implements Data {

	private final transient BlockResultFactory factory = new BlockResultFactory();

	private final Map<Integer, Integer> singleRolls = initNonBlockStatsMap(6);
	private final Map<String, Integer> successfulSingleRolls = initSkillRollsMap(2, 6);
	private final Map<String, Integer> successfulDodgeRolls = initSkillRollsMap(2, 6);
	private final Map<String, Integer> successfulGfiRolls = initSkillRollsMap(2, 3);
	private final Map<String, Integer> failedDodgeRolls = initSkillRollsMap(2, 6);
	private final Map<String, Integer> failedGfiRolls = initSkillRollsMap(2, 3);
	private final Map<Integer, Integer> totalSingleRolls = initNonBlockStatsMap(6);
	private final Map<Integer, Integer> doubleRolls = initNonBlockStatsMap(2, 12);
	private final Map<Integer, Integer> totalDoubleRolls = initNonBlockStatsMap(2, 12);
	private final Map<Integer, Integer> armourRolls = initNonBlockStatsMap(2, 12);
	private final Map<Integer, Integer> injuryRolls = initNonBlockStatsMap(2, 12);
	private final Map<BlockResult, Integer> blockDice = initBlockDiceMap();
	private final Map<Integer, Integer> totalBlocks = initBlockStatsMap();
	private final Map<Integer, Integer> rerolledBlocks = initBlockStatsMap();
	private final Map<Integer, Integer> successfulBlocks = initBlockStatsMap();
	private final Map<Integer, Integer> failedBlocks = initBlockStatsMap();
	private final Map<String, ArmourBreaks> armourBreaks = new HashMap<>();
	private final List<Injury> causedInjuries = new ArrayList<>();
	private final List<TurnOver> turnOvers = new ArrayList<>();
	private final Map<String, Integer> additionalStats = initAdditionalStatsMap();
	private final Map<PlayerAction, Set<String>> playerActionMap = new HashMap<>();

	private final transient ReportIdMapper reportIdMapper = new ReportIdMapper();
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

	private Map<String, Integer> initAdditionalStatsMap() {
		Map<String, Integer> additionalStats = new HashMap<>();
		for (StatKey key : StatKey.values()) {
			additionalStats.put(key.toString(), 0);
		}
		return additionalStats;
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

	public void addPlayerAction(PlayerAction action, String playerId) {
		PlayerAction mappedAction = PlayerActionMapping.get(action);
		if (!playerActionMap.containsKey(mappedAction)) {
			playerActionMap.put(mappedAction, new HashSet<>());
		}
		playerActionMap.get(mappedAction).add(playerId);
	}

	public void addTurnOver(TurnOver turnOver) {
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

	public void addFailedRoll(ReportId reportId, int minimumRoll) {
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

	public void addArmourBreak(int effectiveAV, boolean mbUsed, boolean poUsed, boolean dpUsed) {
		String key = "AV" + effectiveAV;
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
		causedInjuries.add(new Injury(playerId, state));
	}

	public void addApoUse() {
		incrementAdditionalStat(StatKey.APO);
	}

	public void addBribe() {
		incrementAdditionalStat(StatKey.BRIBE);
	}

	public void addBloodLust() {
		incrementAdditionalStat(StatKey.BLOOD_LUST);
	}

	public void addConfusion() {
		incrementAdditionalStat(StatKey.CONFUSION);
	}

	public void addHypnoticGaze() {
		incrementAdditionalStat(StatKey.HYPNOTIC_GAZE);
	}

	public void addReroll() {
		incrementAdditionalStat(StatKey.REROLL);
	}

	public void addScatter() {
		incrementAdditionalStat(StatKey.SCATTER);
	}

	public void addTakeRoot() {
		incrementAdditionalStat(StatKey.TAKE_ROOT);
	}

	public void addTimeOut() {
		incrementAdditionalStat(StatKey.TIME_OUT);
	}

	public void addTouchdown() {
		incrementAdditionalStat(StatKey.TOUCHDOWN);
	}

	public void addWildAnimal() {
		incrementAdditionalStat(StatKey.WILD_ANIMAL);
	}

	public void addWizardUse() {
		incrementAdditionalStat(StatKey.WIZARD);
	}

	public void add(SingleDieStat stat) {
		if (stat.getValue() > 0) {
			addSingleRoll(getNormalizedValue(stat));
		}
		if (stat.isSuccessful()) {
			addSuccessRoll(stat.getReportId(), stat.getMinimumRoll());
		} else {
			addFailedRoll(stat.getReportId(), stat.getMinimumRoll());
		}
		reportIdMapper.map(stat.getReportId(), stat.isSuccessful()).ifPresent(this::incrementAdditionalStat);
	}

	public void add(DicePoolStat stat) {
		getNormalizedValue(stat).forEach(this::addSingleRoll);
	}

	public void add(DoubleDiceStat stat) {
		addDoubleRoll(getNormalizedValue(stat));
	}

	private static List<Integer> getNormalizedValue(DicePoolStat stat) {
		return stat.getBase() == DieBase.D3 ? (stat.getValue().stream().map(val -> val * 2).collect(Collectors.toList())) : stat.getValue();
	}

	private static Integer getNormalizedValue(SingleDieStat stat) {
		return stat.getBase() == DieBase.D3 ? (stat.getValue() * 2) : stat.getValue();
	}

	private static int[] getNormalizedValue(DoubleDiceStat stat) {
		return stat.getBase() == DieBase.D3 ? Arrays.stream(stat.getValue()).map(val -> val * 2).toArray() : stat.getValue();
	}

	private void incrementAdditionalStat(StatKey key) {
		additionalStats.put(key.toString(), additionalStats.get(key.toString()) + 1);
	}

	private void increment(Map<Integer, Integer> rolls, int roll) {
		rolls.put(roll, rolls.get(roll) + 1);
	}

	private void incrementSkillRolls(Map<String, Integer> rolls, int roll) {
		String key = roll + "+";
		rolls.put(key, rolls.get(key) + 1);
	}

	private void decrementSkillRolls(Map<String, Integer> rolls, int roll) {
		String key = roll + "+";
		rolls.put(key, rolls.get(key) - 1);
	}

	private void decrement(Map<Integer, Integer> rolls, int roll) {
		rolls.put(roll, rolls.get(roll) - 1);
	}

}

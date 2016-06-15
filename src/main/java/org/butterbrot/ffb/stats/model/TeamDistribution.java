package org.butterbrot.ffb.stats.model;

import org.butterbrot.ffb.stats.collections.TeamStatsCollection;

import java.util.ArrayList;
import java.util.List;

public class TeamDistribution {
    private String teamName;
    private String coach;
    private String race;
    private List<Distribution> distributions;
    private Distribution blockDistribution;

    public TeamDistribution(TeamStatsCollection collection) {
        this.teamName = collection.getTeamName();
        this.coach = collection.getCoach();
        this.race = collection.getRace();
        this.distributions = new ArrayList<>();
        distributions.add(new Distribution("Total D6s", collection.getTotalSingleRolls(), Distribution.NUMBER_LABELS));
        distributions.add(new Distribution("Single D6s", collection.getSingleRolls(), Distribution.NUMBER_LABELS));
        distributions.add(new Distribution("Successful Dodges", collection.getSuccessfulDodgeRolls(), Distribution.NUMBER_LABELS));
        distributions.add(new Distribution("Failed Dodges", collection.getFailedDodgeRolls(), Distribution.NUMBER_LABELS));
        distributions.add(new Distribution("Successful GFIs", collection.getSuccessfulGfiRolls(), Distribution.NUMBER_LABELS));
        distributions.add(new Distribution("Failed GFIs", collection.getFailedGfiRolls(), Distribution.NUMBER_LABELS));
        distributions.add(new Distribution("Successful D6s", collection.getSuccessfulSingleRolls(), Distribution.NUMBER_LABELS));
        distributions.add(new Distribution("Armour Rolls", collection.getArmourRolls(), Distribution.NUMBER_LABELS));
        distributions.add(new Distribution("Injury Rolls", collection.getInjuryRolls(), Distribution.NUMBER_LABELS));
        distributions.add(new Distribution("Total 2D6s", collection.getTotalDoubleRolls(), Distribution.NUMBER_LABELS));
        distributions.add(new Distribution("Other 2D6s", collection.getDoubleRolls(), Distribution.NUMBER_LABELS));
        distributions.add(new Distribution("Total Blocks", collection.getTotalBlocks(), Distribution.DICE_COUNT_LABLES));
        distributions.add(new Distribution("Rerolled Blocks", collection.getRerolledBlocks(), Distribution.DICE_COUNT_LABLES));
        distributions.add(new Distribution("Successful Blocks", collection.getSuccessfulBlocks(), Distribution.DICE_COUNT_LABLES));
        distributions.add(new Distribution("Failed Blocks", collection.getFailedBlocks(), Distribution.DICE_COUNT_LABLES));
        blockDistribution = new Distribution("Total Block Dice",collection.getBlockDice());
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

    public List<Distribution> getDistributions() {
        return distributions;
    }

    public Distribution getBlockDistribution() {
        return blockDistribution;
    }
}

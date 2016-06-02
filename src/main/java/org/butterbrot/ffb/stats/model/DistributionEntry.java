package org.butterbrot.ffb.stats.model;

public class DistributionEntry {
    private int count;
    private double totalPercentage;
    private double relativePercentage;
    private String label;

    public DistributionEntry(int count, double totalPercentage, double relativePercentage, String label) {
        this.count = count;
        this.totalPercentage = totalPercentage;
        this.relativePercentage = relativePercentage;
        this.label = label;
    }

    public int getCount() {
        return count;
    }

    public double getTotalPercentage() {
        return totalPercentage;
    }

    public double getRelativePercentage() {
        return relativePercentage;
    }

    public String getLabel() {
        return label;
    }
}

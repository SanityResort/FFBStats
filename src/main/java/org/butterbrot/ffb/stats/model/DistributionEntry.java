package org.butterbrot.ffb.stats.model;

public class DistributionEntry {
    private int count;
    private double percentage;
    private String label;

    public DistributionEntry(int count, double percentage, String label) {
        this.count = count;
        this.percentage = percentage;
        this.label = label;
    }

    public int getCount() {
        return count;
    }

    public double getPercentage() {
        return percentage;
    }

    public String getLabel() {
        return label;
    }
}

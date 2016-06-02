package org.butterbrot.ffb.stats.model;

import refactored.com.balancedbytes.games.ffb.BlockResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

class Distribution {

    static final Map<Integer, String> NUMBER_LABELS = new HashMap<>();
    private static final Map<BlockResult, String> SYMBOL_LABELS = new HashMap<>();
    static final Map<Integer, String> DICE_COUNT_LABLES = new HashMap<>();

    static {
        for (int i = 1; i < 13; i++) {
            NUMBER_LABELS.put(i, String.valueOf(i) + "s");
        }
        for (BlockResult blockResult : BlockResult.values()) {
            SYMBOL_LABELS.put(blockResult, blockResult.name());
        }

        DICE_COUNT_LABLES.put(-3, "-3dbs");
        DICE_COUNT_LABLES.put(-2, "-2dbs");
        DICE_COUNT_LABLES.put(1, "1dbs");
        DICE_COUNT_LABLES.put(2, "2dbs");
        DICE_COUNT_LABLES.put(3, "3dbs");
    }

    private String caption;
    private List<DistributionEntry> entries;
    private int sum;
    private int max;

    Distribution(String caption, Map<BlockResult, Integer> stats) {
        init(caption, stats);
        Set<BlockResult> keys = new TreeSet<>();
        keys.addAll(stats.keySet());
        for (BlockResult key : keys) {
            int count = stats.get(key);
            entries.add(new DistributionEntry(count, getPercentage(count, sum), getPercentage(count, max), SYMBOL_LABELS.get(key)));
        }
    }

    Distribution(String caption, Map<Integer, Integer> stats, Map<Integer, String> labels) {
        init(caption, stats);
        Set<Integer> keys = new TreeSet<>();
        keys.addAll(stats.keySet());
        for (Integer key : keys) {
            int count = stats.get(key);
            entries.add(new DistributionEntry(count, getPercentage(count, sum), getPercentage(count, max), labels.get(key)));
        }
    }

    private void init(String caption, Map<?, Integer> stats){
        this.caption = caption;
        entries = new ArrayList<>();
        for (int value : stats.values()) {
            sum += value;
            max = Math.max(max, value);
        }
    }

    private double getPercentage(int count, int total) {
        double percentage = total == 0 ? 0 : (double) count / total;
        return (double) Math.round(percentage * 10000) / 100;
    }

    public String getCaption() {
        return caption;
    }

    public List<DistributionEntry> getEntries() {
        return entries;
    }

    public int getSum() {
        return sum;
    }
}

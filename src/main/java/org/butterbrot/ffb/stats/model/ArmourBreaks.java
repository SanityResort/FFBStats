package org.butterbrot.ffb.stats.model;

public class ArmourBreaks implements Data {
    private int unmodified;
    private int withMB;
    private int withPO;
    private int withPOMB;
    private int withDP;

    public void addArmourBreak(boolean mbUsed, boolean poUsed) {
        if (mbUsed) {
            if (poUsed) {
                withPOMB++;
            } else {
                withMB++;
            }
        } else if (poUsed) {
            withPO++;
        } else {
            unmodified++;
        }
    }

    public void addDpArmourBreak() {
        withDP++;
    }
}

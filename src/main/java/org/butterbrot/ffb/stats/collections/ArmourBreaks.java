package org.butterbrot.ffb.stats.collections;

public class ArmourBreaks {
    private int unmodified;
    private int withMB;
    private int withPO;
    private int withPOMB;

    public void addArmourBreak(boolean mbUsed, boolean poUsed) {
        if (mbUsed) {
            if (poUsed) {
                withPOMB++;
            } else {
                withMB++;
            }
        } else  if (poUsed){
            withPO++;
        } else {
            unmodified++;
        }
    }
}

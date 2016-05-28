/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.InjuryAttribute;

public enum SeriousInjury implements IEnumWithId,
IEnumWithName
{
    BROKEN_RIBS(1, "Broken Ribs (MNG)", "Broken Ribs (Miss next game)", "has broken some ribs (Miss next game)", "is recovering from broken ribs", false, null),
    GROIN_STRAIN(2, "Groin Strain (MNG)", "Groin Strain (Miss next game)", "has got a groin strain (Miss next game)", "is recovering from a groin strain", false, null),
    GOUGED_EYE(3, "Gouged Eye (MNG)", "Gouged Eye (Miss next game)", "has got a gouged eye (Miss next game)", "is recovering from a gouged eye", false, null),
    BROKEN_JAW(4, "Broken Jaw (MNG)", "Broken Jaw (Miss next game)", "has got a broken jaw (Miss next game)", "is recovering from a broken jaw", false, null),
    FRACTURED_ARM(5, "Fractured Arm (MNG)", "Fractured Arm (Miss next game)", "has got a fractured arm (Miss next game)", "is recovering from a fractured arm", false, null),
    FRACTURED_LEG(6, "Fractured Leg (MNG)", "Fractured Leg (Miss next game)", "has got a fractured leg (Miss next game)", "is recovering from a fractured leg", false, null),
    SMASHED_HAND(7, "Smashed Hand (MNG)", "Smashed Hand (Miss next game)", "has got a smashed hand (Miss next game)", "is recovering from a smashed hand", false, null),
    PINCHED_NERVE(8, "Pinched Nerve (MNG)", "Pinched Nerve (Miss next game)", "has got a pinched nerve (Miss next game)", "is recovering from a pinched nerve", false, null),
    DAMAGED_BACK(9, "Damaged Back (NI)", "Damaged Back (Niggling Injury)", "has got a damaged back (Niggling Injury)", "is recovering from a damaged back (Niggling Injury)", true, InjuryAttribute.NI),
    SMASHED_KNEE(10, "Smashed Knee (NI)", "Smashed Knee (Niggling Injury)", "has got a smashed knee (Niggling Injury)", "is recovering from a smashed knee (Niggling Injury)", true, InjuryAttribute.NI),
    SMASHED_HIP(11, "Smashed Hip (-MA)", "Smashed Hip (-1 MA)", "has got a smashed hip (-1 MA)", "is recovering from a smashed hip (-1 MA)", true, InjuryAttribute.MA),
    SMASHED_ANKLE(12, "Smashed Ankle (-MA)", "Smashed Ankle (-1 MA)", "has got a smashed ankle (-1 MA)", "is recovering from a smashed ankle (-1 MA)", true, InjuryAttribute.MA),
    SERIOUS_CONCUSSION(13, "Serious Concussion (-AV)", "Serious Concussion (-1 AV)", "has got a serious concussion (-1 AV)", "is recovering from a serious concussion (-1 AV)", true, InjuryAttribute.AV),
    FRACTURED_SKULL(14, "Fractured Skull (-AV)", "Fractured Skull (-1 AV)", "has got a fractured skull (-1 AV)", "is recovering from a fractured skull (-1 AV)", true, InjuryAttribute.AV),
    BROKEN_NECK(15, "Broken Neck (-AG)", "Broken Neck (-1 AG)", "has got a broken neck (-1 AG)", "is recovering from a broken neck (-1 AG)", true, InjuryAttribute.AG),
    SMASHED_COLLAR_BONE(16, "Smashed Collar Bone (-ST)", "Smashed Collar Bone (-1 ST)", "has got a smashed collar bone (-1 ST)", "is recovering from a smashed collar bone (-1 ST)", true, InjuryAttribute.ST),
    DEAD(17, "Dead (RIP)", "Dead (RIP)", "is dead", "is dead", true, null);
    
    private int fId;
    private String fName;
    private String fButtonText;
    private String fDescription;
    private String fRecovery;
    private boolean fLasting;
    private InjuryAttribute fInjuryAttribute;

    private SeriousInjury(int pValue, String pName, String pButtonText, String pDescription, String pRecovery, boolean pLasting, InjuryAttribute pInjuryAttribute) {
        this.fId = pValue;
        this.fName = pName;
        this.fButtonText = pButtonText;
        this.fDescription = pDescription;
        this.fRecovery = pRecovery;
        this.fLasting = pLasting;
        this.fInjuryAttribute = pInjuryAttribute;
    }

    @Override
    public int getId() {
        return this.fId;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public String getButtonText() {
        return this.fButtonText;
    }

    public String getDescription() {
        return this.fDescription;
    }

    public String getRecovery() {
        return this.fRecovery;
    }

    public boolean isLasting() {
        return this.fLasting;
    }

    public InjuryAttribute getInjuryAttribute() {
        return this.fInjuryAttribute;
    }
}


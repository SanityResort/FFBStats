package org.butterbrot.ffb.stats.validation;

import com.google.common.collect.Lists;
import org.butterbrot.ffb.stats.collections.ArmourBreaks;
import org.butterbrot.ffb.stats.collections.Injury;
import org.butterbrot.ffb.stats.collections.InjuryState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationRunner {

    private static final Logger logger = LoggerFactory.getLogger(ValidationRunner.class);

    public static void main(String[] args) {
        DataValidator validator = new DataValidator();
        validator.setDelegateValidators(Lists.newArrayList(validator, new StringValidator(), new InjuryStateValidator
                (), new IntegerValidator()));

        Injury baseline = new Injury("player1", InjuryState.BH);
        Injury toValidate = new Injury("player2", InjuryState.SI);

        ArmourBreaks baselineBreaks = new ArmourBreaks();
        ArmourBreaks toValidateBreaks = new ArmourBreaks();

        baselineBreaks.addArmourBreak(true, false);
        baselineBreaks.addDpArmourBreak();

        toValidateBreaks.addDpArmourBreak();
        toValidateBreaks.addDpArmourBreak();
        toValidateBreaks.addArmourBreak(false, false);

        logger.info("Starting validation");
        validator.validate(baseline, toValidate);
        validator.validate(baselineBreaks, toValidateBreaks);
        logger.info("Finished validation");
    }

}

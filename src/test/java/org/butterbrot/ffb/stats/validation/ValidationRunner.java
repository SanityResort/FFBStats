package org.butterbrot.ffb.stats.validation;

import com.google.common.collect.Lists;
import org.butterbrot.ffb.stats.collections.ArmourBreaks;
import org.butterbrot.ffb.stats.collections.Injury;
import org.butterbrot.ffb.stats.collections.InjuryState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ValidationRunner {

    private static final Logger logger = LoggerFactory.getLogger(ValidationRunner.class);

    public static void main(String[] args) {
        DataValidator dataValidator = new DataValidator();
        ListValidator listValidator = new ListValidator();
        MapValidator mapValidator = new MapValidator();
        List<Validator> delegates = Lists.newArrayList(dataValidator, new StringValidator(), new InjuryStateValidator
                (), new IntegerValidator(), listValidator, mapValidator);
        dataValidator.setDelegateValidators(delegates);
        listValidator.setDelegateValidators(delegates);
        mapValidator.setDelegateValidators(delegates);

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
        dataValidator.validate(baseline, toValidate);
        dataValidator.validate(baselineBreaks, toValidateBreaks);
        logger.info("Finished validation");
    }

}

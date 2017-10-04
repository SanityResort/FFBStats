package org.butterbrot.ffb.stats.validation;

import com.google.common.collect.Lists;
import org.butterbrot.ffb.stats.StatsStarter;
import org.butterbrot.ffb.stats.collections.ArmourBreaks;
import org.butterbrot.ffb.stats.collections.Injury;
import org.butterbrot.ffb.stats.collections.InjuryState;
import org.butterbrot.ffb.stats.collections.StatsCollection;
import org.butterbrot.ffb.stats.zmq.MessageProcessor;
import org.butterbrot.ffb.stats.zmq.MessageProcessorInitializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StatsStarter.class)
@ConfigurationProperties(prefix = "debug")
public class ValidationIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(ValidationIntegrationTest.class);

    private String logPathTemplate;

    @Test
    public void validateBaseline() {
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

    private StatsCollection getTestCollection(String replayId) {


        return null;
    }

    public void setLogPathTemplate(String logPathTemplate) {
        this.logPathTemplate = logPathTemplate;
    }
}

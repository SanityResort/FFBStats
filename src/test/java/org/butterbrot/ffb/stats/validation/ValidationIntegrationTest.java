package org.butterbrot.ffb.stats.validation;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.butterbrot.ffb.stats.StatsStarter;
import org.butterbrot.ffb.stats.conversion.Unzipper;
import org.butterbrot.ffb.stats.model.StatsCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StatsStarter.class)
@ConfigurationProperties(prefix = "debug")
public class ValidationIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(ValidationIntegrationTest.class);

    private Gson gson = new Gson();

    private String outputPathTemplate;
    private String inputPathTemplate;

    @Resource
    private Unzipper unzipper;

    @Test
    public void validateBaseline() throws IOException {
        DataValidator dataValidator = new DataValidator();
        ListValidator listValidator = new ListValidator();
        MapValidator mapValidator = new MapValidator();
        IntArrayValidator intArrayValidator = new IntArrayValidator();
        List<Validator> delegates = Lists.newArrayList(dataValidator, new StringValidator(), new InjuryStateValidator
                (), new IntegerValidator(), listValidator, mapValidator, new BooleanValidator(), intArrayValidator);
        dataValidator.setDelegateValidators(delegates);
        listValidator.setDelegateValidators(delegates);
        mapValidator.setDelegateValidators(delegates);
        intArrayValidator.setValidators(delegates);


        logger.info("Starting validation");
        StatsCollection baseline = getExpectedStatsCollection("1004777");
        StatsCollection toValidate = getExpectedStatsCollection("1004778");

        dataValidator.validate(baseline, toValidate);
        logger.info("Finished validation");
    }

    private StatsCollection getExpectedStatsCollection(String replayId) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(String.format(outputPathTemplate, replayId)));
        return gson.fromJson(reader, StatsCollection.class);
    }

    private StatsCollection getTestCollection(String replayId) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(String.format(inputPathTemplate, replayId)));


        return null;
    }

    public void setOutputPathTemplate(String outputPathTemplate) {
        this.outputPathTemplate = outputPathTemplate;
    }
}

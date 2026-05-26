package org.butterbrot.ffb.stats.validation;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.butterbrot.ffb.stats.StatsStarter;
import org.butterbrot.ffb.stats.conversion.JsonConverter;
import org.butterbrot.ffb.stats.conversion.Unzipper;
import org.butterbrot.ffb.stats.model.StatsCollection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@EnableConfigurationProperties(value = ValidationIntegrationTest.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StatsStarter.class)
@ConfigurationProperties(prefix = "http")
public class ValidationIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(ValidationIntegrationTest.class);

    private final Gson gson = new Gson();

    private String outputPathTemplate;
    private String inputPathTemplate;

    @Resource
    private Unzipper unzipper;
    @Resource
    private JsonConverter converter;

    @ParameterizedTest
    @ValueSource(strings = {
        "local1",
        "861710",
        "1004777",
        "1005000",
        "1005001",
        "1005002",
        "1005014",
        "1011681",
        "1546677",
        "1548033",
        "1548035",
        "1548042",
        "1902267",
        "1814563",
        "1808149"
    })
    public void validateReplay(String replayId) throws IOException {
        validateBaseline(replayId);
    }

    private void validateBaseline(String replayId) throws IOException {
        DataValidator dataValidator = new DataValidator();
        ListValidator listValidator = new ListValidator();
        MapValidator mapValidator = new MapValidator();
        IntArrayValidator intArrayValidator = new IntArrayValidator();
        //noinspection rawtypes
        List<Validator> delegates = Lists.newArrayList(dataValidator, new StringValidator(), new InjuryStateValidator(),
                new IntegerValidator(), listValidator, mapValidator, new BooleanValidator(), intArrayValidator, new
                        SetValidator());
        dataValidator.setDelegateValidators(delegates);
        listValidator.setDelegateValidators(delegates);
        mapValidator.setDelegateValidators(delegates);
        intArrayValidator.setValidators(delegates);


        logger.info("Starting validation");
        StatsCollection baseline = getExpectedStatsCollection(replayId);
        StatsCollection toValidate = getActualCollection(replayId);
        boolean result = dataValidator.validate(baseline, toValidate);
        assertTrue(result, "Generated data model does not match expectations for replay: " + replayId);
        logger.info("Finished validation");
    }

    @Disabled
    @Test
    public void updateExpectation() throws Exception {
        String replayId = "1808149";
        String statsJson = new Gson().toJson(getActualCollection(replayId));
        String jsonFile = String.format(outputPathTemplate, replayId);
        logger.info("Creating json file: {}", jsonFile);
        Path jsonPath = Paths.get(jsonFile);
        Files.write(jsonPath, statsJson.getBytes(StandardCharsets.UTF_8));
    }

    private StatsCollection getExpectedStatsCollection(String replayId) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(String.format(outputPathTemplate, replayId)));
        return gson.fromJson(reader, StatsCollection.class);
    }

    private StatsCollection getActualCollection(String replayId) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(String.format(inputPathTemplate, replayId)));
        return converter.convert(unzipper.fromGZip(bytes), replayId);
    }

    public void setOutputPathTemplate(String outputPathTemplate) {
        this.outputPathTemplate = outputPathTemplate;
    }

    public void setInputPathTemplate(String inputPathTemplate) {
        this.inputPathTemplate = inputPathTemplate;
    }
}


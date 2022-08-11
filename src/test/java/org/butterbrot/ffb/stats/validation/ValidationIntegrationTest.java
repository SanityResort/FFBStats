package org.butterbrot.ffb.stats.validation;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.butterbrot.ffb.stats.StatsStarter;
import org.butterbrot.ffb.stats.conversion.JsonConverter;
import org.butterbrot.ffb.stats.conversion.Unzipper;
import org.butterbrot.ffb.stats.model.StatsCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StatsStarter.class)
@ConfigurationProperties(prefix = "debug")
public class ValidationIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(ValidationIntegrationTest.class);

    private final Gson gson = new Gson();

    private String outputPathTemplate;
    private String inputPathTemplate;

    @Resource
    private Unzipper unzipper;
    @Resource
    private JsonConverter converter;

    @Test
    public void replay861710() throws IOException {
        validateBaseline("861710");
    }

    @Test
    public void replay1004777() throws IOException {
        validateBaseline("1004777");
    }

    @Test
    public void replay1005000() throws IOException {
        validateBaseline("1005000");
    }

    @Test
    public void replay1005001() throws IOException {
        validateBaseline("1005001");
    }

    @Test
    public void replay1005002() throws IOException {
        validateBaseline("1005002");
    }

    @Test
    public void replay1005014() throws IOException {
        validateBaseline("1005014");
    }

    @Test
    public void replay1011681() throws IOException {
        validateBaseline("1011681");
    }

    @Test
    public void replay1546677() throws IOException {
        validateBaseline("1546677");
    }

    @Test
    public void replay1548033() throws IOException {
        validateBaseline("1548033");
    }

    @Test
    public void replay1548035() throws IOException {
        validateBaseline("1548035");
    }

    @Test
    public void replay1548042() throws IOException {
        validateBaseline("1548042");
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
        assertTrue("Generated data model does not match expectations", result);
        logger.info("Finished validation");
    }

   // @Test
    public void updateExpectation() throws Exception {
        String replayId = "1548042";
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

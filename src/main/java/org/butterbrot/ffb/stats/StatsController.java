package org.butterbrot.ffb.stats;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
@ConfigurationProperties(prefix = "debug")
public class StatsController {

    private static final Logger logger = LoggerFactory.getLogger(StatsController.class);

    private boolean activateOutputLog;
    private String outputPathTemplate;
    private boolean activateInputLog;
    private String inputPathTemplate;

    @Resource
    private StatsProvider provider;

    @RequestMapping(value = "/stats/{replayId}")
    @ResponseBody
    public String stats(@PathVariable(value = "replayId") final String replayId) throws NoSuchReplayException, IOException {

        String statsJson = new Gson().toJson(provider.stats(replayId, activateInputLog, inputPathTemplate));

        try {
            if (activateOutputLog) {
                String jsonFile = String.format(outputPathTemplate, replayId);
                logger.info("Creating json file: {}", jsonFile);
                Path jsonPath = Paths.get(jsonFile);
                Files.write(jsonPath, statsJson.getBytes(Charset.forName("UTF-8")));

            }
        } catch (Throwable err) {
            logger.error("Error writing file", err);
        }
        return statsJson;
    }

    public void setActivateOutputLog(boolean activateOutputLog) {
        this.activateOutputLog = activateOutputLog;
    }

    public void setOutputPathTemplate(String outputPathTemplate) {
        this.outputPathTemplate = outputPathTemplate;
    }

    public void setActivateInputLog(boolean activateInputLog) {
        this.activateInputLog = activateInputLog;
    }

    public void setInputPathTemplate(String inputPathTemplate) {
        this.inputPathTemplate = inputPathTemplate;
    }
}

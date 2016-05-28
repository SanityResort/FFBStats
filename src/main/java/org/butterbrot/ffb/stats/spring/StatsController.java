package org.butterbrot.ffb.stats.spring;

import com.balancedbytes.games.ffb.report.ReportBlockRoll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static javafx.scene.input.KeyCode.R;

@Controller
@EnableAutoConfiguration
public class StatsController {

    private static final Logger logger = LoggerFactory.getLogger(StatsController.class);

    @RequestMapping(value = "/stats/{gameId}")
    @ResponseBody
    public String stats(@PathVariable(value = "gameId") String gameId){
        logger.info("Creating stats for game: {}", gameId);
        return "test" + gameId;
    }


    public static void main(String[] args) {
        SpringApplication.run(StatsController.class, args);
    }
}

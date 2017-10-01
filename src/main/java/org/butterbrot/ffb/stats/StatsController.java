package org.butterbrot.ffb.stats;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.UncheckedExecutionException;
import org.butterbrot.ffb.stats.model.GameDistribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

@Controller
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
@ConfigurationProperties(prefix = "cache")
public class StatsController {

    private static final Logger logger = LoggerFactory.getLogger(StatsController.class);

    private int cacheSize;

    private Cache<String, GameDistribution> cache = CacheBuilder.newBuilder().maximumSize(cacheSize).build();

    @Resource
    private StatsProvider provider;

    @RequestMapping(value = "/stats/{replayId}")
    public String stats(@PathVariable(value = "replayId") final String replayId, Model model) {
        model.addAttribute("replayId", replayId);

        try {
            GameDistribution gameDistribution = cache.get(replayId, new Callable<GameDistribution>() {
                @Override
                public GameDistribution call() throws Exception {
                    return provider.distribution(replayId);
                }
            });

            if (!gameDistribution.isFinished()) {
                cache.invalidate(replayId);
            }

            model.addAttribute("game", gameDistribution);
            return "stats";
        } catch (ExecutionException e) {

            if (e.getCause() instanceof NoSuchReplayException) {
                return "notfound";
            }
            logger.error("Could not load replay", e.getCause());
            return "error";

        } catch (UncheckedExecutionException e) {
            logger.error("Could not load replay", e.getCause());
            return "error";
        }
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }
    // for local testing
    public static void main(String[] args) {
        SpringApplication.run(StatsController.class, args);
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }
}

package org.butterbrot.ffb.stats.web;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.butterbrot.ffb.stats.conversion.JsonConverter;
import org.butterbrot.ffb.stats.conversion.Unzipper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Controller
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
@ConfigurationProperties(prefix = "http")
public class StatsController {

	private static final Logger logger = LoggerFactory.getLogger(StatsController.class);

	private boolean activateOutputLog;
	private String outputPathTemplate;
	private boolean activateInputLog;
	private String inputPathTemplate;
	private String replayEndPoint;
	private boolean addGzipHeader;

	@Resource
	private JsonConverter jsonConverter;

	@Resource
	private Unzipper unzipper;

	private final OkHttpClient client = new OkHttpClient();


	@RequestMapping(value = "/stats", method = RequestMethod.POST)
	@ResponseBody
	public String statsPost(@RequestBody final byte[] replayData) {
		try {
			return createStats(null, replayData);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
				"Could not load replay from posted data", e);
		}
	}

	@RequestMapping(value = "/stats/{replayId}", method = RequestMethod.GET)
	@ResponseBody
	public String stats(@PathVariable(value = "replayId") final String replayId) {

		Request.Builder builder = new Request.Builder().url(String.format(replayEndPoint, replayId));
		if (addGzipHeader) {
		builder.header("Accept-Encoding", "gzip");
		}
		Request request = builder.build();

		try (Response response = client.newCall(request).execute()) {

			if (!response.isSuccessful() || response.body() == null) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Replay with id " + replayId + " not found");
			}

			byte[] gzippedData = Objects.requireNonNull(response.body()).bytes();
			return createStats(replayId, gzippedData);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
				"Could not load replay. Using endpoint value: '" + replayEndPoint + "' and replayId '" + replayId + "' ", e);
		}
	}

	private String createStats(String passedId, byte[] gzippedData) throws IOException {
		JsonObject jsonObject = unzipper.fromGZip(gzippedData);

		String replayId;
		try {
			replayId = jsonObject.get("game").getAsJsonObject().get("gameId").getAsString();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not get gameId/replayId from passed data");
		}

		if (passedId != null && !passedId.equals(replayId)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The replayId passed was " + passedId +
				" but the id from the archive was " + replayId);
		}

		if (passedId == null) {
			logger.info("Extracted gameId {} from passed data", replayId);
		}

		if (activateInputLog) {
			String fileName = String.format(inputPathTemplate, replayId);
			try (OutputStream fileOutputStream = Files.newOutputStream(new File(fileName).toPath())) {
				fileOutputStream.write(gzippedData);
			} catch (IOException e) {
				logger.error("Could not write " + fileName, e);
			}
		}
		String statsJson = new Gson().toJson(jsonConverter.convert(jsonObject, replayId));

		if (activateOutputLog) {
			try {
				String jsonFile = String.format(outputPathTemplate, replayId);
				logger.info("Creating json file: {}", jsonFile);
				Path jsonPath = Paths.get(jsonFile);
				Files.write(jsonPath, statsJson.getBytes(StandardCharsets.UTF_8));
			} catch (Throwable err) {
				logger.error("Error writing file", err);
				return err.getMessage();
			}
		}
		return statsJson;
	}

	@SuppressWarnings("unused")
	public void setActivateOutputLog(boolean activateOutputLog) {
		this.activateOutputLog = activateOutputLog;
	}

	@SuppressWarnings("unused")
	public void setOutputPathTemplate(String outputPathTemplate) {
		this.outputPathTemplate = outputPathTemplate;
	}

	@SuppressWarnings("unused")
	public void setActivateInputLog(boolean activateInputLog) {
		this.activateInputLog = activateInputLog;
	}

	@SuppressWarnings("unused")
	public void setInputPathTemplate(String inputPathTemplate) {
		this.inputPathTemplate = inputPathTemplate;
	}

	@SuppressWarnings("unused")
	public void setReplayEndPoint(String replayEndPoint) {
		this.replayEndPoint = replayEndPoint;
	}

	@SuppressWarnings("unused")
	public void setAddGzipHeader(boolean addGzipHeader) {
		this.addGzipHeader = addGzipHeader;
	}
}

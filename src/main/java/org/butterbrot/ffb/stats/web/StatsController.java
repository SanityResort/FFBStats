package org.butterbrot.ffb.stats.web;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.butterbrot.ffb.stats.NoSuchReplayException;
import org.butterbrot.ffb.stats.conversion.JsonConverter;
import org.butterbrot.ffb.stats.conversion.Unzipper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

	@Resource
	private JsonConverter jsonConverter;

	@Resource
	private Unzipper unzipper;

	@RequestMapping(value = "/stats/{replayId}")
	@ResponseBody
	public String stats(@PathVariable(value = "replayId") final String replayId) throws NoSuchReplayException, IOException, URISyntaxException {

		OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory();
		ClientHttpRequest request = factory.createRequest(new URI(String.format(replayEndPoint, replayId)), HttpMethod.GET);

		try (ClientHttpResponse response = request.execute();
				 InputStream responseStream = response.getBody();
				 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[1024];
			int length;
			while ((length = responseStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, length);
			}

			byte[] gzippedData = outputStream.toByteArray();
			JsonObject jsonObject = unzipper.fromGZip(gzippedData);

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
				String jsonFile = String.format(outputPathTemplate, replayId);
				logger.info("Creating json file: {}", jsonFile);
				Path jsonPath = Paths.get(jsonFile);
				Files.write(jsonPath, statsJson.getBytes(StandardCharsets.UTF_8));

			}
			return statsJson;
		} catch (Throwable err) {
			logger.error("Error writing file", err);
			return err.getMessage();
		}
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
}

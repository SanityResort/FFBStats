package org.butterbrot.ffb.stats.conversion;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

@Service
public class Unzipper {

    public JsonObject fromGZip(byte[] gzippedData) throws IOException {
        try (ByteArrayInputStream byteIn = new ByteArrayInputStream(gzippedData);
            GZIPInputStream gzipInputStream = new GZIPInputStream(byteIn);
            InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream);
            BufferedReader buf = new BufferedReader(inputStreamReader)) {
            Stream<String> stringStream = buf.lines();
            @SuppressWarnings("OptionalGetWithoutIsPresent") String data = stringStream.reduce((s, s2) -> s + s2).get();
            return JsonParser.parseString(data).getAsJsonObject();
        }
    }
}

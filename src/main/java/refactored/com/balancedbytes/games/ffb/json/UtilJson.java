package refactored.com.balancedbytes.games.ffb.json;

import refactored.com.balancedbytes.games.ffb.IEnumWithName;
import refactored.com.balancedbytes.games.ffb.IEnumWithNameFactory;
import refactored.com.balancedbytes.games.ffb.PlayerState;
import refactored.com.balancedbytes.games.ffb.util.StringTool;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public class UtilJson {
    private static final Charset _CHARSET = Charset.forName("UTF-8");

    public static JsonObject toJsonObject(JsonValue pJsonValue) {
        if (pJsonValue == null || !pJsonValue.isObject()) {
            throw new IllegalArgumentException("JsonValue is not an object.");
        }
        return pJsonValue.asObject();
    }

    static PlayerState toPlayerState(JsonValue pJsonValue) {
        if (pJsonValue == null || pJsonValue.isNull()) {
            return null;
        }
        return new PlayerState(pJsonValue.asInt());
    }

    static JsonValue toJsonValue(PlayerState pPlayerState) {
        if (pPlayerState == null) {
            return JsonValue.NULL;
        }
        return JsonValue.valueOf(pPlayerState.getId());
    }

    public static IEnumWithName toEnumWithName(IEnumWithNameFactory pFactory, JsonValue pJsonValue) {
        if (pFactory == null) {
            throw new IllegalArgumentException("Parameter factory must not be null.");
        }
        if (pJsonValue == null || pJsonValue.isNull()) {
            return null;
        }
        return pFactory.forName(pJsonValue.asString());
    }

    public static String deflateToBase64(JsonValue pJsonValue) throws IOException {
        if (pJsonValue == null) {
            return null;
        }
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        DeflaterOutputStream deflaterOut = new DeflaterOutputStream(byteOut, new Deflater(9));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(deflaterOut, _CHARSET));
        out.write(pJsonValue.toString());
        out.close();
        String base64Deflated = Base64.encodeToString(byteOut.toByteArray(), false);
        return new JsonObject().add("compressed", base64Deflated).toString();
    }

    public static JsonValue inflateFromBase64(String pJsonString) throws IOException {
        JsonValue compressedValue;
        if (!StringTool.isProvided(pJsonString)) {
            return null;
        }
        JsonValue jsonValue = JsonValue.readFrom(pJsonString);
        String base64Deflated = null;
        if (jsonValue != null && (compressedValue = jsonValue.asObject().get("compressed")) != null) {
            base64Deflated = compressedValue.asString();
        }
        if (StringTool.isProvided(base64Deflated)) {
            ByteArrayInputStream byteIn = new ByteArrayInputStream(Base64.decodeFast(base64Deflated));
            InputStreamReader in = new InputStreamReader(new InflaterInputStream(byteIn), _CHARSET);
            return JsonValue.readFrom(in);
        }
        return jsonValue;
    }
}


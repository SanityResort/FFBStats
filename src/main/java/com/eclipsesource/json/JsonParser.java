/*
 * Decompiled with CFR 0_122.
 */
package com.eclipsesource.json;

import com.eclipsesource.json.BufferedTextReader;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonNumber;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonString;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.ParseException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

class JsonParser {
    private static final int MIN_BUFFER_SIZE = 10;
    private static final int MAX_BUFFER_SIZE = 1024;
    private final BufferedTextReader reader;
    private int current;
    private StringBuilder buffer;

    JsonParser(Reader reader) {
        this.reader = new BufferedTextReader(reader);
    }

    JsonParser(String string) {
        int buffersize = Math.max(10, Math.min(1024, string.length()));
        this.reader = new BufferedTextReader(new StringReader(string), buffersize);
    }

    JsonValue parse() throws IOException {
        this.read();
        this.skipWhiteSpace();
        JsonValue result = this.readValue();
        this.skipWhiteSpace();
        if (!this.isEndOfText()) {
            throw this.error("Unexpected character");
        }
        return result;
    }

    private JsonValue readValue() throws IOException {
        switch (this.current) {
            case 110: {
                return this.readNull();
            }
            case 116: {
                return this.readTrue();
            }
            case 102: {
                return this.readFalse();
            }
            case 34: {
                return this.readString();
            }
            case 91: {
                return this.readArray();
            }
            case 123: {
                return this.readObject();
            }
            case 45: 
            case 48: 
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 55: 
            case 56: 
            case 57: {
                return this.readNumber();
            }
        }
        throw this.expected("value");
    }

    private JsonArray readArray() throws IOException {
        this.read();
        JsonArray array = new JsonArray();
        this.skipWhiteSpace();
        if (this.readChar(']')) {
            return array;
        }
        do {
            this.skipWhiteSpace();
            array.add(this.readValue());
            this.skipWhiteSpace();
        } while (this.readChar(','));
        if (!this.readChar(']')) {
            throw this.expected("',' or ']'");
        }
        return array;
    }

    private JsonObject readObject() throws IOException {
        this.read();
        JsonObject object = new JsonObject();
        this.skipWhiteSpace();
        if (this.readChar('}')) {
            return object;
        }
        do {
            this.skipWhiteSpace();
            String name = this.readName();
            this.skipWhiteSpace();
            if (!this.readChar(':')) {
                throw this.expected("':'");
            }
            this.skipWhiteSpace();
            object.add(name, this.readValue());
            this.skipWhiteSpace();
        } while (this.readChar(','));
        if (!this.readChar('}')) {
            throw this.expected("',' or '}'");
        }
        return object;
    }

    private String readName() throws IOException {
        if (this.current != 34) {
            throw this.expected("name");
        }
        return this.readStringInternal();
    }

    private JsonValue readNull() throws IOException {
        this.read();
        this.readRequiredChar('u');
        this.readRequiredChar('l');
        this.readRequiredChar('l');
        return JsonValue.NULL;
    }

    private JsonValue readTrue() throws IOException {
        this.read();
        this.readRequiredChar('r');
        this.readRequiredChar('u');
        this.readRequiredChar('e');
        return JsonValue.TRUE;
    }

    private JsonValue readFalse() throws IOException {
        this.read();
        this.readRequiredChar('a');
        this.readRequiredChar('l');
        this.readRequiredChar('s');
        this.readRequiredChar('e');
        return JsonValue.FALSE;
    }

    private void readRequiredChar(char ch) throws IOException {
        if (!this.readChar(ch)) {
            throw this.expected("'" + ch + "'");
        }
    }

    private JsonValue readString() throws IOException {
        return new JsonString(this.readStringInternal());
    }

    private String readStringInternal() throws IOException {
        String captured;
        this.read();
        this.reader.startCapture();
        while (this.current != 34) {
            if (this.current == 92) {
                captured = this.reader.endCapture();
                if (this.buffer == null) {
                    this.buffer = new StringBuilder(captured);
                } else {
                    this.buffer.append(captured);
                }
                this.readEscape(this.buffer);
                this.reader.startCapture();
                continue;
            }
            if (this.current < 32) {
                throw this.expected("valid string character");
            }
            this.read();
        }
        captured = this.reader.endCapture();
        if (this.buffer != null) {
            this.buffer.append(captured);
            captured = this.buffer.toString();
            this.buffer.setLength(0);
        }
        this.read();
        return captured;
    }

    private void readEscape(StringBuilder buffer) throws IOException {
        this.read();
        switch (this.current) {
            case 34: 
            case 47: 
            case 92: {
                buffer.append((char)this.current);
                break;
            }
            case 98: {
                buffer.append('\b');
                break;
            }
            case 102: {
                buffer.append('\f');
                break;
            }
            case 110: {
                buffer.append('\n');
                break;
            }
            case 114: {
                buffer.append('\r');
                break;
            }
            case 116: {
                buffer.append('\t');
                break;
            }
            case 117: {
                char[] hexChars = new char[4];
                for (int i = 0; i < 4; ++i) {
                    this.read();
                    if (!this.isHexDigit()) {
                        throw this.expected("hexadecimal digit");
                    }
                    hexChars[i] = (char)this.current;
                }
                buffer.append((char)Integer.parseInt(String.valueOf(hexChars), 16));
                break;
            }
            default: {
                throw this.expected("valid escape sequence");
            }
        }
        this.read();
    }

    private JsonValue readNumber() throws IOException {
        this.reader.startCapture();
        this.readChar('-');
        int firstDigit = this.current;
        if (!this.readDigit()) {
            throw this.expected("digit");
        }
        if (firstDigit != 48) {
            while (this.readDigit()) {
            }
        }
        this.readFraction();
        this.readExponent();
        return new JsonNumber(this.reader.endCapture());
    }

    private boolean readFraction() throws IOException {
        if (!this.readChar('.')) {
            return false;
        }
        if (!this.readDigit()) {
            throw this.expected("digit");
        }
        while (this.readDigit()) {
        }
        return true;
    }

    private boolean readExponent() throws IOException {
        if (!this.readChar('e') && !this.readChar('E')) {
            return false;
        }
        if (!this.readChar('+')) {
            this.readChar('-');
        }
        if (!this.readDigit()) {
            throw this.expected("digit");
        }
        while (this.readDigit()) {
        }
        return true;
    }

    private boolean readChar(char ch) throws IOException {
        if (this.current != ch) {
            return false;
        }
        this.read();
        return true;
    }

    private boolean readDigit() throws IOException {
        if (!this.isDigit()) {
            return false;
        }
        this.read();
        return true;
    }

    private void skipWhiteSpace() throws IOException {
        while (this.isWhiteSpace()) {
            this.read();
        }
    }

    private void read() throws IOException {
        if (this.isEndOfText()) {
            throw this.error("Unexpected end of input");
        }
        this.current = this.reader.read();
    }

    private ParseException expected(String expected) {
        if (this.isEndOfText()) {
            return this.error("Unexpected end of input");
        }
        return this.error("Expected " + expected);
    }

    private ParseException error(String message) {
        int offset = this.isEndOfText() ? this.reader.getIndex() : this.reader.getIndex() - 1;
        return new ParseException(message, offset, this.reader.getLine(), this.reader.getColumn() - 1);
    }

    private boolean isWhiteSpace() {
        return this.current == 32 || this.current == 9 || this.current == 10 || this.current == 13;
    }

    private boolean isDigit() {
        return this.current >= 48 && this.current <= 57;
    }

    private boolean isHexDigit() {
        return this.current >= 48 && this.current <= 57 || this.current >= 97 && this.current <= 102 || this.current >= 65 && this.current <= 70;
    }

    private boolean isEndOfText() {
        return this.current == -1;
    }
}


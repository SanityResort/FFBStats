/*
 * Decompiled with CFR 0_122.
 */
package com.eclipsesource.json;

import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonWriter;
import java.io.IOException;

class JsonLiteral
extends JsonValue {
    private final String value;

    JsonLiteral(String value) {
        this.value = value;
    }

    @Override
    protected void write(JsonWriter writer) throws IOException {
        writer.write(this.value);
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean asBoolean() {
        return this.isBoolean() ? this.isTrue() : super.asBoolean();
    }

    @Override
    public boolean isNull() {
        return this == NULL;
    }

    @Override
    public boolean isBoolean() {
        return this == TRUE || this == FALSE;
    }

    @Override
    public boolean isTrue() {
        return this == TRUE;
    }

    @Override
    public boolean isFalse() {
        return this == FALSE;
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        JsonLiteral other = (JsonLiteral)object;
        return this.value.equals(other.value);
    }
}


package org.elvaston.model.impl;

import org.elvaston.model.api.Media;

import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * Media implementation for a Json representation of the data.
 */
public class JsonMedia implements Media {

    private final JsonObjectBuilder builder;

    JsonMedia() {
        this(Json.createObjectBuilder());
    }

    private JsonMedia(JsonObjectBuilder builder) {
        this.builder = builder;
    }

    @Override
    public Media with(String name, String value) {
        return new JsonMedia(this.builder.add(name, value));
    }

    @Override
    public Media with(String name, BigDecimal value) {
        return new JsonMedia((this.builder.add(name, value)));
    }

    @Override
    public Media with(String name, long value) {
        return new JsonMedia(this.builder.add(name, value));
    }

    @Override
    public JsonObject json() {
        return this.builder.build();
    }
}

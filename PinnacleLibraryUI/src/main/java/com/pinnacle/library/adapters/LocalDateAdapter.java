package com.pinnacle.library.adapters;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDate;

/**
 * Gson adapter for java.time.LocalDate (ISO-8601).
 */
public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfSrc, JsonDeserializationContext context)
            throws JsonParseException {
        if (json == null || json.isJsonNull()) return null;
        return LocalDate.parse(json.getAsString());
    }

    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return src == null ? JsonNull.INSTANCE : new JsonPrimitive(src.toString());
    }
}

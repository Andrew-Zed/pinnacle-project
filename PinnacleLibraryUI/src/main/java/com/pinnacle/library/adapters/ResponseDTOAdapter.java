package com.pinnacle.library.adapters;

import com.google.gson.*;
import com.pinnacle.library.dto.ResponseDTO;

import java.lang.reflect.Type;

public class ResponseDTOAdapter implements JsonSerializer<ResponseDTO>, JsonDeserializer<ResponseDTO> {

    @Override
    public ResponseDTO deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx)
            throws JsonParseException {
        if (json == null || json.isJsonNull()) {
            return new ResponseDTO(null);
        }

        if (json.isJsonObject()) {
            JsonObject o = json.getAsJsonObject();
            String msg = o.has("message") && !o.get("message").isJsonNull()
                    ? o.get("message").getAsString()
                    : o.toString();
            return new ResponseDTO(msg);
        }

        if (json.isJsonPrimitive()) {
            JsonPrimitive p = json.getAsJsonPrimitive();
            if (p.isString()) {
                return new ResponseDTO(p.getAsString());
            } else {
                return new ResponseDTO(p.getAsString());
            }
        }

        return new ResponseDTO(json.toString());
    }

    @Override
    public JsonElement serialize(ResponseDTO src, Type typeOfSrc, JsonSerializationContext ctx) {
        JsonObject o = new JsonObject();
        o.addProperty("message", src.getMessage());
        return o;
    }

}

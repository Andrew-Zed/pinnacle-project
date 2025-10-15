package com.pinnacle.library.adapters;

import com.google.gson.*;
import com.pinnacle.library.dto.BookRequest;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class BookRequestAdapter implements JsonSerializer<BookRequest>, JsonDeserializer<BookRequest> {

    @Override
    public BookRequest deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx)
            throws JsonParseException {

        JsonObject object = json.getAsJsonObject();
        BookRequest request = new BookRequest();
        if (object.has("title") && !object.get("title").isJsonNull()) request.setTitle(object.get("title").getAsString());
        if (object.has("author") && !object.get("author").isJsonNull()) request.setAuthor(object.get("author").getAsString());
        if (object.has("isbn") && !object.get("isbn").isJsonNull()) request.setIsbn(object.get("isbn").getAsString());
        if (object.has("publishedDate") && !object.get("publishedDate").isJsonNull()) {
            request.setPublishedDate(LocalDate.parse(object.get("publishedDate").getAsString()));
        }
        return request;
    }

    @Override
    public JsonElement serialize(BookRequest src, Type typeOfSrc, JsonSerializationContext ctx) {

        JsonObject object = new JsonObject();
        object.addProperty("title", src.getTitle());
        object.addProperty("author", src.getAuthor());
        object.addProperty("isbn", src.getIsbn());
        if (src.getPublishedDate() != null) {
            object.addProperty("publishedDate", src.getPublishedDate().toString());
        } else {
            object.add("publishedDate", JsonNull.INSTANCE);
        }
        return object;
    }
}

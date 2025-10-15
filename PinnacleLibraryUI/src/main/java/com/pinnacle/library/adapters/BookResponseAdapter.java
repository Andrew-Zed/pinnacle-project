package com.pinnacle.library.adapters;

import com.google.gson.*;
import com.pinnacle.library.dto.BookResponse;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class BookResponseAdapter implements
        JsonDeserializer<BookResponse>,
        JsonSerializer<BookResponse> {

    @Override
    public BookResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject obj = json.getAsJsonObject();
        BookResponse book = new BookResponse();

        if (obj.has("id") && !obj.get("id").isJsonNull()) {
            book.setId(obj.get("id").getAsLong());
        }
        if (obj.has("title") && !obj.get("title").isJsonNull()) {
            book.setTitle(obj.get("title").getAsString());
        }
        if (obj.has("author") && !obj.get("author").isJsonNull()) {
            book.setAuthor(obj.get("author").getAsString());
        }
        if (obj.has("isbn") && !obj.get("isbn").isJsonNull()) {
            book.setIsbn(obj.get("isbn").getAsString());
        }
        if (obj.has("publishedDate") && !obj.get("publishedDate").isJsonNull()) {
            book.setPublishedDate(LocalDate.parse(obj.get("publishedDate").getAsString()));
        }
        return book;
    }

    @Override
    public com.google.gson.JsonElement serialize(BookResponse src, Type typeOfSrc,
                                                 JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        if (src.getId() != null) obj.addProperty("id", src.getId());
        obj.addProperty("title", src.getTitle());
        obj.addProperty("author", src.getAuthor());
        obj.addProperty("isbn", src.getIsbn());
        if (src.getPublishedDate() != null) {
            obj.addProperty("publishedDate", src.getPublishedDate().toString());
        }
        return obj;
    }
}

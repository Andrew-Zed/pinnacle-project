package com.pinnacle.library.adapters;

import com.google.gson.*;
import com.pinnacle.library.model.Book;

import java.lang.reflect.Type;

public class BookAdapter implements JsonSerializer<Book>, JsonDeserializer<Book> {

    @Override
    public Book deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx)
            throws JsonParseException {

        JsonObject o = json.getAsJsonObject();
        Book b = new Book();
        if (o.has("id") && !o.get("id").isJsonNull())      b.setId(o.get("id").getAsLong());
        if (o.has("title") && !o.get("title").isJsonNull()) b.setTitle(o.get("title").getAsString());
        if (o.has("author") && !o.get("author").isJsonNull()) b.setAuthor(o.get("author").getAsString());
        if (o.has("isbn") && !o.get("isbn").isJsonNull())   b.setIsbn(o.get("isbn").getAsString());
        if (o.has("publishedDate") && !o.get("publishedDate").isJsonNull())
            b.setPublishedDate(o.get("publishedDate").getAsString());
        return b;
    }

    @Override
    public JsonElement serialize(Book src, Type typeOfSrc, JsonSerializationContext ctx) {
        JsonObject o = new JsonObject();
        if (src.getId() != null) o.addProperty("id", src.getId());
        o.addProperty("title", src.getTitle());
        o.addProperty("author", src.getAuthor());
        o.addProperty("isbn", src.getIsbn());
        if (src.getPublishedDate() != null) o.addProperty("publishedDate", src.getPublishedDate());
        return o;
    }
}

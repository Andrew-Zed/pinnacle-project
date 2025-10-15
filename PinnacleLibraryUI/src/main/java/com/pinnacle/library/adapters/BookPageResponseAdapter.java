package com.pinnacle.library.adapters;

import com.google.gson.*;
import com.pinnacle.library.dto.BookPageResponse;
import com.pinnacle.library.dto.BookResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Gson adapter for BookPageResponse.
 * Handles both your custom DTO and Spring's Page JSON keys gracefully.
 */
public class BookPageResponseAdapter implements JsonDeserializer<BookPageResponse>, JsonSerializer<BookPageResponse> {

    @Override
    public BookPageResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx)
            throws JsonParseException {

        JsonObject o = json.getAsJsonObject();
        BookPageResponse page = new BookPageResponse();

        List<BookResponse> content = new ArrayList<>();
        if (o.has("content") && o.get("content").isJsonArray()) {
            JsonArray arr = o.get("content").getAsJsonArray();
            for (JsonElement el : arr) {
                BookResponse br = ctx.deserialize(el, BookResponse.class);
                content.add(br);
            }
        }
        page.setContent(content);

        if (o.has("totalPages") && !o.get("totalPages").isJsonNull()) {
            page.setTotalPages(o.get("totalPages").getAsInt());
        } else if (o.has("total_pages") && !o.get("total_pages").isJsonNull()) {
            page.setTotalPages(o.get("total_pages").getAsInt());
        }

        if (o.has("totalElements") && !o.get("totalElements").isJsonNull()) {
            page.setTotalElements(o.get("totalElements").getAsLong());
        } else if (o.has("total_elements") && !o.get("total_elements").isJsonNull()) {
            page.setTotalElements(o.get("total_elements").getAsLong());
        }

        if (o.has("pageNumber") && !o.get("pageNumber").isJsonNull()) {
            page.setPageNumber(o.get("pageNumber").getAsInt());
        } else if (o.has("number") && !o.get("number").isJsonNull()) {
            page.setPageNumber(o.get("number").getAsInt());
        }

        if (o.has("pageSize") && !o.get("pageSize").isJsonNull()) {
            page.setPageSize(o.get("pageSize").getAsInt());
        } else if (o.has("size") && !o.get("size").isJsonNull()) {
            page.setPageSize(o.get("size").getAsInt());
        }

        return page;
    }

    @Override
    public JsonElement serialize(BookPageResponse src, Type typeOfSrc, JsonSerializationContext ctx) {
        JsonObject o = new JsonObject();

        JsonArray arr = new JsonArray();
        if (src.getContent() != null) {
            for (BookResponse br : src.getContent()) {
                arr.add(ctx.serialize(br, BookResponse.class));
            }
        }
        o.add("content", arr);

        o.addProperty("totalPages", src.getTotalPages());
        o.addProperty("totalElements", src.getTotalElements());
        o.addProperty("pageNumber", src.getPageNumber());
        o.addProperty("pageSize", src.getPageSize());

        return o;
    }
}

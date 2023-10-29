package com.elec5619.drawingsquare.web.aiart.eitity;

import com.google.gson.Gson;
import lombok.Data;
import okhttp3.MediaType;
import okhttp3.RequestBody;

@Data
public class AIRequest {
    private String text;
    private String style;
    private String resolution;
    private Integer num;

    public RequestBody toRequestBody() {
        // Use the Gson library to serialize objects to JSON strings
        Gson gson = new Gson();
        String json = gson.toJson(this);

        // Create a RequestBody of type JSON
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, json);

        return requestBody;
    }
}


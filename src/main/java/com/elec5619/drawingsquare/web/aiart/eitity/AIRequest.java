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
        // 使用 Gson 库将对象序列化为 JSON 字符串
        Gson gson = new Gson();
        String json = gson.toJson(this);

        // 创建 JSON 类型的 RequestBody
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, json);

        return requestBody;
    }
}


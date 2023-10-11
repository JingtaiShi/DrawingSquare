package com.elec5619.drawingsquare.web.aiart.controller;

import com.alibaba.fastjson.JSON;
import com.elec5619.drawingsquare.web.aiart.eitity.AIRequest;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


@RestController
@RequestMapping("/api/aiArt")
public class AIArtController {

    private static String API_KEY = "ooxm8CX4f6PewIABlTVvTrx5";

    private static String SECRET_KEY = "3tf1DWiKfZBaLOEaDC8m1eYYTBdztQUp";

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    String taskId;
    @PostMapping("/submitRequest")
    public String submitAIRequest(@RequestBody AIRequest request) throws IOException {
        String accessToken = getAccessToken();
        String apiUrl = "https://aip.baidubce.com/rpc/2.0/ernievilg/v1/txt2img?access_token=" + accessToken;
        System.out.println(request);

        okhttp3.RequestBody body = request.toRequestBody();

        Request req = new Request.Builder()
                .url(apiUrl)
                .method("POST", body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(req).execute();
        // System.out.println(response.body().string());
        String value = response.body().string();
        System.out.println(value);
        taskId = value.substring(18, 26);
        System.out.println(taskId);
        return value;
    }


    @GetMapping("/getImgUrl")
    public String getAIResult() throws IOException, InterruptedException {
        while(true) {
            MediaType mediaType = MediaType.parse("application/json");
            okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "{\"taskId\":\"" + taskId + "\"}");

            Request request = new Request.Builder()
                    .url("https://aip.baidubce.com/rpc/2.0/ernievilg/v1/getImg?access_token=" + getAccessToken())
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build();
            Response response = HTTP_CLIENT.newCall(request).execute();
            String responseBody = response.body().string();
            System.out.println(responseBody);
            response.close();

            // 解析响应并检查任务状态
            JSONObject jsonResponse = new JSONObject(responseBody);
            int status = jsonResponse.getJSONObject("data").getInt("status");

            if (status == 1) {
                // 任务已完成，返回生成的图片结果
                return responseBody;
            } else {
                // 任务未完成，等待一段时间后继续查询
                Thread.sleep(5000); // 等待5s后再次查询
            }
        }

    }

    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
     *
     * @return 鉴权签名（Access Token）
     * @throws IOException IO异常
     */
    private static String getAccessToken() {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();
        try {
            String url = "https://aip.baidubce.com/oauth/2.0/token";
            String grantType = "client_credentials";
            String clientId = API_KEY;
            String clientSecret = SECRET_KEY;
            String apiUrl = String.format("%s?grant_type=%s&client_id=%s&client_secret=%s",
                    url, grantType, clientId, clientSecret);

            URL apiURL = new URL(apiUrl);
            connection = (HttpURLConnection) apiURL.openConnection();
            connection.setRequestMethod("POST");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } else {
                System.out.println("Error response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //取出返回的access_token
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.toString());
        String accessToken = jsonObject.getString("access_token");
        return accessToken;
    }

}

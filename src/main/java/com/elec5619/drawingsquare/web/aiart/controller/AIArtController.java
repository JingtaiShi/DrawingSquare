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

            // Parse the response and check the task status
            JSONObject jsonResponse = new JSONObject(responseBody);
            int status = jsonResponse.getJSONObject("data").getInt("status");

            if (status == 1) {
                // task is complete and the result of the generated image is returned
                return responseBody;
            } else {
                // If the task is not complete, wait for a period of time and continue the query
                Thread.sleep(5000); // Wait 5 seconds and query again
            }
        }

    }

    /**
     * Generate an Access Token from a user's AK, SK
     *
     * @return Access Token
     * @throws IOException
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
        // Retrieve the returned access token
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.toString());
        String accessToken = jsonObject.getString("access_token");
        return accessToken;
    }

}

package com.elec5619.drawingsquare.web.aiart.controller;

import com.elec5619.drawingsquare.web.aiart.eitity.AIRequest;
import com.elec5619.drawingsquare.web.aiart.eitity.GetAIResultRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/ai-art")
public class AIArtController {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @PostMapping("/submit-request")
    public ResponseEntity<String> submitAIRequest(@RequestBody AIRequest request) {
        // 构建提交请求到AI作画接口
        String apiUrl = "https://aip.baidubce.com/rpc/2.0/ernievilg/v1/txt2img";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("access_token", openaiApiKey);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<AIRequest> entity = new HttpEntity<>(request, headers);

        // 发送请求
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

        return response;
    }

    @PostMapping("/get-result")
    public ResponseEntity<String> getAIResult(@RequestBody GetAIResultRequest request) {
        // 构建查询结果请求到AI作画接口
        String apiUrl = "https://aip.baidubce.com/rpc/2.0/ernievilg/v1/getImg";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("access_token", openaiApiKey);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<GetAIResultRequest> entity = new HttpEntity<>(request, headers);

        // 发送请求
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

        return response;
    }
}

package org.qingshan.demo.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

//Basic Authentication
public class OAuth2Util {
    public void callWithBasicAuth(String basicUrl, String username, String password) {
        String paramJson = "";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
        headers.setContentType(type);
        String plainClientCredentials = username + ":" + password;
        String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));
        headers.set("Authorization", "Basic " + base64ClientCredentials);
        HttpEntity httpEntity = new HttpEntity(paramJson, headers);
        ResponseEntity responseEntity = restTemplate.postForEntity(basicUrl, httpEntity, String.class);
    }
}

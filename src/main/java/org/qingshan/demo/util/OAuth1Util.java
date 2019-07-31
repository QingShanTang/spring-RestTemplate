package org.qingshan.demo.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.social.oauth1.AbstractOAuth1ApiBinding;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

//HTTP OAuth认证
public class OAuth1Util extends AbstractOAuth1ApiBinding {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public OAuth1Util(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
        super(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        logger.debug("consumerKey=" + consumerKey);
        logger.debug("consumerSecret=" + consumerSecret);
        logger.debug("accessToken=" + accessToken);
        logger.debug("accessTokenSecret=" + accessTokenSecret);
    }


    public void callWithOAuth(String oauthUrl) {
        //只有getRestTemplate()才能带上验证信息
        RestTemplate restTemplate = getRestTemplate();
        Map map = new HashMap();
        map.put("limit", "5000");
        map.put("syncTimestamp", "1970-01-01T08:00:00.000000");
        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(oauthUrl, JSONObject.class, map);
        JSONObject json = responseEntity.getBody();
        System.out.println(json);
    }
}

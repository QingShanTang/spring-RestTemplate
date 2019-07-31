package org.qingshan.demo.service;

import com.alibaba.fastjson.JSONObject;
import org.qingshan.demo.config.AuthProps;
import org.qingshan.demo.util.OAuth1Util;
import org.qingshan.demo.util.OAuth2Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class OAuthService {
    @Autowired
    private AuthProps authProps;
    private OAuth1Util oAuth1Util = null;
    private OAuth2Util oAuth2Util = null;

    @PostConstruct
    private void initUtil() {
        oAuth1Util = new OAuth1Util(authProps.getConsumerKey(), authProps.getConsumerSecret(), authProps.getAccessToken(), authProps.getAccessTokenSecret());
        oAuth2Util = new OAuth2Util();
    }

    //oAuth验证
    public void callWithOAuth() {
        oAuth1Util.callWithOAuth(authProps.getOauthUrl());
    }

    //Basic Authentication
    public void callWithBasicAuth(){
        oAuth2Util.callWithBasicAuth(authProps.getBasicUrl(),authProps.getUsername(),authProps.getPassword());
    }
}

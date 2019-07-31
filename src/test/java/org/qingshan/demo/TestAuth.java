package org.qingshan.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.qingshan.demo.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestAuth {
    @Autowired
    private OAuthService oAuthService;

    @Test
    public void testCallWithOAuth(){
        oAuthService.callWithOAuth();
    }

    @Test
    public void testCallWithBasicAuth(){
        oAuthService.callWithBasicAuth();
    }
}

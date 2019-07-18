package org.qingshan.demo.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.qingshan.demo.interceptor.UserAgentInterceptor;
import org.qingshan.demo.pojo.Student;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GetRestUtil {
    RestTemplate restTemplate = new RestTemplate();

    //getForObject,不带参数,不认证
    public void request1() {
        String url = "http://localhost:8080/student/find";
        Student student = restTemplate.getForObject(url, Student.class);
        System.out.println(student);
    }

    //getForObject,map传参,不认证
    public void request2() {
        String url = "http://localhost:8080/student/find?id={id}";
        Map<String, Object> map = new HashMap<>();
        map.put("id", "2");
        Student student = restTemplate.getForObject(url, Student.class, map);
        System.out.println(student);
    }

    //getForObject,占位符传参,不认证
    public void request3() {
        String url = "http://localhost:8080/student/find?id={id}";
        //String url = "http://localhost:8080/student/find?id={?}";
        //String url = "http://localhost:8080/student/find?id={1}";
        Student student = restTemplate.getForObject(url, Student.class, "2");
        System.out.println(student);
    }

    //getForObject,uri传参,不认证
    public void request4() {
        String url = "http://localhost:8080/student/find?id=1";
        Student student = restTemplate.getForObject(url, Student.class);
        System.out.println(student);
    }

    //getForEntity,uri传参,不认证,接收对象
    public void request5() {
        String url = "http://localhost:8080/student/find?id=1";
        ResponseEntity<Student> res = restTemplate.getForEntity(url, Student.class);
        System.out.println(res);
    }

    //getForEntity,uri传参,不认证,接收JSON
    public void request6() {
        String url = "http://localhost:8080/student/find?id=1";
        ResponseEntity<JSONObject> res = restTemplate.getForEntity(url, JSONObject.class);
        System.out.println(res);
    }

    //getForEntity,uri传参,不认证,接收字符串
    public void request7() {
        String url = "http://localhost:8080/student/find?id=1";
        ResponseEntity<String> res = restTemplate.getForEntity(url, String.class);
        System.out.println(res);
    }

    //添加请求头-->用JSONObject接回报反射异常的错
    public void request8() {
        String url = "http://localhost:8080/student/get/header?name=豆包";
        restTemplate.setInterceptors(Collections.singletonList(new UserAgentInterceptor()));
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        System.out.println(responseEntity);
    }


}

package org.qingshan.demo.util;

import com.alibaba.fastjson.JSONObject;
import org.qingshan.demo.pojo.Student;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class PostRestUtil {
    RestTemplate restTemplate = new RestTemplate();

    //表单传参
    public void request1() {
        String url = "http://localhost:8080/student/update2";
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("id", "1");
        request.add("name", "豆包");
        Student student = restTemplate.postForObject(url, request, Student.class);
        System.out.println(student);
    }

    //表单传参+map uri传参
    public void request2() {
        String url = "http://localhost:8080/student/update2" + "?id={id}" + "&name={name}";

        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("age", "40");
        Map<String, String> map = new HashMap<>();
        map.put("id", "5");
        map.put("name", "嘻嘻");
        Student student = restTemplate.postForObject(url, request, Student.class, map);
        System.out.println(student);
    }


    //表单传参+uri传参
    public void request3() {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("age", "30");
        String url = "http://localhost:8080/student/update2" + "?id={id}" + "&name={name}";
        Student student = restTemplate.postForObject(url, request, Student.class, "3", "豆花");
        System.out.println(student);
    }

    //调用的第三方接口响应是重定向的时候用
    public void request4() {
        String url = "http://localhost:8080/student/login";
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("userName", "root");
        request.add("password", "123456");
        URI uri = restTemplate.postForLocation(url, request);
        System.out.println(uri);
    }

    //添加请求头
    public void request5() {
        String url = "http://localhost:8080/student/post/header";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "嘻嘻");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        System.out.println(response);
    }

    //exchange方式,调用发自己来选择具体的请求方法
//    那么问题来了，为什么要有这个东西？或者说这个接口的提供可以带来什么好处？
//
//    当你写一个公共的Rest工具类时，就比较方便了，底层统一，具体的方法由上层业务方选择即可
//    get可以通过这种方式直接添加请求头（也就是不需要第一种case中的自定义拦截器来塞入header，显然更加灵活）
    public void request6() {
        String url = "http://localhost:8080/student/post/header";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "嘻嘻");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        System.out.println(response);
    }

    //json为参数
    public void request7() {
        String url = "http://localhost:8080/student/update";
        Map<String, String> params = new HashMap<>();
        params.put("id", "5");
        params.put("name", "嘿嘿嘿");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JSONObject> response = restTemplate.postForEntity(url, request, JSONObject.class);
        System.out.println(response.getStatusCode() + " | " + response.getBody());
    }

    //文件上传
    public void request8() {
        String url = "http://localhost:8080/student/file";
        FileSystemResource resource = new FileSystemResource(new File("/Users/qingshan/Desktop/ss.json"));
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("file", resource);
        params.add("fileName", "ss.json");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, params, String.class);
        System.out.println(responseEntity);
    }


}

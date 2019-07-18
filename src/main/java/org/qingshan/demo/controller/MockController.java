package org.qingshan.demo.controller;


import org.apache.commons.lang3.StringUtils;
import org.qingshan.demo.pojo.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

@Controller
@RequestMapping(value = "/student")
public class MockController {
    private static final Logger log = LoggerFactory.getLogger(MockController.class);

    @ResponseBody
    @GetMapping(value = "/find")
    public Student findStudent(
            @RequestParam(required = false) String id
    ) {
        log.info("Input Param [id] -> {}", id);
        Student student = null;
        if ("1".equals(id)) {
            student = new Student("1", "花花", 11, new Date());
        } else if ("2".equals(id)) {
            student = new Student("2", "青衫", 22, new Date());
        }

        return student;
    }

    @ResponseBody
    @PostMapping(value = "/update")
    public Student updateStudent(
            @RequestBody(required = false) Student stuParam
    ) {
        Student student = new Student("1", "花花", 11, new Date());
        student.setId(stuParam.getId());
        student.setName(stuParam.getName());
        student.setAge(stuParam.getAge());
        student.setCreateDate(stuParam.getCreateDate());
        return student;
    }

    @ResponseBody
    @PostMapping(value = "/update2")
    public Student update2Student(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) Date createDate
    ) {
        Student student = new Student("1", "花花", 11, new Date());
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setCreateDate(createDate);
        return student;
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public String login(
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String password,
            HttpServletResponse response
    ) throws IOException {
        if ("root".equals(userName) && "123456".equals(password)) {
//            response.sendRedirect("/student/success");
            return "redirect:/student/success";
        } else {
//            response.sendRedirect("/student/fail");
            return "redirect:/student/fail";

        }


    }

    //GET添加请求头
    @ResponseBody
    @GetMapping(value = "/get/header")
    public String getHeader(HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam(value = "name", required = false) String name) throws IOException {
        String agent = request.getHeader(HttpHeaders.USER_AGENT);
        if (StringUtils.isEmpty(agent) || !agent.contains("WebKit")) {
            response.sendError(403, "illegal agent");
        }
        return "welcome " + name;
    }

    //POST添加请求头
    @ResponseBody
    @PostMapping(value = "/post/header")
    public String postHeader(HttpServletRequest request,
                             HttpServletResponse response,
                             @RequestParam(value = "name", required = false) String name) throws IOException {
        String agent = request.getHeader(HttpHeaders.USER_AGENT);
        if (StringUtils.isEmpty(agent) || !agent.contains("WebKit")) {
            response.sendError(403, "illegal agent");
        }
        return "welcome " + name;
    }

    @ResponseBody
    @GetMapping(value = "/success")
    public String success(
    ) {
        return "登录成功";
    }

    @ResponseBody
    @GetMapping(value = "/fail")
    public String fail(
    ) {
        return "登录失败";
    }

    //上传文件
    @ResponseBody
    @PostMapping(value = "/file")
    public String file(
            MultipartHttpServletRequest request
    ) throws IOException {
        MultipartFile file = request.getFile("file");
        if (file == null) {
            return "no file!";
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            builder.append(line);
            line = reader.readLine();
        }
        reader.close();
        return builder.toString();
    }
}

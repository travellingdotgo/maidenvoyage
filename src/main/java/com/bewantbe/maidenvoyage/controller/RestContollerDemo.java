package com.bewantbe.maidenvoyage.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.TimeZone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.bewantbe.maidenvoyage.biz.Track;


import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class RestContollerDemo {

    public final static String NEWLINE = "</br>";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 启动应用，浏览器打开http://localhost:8080/hello，会调用该方法，打印:Hello, Spring Boot.Request:GET.
     * @return
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String helloGet() {
        logger.debug("This is a debug message");
        return "Hello, Spring Boot.Request:GET";
    }

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String helloPost() {
        return "Hello, Spring Boot.Request:POST";
    }

    /*
    @GetMapping("/hello")  //等同于@RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String helloGet() {
        return "Hello, Spring Boot.Request.GET";
    }

    @PostMapping("hello")  //等同于@RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String helloPost() {
        return "Hello, Spring Boot.Request:POST";
    }
    */

    @RequestMapping(value = {"/twourl1", "/twourl2"}, method = RequestMethod.GET)
    public String twourl() {
        return "Hello, Spring Boot. twourl1/twour2";
    }

    /**
     * http://localhost:8080/say/5?name=howieli
     */
    @GetMapping(value = "/say/{id}")
    public String helloGet(@PathVariable("id") int id, @RequestParam("name") String name) {
        return "id: " +  id + ",name:" + name;
    }

    @RequestMapping(value = "/getip", method = RequestMethod.GET)
    public String getIp() {
        return "Hello, Spring Boot.Request:GET";
    }


    @RequestMapping(value = "/getclientip", method = RequestMethod.GET)
    public String getClientIP(HttpServletRequest request) {
        return "Hello, you are from ip: " + request.getRemoteAddr().toString() + " ?";
    }

    /*
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexGet() {
        return "I am the root index";
    }
    */


}
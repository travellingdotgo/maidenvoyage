package com.bewantbe.maidenvoyage.controller;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
public class RestContollerDemo {

    public final static String NEWLINE = "</br>";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String helloGet() {
        logger.debug("This is a debug message");
        return "Hello, Spring Boot.Request:GET";
    }

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String helloPost() {
        return "Hello, Spring Boot.Request:POST";
    }

    //@GetMapping("/hello")  //等同于@RequestMapping(value = "/hello", method = RequestMethod.GET)

    @RequestMapping(value = {"/twourl1", "/twourl2"}, method = RequestMethod.GET)
    public String twourl() {
        return "Hello, Spring Boot. twourl1/twour2";
    }

     // http://localhost:8080/say/5?name=howieli
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

}
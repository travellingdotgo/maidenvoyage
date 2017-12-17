package com.bewantbe.maidenvoyage.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@RestController
public class HelloController {

    /*
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexGet() {
        return "I am the root index";
    }
    */

    /**
     * 启动应用，浏览器打开http://localhost:8080/hello，会调用该方法，打印:Hello, Spring Boot.Request:GET.
     * @return
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String helloGet() {
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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndex(HttpServletRequest request) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String sourceip = request.getRemoteAddr().toString();
        String time = df.format(new Date());
        String pageurl = "/";
        //String sql = "insert into query (sourceip,time,pageurl) values ('0.0.0.0','12312345678','/')";
        String sql = "insert into query (sourceip,time,pageurl) values ('" + sourceip  + "\',\'" + time + "\',\'" + pageurl + "\')";
        jdbcTemplate.execute(sql);
        System.out.println("执行完成");

        return "Hello, you are from ip: " + request.getRemoteAddr().toString() + " ?";
    }

}
package com.bewantbe.maidenvoyage.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
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

@RestController
public class HelloController {


    public final static String NEWLINE = "</br>";

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
        String useragent = request.getHeader("User-Agent");
        String time = df.format(new Date());
        String pageurl = "/";
        //String sql = "insert into query (sourceip,time,pageurl) values ('0.0.0.0','12312345678','/')";


        if(!sourceip.equals("127.0.0.1")){
            String geo = testHttp(sourceip);
            String geoinfo = geo.substring( 5,geo.length()-1);
            System.out.println("sdfdaf: " + geoinfo);
            useragent += "...." + geoinfo;
        }else{

        }

        String sql = "insert into query (sourceip,time,pageurl,useragent) values ('" + sourceip
                + "\',\'" + time
                + "\',\'" + pageurl
                + "\',\'" + useragent + "\')";

        jdbcTemplate.execute(sql);
        System.out.println("执行完成: " + sql);

        /*
        try {
            JSONObject jsonObject = new JSONObject(result2);
            JSONObject data = jsonObject.getJSONObject("data");
            System.out.println("sdfdaf: " + data.toString());

        }catch (Exception e){

        }
        */

        return "Hello, you are from ip: " + sourceip + "  " + useragent +" ?";
    }


    @RequestMapping(value = "/getquery", method = RequestMethod.GET)
    public String getQuery(HttpServletRequest request) {

        String sqlSelect = "SELECT * FROM query";
        List<Track> listContact = jdbcTemplate.query(sqlSelect, new RowMapper<Track>() {

            public Track mapRow(ResultSet result, int rowNum) throws SQLException {
                Track contact = new Track();
                contact.setSourceip (result.getString("sourceip"));
                contact.setTime(result.getString("time"));
                contact.setPageurl(result.getString("pageurl"));
                contact.setUseragent(result.getString("useragent"));

                return contact;
            }

        });

        String query_infos = "";

        for (Track aContact : listContact) {
            String sourceip = aContact.getSourceip();
            String time = aContact.getTime();
            String useragent = aContact.getUseragent();

            // 111.111.111.111
            // 2017-12-19 11:32:12

            String info = String.format("%s _ %s _ %s", sourceip,time,useragent );
            query_infos +=  info + NEWLINE;
        }

        String str = "Hello, you are from ip: " + NEWLINE
                    + request.getRemoteAddr().toString()
                    +  NEWLINE + NEWLINE
                    + "history: "  + NEWLINE
                    + query_infos  + NEWLINE + NEWLINE;

        return str;
    }

    public String testHttp( String ip ){
        String URL = "http://api.ip138.com/query/?ip=" + ip + "&datatype=jsonp&callback=find";

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("token", "62cf4a23815ff4228fc014556fb4e776");
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate
                .exchange(
                        URL,
                        HttpMethod.GET, requestEntity, String.class);
        String body = response.getBody();
        System.out.println(body);
        return body;
    }

}
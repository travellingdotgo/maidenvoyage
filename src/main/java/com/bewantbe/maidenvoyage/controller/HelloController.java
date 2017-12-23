package com.bewantbe.maidenvoyage.controller;


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

@Controller
public class HelloController {


    public final static String NEWLINE = "</br>";

    /*
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexGet() {
        return "I am the root index";
    }
    */

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getIndex(HttpServletRequest request, Model model) {
        debug(request);

        ModelAndView modelAndView = new ModelAndView("index");


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String sourceip = "8.8.8.8";
        try{
            sourceip = getIpAddr(request); //request.getAttribute("X-real-ip").toString();//request.getRemoteAddr().toString();
        }
        catch (Exception e){
            sourceip = "9.9.9.9";
        }
        String useragent = request.getHeader("User-Agent");
        String time = df.format(new Date());
        String pageurl = "/";
        //String sql = "insert into query (sourceip,time,pageurl) values ('0.0.0.0','12312345678','/')";


        String geoinfo = "";
        if(!sourceip.equals("127.0.0.1")){
            String geo = getGeo(sourceip);
            geoinfo = geo.substring( 5,geo.length()-1);
            //System.out.println("sdfdaf: " + geoinfo);
            //useragent = geoinfo + "...." + useragent;
        }else{

        }

        String sql = "insert into queryv2 (sourceip,time,pageurl,loc,useragent) values ('" + sourceip
                + "\',\'" + time
                + "\',\'" + pageurl
                + "\',\'" + geoinfo
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

        String info = String.format("%30s _ %30s _ %s", sourceip,time,useragent );
        System.out.println(info);

        //.....................page....Obj....
        model.addAttribute("index", info);
        //return "Hello, you are from ip: " + info;
        return modelAndView;
    }


    @RequestMapping(value = "/getquery", method = RequestMethod.GET)
    public void getQuery(HttpServletRequest request, Model model) {
        debug(request);

        String sqlSelect = "SELECT * FROM queryv2";
        List<Track> listContact = jdbcTemplate.query(sqlSelect, new RowMapper<Track>() {

            public Track mapRow(ResultSet result, int rowNum) throws SQLException {
                Track contact = new Track();
                contact.setSourceip (result.getString("sourceip"));
                contact.setTime(result.getString("time"));
                contact.setPageurl(result.getString("pageurl"));
                contact.setLoc(result.getString("loc"));
                contact.setUseragent(result.getString("useragent"));

                return contact;
            }

        });


        model.addAttribute("querylist", listContact);

        //return "getquery";

        /*
        String query_infos = "";

        for (Track aContact : listContact) {
            String sourceip = aContact.getSourceip();
            String time = aContact.getTime();
            String useragent = aContact.getUseragent();

            // 111.111.111.111
            // 2017-12-19 11:32:12

            String info = String.format("%30s _ %30s _ %s", sourceip,time,useragent );
            query_infos +=  info + NEWLINE;
        }


        String sourceip = "8.8.8.8";
        try{
            sourceip = getIpAddr(request); //request.getAttribute("X-real-ip").toString();//request.getRemoteAddr().toString();
        }
        catch (Exception e){
            sourceip = "9.9.9.9";
        }

        String str = "Hello, you are from ip: " + NEWLINE
                    + sourceip
                    //+ request.getRemoteAddr().toString()
                    +  NEWLINE + NEWLINE
                    + "history: "  + NEWLINE
                    + query_infos  + NEWLINE + NEWLINE;


        return str;
        */

    }

    public String getGeo( String ip ){
        final String MYURL1 = "23.83.245.240";
        final String MYURL2 = "125.118.92.";
        if(MYURL1.equals(ip)){
            return "find({\"ret\":\"ok\",\"ip\":\"42.120.74.111\",\"data\":[\"老地方1\",\"  \",\"  \",\"  \",\"  \",\"  \"]})";
        }
        if(ip.contains(MYURL2)){
            return "find({\"ret\":\"ok\",\"ip\":\"42.120.74.111\",\"data\":[\"老地方2\",\"  \",\"  \",\"  \",\"  \",\"  \"]})";
        }

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

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-real-ip");

        if (ip == null || ip.length() == 0
                || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
            if (ip != null) {
                ip = ip.split(",")[0].trim();
            }
        }

        if (ip == null || ip.length() == 0
                || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null
                || ip.length() == 0
                || "unknown".equalsIgnoreCase(ip)
                ){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0
                || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }

        return ip;
    }


    public static void debug(HttpServletRequest request){
        Enumeration<String> h = request.getHeaderNames();
        while(h.hasMoreElements()){
            String n = h.nextElement();
            System.out.println(n+"==="+request.getHeader(n));
        }
    }

}
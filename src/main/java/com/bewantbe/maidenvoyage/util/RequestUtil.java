package com.bewantbe.maidenvoyage.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class RequestUtil {

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

        String url  = request.getScheme()
                + "://" + request.getServerName()
                + ":" +request.getServerPort()
                + request.getServletPath();
        if (request.getQueryString() != null){
            url += "?" + request.getQueryString();
        }
        System.out.println("combined url: " + url);
    }



    public static String getGeo( String ip ){
        final String MYURL1 = "23.83.245.240";
        final String MYURL3 = "127.0.0.1";
        final String MYURL2 = "125.118.92.";

        if(MYURL1.equals(ip)){
            return "{\"ret\":\"ok\",\"ip\":\"42.120.74.111\",\"data\":[\"老地方1\",\"  \",\"  \",\"  \",\"  \",\"  \"]}";
        } else if(MYURL3.equals(ip)){
            return "{\"ret\":\"ok\",\"ip\":\"42.120.74.111\",\"data\":[\"本地回环\",\"  \",\"  \",\"  \",\"  \",\"  \"]}";
        } else if(ip.contains(MYURL2)){
            return "{\"ret\":\"ok\",\"ip\":\"42.120.74.111\",\"data\":[\"老地方2\",\"  \",\"  \",\"  \",\"  \",\"  \"]}";
        }
        // return "find({\"ret\":\"ok\",\"ip\":\"42.120.74.111\",\"data\":[\"本地回环\",\"  \",\"  \",\"  \",\"  \",\"  \"]})";

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

        if(body.length() >= 10){
            return body.substring( 5,body.length()-1);
        }else{
            return "Error_In_getGeo";
        }
    }

}

package com.bewantbe.maidenvoyage.controller;


import com.bewantbe.maidenvoyage.Consts;
import com.bewantbe.maidenvoyage.util.RequestUtil;
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

import org.springframework.jdbc.core.RowMapper;
import com.bewantbe.maidenvoyage.biz.Track;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

    public final static String NEWLINE = "</br>";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getIndex(HttpServletRequest request, Model model) {
        RequestUtil.debug(request);

        ModelAndView modelAndView = new ModelAndView("index");

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String sourceip = RequestUtil.getIpAddr(request);
        String useragent = request.getHeader("User-Agent");
        String host = request.getHeader("Host");
        String time = df.format(new Date());
        String pageurl = request.getRequestURL().toString();//getRequestURI


        if (isInBlacklist(sourceip)){
            System.out.println("isInBlacklist !");
        }

        String geoinfo = searchIp2GeoInSpeciallist(sourceip);
        if(geoinfo==null){
            System.out.println("searchIp2GeoInSpeciallist return null ");
            try{
                geoinfo = searchIp2GeoInCache(sourceip);
                if(geoinfo==null){
                    System.out.println("searchIp2GeoInCache return null ");
                    geoinfo = RequestUtil.getGeo(sourceip);
                    updateIp2geoCache(sourceip,geoinfo);
                }else{
                    System.out.println("searchIp2GeoInCache success ");
                }

                if(useragent==null) {
                    useragent="NULL";
                }
            }catch (Exception e){
                System.out.println("searchIp2GeoInCache exception: " + e.toString() );
            }
        }

        String sql = "insert into queryv3 (sourceip,time,pageurl,loc,useragent,host) values "
                + "('" + sourceip
                + "\',\'" + time
                + "\',\'" + pageurl
                + "\',\'" + geoinfo
                + "\',\'" + useragent
                + "\',\'" + host + "\')";

        jdbcTemplate.execute(sql);
        System.out.println("执行完成: " + sql);

        String info = String.format("%s <br /> %s <br /> %s", sourceip,time,useragent );
        System.out.println(info);

        model.addAttribute("index", info);
        return modelAndView;
    }

    @RequestMapping(value = "/getquery", method = RequestMethod.GET)
    public void getQuery(HttpServletRequest request, Model model) {
        RequestUtil.debug(request);

        String sqlSelect = "SELECT * FROM queryv3";
        List<Track> listContact = jdbcTemplate.query(sqlSelect, new RowMapper<Track>() {

            public Track mapRow(ResultSet result, int rowNum) throws SQLException {
                Track contact = new Track();
                contact.setSourceip (result.getString("sourceip"));
                contact.setTime(result.getString("time"));
                contact.setPageurl(result.getString("pageurl"));
                contact.setLoc(result.getString("loc"));
                contact.setUseragent(result.getString("useragent"));
                contact.setHost(result.getString("host"));

                return contact;
            }

        });

        model.addAttribute("querylist", listContact);
    }

    // .     .     .     .     .     Util    .     .     .     .     .
    private void updateIp2geoCache(String ipaddr, String geo){
        String sql = "replace into ip2geocache (ipaddr,geo) values "
                + "('" + ipaddr
                + "\',\'" + geo + "\')";

        jdbcTemplate.execute(sql);
        System.out.println("执行完成: " + sql);
    }



    private boolean isInBlacklist(String ipaddr){
        System.out.println("isInBlacklist ipaddr=" + ipaddr);

        for (int i = 0; i< Consts.blacklist.length; i++){
            if (ipaddr.startsWith(Consts.blacklist[i])){
                System.out.println("startsWith blacklist: " + Consts.blacklist[i]);
                return true;
            }
        }

        return false;
    }

    private String searchIp2GeoInSpeciallist(String ipaddr){
        System.out.println("searchIp2GeoInSpeciallist ipaddr=" + ipaddr);

        final String MYURL1 = "127.0.0.1";
        final String MYURL2 = "localhost";
        final String MYURL3 = "0:0:0:0:0:0:0:1";

        if(MYURL1.equals(ipaddr)){
            return "{\"ret\":\"ok\",\"ip\":\"0.0.0.0\",\"data\":[\"本地回环1\",\"  \",\"  \",\"  \",\"  \",\"  \"]}";
        } else if(MYURL2.equals(ipaddr)){
            return "{\"ret\":\"ok\",\"ip\":\"0.0.0.0\",\"data\":[\"本地回环2\",\"  \",\"  \",\"  \",\"  \",\"  \"]}";
        } else if(MYURL3.equals(ipaddr)){
            return "{\"ret\":\"ok\",\"ip\":\"0.0.0.0\",\"data\":[\"本地回环3\",\"  \",\"  \",\"  \",\"  \",\"  \"]}";
        }

        return null;
    }

    private String searchIp2GeoInCache(String ipaddr) throws Exception{
        /*
        jdbcTemplate.update(
                "insert into t_actor (first_name, last_name) values (?, ?)",
                "Leonor", "Watling");
                */

        /*
        String geo = jdbcTemplate.queryForObject(
                "select geo from ip2geocache where ipaddr = ?",
                new Object[]{ipaddr}, String.class);

        return geo;
        */
        List<String> list = jdbcTemplate.queryForList(
                "select geo from ip2geocache where ipaddr = ?",
                new Object[]{ipaddr}, String.class);

        if (list.size()==0){
            return null;
        }else if (list.size()==1){
            return list.get(0);
        }else{
            throw new Exception("the result list int this senario should be between 0-1");
        }
    }


}
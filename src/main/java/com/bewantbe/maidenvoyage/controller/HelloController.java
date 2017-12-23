package com.bewantbe.maidenvoyage.controller;


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

        String geoinfo = RequestUtil.getGeo(sourceip);

        String sql = "insert into queryv3 (sourceip,time,pageurl,loc,useragent,host) values "
                + "('" + sourceip
                + "\',\'" + time
                + "\',\'" + pageurl
                + "\',\'" + geoinfo
                + "\',\'" + useragent
                + "\',\'" + host + "\')";

        jdbcTemplate.execute(sql);
        System.out.println("执行完成: " + sql);

        String info = String.format("%30s _ %30s _ %s", sourceip,time,useragent );
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
}
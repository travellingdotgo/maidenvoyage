package com.bewantbe.maidenvoyage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
public class AboutmeContoller {

    public final static String NEWLINE = "</br>";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/aboutme", method = RequestMethod.GET)
    public String getAboutme() {
        return "Hello, about me";
    }

}
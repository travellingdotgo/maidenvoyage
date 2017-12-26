package com.bewantbe.maidenvoyage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
public class AboutmeContoller {

    public final static String NEWLINE = "</br>";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/aboutme", method = RequestMethod.GET)
    public ModelAndView getAboutme() {
        //return "Hello, about me";
        System.out.println("getAboutme: " );
        return new ModelAndView("redirect:/cv");
    }


    @RequestMapping(value = "/resume", method = RequestMethod.GET)
    public ModelAndView getResume() {
        return new ModelAndView("forward:/aboutme");
    }

}
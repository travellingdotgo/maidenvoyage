package com.bewantbe.maidenvoyage.util;

import com.bewantbe.maidenvoyage.MaidenvoyageApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

import static jdk.nashorn.internal.runtime.regexp.joni.Syntax.Java;

public class DebugUtil {

    public static void debugLoadedBeans(ApplicationContext ctx){
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }


    public static void debugLoadedBeans(Class cls, String[] args){

        ApplicationContext ctx = SpringApplication.run(cls, args);
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

}

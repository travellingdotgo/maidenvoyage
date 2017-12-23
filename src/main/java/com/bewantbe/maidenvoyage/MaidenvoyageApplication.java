package com.bewantbe.maidenvoyage;

import com.bewantbe.maidenvoyage.util.DebugUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;


@MapperScan("com.bewantbe.maidenvoyage.dao")
@EnableAutoConfiguration
@SpringBootApplication
public class MaidenvoyageApplication {

	@Value("${trand}")
	private static String trand;

	public static void main(String[] args) {
		System.out.println("main 启动中: ");
		ApplicationContext ctx = SpringApplication.run(MaidenvoyageApplication.class, args);
		//DebugUtil.debugLoadedBeans(ctx);
	}
}

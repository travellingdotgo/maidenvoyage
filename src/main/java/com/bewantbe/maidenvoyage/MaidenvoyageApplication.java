package com.bewantbe.maidenvoyage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class MaidenvoyageApplication {

	//
	@Value("${trand}")
	private static String trand;

	public static void main(String[] args) {
		System.out.println("执行完成: " + trand);
		SpringApplication.run(MaidenvoyageApplication.class, args);
	}
}

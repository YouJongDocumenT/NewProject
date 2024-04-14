package com.ras.bandostockproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@MapperScan("com.ras.bandostockproject.mapper")
public class BandoStockProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BandoStockProjectApplication.class, args);
	}

}

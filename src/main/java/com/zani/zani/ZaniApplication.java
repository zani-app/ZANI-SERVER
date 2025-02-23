package com.zani.zani;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableJpaRepositories(basePackages = "com.zani.zani.*.repository.mysql")
@EnableRedisRepositories(basePackages = "com.zani.zani.*.repository.redis")
@EnableScheduling
@SpringBootApplication
public class ZaniApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZaniApplication.class, args);
	}

}

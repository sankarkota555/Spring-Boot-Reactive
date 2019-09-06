package com.reactive.common.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication(exclude = {})
@PropertySource("classpath:common.properties")
@EnableReactiveMongoRepositories(basePackages = "com.reactive")
public class CommonConfig {

}

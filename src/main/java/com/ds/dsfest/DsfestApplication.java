package com.ds.dsfest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class DsfestApplication {

  public static void main(String[] args) {
    SpringApplication.run(DsfestApplication.class, args);
  }
}

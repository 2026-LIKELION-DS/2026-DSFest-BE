package com.ds.dsfest.domain.admin;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "admin")
public class AdminProperties {

  private String username;
  private String password;
}

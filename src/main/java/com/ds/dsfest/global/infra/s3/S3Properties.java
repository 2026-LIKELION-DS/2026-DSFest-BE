package com.ds.dsfest.global.infra.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "cloud.aws")
public class S3Properties {

  private String accessKey;
  private String secretKey;
  private String region;
  private String bucket;
}

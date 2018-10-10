package com.lewisheadden.neighbor.configuration;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.util.ClientBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kubernetes")
@Data
public class KubernetesConfiguration {
  private String apiUrl;
  private String namespace;

  @Bean
  public ApiClient kubernetesClient() throws IOException {
    final ApiClient client = ClientBuilder.standard()
        .setBasePath(apiUrl)
        .setVerifyingSsl(false)
        .build();
    client.getHttpClient().setReadTimeout(60, TimeUnit.SECONDS);
    return client;
  }
}

package com.lewisheadden.neighbor;

import com.lewisheadden.neighbor.reactions.runner.KubernetesEventListener;
import io.kubernetes.client.ApiException;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class NeighborApplication {

  @Autowired
  private KubernetesEventListener eventListener;

  public static void main(String[] args) {
    SpringApplication.run(NeighborApplication.class, args);
  }

  @PostConstruct
  public void init() throws ApiException {
    eventListener.listen();
  }
}

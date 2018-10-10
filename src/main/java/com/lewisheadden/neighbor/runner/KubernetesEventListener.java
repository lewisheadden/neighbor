package com.lewisheadden.neighbor.runner;

import com.google.gson.reflect.TypeToken;
import com.lewisheadden.neighbor.configuration.KubernetesConfiguration;
import com.squareup.okhttp.Call;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.BatchV1Api;
import io.kubernetes.client.models.V1Job;
import io.kubernetes.client.util.Watch;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KubernetesEventListener {

  private KubernetesConfiguration configuration;
  private ApiClient kubernetesClient;

  public KubernetesEventListener(
      final KubernetesConfiguration configuration, final ApiClient kubernetesClient) {
    this.configuration = configuration;
    this.kubernetesClient = kubernetesClient;
  }

  @Async
  public void listen() throws ApiException {
    while (true) {
      try {
        watchJobs();
      } catch (RuntimeException e) {
        log.info("Watch expired.");
      }
    }
  }

  private void watchJobs() throws ApiException {
    final Call call =
        new BatchV1Api(kubernetesClient)
            .listNamespacedJobCall(
                configuration.getNamespace(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                Boolean.TRUE,
                null,
                null);
    Watch<V1Job> watch =
        Watch.createWatch(
            kubernetesClient, call, new TypeToken<Watch.Response<V1Job>>() {}.getType());

    for (Watch.Response<V1Job> item : watch) {
      final V1Job job = item.object;
      if ("MODIFIED".equals(item.type)) {
        log.info(
            "Job {} completed at {}",
            job.getMetadata().getName(),
            job.getStatus().getCompletionTime());
      } else {
        log.info("Found existing job {} which completed at {}", job.getMetadata().getName(), job.getStatus().getCompletionTime());
      }
    }
  }
}

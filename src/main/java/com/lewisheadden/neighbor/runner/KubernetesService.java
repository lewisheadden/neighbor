package com.lewisheadden.neighbor.runner;

import com.lewisheadden.neighbor.configuration.KubernetesConfiguration;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.BatchV1Api;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Container;
import io.kubernetes.client.models.V1Job;
import io.kubernetes.client.models.V1JobSpec;
import io.kubernetes.client.models.V1ObjectMeta;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodSpec;
import io.kubernetes.client.models.V1PodTemplateSpec;
import java.util.Arrays;
import java.util.UUID;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KubernetesService {

  private KubernetesConfiguration configuration;
  private ApiClient kubernetesClient;

  public KubernetesService(
      final KubernetesConfiguration configuration, final ApiClient kubernetesClient) {
    this.configuration = configuration;
    this.kubernetesClient = kubernetesClient;
  }

  public void runTask() throws ApiException {
    final V1Job job =
        new V1Job()
            .metadata(new V1ObjectMeta().name("neighbor-" + UUID.randomUUID()))
            .spec(new V1JobSpec().template(new V1PodTemplateSpec().spec(new V1PodSpec()
                .addContainersItem(
                    new V1Container()
                        .name("default")
                        .image("busybox")
                        .command(Arrays.asList("uname", "-a")))
                .restartPolicy("OnFailure"))));
    new BatchV1Api(kubernetesClient).createNamespacedJob(configuration.getNamespace(), job, null);
  }

}

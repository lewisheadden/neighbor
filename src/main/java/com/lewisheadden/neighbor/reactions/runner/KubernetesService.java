package com.lewisheadden.neighbor.reactions.runner;

import com.lewisheadden.neighbor.configuration.KubernetesConfiguration;
import com.lewisheadden.neighbor.events.EventEntity;
import com.lewisheadden.neighbor.reactions.storage.ReactionEntity;
import com.lewisheadden.neighbor.reactions.storage.ReactionRepository;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.BatchV1Api;
import io.kubernetes.client.models.V1Container;
import io.kubernetes.client.models.V1Job;
import io.kubernetes.client.models.V1JobSpec;
import io.kubernetes.client.models.V1ObjectMeta;
import io.kubernetes.client.models.V1PodSpec;
import io.kubernetes.client.models.V1PodTemplateSpec;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KubernetesService {

  private KubernetesConfiguration configuration;
  private ApiClient kubernetesClient;
  private ReactionRepository repository;

  public KubernetesService(
      final KubernetesConfiguration configuration,
      final ApiClient kubernetesClient,
      final ReactionRepository repository) {
    this.configuration = configuration;
    this.kubernetesClient = kubernetesClient;
    this.repository = repository;
  }

  public void runTask(final EventEntity event) throws ApiException {
    final UUID reactionId = UUID.randomUUID();
    final V1Job job =
        new V1Job()
            .metadata(
                new V1ObjectMeta()
                    .name("neighbor-" + reactionId)
                    .putLabelsItem("com.lewisheadden.neighbor/id", reactionId.toString()))
            .spec(
                new V1JobSpec()
                    .template(
                        new V1PodTemplateSpec()
                            .spec(
                                new V1PodSpec()
                                    .addContainersItem(
                                        new V1Container()
                                            .name("default")
                                            .image("busybox")
                                            .command(Arrays.asList("sleep", "10")))
                                    .restartPolicy("OnFailure"))));
    new BatchV1Api(kubernetesClient).createNamespacedJob(configuration.getNamespace(), job, null);
    repository.save(new ReactionEntity(reactionId, Instant.now(), false, event));
  }
}

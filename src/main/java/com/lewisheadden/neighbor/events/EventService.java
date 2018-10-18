package com.lewisheadden.neighbor.events;

import com.lewisheadden.neighbor.events.storage.EventRepository;
import com.lewisheadden.neighbor.reactions.runner.KubernetesService;
import io.kubernetes.client.ApiException;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class EventService {

  private EventRepository repository;
  private KubernetesService kubernetesService;

  public EventService(final EventRepository repository, final KubernetesService kubernetesService) {
    this.repository = repository;
    this.kubernetesService = kubernetesService;
  }

  public void processEvent(
      final String provider, final String type, final String id, final String message)
      throws ApiException {
    final EventEntity event = new EventEntity(UUID.randomUUID(), provider, type, id, message);
    repository.save(event);
    kubernetesService.runTask(event);
  }
}

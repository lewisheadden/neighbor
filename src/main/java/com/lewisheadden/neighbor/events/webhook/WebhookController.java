package com.lewisheadden.neighbor.events.webhook;

import com.lewisheadden.neighbor.events.EventService;
import com.lewisheadden.neighbor.reactions.runner.KubernetesService;
import io.kubernetes.client.ApiException;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

  private EventService eventService;

  public WebhookController(final EventService eventService) {
    this.eventService = eventService;
  }

  @PostMapping("/")
  public void post(@RequestBody final WebhookPayload payload) throws ApiException {
    eventService.processEvent(
        "webhook", "POST", UUID.randomUUID().toString(), payload.getMessage());
  }
}

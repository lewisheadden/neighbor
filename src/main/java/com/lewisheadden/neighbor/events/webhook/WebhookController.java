package com.lewisheadden.neighbor.events.webhook;

import com.lewisheadden.neighbor.runner.KubernetesService;
import io.kubernetes.client.ApiException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

  private KubernetesService kubernetesService;

  public WebhookController(final KubernetesService kubernetesService) {
    this.kubernetesService = kubernetesService;
  }

  @PostMapping("/")
  public void post() throws ApiException {
    kubernetesService.runTask();
  }

}

package com.lewisheadden.neighbor.events.webhook;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WebhookPayload {
  private String message;
}

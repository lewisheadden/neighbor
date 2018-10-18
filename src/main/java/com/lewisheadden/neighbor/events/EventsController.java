package com.lewisheadden.neighbor.events;

import com.lewisheadden.neighbor.events.storage.EventRepository;
import com.lewisheadden.neighbor.reactions.storage.ReactionEntity;
import com.lewisheadden.neighbor.reactions.storage.ReactionRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/events")
public class EventsController {

  private EventRepository repository;

  public EventsController(final EventRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public List<EventEntity> index() {
    return repository.findAll();
  }
}

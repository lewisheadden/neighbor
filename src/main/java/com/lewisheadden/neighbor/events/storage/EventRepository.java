package com.lewisheadden.neighbor.events.storage;

import com.lewisheadden.neighbor.events.EventEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, UUID> {
}

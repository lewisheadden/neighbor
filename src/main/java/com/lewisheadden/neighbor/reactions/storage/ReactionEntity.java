package com.lewisheadden.neighbor.reactions.storage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lewisheadden.neighbor.events.EventEntity;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(exclude = {"event"})
@ToString(exclude = {"event"})
@AllArgsConstructor
@NoArgsConstructor
public class ReactionEntity {
  @Id
  private UUID id;
  private Instant createdAt;
  private boolean completed;
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name = "event_id")
  @JsonIgnore
  private EventEntity event;
}

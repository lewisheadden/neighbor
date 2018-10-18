package com.lewisheadden.neighbor.events;

import com.lewisheadden.neighbor.reactions.storage.ReactionEntity;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = {"reactions"})
@EqualsAndHashCode(exclude = {"reactions"})
public class EventEntity {
  @Id private UUID id;
  private String provider;
  private String type;
  private String providerId;
  private String message;
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "event")
  private Set<ReactionEntity> reactions;

  public EventEntity() {}

  public EventEntity(UUID id, String provider, String type, String providerId, String message) {
    this.id = id;
    this.provider = provider;
    this.type = type;
    this.providerId = providerId;
    this.message = message;
  }
}

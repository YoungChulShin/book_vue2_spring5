package com.taskagile.domain.model.team.events;

import com.taskagile.domain.common.event.DomainEvent;
import com.taskagile.domain.model.team.Team;

import lombok.Getter;

@Getter
public class TeamCreatedEvent extends DomainEvent{

  private static final long serialVersionUID = 2580061707540917880L;

  private Team team;

  public TeamCreatedEvent(final Object source, Team team) {
    super(source);
    this.team = team;
  }
}

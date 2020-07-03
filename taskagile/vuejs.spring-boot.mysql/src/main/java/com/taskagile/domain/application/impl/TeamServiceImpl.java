package com.taskagile.domain.application.impl;

import java.util.List;

import javax.transaction.Transactional;

import com.taskagile.domain.application.TeamService;
import com.taskagile.domain.application.commands.CreateTeamCommand;
import com.taskagile.domain.common.event.DomainEventPublisher;
import com.taskagile.domain.model.team.Team;
import com.taskagile.domain.model.team.TeamId;
import com.taskagile.domain.model.team.TeamRepository;
import com.taskagile.domain.model.team.events.TeamCreatedEvent;
import com.taskagile.domain.model.user.UserId;

import org.springframework.stereotype.Service;

@Transactional
@Service
public class TeamServiceImpl implements TeamService{

  private DomainEventPublisher domainEventPublisher;
  private TeamRepository teamRepository;

  public TeamServiceImpl(TeamRepository teamRepository, DomainEventPublisher domainEventPublisher) {
    this.teamRepository = teamRepository;
    this.domainEventPublisher = domainEventPublisher;
  }

	@Override
	public List<Team> findTeamsByUserId(UserId userId) {
    return teamRepository.findTeamsByUserId(userId);
	}

	@Override
	public Team createTeam(CreateTeamCommand command) {

    // 팀 저장
    Team team = Team.create(command.getName(), command.getUserId());
    teamRepository.save(team);

    // 이벤트 발행
    domainEventPublisher.publish(new TeamCreatedEvent(this, team));

    return team;
	}

	@Override
	public Team findById(TeamId id) {
    return teamRepository.findById(id);
	}
}

package com.taskagile.domain.model.team;

import java.util.List;

import com.taskagile.domain.model.user.UserId;

public interface TeamRepository {

  List<Team> findTeamsByUserId(UserId userId);

  Team findById(TeamId id);

  void save(Team team);
}

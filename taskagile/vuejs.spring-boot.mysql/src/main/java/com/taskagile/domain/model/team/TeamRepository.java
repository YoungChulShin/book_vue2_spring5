package com.taskagile.domain.model.team;

import java.util.List;

import com.taskagile.domain.model.user.UserId;

public interface TeamRepository {

  List<Team> findTeamsByUserId(UserId userId);

  void save(Team team);
}

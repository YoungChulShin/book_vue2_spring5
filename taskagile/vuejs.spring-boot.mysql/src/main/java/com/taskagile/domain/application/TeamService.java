package com.taskagile.domain.application;

import java.util.List;

import com.taskagile.domain.application.commands.CreateTeamCommand;
import com.taskagile.domain.model.team.Team;
import com.taskagile.domain.model.user.UserId;

public interface TeamService {
    
    List<Team> findTeamsByUserId(UserId userId);

    Team createTeam(CreateTeamCommand command);
}
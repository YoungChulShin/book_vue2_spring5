package com.taskagile.web.results;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.taskagile.domain.model.board.Board;
import com.taskagile.domain.model.team.Team;
import com.taskagile.domain.model.user.User;

import org.springframework.http.ResponseEntity;

import lombok.Getter;

public class MyDataResult {

  public static ResponseEntity<ApiResult> build(User user, List<Team> teams, List<Board> boards, String realTimeServerUrl, String realTimeToken) {

    Map<String, Object> userData = new HashMap<>();
    userData.put("name", user.getUsername());
    userData.put("token", realTimeToken);

    Map<String, Object> settings = new HashMap<>();
    settings.put("realTimeServerUrl", realTimeServerUrl);

    List<TeamResult> teamResults = new ArrayList<>();
    for (Team team : teams) {
      teamResults.add(new TeamResult(team));
    }

    List<BoardResult> boardResults = new ArrayList<>();
    for (Board board : boards) {
      boardResults.add(new BoardResult(board));
    }

    ApiResult apiResult = ApiResult.blank()
      .add("user", userData)
      .add("teams", teamResults)
      .add("boards", boardResults)
      .add("settings", settings);

    return Result.ok(apiResult);
  }

  @Getter
  private static class TeamResult {

    private long id;
    private String name;

    TeamResult(Team team) {
      this.id = team.getId().value();
      this.name = team.getName();
    }
  }

  @Getter
  private static class BoardResult {

    private long id;
    private String name;
    private String description;
    private long teamId;

    BoardResult(Board board) {
      this.id = board.getId().value();
      this.name = board.getName();
      this.description = board.getDescription();
      this.teamId = board.getTeamId().value();
    }
  }
}

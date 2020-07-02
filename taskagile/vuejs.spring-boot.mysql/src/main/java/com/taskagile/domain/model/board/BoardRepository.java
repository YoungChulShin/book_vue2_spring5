package com.taskagile.domain.model.board;

import java.util.List;

import com.taskagile.domain.model.user.UserId;

public interface BoardRepository {

  List<Board> findBoardsByMembership(UserId userId);

  void save(Board board);
}

package com.taskagile.domain.model.board;

import java.util.List;

import com.taskagile.domain.model.user.User;
import com.taskagile.domain.model.user.UserId;

public interface BoardMemberRepository {

  void save(BoardMember boardMember);

  List<User> findMembers(BoardId boardId);

  void add(BoardId boardId, UserId userId);
}

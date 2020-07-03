package com.taskagile.domain.model.board;

import java.util.List;

import com.taskagile.domain.model.user.User;

public interface BoardMemberRepository {

  void save(BoardMember boardMember);

  List<User> findMembers(BoardId boardId);
}

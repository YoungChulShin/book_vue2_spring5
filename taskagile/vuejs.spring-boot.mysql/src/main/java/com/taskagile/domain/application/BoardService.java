package com.taskagile.domain.application;

import java.util.List;

import com.taskagile.domain.application.commands.CreateBoardCommand;
import com.taskagile.domain.model.board.Board;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.user.User;
import com.taskagile.domain.model.user.UserId;
import com.taskagile.domain.model.user.UserNotFoundException;

public interface BoardService {

    List<Board> findBoardsByMembership(UserId userId);

    Board findById(BoardId boardId);

    Board createBoard(CreateBoardCommand command);

    List<User> findMembers(BoardId boardId);

    User addMember(BoardId boardId, String usernameOrEmailAddress) throws UserNotFoundException;
}

package com.taskagile.domain.application;

import java.util.List;

import com.taskagile.domain.application.commands.CreateBoardCommand;
import com.taskagile.domain.model.board.Board;
import com.taskagile.domain.model.user.UserId;

public interface BoardService {

    List<Board> findBoardsByMembership(UserId uuserId);

    Board createBoard(CreateBoardCommand command);
}
package com.taskagile.domain.application;

import java.util.List;

import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.cardlist.CardList;

public interface CardListService {

  List<CardList> findByBoardId(BoardId boardId);
}

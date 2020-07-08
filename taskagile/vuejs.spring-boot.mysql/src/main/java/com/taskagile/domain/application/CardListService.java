package com.taskagile.domain.application;

import java.util.List;

import com.taskagile.domain.application.commands.AddCardListCommand;
import com.taskagile.domain.application.commands.ChangeCardListPositionsCommand;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.cardlist.CardList;

public interface CardListService {

  List<CardList> findByBoardId(BoardId boardId);

  CardList addCardList(AddCardListCommand command);

  void changePositions(ChangeCardListPositionsCommand command);
}

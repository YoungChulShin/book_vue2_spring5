package com.taskagile.domain.application;

import java.util.List;

import com.taskagile.domain.application.commands.AddCardCommand;
import com.taskagile.domain.application.commands.ChangeCardPositionCommand;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.card.Card;

public interface CardService {

  List<Card> findByBoardId(BoardId boardId);

  Card addCard(AddCardCommand command);

  void changeCardPositions(ChangeCardPositionCommand command);
}

package com.taskagile.domain.model.card;

import java.util.List;

import com.taskagile.domain.model.board.BoardId;

public interface CardRepository {

  List<Card> findByBoardId(BoardId boardId);
  void save(Card card);
  void changePositions(List<CardPosition> cardPositions);
}

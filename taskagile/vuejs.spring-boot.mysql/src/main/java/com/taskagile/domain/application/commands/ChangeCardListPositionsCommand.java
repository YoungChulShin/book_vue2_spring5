package com.taskagile.domain.application.commands;

import java.util.List;

import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.cardlist.CardListPosition;

import lombok.Getter;

@Getter
public class ChangeCardListPositionsCommand {

  private BoardId boardId;
  private List<CardListPosition> cardListPositions;

  public ChangeCardListPositionsCommand(BoardId boardId, List<CardListPosition> cardListPositions) {
    this.boardId = boardId;
    this.cardListPositions = cardListPositions;
  }
}

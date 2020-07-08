package com.taskagile.domain.application.commands;

import java.util.List;

import com.taskagile.domain.model.card.CardPosition;

import lombok.Getter;

@Getter
public class ChangeCardPositionCommand {

  private List<CardPosition> cardPositions;

  public ChangeCardPositionCommand(List<CardPosition> cardPositions) {
    this.cardPositions = cardPositions;
  }
}

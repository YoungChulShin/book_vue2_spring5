package com.taskagile.web.payload;

import java.util.List;

import com.taskagile.domain.application.commands.ChangeCardPositionCommand;
import com.taskagile.domain.model.card.CardPosition;

import lombok.Getter;

@Getter
public class ChangeCardPositionPayload {

  private long boardId;
  private List<CardPosition> cardPositions;

  public ChangeCardPositionCommand toCommand(ChangeCardPositionPayload payload) {
    return new ChangeCardPositionCommand(payload.cardPositions);
  }
}

package com.taskagile.web.payload;

import com.taskagile.domain.application.commands.AddCardListCommand;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.user.UserId;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddCardListPayload {

  private long boardId;
  private String name;
  private int position;

  public AddCardListPayload(long boardId, String name, int position) {
    this.boardId = boardId;
    this.name = name;
    this.position = position;
  }

  public AddCardListCommand toCommand(UserId userId) {
    return new AddCardListCommand(new BoardId(boardId), userId, name, position);
  }
}

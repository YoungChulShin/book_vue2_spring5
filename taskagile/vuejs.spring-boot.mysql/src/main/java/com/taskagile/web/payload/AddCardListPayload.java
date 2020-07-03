package com.taskagile.web.payload;

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
}

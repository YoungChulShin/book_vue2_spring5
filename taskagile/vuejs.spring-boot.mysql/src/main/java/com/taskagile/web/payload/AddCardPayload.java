package com.taskagile.web.payload;

import com.taskagile.domain.application.commands.AddCardCommand;
import com.taskagile.domain.model.cardlist.CardListId;
import com.taskagile.domain.model.user.UserId;

import lombok.Getter;

@Getter
public class AddCardPayload {

  private long boardId;
  private long cardListId;
  private String title;
  private int position;

  public AddCardCommand toCommand(UserId userId) {
    return new AddCardCommand(new CardListId(cardListId),userId,title,position);
  }
}

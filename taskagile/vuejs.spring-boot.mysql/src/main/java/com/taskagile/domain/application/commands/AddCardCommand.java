package com.taskagile.domain.application.commands;

import com.taskagile.domain.model.cardlist.CardListId;
import com.taskagile.domain.model.user.UserId;

import lombok.Getter;

@Getter
public class AddCardCommand {

  private CardListId cardListId;
  private UserId userId;
  private String title;
  private int position;

  public AddCardCommand(CardListId cardListId, UserId userId, String title, int position) {
    this.cardListId = cardListId;
    this.userId = userId;
    this.title = title;
    this.position = position;
  }
}

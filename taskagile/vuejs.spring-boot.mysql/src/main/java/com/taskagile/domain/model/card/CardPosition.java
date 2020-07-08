package com.taskagile.domain.model.card;

import com.taskagile.domain.model.cardlist.CardListId;

import lombok.Getter;

@Getter
public class CardPosition {

  private CardListId cardListId;
  private CardId cardId;
  private int position;

  public CardPosition(CardListId cardListId, CardId cardId, int position) {
    this.cardListId = cardListId;
    this.cardId = cardId;
    this.position = position;
  }
}

package com.taskagile.domain.model.card;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.taskagile.domain.common.model.AbstractBaseEntity;
import com.taskagile.domain.model.cardlist.CardListId;
import com.taskagile.domain.model.user.UserId;

import lombok.Getter;

@Getter
@Entity
@Table(name = "card")
public class Card extends AbstractBaseEntity {

  private static final long serialVersionUID = 6030626206663838257L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "card_list_id")
  private long cardListId;

  @Column(name = "user_id")
  private long userId;

  @Column(name = "title")
  private String title;

  @Column(name = "position")
  private int position;

  @Column(name = "archived")
  private boolean archived;

  @Column(name = "created_date", nullable = false)
  private LocalDateTime createdDate;

  public CardId getId() {
    return new CardId(id);
  }

  public CardListId getCardListId() {
    return new CardListId(cardListId);
  }

  public UserId getUserId() {
    return new UserId(userId);
  }

  public static Card create(CardListId cardListId, UserId userId, String title, int position) {
    Card card = new Card();
    card.cardListId = cardListId.value();
    card.userId = userId.value();
    card.title = title;
    card.position = position;
    card.archived = false;
    card.createdDate = LocalDateTime.now();
    return card;
  }

@Override
public boolean equals(Object o) {
  if (this == o) return true;
  if (!(o instanceof Card)) return false;
  Card card = (Card) o;
  return cardListId == card.cardListId &&
    userId == card.userId &&
    position == card.position &&
    archived == card.archived &&
    Objects.equals(title, card.title);
}

@Override
public int hashCode() {
	return Objects.hash(cardListId, userId, title, position, archived);
}

@Override
public String toString() {
	return "Card{" +
      "id=" + id +
      ", cardListId=" + cardListId +
      ", userId=" + userId +
      ", title='" + title + '\'' +
      ", position=" + position +
      ", archived=" + archived +
      ", createdDate=" + createdDate +
      '}';
  }
}

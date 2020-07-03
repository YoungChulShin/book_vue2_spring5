package com.taskagile.domain.model.cardlist;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.taskagile.domain.common.model.AbstractBaseEntity;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.user.UserId;

import lombok.Getter;

@Getter
@Entity
@Table(name = "card_list")
public class CardList extends AbstractBaseEntity {

  private static final long serialVersionUID = -6547708151192480923L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "board_id")
  private long boardId;

  @Column(name = "user_id")
  private long userId;

  @Column(name = "name")
  private String name;

  @Column(name = "position")
  private int position;

  @Column(name = "archived")
  private boolean archived;

  @Column(name = "created_date", nullable = false)
  private LocalDateTime createdDate;

  public static CardList create(BoardId boardId, UserId userId, String name, int position) {
    CardList cardList = new CardList();
    cardList.boardId = boardId.value();
    cardList.userId = userId.value();
    cardList.name = name;
    cardList.position = position;
    cardList.archived = false;
    cardList.createdDate = LocalDateTime.now();
    return cardList;
  }

  public CardListId getId() {
    return new CardListId(id);
  }

  public BoardId getBoardId() {
    return new BoardId(boardId);
  }

  public UserId getUserId() {
    return new UserId(userId);
  }

	@Override
	public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CardList)) return false;
    CardList cardList = (CardList) o;
    return boardId == cardList.boardId &&
      position == cardList.position &&
      archived == cardList.archived &&
      Objects.equals(name, cardList.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(boardId, name, position, archived);
	}

	@Override
	public String toString() {
    return "CardList{" +
      "id=" + id +
      ", boardId=" + boardId +
      ", userId=" + userId +
      ", name='" + name + '\'' +
      ", position=" + position +
      ", archived=" + archived +
      ", createdDate=" + createdDate +
      '}';
	}

}

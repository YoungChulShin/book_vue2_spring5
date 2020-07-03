package com.taskagile.web.results;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.taskagile.domain.model.board.Board;
import com.taskagile.domain.model.card.Card;
import com.taskagile.domain.model.cardlist.CardList;
import com.taskagile.domain.model.cardlist.CardListId;
import com.taskagile.domain.model.team.Team;
import com.taskagile.domain.model.user.User;

import org.springframework.http.ResponseEntity;

import lombok.Getter;

public class BoardResult {

  public static ResponseEntity<ApiResult> build(Team team, Board board, List<User> members,
    List<CardList> cardLists, List<Card> cards) {

    // Board Data
    Map<String, Object> boardData = new HashMap();
    boardData.put("id", board.getId().value());
    boardData.put("name", board.getName());
    boardData.put("personal", board.isPersonal());

    // Member Data
    List<MemberData> memberData = new ArrayList<>();
    for (User user : members) {
      memberData.add(new MemberData(user));
    }

    //Card List Data
    List<CardListData> cardListData = new ArrayList<>();
    Map<CardListId, List<Card>> cardsByList = new HashMap<>();
    for (Card card : cards) {
      cardsByList.computeIfAbsent(card.getCardListId(), k -> new ArrayList<>()).add(card);
    }

    for (CardList cardList : cardLists) {
      cardListData.add(new CardListData(cardList, cardsByList.get(cardList.getId())));
    }

    ApiResult result = ApiResult.blank()
      .add("board", boardData)
      .add("members", memberData)
      .add("cardLists", cardListData);

    if (!board.isPersonal()) {
      Map<String, String> teamData = new HashMap<>();
      teamData.put("name", team.getName());
      result.add("team", teamData);
    }

    return Result.ok(result);
  }

  @Getter
  private static class MemberData {
    private long userId;
    private String shortName;

    MemberData(User user) {
      this.userId = user.getId().value();
      this.shortName = user.getInitials();
    }
  }

  @Getter
  private static class CardListData {
    private long id;
    private String name;
    private int poisition;
    private List<CardData> cards = new ArrayList<>();

    CardListData(CardList cardList, List<Card> cards) {
      this.id = cardList.getId().value();
      this.name = cardList.getName();
      this.poisition = cardList.getPosition();
      if (cards != null) {
        for (Card card : cards) {
          this.cards.add(new CardData(card));
        }
      }
    }
  }

  @Getter
  private static class CardData {
    private long id;
    private String title;
    private int position;

    CardData(Card card) {
      this.id = card.getId().value();
      this.title = card.getTitle();
      this.position = card.getPosition();
    }
  }
}

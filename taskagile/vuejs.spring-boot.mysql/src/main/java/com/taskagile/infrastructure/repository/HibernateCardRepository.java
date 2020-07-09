package com.taskagile.infrastructure.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.card.Card;
import com.taskagile.domain.model.card.CardPosition;
import com.taskagile.domain.model.card.CardRepository;

import org.hibernate.query.NativeQuery;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

public class HibernateCardRepository extends HibernateSupport<Card> implements CardRepository {

  private JdbcTemplate JdbcTemplate;

  HibernateCardRepository(EntityManager entityManager, JdbcTemplate jdbcTemplate) {
    super(entityManager);
    this.JdbcTemplate = jdbcTemplate;
  }

	@Override
	public List<Card> findByBoardId(BoardId boardId) {
    String sql = "SELECT c.* FROM card c LEFT JOIN card_list cl ON c.card_list_id = cl.id WHERE cl.board_id = :boardId";
    NativeQuery<Card> query = getSession().createNativeQuery(sql, Card.class);
    query.setParameter("boardId", boardId.value());
    return query.list();
	}

	@Override
	public void changePositions(final List<CardPosition> cardPositions) {

    String sql = "update card set card_list_id = ?, `position` = ? where id = ?";

    JdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        CardPosition cardPosition = cardPositions.get(i);
        ps.setLong(1, cardPosition.getCardListId().value());
        ps.setLong(2, cardPosition.getPosition());
        ps.setLong(3, cardPosition.getCardId().value());
      }

      @Override
      public int getBatchSize() {
        return cardPositions.size();
      }
    });
	}
}

package com.taskagile.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;

import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.cardlist.CardList;
import com.taskagile.domain.model.cardlist.CardListRepository;

import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateCardListRepository extends HibernateSupport<CardList> implements CardListRepository{

	HibernateCardListRepository(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public List<CardList> findByBoardId(BoardId boardId) {
    String sql = "SELECT cl.* FROM card_list WHERE cl.board_id = :board_id";
    NativeQuery<CardList> query = getSession().createNativeQuery(sql, CardList.class);
    query.setParameter("board_id", boardId.value());
		return query.list();
	}
}

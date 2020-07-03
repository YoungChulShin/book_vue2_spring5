package com.taskagile.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;

import com.taskagile.domain.model.team.Team;
import com.taskagile.domain.model.team.TeamId;
import com.taskagile.domain.model.team.TeamRepository;
import com.taskagile.domain.model.user.UserId;

import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateTeamRepository extends HibernateSupport<Team> implements TeamRepository {

  HibernateTeamRepository(EntityManager entityManager) {
    super(entityManager);
  }

	@Override
	public List<Team> findTeamsByUserId(UserId userId) {
    String sql =
      " SELECT t.* FROM team t WHERE t.user_id = :userId " +
      " UNION " +
      " ( " +
      " SELECT t.* FROM team t, board b, board_member bm " +
      " WHERE t.id = b.team_id AND bm.board_id = b.id AND bm.user_id  = :userId " +
      " ) ";

      NativeQuery<Team> query = getSession().createNativeQuery(sql, Team.class);
      query.setParameter("userId", userId.value());

      return query.list();
	}

	@Override
	public Team findById(TeamId id) {
    Query<Team> query = getSession().createQuery("FROM team WHERE id = :id", Team.class);
    query.setParameter("teamId", id.value());

    return query.uniqueResult();
	}
}

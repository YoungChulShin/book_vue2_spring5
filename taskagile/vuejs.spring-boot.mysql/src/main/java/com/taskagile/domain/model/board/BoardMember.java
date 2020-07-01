package com.taskagile.domain.model.board;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.taskagile.domain.common.model.AbstractBaseEntity;
import com.taskagile.domain.model.user.UserId;

@Entity
@Table(name = "board_member")
public class BoardMember extends AbstractBaseEntity {

  private static final long serialVersionUID = 1101935717986500672L;

  @EmbeddedId // 복합 키로 설정할 수 있다
  private BoardMemberId id;

  @Embeddable
  public static class BoardMemberId implements Serializable {

    private static final long serialVersionUID = -5739169913659318896L;

    @Column(name = "board_id")
    private long boardId;

    @Column(name = "user_id")
    private long userId;

    public BoardId getBoardId() {
      return new BoardId(boardId);
    }

    public UserId getUserId() {
      return new UserId(userId);
    }
  }

  public BoardId getBoardId() {
    return id.get
  }

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}

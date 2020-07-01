package com.taskagile.domain.model.team;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.taskagile.domain.common.model.AbstractBaseEntity;
import com.taskagile.domain.model.user.UserId;

import lombok.Getter;

@Getter
@Entity
@Table(name = "team")
public class Team extends AbstractBaseEntity{

  private static final long serialVersionUID = -2264390861852998965L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, length = 128)
  private String name;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "archived")
  private boolean archived;

  @Column(name = "created_date", nullable = false)
  private LocalDateTime createdDate;

  public static Team create(String name, UserId createorId) {
    Team team = new Team();
    team.name = name;
    team.userId = createorId.value();
    team.archived = false;
    team.createdDate = LocalDateTime.now();
    return team;
  }

  public TeamId getId() {
    return new TeamId(id);
  }

  public UserId getUserId() {
    return new UserId(userId);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Team)) return false;
    Team team = (Team) o;
    return userId == team.userId &&
      Objects.equals(name, team.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, userId);
  }

  @Override
  public String toString() {
    return "Team{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", userId=" + userId +
      ", archived=" + archived +
      ", createdDate=" + createdDate +
      '}';
  }
}

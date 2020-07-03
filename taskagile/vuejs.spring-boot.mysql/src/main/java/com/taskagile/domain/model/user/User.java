package com.taskagile.domain.model.user;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.taskagile.domain.common.model.AbstractBaseEntity;

import lombok.Getter;

@Getter
@Entity
@Table(name = "user")
public class User extends AbstractBaseEntity{

  private static final long serialVersionUID = -538781580460070724L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username", nullable = false, length = 50, unique = true)
  private String username;

  @Column(name = "email_address", nullable = false, length = 100, unique = true)
  private String emailAddress;

  @Column(name = "password", nullable = false, length = 30)
  private String password;

  @Column(name = "first_name", nullable = false, length = 45)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 45)
  private String lastName;

  @Column(name = "created_date", nullable = false)
  private LocalDateTime createdDate;

  public UserId getId() {
    return new UserId(id);
  }

  public String getInitials() {
    return (firstName.substring(0, 1) + lastName.substring(0, 1)).toUpperCase();
  }

  public static User create(String username, String emailAddress, String password) {
    User user = new User();
    user.username = username;
    user.emailAddress = emailAddress;
    user.password = password;
    user.firstName = "";
    user.lastName = "";
    user.createdDate = LocalDateTime.now();
    return user;
  }

	@Override
	public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof User)) {
      return false;
    }

    User user = (User)obj;
    return Objects.equals(username, user.getUsername()) &&
           Objects.equals(emailAddress, user.getEmailAddress());
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, emailAddress);
  }

	@Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", username='" + username + '\'' +
      ", emailAddress='" + emailAddress + '\'' +
      ", password=<Protected> " +
      ", firstName='" + firstName + '\'' +
      ", lastName='" + lastName + '\'' +
      ", createdDate=" + createdDate +
      '}';
  }
}

package com.taskagile.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.taskagile.domain.model.user.User;
import com.taskagile.domain.model.user.UserRepository;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
public class HibernateUserRepositoryTests {

  @TestConfiguration
  public static class UserRepositoryTestContextConfiguration {
    @Bean
    public UserRepository userRepository(EntityManager entityManager) {
      return new HibernateUserRepository(entityManager);
    }
  }

  @Autowired
  private UserRepository repository;

  @Test(expected = PersistenceException.class)
  public void save_nullUsernameUser_shouldFail() {
    User invalidUser = User.create(null, "go1323@gmail.com", "test");
    repository.save(invalidUser);
  }

  @Test(expected = PersistenceException.class)
  public void save_nullEmailAddressUser_shouldFail() {
    User invalidUser = User.create("yschin", null, "test");
    repository.save(invalidUser);
  }

  @Test(expected = PersistenceException.class)
  public void save_nullPasswordUser_shouldFail() {
    User invalidUser = User.create("yschin", "go1323@gmail.com", null);
    repository.save(invalidUser);
  }

  @Test
  public void save_validUser_shouldSuccess() {

    String username = "youngchul";
    String email = "go1323@gmail.com";
    User newUser = User.create(username, email, "test");
    repository.save(newUser);

    assertNotNull(newUser.getId(), "New user's id should be generated");
    assertNotNull(newUser.getCreatedDate(), "New user's created date should be generated");
    assertEquals(username, newUser.getUsername());
    assertEquals(email, newUser.getEmailAddress());
    assertEquals("", newUser.getFirstName());
    assertEquals("", newUser.getLastName());
  }

  @Test
  public void save_usernameAlreadyExist_shouldFail() {
    String username = "youngchul";
    String emailAddress = "go1323@gmail.com";
    User alreadyExist = User.create(username, emailAddress, "test");
    repository.save(alreadyExist);

    try {
      User newUser = User.create(username, emailAddress, "test");
      repository.save(newUser);
    } catch(Exception e) {
      assertEquals(ConstraintViolationException.class.toString(), e.getCause().getClass().toString());
    }
  }

  public void findByEmailAddress_notExist_shouldReturnEmptyResult() {
    String emailAddress = "go1323@gmail.com";
    User user = repository.findByEmailAddress(emailAddress);
    assertNull(user);
  }

  public void findByEmailAddress_exist_shouldReturnResult() {
    String username = "youngchul";
    String emailAddress = "go1323@gmail.com";
    User newUser = User.create(username, emailAddress, "password");
    repository.save(newUser);
    User foundUser = repository.findByEmailAddress(emailAddress);

    assertEquals(username, foundUser.getUsername());
  }

  public void findByUsername_notExist_shouldReturnEmptyResult() {
    String userName = "youngchulshin";
    User user = repository.findByUsername(userName);
    assertNull(user);
  }

  public void findByUsername_exist_shouldReturnResult() {
    String username = "youngchul";
    String emailAddress = "go1323@gmail.com";
    User newUser = User.create(username, emailAddress, "password");
    repository.save(newUser);
    User foundUser = repository.findByUsername(username);

    assertEquals(emailAddress, foundUser.getEmailAddress());
  }
}

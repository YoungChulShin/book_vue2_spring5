package com.taskagile.domain.model.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.taskagile.domain.common.security.PasswordEncryptor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class RegistrationManagementTests {

  private UserRepository repositoryMock;
  private PasswordEncryptor passwordEncryptorMock;
  private RegistrationManagement instance;

  @Before
  public void setup() {
    repositoryMock = mock(UserRepository.class);
    passwordEncryptorMock = mock(PasswordEncryptor.class);
    instance = new RegistrationManagement(repositoryMock, passwordEncryptorMock);
  }


  @Test(expected = UsernameExistsException.class)
  public void register_existedUsername_shouldFail() throws RegistrationException {
    String username = "youngchulshin";
    String emailAddress = "go1323@gmail.com";
    String password = "myPassword";

    when(repositoryMock.findByUsername(username)).thenReturn(new User());
    instance.register(username, emailAddress, password);
  }

  @Test (expected = EmailAddressExistsException.class)
  public void register_existedEmailAddress_shouldFail() throws RegistrationException {
    String username = "youngchulshin";
    String emailAddress = "go1323@gmail.com";
    String password = "myPassword";

    when(repositoryMock.findByEmailAddress(emailAddress)).thenReturn(new User());
    instance.register(username, emailAddress, password);
  }

  @Test
  public void register_uppercaseEmailAddress_shouldSucceedAndBecomeLowerCase() throws RegistrationException {
    String username = "youngchulshin";
    String emailAddress = "GO1323@Gmail.com";
    String password = "myPassword";

    instance.register(username, emailAddress, password);
    User userToSave = User.create(username, emailAddress.toLowerCase(), password);

    verify(repositoryMock).save(userToSave);
  }

  @Test
  public void register_newUser_shouldSucceed() throws RegistrationException {
    String username = "youngchulshin";
    String emailAddress = "go1323@gmail.com";
    String password = "myPassword";
    String encryptedPassword = "EncryptedmyPassword";
    User newUser = User.create(username, emailAddress, encryptedPassword);

    // 리파지토리 목 설정
    when(repositoryMock.findByUsername(username)).thenReturn(null);
    when(repositoryMock.findByEmailAddress(emailAddress)).thenReturn(null);
    doNothing().when(repositoryMock).save(newUser);
    when(passwordEncryptorMock.encrypt(password)).thenReturn(encryptedPassword);

    User savedUser = instance.register(username, emailAddress, password);

    // 아래의 기능이 register의 내부 동작을 정의하는 것
    // 1. findByUsername으로 사용자를 찾고
    // 2. findByEmailAddress으로 사용자를 찾고
    // 3. 없으면 save로 저장
    InOrder inOrder = inOrder(repositoryMock);
    inOrder.verify(repositoryMock).findByUsername(username);
    inOrder.verify(repositoryMock).findByEmailAddress(emailAddress);
    inOrder.verify(repositoryMock).save(newUser);
    verify(passwordEncryptorMock).encrypt(password);  // 이 동작이 일어났는지 검증
    assertEquals(encryptedPassword, savedUser.getPassword(), "Saved user's password should be encrypted");
  }
}

package com.taskagile.domain.application.impl;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.taskagile.domain.application.commands.RegistrationCommand;
import com.taskagile.domain.common.event.DomainEventPublisher;
import com.taskagile.domain.common.mail.MailManager;
import com.taskagile.domain.common.mail.MessageVariable;
import com.taskagile.domain.model.user.EmailAddressExistsException;
import com.taskagile.domain.model.user.RegistrationException;
import com.taskagile.domain.model.user.RegistrationManagement;
import com.taskagile.domain.model.user.User;
import com.taskagile.domain.model.user.UsernameExistsException;
import com.taskagile.domain.model.user.events.UserRegisteredEvent;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class UserServiceImplTests {
  // UserServiceImplTests

  private RegistrationManagement registrationManagementMock;
  private DomainEventPublisher domainEventPublisherMock;
  private MailManager mailManagerMock;
  private UserServiceImpl instance;

  @Before
  public void setUp() {
    registrationManagementMock = mock(RegistrationManagement.class);
    domainEventPublisherMock = mock(DomainEventPublisher.class);
    mailManagerMock = mock(MailManager.class);
    instance = new UserServiceImpl(registrationManagementMock, domainEventPublisherMock, mailManagerMock);
  }

  @Test(expected = IllegalArgumentException.class)
  public void register_nullCommand_shouldFail() throws RegistrationException {

    instance.register(null);
  }

  @Test(expected = RegistrationException.class)
  public void register_existingUsername_shouldFail() throws RegistrationException {

    String username = "existing";
    String emailAddress = "go1323@gmail.com";
    String password = "testPassword";

    // register에 등록된 값으로 registrationManagementMock이 호출되면
    doThrow(UsernameExistsException.class).when(registrationManagementMock)
      .register(username, emailAddress, password);

    RegistrationCommand command = new RegistrationCommand(username, emailAddress, password);
    instance.register(command);
  }

  @Test(expected = RegistrationException.class)
  public void register_existingEmailAddress_shouldFail() throws RegistrationException {

    String username = "existing";
    String emailAddress = "go1323@gmail.com";
    String password = "testPassword";

    doThrow(EmailAddressExistsException.class).when(registrationManagementMock)
      .register(username, emailAddress, password);

    RegistrationCommand command = new RegistrationCommand(username, emailAddress, password);
    instance.register(command);
  }

  @Test
  public void register_validCommand_shouldSucceed() throws RegistrationException {

    String username = "existing";
    String emailAddress = "go1323@gmail.com";
    String password = "testPassword";
    User newUser = User.create(username, emailAddress, password);

    when(registrationManagementMock.register(username, emailAddress, password)).thenReturn(newUser);

    RegistrationCommand command = new RegistrationCommand(username, emailAddress, password);

    instance.register(command);

    verify(mailManagerMock).send(
      emailAddress,
      "Welcome to TaskAgile",
      "welcome.ftl",
      MessageVariable.from("user", newUser)
    );

    verify(domainEventPublisherMock).publish(new UserRegisteredEvent(newUser));
  }
}

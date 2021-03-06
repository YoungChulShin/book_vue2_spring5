package com.taskagile.domain.application.impl;

import javax.transaction.Transactional;

import com.taskagile.domain.application.UserService;
import com.taskagile.domain.application.commands.RegistrationCommand;
import com.taskagile.domain.common.event.DomainEventPublisher;
import com.taskagile.domain.common.mail.MailManager;
import com.taskagile.domain.common.mail.MessageVariable;
import com.taskagile.domain.model.user.RegistrationException;
import com.taskagile.domain.model.user.RegistrationManagement;
import com.taskagile.domain.model.user.SimpleUser;
import com.taskagile.domain.model.user.User;
import com.taskagile.domain.model.user.UserRepository;
import com.taskagile.domain.model.user.events.UserRegisteredEvent;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Transactional
@Service
public class UserServiceImpl implements UserService{

  private RegistrationManagement registrationManagement;
  private DomainEventPublisher domainEventPublisher;
  private MailManager mailManager;

  @Autowired
  private UserRepository userRepository;

  public UserServiceImpl(RegistrationManagement registrationManagement,
                         DomainEventPublisher domainEventPublisher,
                         MailManager mailManager) {
    this.registrationManagement = registrationManagement;
    this.domainEventPublisher = domainEventPublisher;
    this.mailManager = mailManager;
  }

	@Override
	public void register(RegistrationCommand command) throws RegistrationException {

    Assert.notNull(command, "Paraemter `command` must not be null");

    // 사용자 등록
    User newUser = registrationManagement.register(
      command.getUsername(),
      command.getEmailAddress(),
      command.getPassword());

    // 메일 전달
    sendWelcomeMessage(newUser);

    // 이벤트 발생
    domainEventPublisher.publish(new UserRegisteredEvent(newUser));
  }

  private void sendWelcomeMessage(User user) {

    mailManager.send(user.getEmailAddress(),
      "Welcome to TaskAgile",
      "welcome.ftl",
      MessageVariable.from("user", user));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    if (StringUtils.isEmpty(username)) {
      throw new UsernameNotFoundException("No user found");
    }

    User user;
    if (username.contains("@")) {
      user = userRepository.findByEmailAddress(username);
    } else  {
      user = userRepository.findByUsername(username);
    }

    if (user == null) {
      throw new UsernameNotFoundException("No user found by `" + username + "`");
    }

    return new SimpleUser(user);
  }
}

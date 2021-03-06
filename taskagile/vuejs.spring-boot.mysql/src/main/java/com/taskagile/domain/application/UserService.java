package com.taskagile.domain.application;

import com.taskagile.domain.application.commands.RegistrationCommand;
import com.taskagile.domain.model.user.RegistrationException;
import com.taskagile.domain.model.user.User;
import com.taskagile.domain.model.user.UserId;

import org.springframework.security.core.userdetails.UserDetailsService;

// 모델의 작업 조정하기
// 보안 제약사항으로부터 도메인 보호하기
// 트랜잭션 컨트롤하기
public interface UserService extends UserDetailsService {
  void register(RegistrationCommand command) throws RegistrationException;
  User findById(UserId userId);
}

package com.taskagile.domain.common.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncryptorDelegator implements PasswordEncryptor {

  private PasswordEncoder encoder;

  public PasswordEncryptorDelegator(PasswordEncoder passwordEncoder) {
    this.encoder = passwordEncoder;
  }

	@Override
	public String encrypt(String rawPassword) {
    return encoder.encode(rawPassword);
	}
}

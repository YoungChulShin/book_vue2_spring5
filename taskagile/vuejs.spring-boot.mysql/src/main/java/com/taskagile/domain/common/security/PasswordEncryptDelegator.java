package com.taskagile.domain.common.security;

import org.springframework.stereotype.Component;

@Component
public class PasswordEncryptDelegator implements PasswordEncryptor {

	@Override
	public String encrypt(String rawPassword) {

		return rawPassword;
	}
}

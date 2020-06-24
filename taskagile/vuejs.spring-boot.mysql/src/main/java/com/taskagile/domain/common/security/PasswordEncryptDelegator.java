package com.taskagile.domain.common.security;

public class PasswordEncryptDelegator implements PasswordEncryptor {

	@Override
	public String encrypt(String rawPassword) {

		return rawPassword;
	}
}

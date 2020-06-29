package com.taskagile.web.apis.authenticate;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taskagile.utils.JsonUtils;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.Getter;
import lombok.Setter;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  private static final Logger log =  LoggerFactory.getLogger(AuthenticationFilter.class);

  public AuthenticationFilter() {
    super(new AntPathRequestMatcher("/api/authenications", "POST"));
  }

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

        log.debug("Processing login request");

        String requestBody = IOUtils.toString(request.getReader()); // Reader to String
        LoginRequest loginRequest = JsonUtils.toObject(requestBody, LoginRequest.class);  // String to Object. ObjectMapper 사용

        if (loginRequest == null || loginRequest.isInvalid()) {
          throw new InsufficientAuthenticationException("Invalid authentication request");
        }

        UsernamePasswordAuthenticationToken token =
          new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        return this.getAuthenticationManager().authenticate(token);
  }

  @Getter
  @Setter
  static class LoginRequest {
    private String username;
    private String password;

    public boolean isInvalid() {
      return StringUtils.isBlank(username) || StringUtils.isBlank(password);
    }
  }

}

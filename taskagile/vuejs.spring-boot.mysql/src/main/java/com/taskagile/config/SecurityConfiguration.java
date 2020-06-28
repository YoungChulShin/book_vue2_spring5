package com.taskagile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
  private static final String[] PUBLIC = new String[] {
    "/error", "/login", "/logout", "/register", "/api/registrations" };

  // HTTP 요청에 기반한 시큐리티
  @Override
  protected void configure(final HttpSecurity http) throws Exception {

    http
      .authorizeRequests()
        .antMatchers(PUBLIC).permitAll()
        .anyRequest().authenticated()
        .and()
      .formLogin()
        .loginPage("/login")
        .and()
      .logout() // WebSecurityConfigurerAdapter에서 로그아웃을 기본적으로 지원한다
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login?logged-out")
        .and()
      .csrf().disable();
  }

  //  요청에 기반한 시큐리티
  @Override
  public void configure(final WebSecurity web) {

    final String[] ignoreList = new String[] {
      "/static/**", "/js/**", "/css/**", "/images/**", "favicon.ico" };

    web.ignoring()
      .antMatchers(ignoreList);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

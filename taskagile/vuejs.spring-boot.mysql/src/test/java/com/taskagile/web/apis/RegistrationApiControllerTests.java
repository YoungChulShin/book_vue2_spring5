package com.taskagile.web.apis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskagile.config.SecurityConfigurtation;
import com.taskagile.domain.application.UserService;
import com.taskagile.domain.model.user.EmailAddressExistsException;
import com.taskagile.domain.model.user.UsernameExistsException;
import com.taskagile.web.payload.RegistrationPayload;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { SecurityConfigurtation.class, RegistrationApiController.class })
@WebMvcTest(RegistrationApiController.class)
@ActiveProfiles("test")
public class RegistrationApiControllerTests {

  @Autowired
  private MockMvc mvc;

  @Autowired
  protected ObjectMapper objectMapper;

  @MockBean
  private UserService serviceMock;

  @Test
  public void register_blankPayload_shouldFailAndReturn400() throws Exception {
    mvc.perform(post("/api/registrations"))
      .andExpect(status().isBadRequest());
  }

  @Test
  public void register_existedUsername_shouldFailAndReturn400() throws Exception {

    RegistrationPayload payload = new RegistrationPayload();
    payload.setUsername("exist");
    payload.setEmailAddress("go1323@naver.com");
    payload.setPassword("testPassword");

    doThrow(UsernameExistsException.class)
      .when(serviceMock)
      .register(payload.toCommand());

    mvc.perform(post("/api/registrations")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(payload)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Username already exists"));
  }

  @Test
  public void register_existedEmailAddress_shouldFailAndReturn400() throws Exception {

    RegistrationPayload payload = new RegistrationPayload();
    payload.setUsername("exist");
    payload.setEmailAddress("go1323@naver.com");
    payload.setPassword("testPassword");

    doThrow(EmailAddressExistsException.class)
      .when(serviceMock)
      .register(payload.toCommand());

    mvc.perform(post("/api/registrations")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(payload)))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message").value("Email address already exists"));
  }

  @Test
  public void register_validPayload_shouldSucceedAndReturn201() throws Exception {

    RegistrationPayload payload = new RegistrationPayload();
    payload.setUsername("exist");
    payload.setEmailAddress("go1323@naver.com");
    payload.setPassword("testPassword");

    doNothing().when(serviceMock).register(payload.toCommand());

    mvc.perform(post("/api/registrations")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(payload)))
      .andExpect(status().isCreated());
  }
}

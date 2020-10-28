package com.bms.gateway;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.bms.gateway.model.AuthenticationRequest;
import com.bms.gateway.model.User;
import com.bms.gateway.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = { "server.port:0",
		"spring.cloud.config.discovery.enabled:false", "spring.cloud.config.enabled:false",
		"spring.profiles.active:test" })
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestPropertySource("classpath:junit.properties")
class ApiGatewayApplicationTests {

	private static final String LOGIN_URL = "/v1/login";
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	UserRepository userRepository;

	@Test
	void testLogin() throws Exception {

		AuthenticationRequest authReq = new AuthenticationRequest("axavier", "123456");
		User userObj = new User();
		userObj.setActive(true);
		userObj.setPassword(new BCryptPasswordEncoder().encode("123456"));
		userObj.setRoles("ROLE_USER,ROLE_ADMIN");
		userObj.setUserId(Long.valueOf(1));
		userObj.setUsername("axavier");
		Optional<User> user = Optional.of(userObj);
		doReturn(user).when(userRepository).findByUsername(Mockito.anyString());

		String jsonReq = objectMapper.writeValueAsString(authReq);
		mockMvc.perform(post(LOGIN_URL).contentType("application/json").content(jsonReq)).andExpect(status().isOk());

	}

	@Test
	void testLoginFail() throws Exception {

		AuthenticationRequest authReq = new AuthenticationRequest("axavier", "123456");

		Optional<User> user = Optional.empty();
		doReturn(user).when(userRepository).findByUsername(Mockito.anyString());

		String jsonReq = objectMapper.writeValueAsString(authReq);
		mockMvc.perform(post(LOGIN_URL).contentType("application/json").content(jsonReq))
				.andExpect(status().isForbidden());

	}

	@Test
	void testAuthFail() throws Exception {

		mockMvc.perform(get("/customer/1").contentType("application/json")).andExpect(status().isForbidden());

	}

}

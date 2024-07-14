package com.aeon.hadog.controller;

import com.aeon.hadog.base.dto.user.JoinRequestDTO;
import com.aeon.hadog.base.dto.user.LoginRequestDTO;
import com.aeon.hadog.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    UserService userService;

    @Test
    @DisplayName("회원가입 테스트")
    void register() throws Exception {
        // given
        JoinRequestDTO dto = JoinRequestDTO.builder()
                .name("홍길동")
                .id("user01")
                .password("user012@")
                .nickname("길동이01")
                .email("user01@gmail.com")
                .build();

        String json = new ObjectMapper().writeValueAsString(dto);

        // when
        ResultActions resultActions = mockMvc.perform(post("/user")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 테스트")
    void login() throws Exception {
        // given
        LoginRequestDTO dto = LoginRequestDTO.builder()
                .id("user2")
                .password("User3@@@")
                .build();

        String json = new ObjectMapper().writeValueAsString(dto);

        // when
        ResultActions resultActions = mockMvc.perform(post("/user/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    @DisplayName("아이디 중복 테스트")
    void checkId() throws Exception {
        // given
        String id = "user3";

        // when
        ResultActions resultActions = mockMvc.perform(get("/user/id").param("id", id));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data").value(true))
                .andExpect(jsonPath("$.message").value("id값 중복 확인"));
    }

    @Test
    @DisplayName("닉네임 중복 테스트")
    void checkNickName() throws Exception {
        // given
        String nickName = "hello4";

        // when
        ResultActions resultActions = mockMvc.perform(get("/user/nickName").param("nickName", nickName));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data").value(true))
                .andExpect(jsonPath("$.message").value("닉네임 중복 확인"));
    }

    @Test
    @DisplayName("이메일 중복 테스트")
    void checkEmail() throws Exception {
        // given
        String email = "user3@gmail.com";

        // when
        ResultActions resultActions = mockMvc.perform(get("/user/email").param("email", email));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data").value(true))
                .andExpect(jsonPath("$.message").value("email 중복 확인"));
    }

    @Test
    @DisplayName("비밀번호 변경 테스트")
    void modifyPassword() throws Exception {
        // given
        String id = "user3";
        String password = "user333!";
        String newPassword = "user333@";

        LoginRequestDTO loginDto = new LoginRequestDTO(id, password);
        String token = loginUserAndGetToken(loginDto);

        // when
        ResultActions resultActions = mockMvc.perform(patch("/user/password")
                .header("Authorization",  "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("prevPassword", password)
                .param("newPassword", newPassword));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data").value(true))
                .andExpect(jsonPath("$.message").value("패스워드 변경 완료"));
    }

    @Test
    @DisplayName("회원 탈퇴 테스트")
    void deleteUser() throws Exception {
        // given
        String id = "user3";
        String password = "user333!";

        LoginRequestDTO loginDto = new LoginRequestDTO(id, password);
        String token = loginUserAndGetToken(loginDto);


        // when
        ResultActions resultActions = mockMvc.perform(delete("/user")
                .header("Authorization",  "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("password", password));


        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data").value(true))
                .andExpect(jsonPath("$.message").value("회원 탈퇴 완료"));
    }

    private String loginUserAndGetToken(LoginRequestDTO loginDto) throws Exception {
        String json = new ObjectMapper().writeValueAsString(loginDto);

        String response = mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // 응답에서 토큰 추출
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        String token = jsonNode.get("data").asText();

        return token;
    }
}
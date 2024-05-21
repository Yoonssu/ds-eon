package com.aeon.hadog.controller;

import com.aeon.hadog.base.dto.diary.DiaryDTO;
import com.aeon.hadog.base.dto.user.LoginRequestDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class DiaryControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("일기 업로드 테스트")
    void uploadDiary() throws Exception {

        // given
        String id = "user3";
        String password = "user333!";

        LoginRequestDTO loginDto = new LoginRequestDTO(id, password);
        String token = loginUserAndGetToken(loginDto);

        DiaryDTO dto = DiaryDTO.builder()
                .emotionTrackId(1L)
                .content("강아지가 기분이 좋아보인다.")
                .build();

        String json = new ObjectMapper().writeValueAsString(dto);


        // when
        ResultActions resultActions = mockMvc.perform(post("/diary")
                .header("Authorization",  "Bearer " + token)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("일기 상세 보기 테스트")
    void detailDiary() throws Exception {
        // given
        String id = "user3";
        String password = "user333!";

        LoginRequestDTO loginDto = new LoginRequestDTO(id, password);
        String token = loginUserAndGetToken(loginDto);
    }

    @Test
    @DisplayName("일기 수정 테스트")
    void modifyDiary() throws Exception {
        // given
        String id = "user3";
        String password = "user333!";

        LoginRequestDTO loginDto = new LoginRequestDTO(id, password);
        String token = loginUserAndGetToken(loginDto);
    }

    @Test
    @DisplayName("일기 목록 테스트")
    void getDiarys() throws Exception {
        // given
        String id = "user3";
        String password = "user333!";

        LoginRequestDTO loginDto = new LoginRequestDTO(id, password);
        String token = loginUserAndGetToken(loginDto);
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
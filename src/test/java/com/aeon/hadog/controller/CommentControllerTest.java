package com.aeon.hadog.controller;

import com.aeon.hadog.base.dto.comment.CommentDTO;
import com.aeon.hadog.base.dto.user.LoginRequestDTO;
import com.aeon.hadog.service.ShelterPostCommentService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.xml.transform.Result;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("댓글 작성 테스트")
    void addComment() throws Exception {

        // given
        String id = "mk020";
        String password = "Pw1234!!";

        LoginRequestDTO loginDto = new LoginRequestDTO(id, password);
        String token = loginUserAndGetToken(loginDto);

        CommentDTO commentDTO = CommentDTO.builder()
                        .postId(1L)
                        .commentId(1L)
                        .createdDate(LocalDateTime.now())
                        .content("댓글")
                        .build();

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.multipart("/addComment")
                        .param("postId", commentDTO.getPostId().toString())
                        .param("content", commentDTO.getContent())
                        .header("Authorization",  "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("댓글 작성 성공"))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    @DisplayName("댓글 목록 보기 테스트")
    void getComments() throws Exception {

        // given
        Long postId = 1L;

        // when
        ResultActions resultActions = mockMvc.perform(get("/comment/1"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("댓글 조회 성공"))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    void deleteComment() throws Exception {

        // given
        String id = "mk020";
        String password = "Pw1234!!";

        LoginRequestDTO loginDto = new LoginRequestDTO(id, password);
        String token = loginUserAndGetToken(loginDto);

        Long postId = 1L;

        // when
        ResultActions resultActions = mockMvc.perform(delete("/delComment/1")
                .header("Authorization",  "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("댓글 삭제 성공"))
                .andExpect(jsonPath("$.data").exists());
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
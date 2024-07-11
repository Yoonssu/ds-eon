package com.aeon.hadog.controller;

import com.aeon.hadog.base.dto.adoptPost.AdoptPostDTO;
import com.aeon.hadog.base.dto.diary.DiaryDTO;
import com.aeon.hadog.base.dto.user.LoginRequestDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AdoptPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("임보 공고 업로드 테스트")
    void uploadAdoptPost() throws Exception {
        // given
        String id = "user3";
        String password = "user333!";

        LoginRequestDTO loginDto = new LoginRequestDTO(id, password);
        String token = loginUserAndGetToken(loginDto);

        AdoptPostDTO adoptPost = AdoptPostDTO.builder()
                .content("This is a sample content.")
                .name("Buddy")
                .breed("Golden Retriever")
                .sex("Male")
                .age("2 years")
                .phone("123-456-7890")
                .duration("1 year")
                .neutering(true)
                .adoptStatus(false)
                .build();


        String adoptPostDTOJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(adoptPost);

        MockMultipartFile adoptPostDTOFile = new MockMultipartFile(
                "adoptPostDTO",
                "adoptPostDTO",
                "application/json",
                adoptPostDTOJson.getBytes()
        );

        MockMultipartFile image1 = new MockMultipartFile(
                "images",
                "image1.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "image1".getBytes()
        );

        MockMultipartFile image2 = new MockMultipartFile(
                "images",
                "image2.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "image2".getBytes()
        );


        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.multipart("/adoptPost")
                .file(adoptPostDTOFile)
                .file(image1)
                .file(image2)
                .header("Authorization",  "Bearer " + token)
                .contentType(MediaType.MULTIPART_FORM_DATA));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("입양 공고 작성 완료"))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    @DisplayName("임시보호 공고 상세 보기 테스트")
    void getPost() throws Exception {
        // given
        Long postId = 2L;

        // when
        ResultActions resultActions = mockMvc.perform(get("/adoptPost/post")
                .param("postId", String.valueOf(postId)));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("입양 공고 상세 불러오기 완료"))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    @DisplayName("임시보호 공고 목록 보기 테스트")
    void getPostList() throws Exception{
        // given
        int page = 0;

        // when
        ResultActions resultActions = mockMvc.perform(get("/adoptPost/list")
                .param("page", String.valueOf(page)));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("입양 공고 목록 불러오기 완료"))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    @DisplayName("임시보호 공고 상태 변경 테스트")
    void modifyAdoptStatus() throws Exception{
        // given
        String id = "user3";
        String password = "user333!";

        LoginRequestDTO loginDto = new LoginRequestDTO(id, password);
        String token = loginUserAndGetToken(loginDto);

        Long postId = 2L;


        // when
        ResultActions resultActions = mockMvc.perform(patch("/adoptPost")
                .header("Authorization",  "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("adoptPostId", String.valueOf(postId))
                .param("isAdopt", String.valueOf(false)));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("입양 상태 수정 완료"))
                .andExpect(jsonPath("$.data").exists());;
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
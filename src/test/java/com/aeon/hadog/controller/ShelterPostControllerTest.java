package com.aeon.hadog.controller;

import com.aeon.hadog.base.dto.shelter.ShelterPostDTO;
import com.aeon.hadog.base.dto.user.LoginRequestDTO;
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

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ShelterPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("보호소 공고 작성 테스트")
    void uploadShelterPost() throws Exception {
        // given
        ShelterPostDTO shelterPostDTO = ShelterPostDTO.builder()
                .title("유기견 공고")
                .content("유기견 공고 내용")
                .startDate(LocalDateTime.of(2024, 5, 1, 0, 0))
                .endDate(LocalDateTime.of(2024, 5, 10, 0, 0))
                .sex("Male")
                .color("갈색")
                .age("2살")
                .weight(2.5f)
                .adoptNum("12345")
                .findLocation("덕성여대 정문 앞")
                .center("유기견 센터")
                .centerPhone("010-1234-5678")
                .department("도봉구 유기견 부서")
                .departmentPhone("010-9876-5432")
                .build();

        String shelterPostDTOJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(shelterPostDTO);

        MockMultipartFile shelterPostDTOFile = new MockMultipartFile(
                "shelterPostDTO",
                "shelterPostDTO",
                "application/json",
                shelterPostDTOJson.getBytes()
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
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.multipart("/shelterPost")
                        .file(shelterPostDTOFile)
                        .file(image1)
                        .file(image2)
                        .contentType(MediaType.MULTIPART_FORM_DATA));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("보호소 공고 작성 완료"))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    @DisplayName("보호소 공고 상세 보기 테스트")
    void getPost() throws Exception {
        // given
        Long postId = 21L;

        // when
        ResultActions resultActions = mockMvc.perform(get("/shelterPost")
                .param("postId", String.valueOf(postId)));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("보호소 공고 상세 불러오기 완료"))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    @DisplayName("보호소 공고 목록 보기 테스트")
    void getPostList() throws Exception {
        // given
        int page = 1;

        // when
        ResultActions resultActions = mockMvc.perform(get("/shelterPost/list")
                .param("page", String.valueOf(page)));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("보호소 공고 목록 불러오기 완료"))
                .andExpect(jsonPath("$.data").exists());
    }
}
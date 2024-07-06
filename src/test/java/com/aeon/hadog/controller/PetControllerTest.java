package com.aeon.hadog.controller;

import com.aeon.hadog.base.dto.pet.PetDTO;
import com.aeon.hadog.base.dto.user.JoinRequestDTO;
import com.aeon.hadog.base.dto.user.LoginRequestDTO;
import com.aeon.hadog.service.AmazonS3Service;
import com.aeon.hadog.service.PetService;
import com.aeon.hadog.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// 계속 오류 ..

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PetControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PetService petService;

    @MockBean
    private UserService userService;

    @MockBean
    private AmazonS3Service amazonS3Service;

    @Test
    @DisplayName("반려견 등록")
    void register() throws Exception {
        // given
        String id = "mk020";
        String password = "Pw1234!!";

        LoginRequestDTO loginDto = new LoginRequestDTO(id, password);
        String token = loginUserAndGetToken(loginDto);

        PetDTO petDTO = new PetDTO();
        petDTO.setAge(3);
        petDTO.setName("뽀삐");
        petDTO.setSex("M");
        petDTO.setNeuter(true);
        petDTO.setBreed("말티즈");

        String petJson = new ObjectMapper().writeValueAsString(petDTO);

        // when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.multipart("/pet/register")
                .file(new MockMultipartFile("file", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test image".getBytes()))
                .param("pet", petJson)
                .header("Authorization",  "Bearer " + token)
                .contentType(MediaType.MULTIPART_FORM_DATA));

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data").value(true))
                .andExpect(jsonPath("$.message").value("반려견 등록 성공"));

    }

    @Test
    @DisplayName("반려견 정보 수정")
    @WithMockUser(username = "user", password = "Pw1234!!")
    void update() throws Exception {

        //given
        String id = "mk020";
        String password = "Pw1234!!";

        LoginRequestDTO loginDto = new LoginRequestDTO(id, password);
        String token = loginUserAndGetToken(loginDto);

        PetDTO petDTO = new PetDTO();
        petDTO.setAge(4);
        petDTO.setName("뽀삐");
        petDTO.setSex("F");
        petDTO.setNeuter(true);
        petDTO.setBreed("말티즈");

        String petJson = new ObjectMapper().writeValueAsString(petDTO);

        // when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.multipart("/pet/update/1")
                .file(new MockMultipartFile("file", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test image".getBytes()))
                .param("pet", petJson)
                .header("Authorization",  "Bearer " + token)
                .contentType(MediaType.MULTIPART_FORM_DATA));

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data").value(true))
                .andExpect(jsonPath("$.message").value("반려견 정보 수정 성공"));
    }

    private String loginUserAndGetToken(LoginRequestDTO loginDto) throws Exception {
        String json = new ObjectMapper().writeValueAsString(loginDto);

        String response = mvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
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
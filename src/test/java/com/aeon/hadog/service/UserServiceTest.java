package com.aeon.hadog.service;

import com.aeon.hadog.base.dto.user.JoinRequestDTO;
import com.aeon.hadog.domain.User;
import com.aeon.hadog.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.JsonPathAssertions;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void signup() {
        // given
        JoinRequestDTO joinRequestDTO = JoinRequestDTO.builder()
                .name("김민지")
                .id("minji01")
                .password("minji01@")
                .nickname("김민지01")
                .email("minji01@gmail.com")
                .build(); 

        when(userRepository.existsById(joinRequestDTO.getId())).thenReturn(false);
        when(userRepository.existsByNickname(joinRequestDTO.getNickname())).thenReturn(false);
        when(userRepository.existsByEmail(joinRequestDTO.getEmail())).thenReturn(false);

        // when
        Long userId = userService.signup(joinRequestDTO);

        // then
        User findUser = userRepository.findByUserId(userId).orElseThrow();
        assertThat(findUser.getUserId(), equalTo(userId));
        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    void signin() {
        // given

        // when

        // then
    }

    @Test
    void checkId() {
        // given

        // when

        // then
    }

    @Test
    void checkNickName() {
        // given

        // when

        // then
    }

    @Test
    void checkEmail() {
        // given

        // when

        // then
    }

    @Test
    void modifyPassword() {
        // given

        // when

        // then
    }

    @Test
    void deleteUser() {
        // given

        // when

        // then
    }
}
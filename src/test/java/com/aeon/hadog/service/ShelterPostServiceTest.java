package com.aeon.hadog.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ShelterPostServiceTest {

    @Test
    @DisplayName("보호소 공고 작성 테스트")
    void uploadPost() {
    }

    @Test
    @DisplayName("보호소 공고 상세 보기 테스트")
    void getPostDetail() {
    }

    @Test
    @DisplayName("보호소 공고 목록 보기 테스트")
    void getList() {
    }
}
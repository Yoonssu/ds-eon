package com.aeon.hadog.service;

import com.aeon.hadog.base.dto.diary.DiaryDTO;
import com.aeon.hadog.domain.Diary;
import com.aeon.hadog.domain.User;
import com.aeon.hadog.repository.DiaryRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class DiaryServiceTest {

    @Autowired
    DiaryService diaryService;

    @Autowired
    DiaryRepository diaryRepository;

    @Test
    @DisplayName("일기 생성 테스트")
    void createDiary() {
        // given
        String userId = "user3";
        DiaryDTO dto = DiaryDTO.builder()
                .emotionTrackId(1L)
                .content("해피가 행복해보인다")
                .build();

        // when
        Long diaryId = diaryService.createDiary(userId, dto);

        // then
        assertNotNull(diaryId);
        Diary diary = diaryRepository.findByDiaryId(diaryId).orElseThrow();
        assertEquals(diary.getDiaryId(), diaryId);
    }

    @Test
    @DisplayName("일기 상세 테스트")
    void getDiary() {
        // given
        String userId = "user3";
        Long diaryId = 3L;
        Diary diary = diaryRepository.findByDiaryId(3L).orElseThrow();

        // when
        DiaryDTO dto = diaryService.getDiary(userId, diaryId);

        // then
        assertNotNull(dto);
        assertEquals(diary.getEmotionTrack().getEmotionTrackId(), dto.getEmotionTrackId());
        assertEquals(diary.getDiaryDate(), dto.getDiaryDate());
        assertEquals(diary.getContent(), dto.getContent());
    }

    @Test
    @DisplayName("일기 수정 테스트")
    void modifyDiary() {
        // given
        String userId = "user3";
        Long diaryId = 3L;
        String content = "해피가 슬퍼보인다.";

        // when
        DiaryDTO dto = diaryService.modifyDiary(userId, diaryId, content);
        Diary diary = diaryRepository.findByDiaryId(3L).orElseThrow();

        // then
        assertNotNull(dto);
        assertEquals(diary.getEmotionTrack().getEmotionTrackId(), dto.getEmotionTrackId());
        assertEquals(diary.getDiaryDate(), dto.getDiaryDate());
        assertEquals(diary.getContent(), dto.getContent());
    }

    @Test
    @DisplayName("일기 목록 테스트")
    void getDiarys() {
        // given
        String userId = "user3";
        LocalDateTime date = LocalDateTime.of(2024, 5, 2, 0, 0);
        List<DiaryDTO> diaries = diaryRepository.findDiaryDTOByUserIdAndDiaryDate(4L, date);

        // when
        List<DiaryDTO> dtos = diaryService.getDiarys(userId, date);

        // then
        assertNotNull(dtos);
        assertEquals(diaries.size(), dtos.size());
        assertEquals(diaries.get(0).getContent(), dtos.get(0).getContent());
        assertEquals(diaries.get(1).getContent(), dtos.get(1).getContent());
    }
}
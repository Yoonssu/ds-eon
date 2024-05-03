package com.aeon.hadog.service;

import com.aeon.hadog.base.code.ErrorCode;
import com.aeon.hadog.base.dto.diary.DiaryDTO;
import com.aeon.hadog.base.exception.DiaryNotFoundException;
import com.aeon.hadog.base.exception.UserNotFoundException;
import com.aeon.hadog.domain.Diary;
import com.aeon.hadog.domain.User;
import com.aeon.hadog.repository.DiaryRepository;
import com.aeon.hadog.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {

    private DiaryRepository diaryRepository;

    private UserRepository userRepository;

    public Long createDiary(String userId, DiaryDTO diaryDTO){

        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        Diary diary = Diary.builder()
                .emotionTrack(diaryDTO.getEmotionTrack())
                .diaryDate(diaryDTO.getDiaryDate())
                .content(diaryDTO.getContent())
                .build();

        diaryRepository.save(diary);

        return diary.getDiaryId();
    }

    public DiaryDTO getDiary(String userId, Long diaryId){
        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        Diary diary = diaryRepository.findByDiaryId(diaryId).orElseThrow(()->new DiaryNotFoundException(ErrorCode.DIARY_NOT_FOUND));

        DiaryDTO diaryDTO = DiaryDTO.builder()
                .emotionTrack(diary.getEmotionTrack())
                .diaryDate(diary.getDiaryDate())
                .content(diary.getContent())
                .build();

        return diaryDTO;
    }
}

package com.aeon.hadog.service;

import com.aeon.hadog.base.code.ErrorCode;
import com.aeon.hadog.base.dto.diary.UploadDiaryDTO;
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

    public Long createDiary(String userId, UploadDiaryDTO uploadDiaryDTO){

        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        Diary diary = Diary.builder()
                .emotionTrack(uploadDiaryDTO.getEmotionTrack())
                .diaryDate(uploadDiaryDTO.getDiaryDate())
                .content(uploadDiaryDTO.getContent())
                .build();

        diaryRepository.save(diary);

        return diary.getDiaryId();
    }
}

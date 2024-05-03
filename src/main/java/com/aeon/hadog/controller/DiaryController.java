package com.aeon.hadog.controller;

import com.aeon.hadog.base.dto.diary.UploadDiaryDTO;
import com.aeon.hadog.base.dto.response.ResponseDTO;
import com.aeon.hadog.base.dto.user.LoginRequestDTO;
import com.aeon.hadog.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<ResponseDTO> uploadDiary(@AuthenticationPrincipal String userId, @RequestBody UploadDiaryDTO uploadDiaryDTO){
        Long diaryId = diaryService.createDiary(userId, uploadDiaryDTO);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, "일기 작성 완료", diaryId));
    }
}

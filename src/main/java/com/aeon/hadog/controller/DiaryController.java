package com.aeon.hadog.controller;

import com.aeon.hadog.base.dto.diary.DiaryDTO;
import com.aeon.hadog.base.dto.response.ResponseDTO;
import com.aeon.hadog.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<ResponseDTO> uploadDiary(@AuthenticationPrincipal String userId, @RequestBody DiaryDTO diaryDTO){
        Long diaryId = diaryService.createDiary(userId, diaryDTO);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, "일기 작성 완료", diaryId));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> DetailDiary(@AuthenticationPrincipal String userId, @RequestParam Long diaryId){
        DiaryDTO result = diaryService.getDiary(userId, diaryId);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, "일기 상세 불러오기 완료", result));
    }
}

package com.aeon.hadog.controller;

import com.aeon.hadog.base.dto.MyPageDTO;
import com.aeon.hadog.base.dto.pet.PetDTO;
import com.aeon.hadog.base.dto.response.ResponseDTO;
import com.aeon.hadog.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/userinfo")
    public ResponseEntity<ResponseDTO<MyPageDTO>> getUserInfo(@AuthenticationPrincipal String user) {
        try {
            MyPageDTO userInfo = myPageService.getUserInfo(user);
            return ResponseEntity.ok(new ResponseDTO<>(200, true, "사용자 정보 조회 성공", userInfo));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseDTO<>(400, false, "사용자 정보 조회 실패: " + e.getMessage(), null));
        }
    }

    @GetMapping("/petinfo")
    public ResponseEntity<ResponseDTO<List<PetDTO>>> getPetInfo(@AuthenticationPrincipal String user) {
        try {
            List<PetDTO> petInfo = myPageService.getPetInfo(user);
            return ResponseEntity.ok(new ResponseDTO<>(200, true, "반려견 정보 조회 성공", petInfo));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseDTO<>(400, false, "반려견 정보 조회 실패: " + e.getMessage(), null));
        }
    }

    @PutMapping("/nickname")
    public ResponseEntity<ResponseDTO<Void>> updateNickname(@AuthenticationPrincipal String user, @RequestParam String newNickname) {

        try {
            myPageService.updateNickname(user, newNickname);
            return ResponseEntity.ok(new ResponseDTO<>(200, true, "닉네임 수정 성공", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseDTO<>(400, false, "닉네임 수정 실패: " + e.getMessage(), null));
        }
    }
}

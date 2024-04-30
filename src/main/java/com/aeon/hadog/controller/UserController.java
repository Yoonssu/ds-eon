package com.aeon.hadog.controller;

import com.aeon.hadog.base.dto.response.ResponseDTO;
import com.aeon.hadog.base.dto.user.JoinRequestDTO;
import com.aeon.hadog.base.dto.user.LoginRequestDTO;
import com.aeon.hadog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ResponseDTO> register(@Valid @RequestBody JoinRequestDTO joinRequestDTO, Errors errors) {
        if (errors.hasErrors()) {
            Map<String, String> validatorResult = userService.validateHandling(errors);
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseDTO<>(400, false, "회원가입 실패", validatorResult));
        }

        Long userId = userService.signup(joinRequestDTO);

        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, "회원가입 성공", userId));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        String token = userService.signin(loginRequestDTO);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, "로그인 성공", token));
    }

    @GetMapping("/id")
    public ResponseEntity<ResponseDTO> checkId(@RequestParam String id){
        Boolean isexist = userService.checkId(id);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, "id값 중복 확인", isexist));
    }

    @GetMapping("/nickName")
    public ResponseEntity<ResponseDTO> checkNickName(@RequestParam String nickName){
        Boolean isexist = userService.checkNickName(nickName);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, "닉네임 중복 확인", isexist));
    }

    @GetMapping("/email")
    public ResponseEntity<ResponseDTO> checkEmail(@RequestParam String email){
        Boolean isexist = userService.checkEmail(email);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, "email 중복 확인", isexist));
    }

    @PatchMapping("/password")
    public ResponseEntity<ResponseDTO> modifyPassword(@AuthenticationPrincipal String userId, @RequestParam String prevPassword,  @RequestParam String newPassword){
        Boolean isModify = userService.modifyPassword(userId, prevPassword, newPassword);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, "패스워드 변경 완료", isModify));
    }


    @DeleteMapping
    public ResponseEntity<ResponseDTO> deleteUser(@AuthenticationPrincipal String userId, @RequestParam String password){
        Boolean isDelete = userService.deleteUser(userId, password);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, "회원 탈퇴 완료", isDelete));
    }

}

package com.aeon.hadog.controller;

import com.aeon.hadog.base.dto.response.ResponseDTO;
import com.aeon.hadog.base.dto.user.JoinRequestDTO;
import com.aeon.hadog.base.dto.user.LoginRequestDTO;
import com.aeon.hadog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<ResponseDTO> register(@RequestBody JoinRequestDTO joinRequestDTO) {
        Long userId = userService.signup(joinRequestDTO);

        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, null, userId));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        String token = userService.signin(loginRequestDTO);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, null, token));
    }

    @GetMapping("/id")
    public ResponseEntity<ResponseDTO> checkId(@RequestParam String id){
        Boolean isexist = userService.checkId(id);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, null, isexist));
    }

    @GetMapping("/nickName")
    public ResponseEntity<ResponseDTO> checkNickName(@RequestParam String nickName){
        Boolean isexist = userService.checkNickName(nickName);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, null, isexist));
    }

    @GetMapping("/email")
    public ResponseEntity<ResponseDTO> checkEmail(@RequestParam String email){
        Boolean isexist = userService.checkEmail(email);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, null, isexist));
    }
}

package com.aeon.hadog.service;


import com.aeon.hadog.base.code.ErrorCode;
import com.aeon.hadog.base.config.security.JwtTokenProvider;
import com.aeon.hadog.base.dto.user.JoinRequestDTO;
import com.aeon.hadog.base.dto.user.LoginRequestDTO;
import com.aeon.hadog.base.exception.*;
import com.aeon.hadog.domain.User;
import com.aeon.hadog.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder =new BCryptPasswordEncoder();


    public Long signup(JoinRequestDTO joinRequestDTO) {

        if(userRepository.existsById(joinRequestDTO.getId())){
            throw new DuplicateIdException(ErrorCode.DUPLICATE_ID_REQUEST);
        }
        if(userRepository.existsByNickname(joinRequestDTO.getNickname())){
            throw new DuplicateNicknameException(ErrorCode.DUPLICATE_NICKNAME_REQUEST);
        }
        if(userRepository.existsByEmail(joinRequestDTO.getEmail())){
            throw new DuplicateEmailException(ErrorCode.DUPLICATE_EMAIL_REQUEST);
        }

        User user= User.builder()
                .name(joinRequestDTO.getName())
                .id(joinRequestDTO.getId())
                .password(passwordEncoder.encode(joinRequestDTO.getPassword()))
                .nickname(joinRequestDTO.getNickname())
                .email(joinRequestDTO.getEmail())
                .build();

        userRepository.save(user);

        return user.getUserId();
    }

    public String signin(LoginRequestDTO loginRequestDTO){
        User user = userRepository.findById(loginRequestDTO.getId()).orElseThrow(()->new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        if(!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())){
            throw new InvalidLoginException(ErrorCode.INVALID_LOGIN);
        }

        String token = JwtTokenProvider.createToken(user.getId());

        return token;

    }

    public boolean checkId(String id){
        return userRepository.existsById(id);
    }

    public boolean checkNickName(String nickName){
        return userRepository.existsByNickname(nickName);
    }

    public boolean checkEmail(String email){ return userRepository.existsByEmail(email); }

    public boolean modifyPassword(String userId, String newPassword){
        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return true;
    }

    public boolean deleteUser(String userId, String password){
        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new PasswordMismatchException(ErrorCode.PASSWORD_MISMATCH);
        }

        userRepository.deleteById(user.getId());

        return true;
    }

    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        /* 유효성 검사에 실패한 필드 목록을 받음 */
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }

}

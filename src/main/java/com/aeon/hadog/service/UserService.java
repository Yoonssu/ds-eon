package com.aeon.hadog.service;


import com.aeon.hadog.base.config.security.JwtTokenProvider;
import com.aeon.hadog.base.dto.user.JoinRequestDTO;
import com.aeon.hadog.base.dto.user.LoginRequestDTO;
import com.aeon.hadog.domain.User;
import com.aeon.hadog.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder =new BCryptPasswordEncoder();


    public Long signup(JoinRequestDTO joinRequestDTO) {

        if(userRepository.existsById(joinRequestDTO.getId())){
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }
        if(userRepository.existsByNickname(joinRequestDTO.getNickname())){
            throw new RuntimeException("이미 존재하는 닉네임입니다.");
        }
        if(userRepository.existsByEmail(joinRequestDTO.getEmail())){
            throw new RuntimeException("이미 존재하는 이메일입니다.");
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
        User user = userRepository.findById(loginRequestDTO.getId()).orElseThrow();

        if(!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())){
            throw new RuntimeException("로그인 실패");
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

    public boolean checkEmail(String email){
        return userRepository.existsByEmail(email);
    }

}

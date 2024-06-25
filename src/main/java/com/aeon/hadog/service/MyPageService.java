package com.aeon.hadog.service;

import com.aeon.hadog.base.code.ErrorCode;
import com.aeon.hadog.base.dto.MyPageDTO;
import com.aeon.hadog.base.dto.PetDTO;
import com.aeon.hadog.base.exception.PetNotFoundException;
import com.aeon.hadog.base.exception.UserNotFoundException;
import com.aeon.hadog.domain.Pet;
import com.aeon.hadog.domain.User;
import com.aeon.hadog.repository.PetRepository;
import com.aeon.hadog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;

    public MyPageDTO getUserInfo(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        return MyPageDTO.builder()
                .name(user.getName())
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }

    public List<PetDTO> getPetInfo(Long userId) {
        Optional<Pet> petOptional = petRepository.findById(userId);
        if (petOptional.isPresent()) {
            Pet pet = petOptional.get();
            return Collections.singletonList(mapToPetDTO(pet));
        } else {
            throw new PetNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
    }

    public void updateNickname(String userId, String newNickname) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        user.setNickname(newNickname);
        userRepository.save(user);
    }

    private PetDTO mapToPetDTO(Pet pet) {
        return PetDTO.builder()
                .name(pet.getName())
                .breed(pet.getBreed())
                .sex(pet.getSex())
                .neuter(pet.getNeuter())
                .image(pet.getImage())
                .age(pet.getAge())
                .build();
    }
}

package com.aeon.hadog.service;

import com.aeon.hadog.base.code.ErrorCode;
import com.aeon.hadog.base.dto.MyPageDTO;
import com.aeon.hadog.base.dto.adopt_review.AdoptReviewDTO;
import com.aeon.hadog.base.dto.adopt_review.ReviewImageDTO;
import com.aeon.hadog.base.dto.pet.PetDTO;
import com.aeon.hadog.base.exception.PetNotFoundException;
import com.aeon.hadog.base.exception.UserNotFoundException;
import com.aeon.hadog.domain.AdoptReview;
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
    private final AdoptReviewService adoptReviewService;

    public MyPageDTO getUserInfo(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        return MyPageDTO.builder()
                .name(user.getName())
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }

    public List<PetDTO> getPetInfo(String userId) {
        Optional<Pet> petOptional = petRepository.findById(Long.valueOf(userId));
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

    // 사용자 ID를 기반으로 해당 사용자가 작성한 입양 후기 목록을 DTO로 변환하여 반환하는 메서드
    public List<AdoptReviewDTO> getAdoptReviewsByUserId(String userId) {
        return adoptReviewService.findReviewsByUserId(userId).stream()
                .map(this::mapToAdoptReviewDTO)
                .collect(Collectors.toList());
    }

    private AdoptReviewDTO mapToAdoptReviewDTO(AdoptReview review) {
        return AdoptReviewDTO.builder()
                .reviewId(review.getReviewId())
                .reviewDate(review.getReviewDate())
                .content(review.getContent())
                .images(review.getImages().stream()
                        .map(image -> ReviewImageDTO.builder()
                                .imageId(image.getImageId())
                                .fileName(image.getFileName())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }


}

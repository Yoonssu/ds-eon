package com.aeon.hadog.service;

import com.aeon.hadog.base.code.ErrorCode;
import com.aeon.hadog.base.dto.shelter.ListShelterPostDTO;
import com.aeon.hadog.base.dto.shelter.ShelterPostDTO;
import com.aeon.hadog.base.exception.ShelterPostNotFoundException;
import com.aeon.hadog.domain.ShelterPost;
import com.aeon.hadog.repository.ShelterPostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShelterPostService {

    private final ShelterPostRepository shelterPostRepository;

    public Long uploadPost(ShelterPostDTO shelterPostDTO){
        ShelterPost post = ShelterPost.builder()
                .title(shelterPostDTO.getTitle())
                .content(shelterPostDTO.getContent())
                .startDate(shelterPostDTO.getStartDate())
                .endDate(shelterPostDTO.getEndDate())
                .sex(shelterPostDTO.getSex())
                .color(shelterPostDTO.getColor())
                .age(shelterPostDTO.getAge())
                .weight(shelterPostDTO.getWeight())
                .adoptNum(shelterPostDTO.getAdoptNum())
                .findLocation(shelterPostDTO.getFindLocation())
                .center(shelterPostDTO.getCenter())
                .centerPhone(shelterPostDTO.getCenterPhone())
                .department(shelterPostDTO.getDepartment())
                .departmentPhone(shelterPostDTO.getDepartmentPhone())
                .build();

        shelterPostRepository.save(post);

        return post.getShelterPostId();
    }

    public ShelterPostDTO getPost(Long postId){
        ShelterPost post = shelterPostRepository.findByShelterPostId(postId).orElseThrow(()->new ShelterPostNotFoundException(ErrorCode.SHELTER_POST_NOT_FOUND));

        ShelterPostDTO dto = ShelterPostDTO.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .startDate(post.getStartDate())
                .endDate(post.getEndDate())
                .sex(post.getSex())
                .color(post.getColor())
                .age(post.getAge())
                .weight(post.getWeight())
                .adoptNum(post.getAdoptNum())
                .findLocation(post.getFindLocation())
                .center(post.getCenter())
                .centerPhone(post.getCenterPhone())
                .department(post.getDepartment())
                .departmentPhone(post.getDepartmentPhone())
                .build();

        return dto;
    }

    public Page<ListShelterPostDTO> getList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("shelterPostId"));
        Pageable pageable = PageRequest.of(page, 15, Sort.by(sorts));
        Page<ShelterPost> posts = shelterPostRepository.findAll(pageable);
        return posts.map(this::convertToListDTO);
    }

    private ListShelterPostDTO convertToListDTO(ShelterPost shelterPost) {
        ListShelterPostDTO dto = new ListShelterPostDTO();
        dto.setTitle(shelterPost.getTitle());
        dto.setSex(shelterPost.getSex());
        dto.setAge(shelterPost.getAge());
        return dto;
    }
}
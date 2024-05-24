package com.aeon.hadog.service;

import com.aeon.hadog.base.dto.shelter.ListShelterPostDTO;
import com.aeon.hadog.base.dto.shelter.ShelterPostDTO;
import com.aeon.hadog.base.dto.user.JoinRequestDTO;
import com.aeon.hadog.domain.ShelterPost;
import com.aeon.hadog.domain.ShelterPostImages;
import com.aeon.hadog.domain.User;
import com.aeon.hadog.repository.ShelterPostImagesRepository;
import com.aeon.hadog.repository.ShelterPostRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ShelterPostServiceTest {

    @Autowired
    private ShelterPostService shelterPostService;

    @Autowired
    private ShelterPostRepository shelterPostRepository;

    @Autowired
    private ShelterPostImagesRepository shelterPostImagesRepository;


    @Test
    @DisplayName("보호소 공고 작성 테스트")
    void uploadPost() {
        // given
        ShelterPostDTO shelterPostDTO = ShelterPostDTO.builder()
                .title("유기견 공고")
                .content("유기견 공고 내용")
                .imageUrls(Arrays.asList("test1.jpg", "test2.jpg"))
                .startDate(LocalDateTime.of(2024, 5, 1, 0, 0))
                .endDate(LocalDateTime.of(2024, 5, 10, 0, 0))
                .sex("Male")
                .color("갈색")
                .age("2살")
                .weight(2.5f)
                .adoptNum("12345")
                .findLocation("덕성여대 정문 앞")
                .center("유기견 센터")
                .centerPhone("010-1234-5678")
                .department("도봉구 유기견 부서")
                .departmentPhone("010-9876-5432")
                .build();

        // when
        Long postId = shelterPostService.uploadPost(shelterPostDTO);

        // then
        assertNotNull(postId);
        ShelterPost findPost = shelterPostRepository.findByShelterPostId(postId).orElseThrow();
        assertEquals(findPost.getShelterPostId(), postId);
    }

    @Test
    @DisplayName("보호소 공고 상세 보기 테스트")
    void getPostDetail() {
        // given
        Long postId = 21L;
        // when
        ShelterPostDTO result = shelterPostService.getPostDetail(postId);

        // then
        assertNotNull(result);
        assertEquals("유기견20", result.getTitle());
        assertEquals("유기견 공고입니다.", result.getContent());
        assertEquals(LocalDateTime.of(2024, 5, 2, 0, 0), result.getStartDate());
        assertEquals(LocalDateTime.of(2024, 5, 22, 0, 0), result.getEndDate());
        assertEquals("여성", result.getSex());
        assertEquals("흰색", result.getColor());
        assertEquals("2살 미만", result.getAge());
        assertEquals(1.5f, result.getWeight());
        assertEquals("abvi323", result.getAdoptNum());
        assertEquals("덕성여자대학교 정문", result.getFindLocation());
        assertEquals("도봉구 보호소", result.getCenter());
        assertEquals("02-123-4567", result.getCenterPhone());
        assertEquals("도봉구 유기동물 부서", result.getDepartment());
        assertEquals("02-789-4563", result.getDepartmentPhone());
    }

    @Test
    @DisplayName("보호소 공고 목록 보기 테스트")
    void getList() {
        // given
        int page = 0;

        // when
        Page<ListShelterPostDTO> result = shelterPostService.getList(page);

        // then
        assertNotNull(result);
        assertEquals(3, result.getContent().size());

        ListShelterPostDTO dto1 = result.getContent().get(0);
        assertEquals("유기견22", dto1.getTitle());
        assertEquals("여성", dto1.getSex());
        assertEquals("2살 미만", dto1.getAge());
        assertEquals("https://hadog.s3.ap-northeast-2.amazonaws.com/shelterPost/943b4a10-4c0d-4078-86d4-c3cf58627f98.d3.jpeg", dto1.getThumbnail());

        ListShelterPostDTO dto2 = result.getContent().get(1);
        assertEquals("유기견21", dto2.getTitle());
        assertEquals("여성", dto2.getSex());
        assertEquals("2살 미만", dto2.getAge());
        assertEquals("https://hadog.s3.ap-northeast-2.amazonaws.com/shelterPost/8c965550-d411-4ff8-b023-24173ad1e2c4.d3.jpeg", dto2.getThumbnail());


        ListShelterPostDTO dto3 = result.getContent().get(2);
        assertEquals("유기견20", dto3.getTitle());
        assertEquals("여성", dto3.getSex());
        assertEquals("2살 미만", dto3.getAge());
        assertEquals("https://hadog.s3.ap-northeast-2.amazonaws.com/shelterPost/d8777245-f293-479b-a7ec-827d48b8c819.d3.jpeg", dto3.getThumbnail());
    }
}
package com.aeon.hadog.service;

import com.aeon.hadog.base.dto.adoptPost.AdoptPostDTO;
import com.aeon.hadog.base.dto.adoptPost.ListAdoptPostDTO;
import com.aeon.hadog.base.dto.diary.DiaryDTO;
import com.aeon.hadog.base.dto.shelter.ListShelterPostDTO;
import com.aeon.hadog.base.dto.shelter.ShelterPostDTO;
import com.aeon.hadog.domain.AdoptPost;
import com.aeon.hadog.domain.AdoptPostImages;
import com.aeon.hadog.domain.Diary;
import com.aeon.hadog.domain.User;
import com.aeon.hadog.repository.AdoptPostImagesRepository;
import com.aeon.hadog.repository.AdoptPostRepository;
import com.aeon.hadog.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class AdoptPostServiceTest {

    @Autowired
    AdoptPostService adoptPostService;

    @Autowired
    AdoptPostRepository adoptPostRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdoptPostImagesRepository adoptPostImagesRepository;

    @Test
    void uploadPost() {
        // given
        String userId = "user3";
        List<String> imageUrls = new ArrayList<>(Arrays.asList("img1.jpg", "img2.jpg"));
        AdoptPostDTO adoptPost = AdoptPostDTO.builder()
                .content("This is a sample content.")
                .name("Buddy")
                .breed("Golden Retriever")
                .sex("Male")
                .age("2 years")
                .phone("123-456-7890")
                .duration("1 year")
                .imageUrls(imageUrls)
                .neutering(true)
                .adoptStatus(false)
                .build();

        // when
        Long adoptPostId = adoptPostService.uploadPost(userId, adoptPost);

        // then
        assertNotNull(adoptPostId);
        AdoptPost post = adoptPostRepository.findByAdoptPostId(adoptPostId).orElseThrow();
        assertEquals(post.getAdoptPostId(), adoptPostId);
    }

    @Test
    void getPostDetail() {
        // given
        Long postId = 2L;
        String userId = "user3";

        // when
        User user = userRepository.findById(userId).orElseThrow();

        AdoptPostDTO result = adoptPostService.getPostDetail(postId);

        // then
        assertNotNull(result);
        assertEquals("유기견 공고입니다.", result.getContent());
        assertEquals("진돌이", result.getName());
        assertEquals("믹스", result.getBreed());
        assertEquals("여성", result.getSex());
        assertEquals("2살 미만", result.getAge());
        assertEquals("02-789-4563", result.getPhone());
        assertEquals("6개월", result.getDuration());
        assertEquals(true, result.isAdoptStatus());
        assertEquals(true, result.isNeutering());
        assertEquals(user, result.getUser());
    }

    @Test
    void getList() {
        /// given
        int page = 0;

        // when
        Page<ListAdoptPostDTO> result = adoptPostService.getList(page);

        // then
        assertNotNull(result);
        assertEquals(2, result.getContent().size());

        ListAdoptPostDTO dto1 = result.getContent().get(0);
        assertEquals("진돌이", dto1.getName());
        assertEquals("믹스", dto1.getBreed());
        assertEquals("2살 미만", dto1.getAge());
        assertEquals("6개월", dto1.getDuration());
        assertEquals("https://hadog.s3.ap-northeast-2.amazonaws.com/adoptPost/086bf8cf-7247-492e-8b7f-2f16afd05b3e.%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202024-06-16%20085253.png", dto1.getThumbnail());

        ListAdoptPostDTO dto2 = result.getContent().get(1);
        assertEquals("진돌이", dto2.getName());
        assertEquals("믹스", dto2.getBreed());
        assertEquals("2살 미만", dto2.getAge());
        assertEquals("6개월", dto2.getDuration());
        assertEquals("https://hadog.s3.ap-northeast-2.amazonaws.com/adoptPost/51afe36f-4f4a-4d05-b4a3-aa3d66318760.%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202024-06-16%20085253.png", dto2.getThumbnail());

    }

    @Test
    void modifyAdoptStatus() {
    }
}
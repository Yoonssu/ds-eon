package com.aeon.hadog.service;

import com.aeon.hadog.base.dto.comment.CommentDTO;
import com.aeon.hadog.repository.ShelterPostCommentRepository;
import com.aeon.hadog.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ShelterPostCommentServiceTest {

    @Autowired
    ShelterPostCommentService shelterPostCommentService;

    @Autowired
    ShelterPostCommentRepository shelterPostCommentRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("게시글에 대한 댓글 조회")
    void getCommentByShelterPostId() {

        // given
        String userId = "mk020";
        CommentDTO commentDTO = CommentDTO.builder()
                .userId(userId)
                .postId(1L)
                .commentId(1L)
                .createdDate(LocalDateTime.now())
                .content("부모댓글")
                .build();

        // when
        List<CommentDTO> comments = shelterPostCommentService.getCommentByShelterPostId(commentDTO.getPostId());

        // then
        assertNotNull(comments);
        assertEquals(2, comments.size());
        assertEquals(1L, comments.get(0).getCommentId());
        assertEquals("부모댓글", comments.get(0).getContent());
    }

    @Test
    @DisplayName("댓글 작성 테스트")
    void addComment() {

        // given
        String userId = "mk020";
        CommentDTO commentDTO = CommentDTO.builder()
                .userId(userId)
                .postId(1L)
                .commentId(1L)
                .createdDate(LocalDateTime.now())
                .content("부모댓글")
                .build();

        // when
        CommentDTO comment = shelterPostCommentService.addComment(commentDTO.getPostId(), commentDTO.getUserId(), commentDTO.getContent(), commentDTO.getParentCommentId());

        // then
        assertNotNull(comment);
        assertEquals(1L, commentDTO.getPostId());
        assertEquals(userId, commentDTO.getUserId());
        assertEquals("부모댓글", commentDTO.getContent());
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    void deleteComment() {

        // given
        String userId = "mk020";
        CommentDTO commentDTO = CommentDTO.builder()
                .userId(userId)
                .postId(1L)
                .commentId(1L)
                .createdDate(LocalDateTime.now())
                .content("부모댓글")
                .build();

        // when
        Long commentId = shelterPostCommentService.deleteComment(commentDTO.getCommentId(), commentDTO.getUserId());

        // then
        assertNotNull(commentId);
        assertEquals(commentDTO.getCommentId(), commentId);
    }
}
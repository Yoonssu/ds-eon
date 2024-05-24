package com.aeon.hadog.service;

import com.aeon.hadog.base.code.ErrorCode;
import com.aeon.hadog.base.exception.CommentNotFoundException;
import com.aeon.hadog.base.exception.UserNotFoundException;
import com.aeon.hadog.domain.ShelterPostComment;
import com.aeon.hadog.repository.ShelterPostCommentRepository;
import com.aeon.hadog.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShelterPostCommentService {

    private final ShelterPostCommentRepository shelterPostCmtRepository;
    private final UserService userService;

    // 게시글에 대한 댓글 조회
    public List<ShelterPostComment> getCommentByShelterPostId(Long shelterPost) {
        return shelterPostCmtRepository.findByShelterPostId(shelterPost);
    }

    // 댓글 작성
    @Transactional
    public Long addComment(Long shelterPostId, String userId, String content) {

        ShelterPostComment comment = ShelterPostComment.builder()
                .shelterPostId(shelterPostId)
                .userId(userId)
                .content(content)
                .createdDate(LocalDateTime.now())
                .build();

        shelterPostCmtRepository.save(comment);

        return comment.getShelterPostCommentId();
    }

    // 댓글 삭제
    @Transactional
    public Long deleteComment(Long commentId, String userId) {


        if (shelterPostCmtRepository.existsById(commentId)) {

            ShelterPostComment comment = shelterPostCmtRepository.findByShelterPostCommentId(commentId);

            if (comment.getUserId().equals(userId)) {

                shelterPostCmtRepository.deleteById(commentId);
                return commentId;
            } else {
                throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
            }

        } else {
            throw new CommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND);
        }
    }
}

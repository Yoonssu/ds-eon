package com.aeon.hadog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="shelter_post_comment")
public class ShelterPostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shelterPostCommentId; // 댓글 아이디

    @Column(nullable=false)
    private Long shelterPostId; // 게시글 아이디

    @Column(nullable=false)
    private String userId; // 유저 string 아이디

    @Column(nullable=false, columnDefinition = "TEXT")
    private String content; // 댓글 내용

    @Column(nullable=false)
    @CreatedDate
    private LocalDateTime createdDate; // 댓글 작성 날짜

//    @ManyToOne
//    @JoinColumn(name = "parent_id")
//    private ShelterPostComment parent;

}

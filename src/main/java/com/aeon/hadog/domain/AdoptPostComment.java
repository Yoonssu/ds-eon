package com.aeon.hadog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="adopt_post_comment")
public class AdoptPostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adoptPostCommentId;

    @Column(nullable=false)
    private Long adoptPostId;

    @Column(nullable=false)
    private String userId;

    @Column(nullable=false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable=false)
    @CreatedDate
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private AdoptPostComment parentComment;

    @OneToMany(mappedBy = "parentComment", orphanRemoval = true)
    private List<AdoptPostComment> replies;

}

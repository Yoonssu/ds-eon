package com.aeon.hadog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="adopt_post")
public class AdoptPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adoptPostId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable=false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String breed;

    @Column(nullable=false)
    private String age;

    @Column(nullable=false)
    private String sex;

    @Column(nullable=false)
    private boolean neutering;

    @Column(nullable=false)
    private String duration;

    @Column(nullable=false)
    private String phone;

    @Column(nullable=false)
    private boolean adoptStatus;

}

package com.aeon.hadog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="shelter_post")
public class ShelterPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shelterPostId;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable=false)
    private LocalDateTime startDate;

    @Column(nullable=false)
    private LocalDateTime endDate;

    @Column(nullable=false)
    private String sex;

    @Column(nullable=false)
    private String color;

    @Column(nullable=false)
    private String age;

    @Column(nullable=false)
    private Float weight;

    @Column(nullable=false)
    private String adoptNum;

    @Column(nullable=false)
    private String findLocation;

    @Column(nullable=false)
    private String center;

    @Column(nullable=false)
    private String centerPhone;

    @Column(nullable=false)
    private String department;

    @Column(nullable=false)
    private String departmentPhone;



}

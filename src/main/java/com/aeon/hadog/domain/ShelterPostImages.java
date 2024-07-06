package com.aeon.hadog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="shelter_post_images")
public class ShelterPostImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shelterPostImagesId;

    @Column(nullable=false, length = 200)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "shelter_post_id", nullable = false)
    ShelterPost shelterPost;
}

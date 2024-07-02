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
@Table(name="adopt_post_images")
public class AdoptPostImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adoptPostImagesId;

    @Column(nullable=false, length = 200)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "adopt_post_id", nullable = false)
    AdoptPost adoptPost;
}

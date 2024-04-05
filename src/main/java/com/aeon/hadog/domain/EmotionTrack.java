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
@Table(name="emotion_track")
public class EmotionTrack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emotionTrackId;

    @Column(nullable=false)
    private Long petId;

    @Column(nullable=false)
    private LocalDateTime emotionDate;

    @Column(nullable=false)
    private Long emotionId;
}

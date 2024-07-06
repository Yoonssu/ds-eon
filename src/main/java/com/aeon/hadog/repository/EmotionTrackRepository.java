package com.aeon.hadog.repository;

import com.aeon.hadog.domain.EmotionTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmotionTrackRepository extends JpaRepository<EmotionTrack, Long> {
    Optional<EmotionTrack> findByEmotionTrackId(Long Id);
}
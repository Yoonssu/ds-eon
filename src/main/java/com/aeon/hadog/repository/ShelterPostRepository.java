package com.aeon.hadog.repository;

import com.aeon.hadog.domain.ShelterPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShelterPostRepository extends JpaRepository<ShelterPost, Long> {
    Optional<ShelterPost> findByShelterPostId(Long id);

    Page<ShelterPost> findAll(Pageable pageable);
}

package com.aeon.hadog.repository;

import com.aeon.hadog.domain.AdoptPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdoptPostRepository extends JpaRepository<AdoptPost, Long> {
    Optional<AdoptPost> findByAdoptPostId(Long id);
    Page<AdoptPost> findAll(Pageable pageable);

}

package com.aeon.hadog.repository;

import com.aeon.hadog.domain.ShelterPost;
import com.aeon.hadog.domain.ShelterPostImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShelterPostImagesRepository extends JpaRepository<ShelterPostImages, Long> {
    List<ShelterPostImages> findByShelterPost(ShelterPost post);
    ShelterPostImages findFirstByShelterPostOrderByShelterPostImagesIdAsc(ShelterPost shelterPost);
}

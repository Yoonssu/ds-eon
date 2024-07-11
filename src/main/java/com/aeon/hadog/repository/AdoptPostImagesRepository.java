package com.aeon.hadog.repository;

import com.aeon.hadog.domain.AdoptPost;
import com.aeon.hadog.domain.AdoptPostImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdoptPostImagesRepository extends JpaRepository<AdoptPostImages, Long> {
    List<AdoptPostImages> findByAdoptPost(AdoptPost post);

    AdoptPostImages findFirstByAdoptPostOrderByAdoptPostImagesIdAsc(AdoptPost post);


}

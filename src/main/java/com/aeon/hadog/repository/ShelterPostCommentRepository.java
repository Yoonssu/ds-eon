package com.aeon.hadog.repository;

import com.aeon.hadog.domain.ShelterPost;
import com.aeon.hadog.domain.ShelterPostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShelterPostCommentRepository extends JpaRepository<ShelterPostComment, Long> {

    List<ShelterPostComment> findByShelterPostId(Long shelterPostId);

    ShelterPostComment findByShelterPostCommentId(Long shelterPostCmtId);

}

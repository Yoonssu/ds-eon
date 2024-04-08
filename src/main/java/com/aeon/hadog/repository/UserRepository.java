package com.aeon.hadog.repository;

import com.aeon.hadog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

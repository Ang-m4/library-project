package com.project.library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.project.library.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT * FROM users u WHERE u.user_nickname = ?1", nativeQuery = true)
    Optional<User> findByNickname(String nickname);

}

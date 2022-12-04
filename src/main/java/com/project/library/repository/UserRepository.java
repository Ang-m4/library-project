package com.project.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.library.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}

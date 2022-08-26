package com.project.library.Db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.library.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}

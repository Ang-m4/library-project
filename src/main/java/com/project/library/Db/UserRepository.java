package com.project.library.Db;

import org.springframework.data.repository.CrudRepository;
import com.project.library.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}

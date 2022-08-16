package com.project.library.interfaces;

import org.springframework.data.repository.CrudRepository;
import com.project.library.model.User;

public interface IUser extends CrudRepository<User, Long> {

}

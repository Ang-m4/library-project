package com.project.library.service;

import com.project.library.interfaceService.IUserService;
import com.project.library.interfaces.IUser;
import com.project.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class UserService implements IUserService {
    @Autowired
    private IUser dataUser;
    @Override
    public List<User> listUser() {
        return (List<User>)dataUser.findAll();
    }

    @Override
    public Optional<User> searchIdUser(long id) {
        return null;
    }

    @Override
    public int saveUser(User u) {
        return 0;
    }

    @Override
    public void deleteUser(long id) {
    }
}


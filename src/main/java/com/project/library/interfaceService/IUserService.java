package com.project.library.interfaceService;

import com.project.library.model.User;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    public List<User>listUser();
    public Optional<User> searchIdUser(long id);
    public int saveUser(User user);
    public void deleteUser(long id);
}

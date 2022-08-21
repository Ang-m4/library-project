package com.project.library.Db;

import com.project.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = new User(1, "John", "Doe", "1234321");
        User user2 = new User(2, "Jane", "Doe", "12321");
        userRepository.save(user);
        userRepository.save(user2);
    }
}

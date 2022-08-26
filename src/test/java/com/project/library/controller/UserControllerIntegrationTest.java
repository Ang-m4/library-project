package com.project.library.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.project.library.Db.UserRepository;
import com.project.library.model.User;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate rest;

    @BeforeEach
    public void init() {
        
        User user = new User(1l,"Bob","Doe","1234567");
        User user1 = new User(2l,"Mary","Doe","1234567");
        User user2 = new User(3l,"Cecille","Doe","1234567");

        userRepository.saveAll(List.of(user,user1,user2));
    }

    @AfterEach
    public void clean() {

        userRepository.deleteAll();
    }

    @Test
    void testAddUser() {

        User user = new User(4l,"Dani","Doe","1234567");
        rest.postForEntity("http://localhost:" + port + "/user/add", user, User.class);
        assertEquals(4, userRepository.count());

    }

    @Test
    void testDeleteUser() {

        rest.delete("http://localhost:" + port + "/user/1/delete");
        assertEquals(2, userRepository.count());

    }

    @Test
    void testGet() {

        User user = rest.getForObject("http://localhost:" + port + "/user/1", User.class);
        assertEquals("Bob", user.getName());

    }

    @Test
    void testList() {

        User[] users = rest.getForObject("http://localhost:" + port + "/user/list", User[].class);
        assertEquals(3, users.length);

    }

    @Test
    void testUpdateUser() {
        User user = new User(1l,"Bob second","Doe","1234567");
        rest.put("http://localhost:" + port + "/user/1/update", user);
        assertEquals("Bob second", userRepository.findById(1l).get().getName());

    }
}

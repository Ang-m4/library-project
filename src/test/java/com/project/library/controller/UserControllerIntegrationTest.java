package com.project.library.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

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

import com.project.library.model.User;
import com.project.library.repository.UserRepository;

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

    	User reader = new User(1, "John reader", "Doe", "1234321", false, new ArrayList<>(), new ArrayList<>(),"Jhonie","ROLE_READER");
        User reader2 = new User(2, "Jane reader", "Doe", "12321", false, new ArrayList<>(), new ArrayList<>(),"Jenny","ROLE_READER");

        userRepository.save(reader);
        userRepository.save(reader2);
    }
    
    @AfterEach
    public void clean() {
    	userRepository.deleteAll();
    }

    @Test
    void testAddReader() {
    
        User reader = new User(3, "Dani reader", "Doe", "1234321", false, new ArrayList<>(), new ArrayList<>(),"Dani","ROLE_READER");
    	rest.postForEntity("http://localhost:" + port + "/user/add", reader, User.class);
    	assertEquals(3, userRepository.count());

    }

    @Test
    void testDeleteReader() {
        rest.delete("http://localhost:" + port + "/user/1/delete");
    	assertEquals(1, userRepository.count());
    }

    @Test
    void testGet() {
        User reader = rest.getForObject("http://localhost:" + port + "/user/1", User.class);
        assertEquals(1, reader.getId());

        User reader2 = rest.getForObject("http://localhost:" + port + "/user/2", User.class);
        assertEquals(2, reader2.getId());
        
    }

    @Test
    void testList() {
        User[] readers = rest.getForObject("http://localhost:" + port + "/user/list", User[].class);
        assertEquals(2, readers.length);
    }

    @Test
    void testUpdateReader() {
        User reader = new User(1, "John not reader", "Doe", "1234321", false, new ArrayList<>(), new ArrayList<>(), "Jhonie", "ROLE_READER");
        rest.put("http://localhost:" + port + "/user/1/update", reader);
        assertEquals("John not reader", userRepository.findById(1l).get().getName());
    }
    
}

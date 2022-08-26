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

import com.project.library.Db.ReaderRepository;
import com.project.library.model.Reader;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ReaderControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ReaderRepository readerRepository;
    
    @Autowired
    private TestRestTemplate rest;

    @BeforeEach
    public void init() {

    	Reader reader = new Reader(1, "John reader", "Doe", "1234321", false, new ArrayList<>(), new ArrayList<>());
    	Reader reader2 = new Reader(2, "Jane reader", "Doe", "12321", false, new ArrayList<>(), new ArrayList<>());
    	readerRepository.save(reader);  
    	readerRepository.save(reader2);
    }
    
    @AfterEach
    public void clean() {
    	readerRepository.deleteAll();
    }

    @Test
    void testAddReader() {
    
        Reader reader = new Reader(3, "Dani reader", "Doe", "1234321", false, new ArrayList<>(), new ArrayList<>());
    	rest.postForEntity("http://localhost:" + port + "/reader/add", reader, Reader.class);
    	assertEquals(3, readerRepository.count());

    }

    @Test
    void testDeleteReader() {
        rest.delete("http://localhost:" + port + "/reader/1/delete");
    	assertEquals(1, readerRepository.count());
    }

    @Test
    void testGet() {
        Reader reader = rest.getForObject("http://localhost:" + port + "/reader/1", Reader.class);
        assertEquals(1, reader.getId());

        Reader reader2 = rest.getForObject("http://localhost:" + port + "/reader/2", Reader.class);
        assertEquals(2, reader2.getId());
        
    }

    @Test
    void testList() {
        Reader[] readers = rest.getForObject("http://localhost:" + port + "/reader/list", Reader[].class);
        assertEquals(2, readers.length);
    }

    @Test
    void testUpdateReader() {
        Reader reader = new Reader(1, "John not reader", "Doe", "1234321", false, new ArrayList<>(), new ArrayList<>());
        rest.put("http://localhost:" + port + "/reader/1/update", reader);
        assertEquals("John not reader", readerRepository.findById(1l).get().getName());
    }
    
}

package com.project.library.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.library.Db.ReaderRepository;
import com.project.library.model.Reader;

@RestController
@RequestMapping("/reader")
public class ReaderController {
    
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReaderRepository readerRepository;

    @GetMapping("/list")
    public List<Reader> list() {
        List<Reader> readers = (List<Reader>) readerRepository.findAll();

        if (readers.size() == 0) {
            logger.error("No readers found in the database");
        } else {
            logger.info("Getting readers from database");
        }
        return readers;
    }

    @GetMapping("/{id}")
    public Reader get(@PathVariable Long id) {

        Reader reader = null;
        if(readerRepository.findById(id).isPresent()){
            logger.info("Getting reader with id {}", id);
            reader = readerRepository.findById(id).get();
        }
        else{
            logger.error("Reader with id {} not found", id);
        }
        return reader;
    }

    @PostMapping("/add")
    public Reader addReader(@RequestBody Reader reader) {

        Reader newReader = readerRepository.save(reader);
        logger.info("Saving reader {} to the database", newReader.getId());

        return newReader;
    }

    @PutMapping("/{id}/update")
    public Reader updateReader(@PathVariable Long id, @RequestBody Reader reader) {
        
        reader.setId(id);
        Reader updatedReader = readerRepository.save(reader);
        logger.info("Updating reader {} , and sending to the database", updatedReader.getId());

        return updatedReader;
    }

    @DeleteMapping("/{id}/delete")
    public void deleteReader(@PathVariable Long id) {

        if(readerRepository.findById(id).isPresent()){
            logger.info("Deleting reader with id {}", id);
            readerRepository.deleteById(id);
        }
        else{
            logger.error("Reader with id {} not found", id);
        }
    }
}

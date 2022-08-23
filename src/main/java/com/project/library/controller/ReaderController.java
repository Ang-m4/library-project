package com.project.library.controller;

import java.util.List;

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
    
    @Autowired
    private ReaderRepository readerRepository;

    @GetMapping("/list")
    public List<Reader> list() {
        List<Reader> readers = (List<Reader>) readerRepository.findAll();
        return readers;
    }

    @GetMapping("/{id}")
    public Reader get(@PathVariable Long id) {
        Reader reader = readerRepository.findById(id).get();
        return reader;
    }

    @PostMapping("/add")
    public Reader addReader(@RequestBody Reader reader) {
        return readerRepository.save(reader);
    }

    @PutMapping("/{id}/update")
    public Reader updateReader(@PathVariable Long id, @RequestBody Reader reader) {
        
        reader.setId(id);
        return readerRepository.save(reader);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteReader(@PathVariable Long id) {
        readerRepository.deleteById(id);
    }
    
}

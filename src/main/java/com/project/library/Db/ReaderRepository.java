package com.project.library.Db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.library.model.Reader;

@Repository
public interface ReaderRepository extends CrudRepository<Reader, Long> {

}
    


package com.project.library.controller;

import java.util.List;

import com.project.library.model.Book;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get the list of readers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Readers listed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reader.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Readers not found",
                    content = @Content) })
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

    @Operation(summary = "Get a reader by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reader found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reader.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Reader not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public Reader get(@Parameter(description = "id of reader to be searched") @PathVariable Long id) {

        Reader reader = null;
        if (readerRepository.findById(id).isPresent()) {
            logger.info("Getting reader with id {}", id);
            reader = readerRepository.findById(id).get();
        } else {
            logger.error("Reader with id {} not found", id);
        }
        return reader;
    }

    @Operation(summary = "Add a new reader")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reader added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reader.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Reader not added",
                    content = @Content) })
    @PostMapping("/add")
    public Reader addReader(@RequestBody Reader reader) {

        Reader newReader = readerRepository.save(reader);
        logger.info("Saving reader {} to the database", newReader.getId());

        return newReader;
    }

    @Operation(summary = "Update a reader by its")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reader updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reader.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Reader not found",
                    content = @Content) })
    @PutMapping("/{id}/update")
    public Reader updateReader(@Parameter(description = "id of reader to be updated") @PathVariable Long id, @RequestBody Reader reader) {

        reader.setId(id);
        Reader updatedReader = readerRepository.save(reader);
        logger.info("Updating reader {} , and sending to the database", updatedReader.getId());

        return updatedReader;
    }

    @Operation(summary = "Delete a reader by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reader deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reader.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Reader not found",
                    content = @Content) })
    @DeleteMapping("/{id}/delete")
    public void deleteReader(@Parameter(description = "id of reader to be deleted") @PathVariable Long id) {

        if (readerRepository.findById(id).isPresent()) {
            logger.info("Deleting reader with id {}", id);
            readerRepository.deleteById(id);
        } else {
            logger.error("Reader with id {} not found", id);
        }
    }
}

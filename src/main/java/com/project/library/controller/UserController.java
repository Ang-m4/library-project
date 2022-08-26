package com.project.library.controller;

import com.project.library.Db.UserRepository;
import com.project.library.model.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;
    @Operation(summary = "Get the list of user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users listed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Users not found",
                    content = @Content) })
    @GetMapping("/list")
    public List<User> list() {
        List<User> users = (List<User>) userRepository.findAll();

        if (users.size() == 0) {
            logger.error("No users found in the database");
        } else {
            logger.info("Getting users from database");
        }
        return users;
    }

    @Operation(summary = "Get a user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public User get(@Parameter(description = "id of user to be added") @PathVariable long id) {

        User user = null;
        
        if (userRepository.findById(id).isPresent()) {
            logger.info("Getting user with id {}", id);
            user = userRepository.findById(id).get();
        } else {
            logger.error("User with id {} not found", id);
        }
        return user;
    }

    @Operation(summary = "Add a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not added",
                    content = @Content) })
    @PostMapping("/add")
    public User addUser(@RequestBody User user) {

        User newUser = userRepository.save(user);
        logger.info("Saving user {} to the database", newUser.getId());
        
        return newUser;
    }

    @Operation(summary = "Update a user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @PutMapping("/{id}/update")
    public User updateUser(@Parameter(description = "id of user to be updated") @PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = userRepository.save(user);
        logger.info("Updating user {} , and sending to the database", id);

        return updatedUser;
    }

    @Operation(summary = "Delete a user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @DeleteMapping("/{id}/delete")
    public void deleteUser(@Parameter(description = "id of user to be deleted")@PathVariable Long id) {

        if (userRepository.findById(id).isPresent()) {
            logger.info("Deleting user with id {}", id);
            userRepository.deleteById(id);
        } else {
            logger.error("User with id {} not found", id);
        }
    }
}

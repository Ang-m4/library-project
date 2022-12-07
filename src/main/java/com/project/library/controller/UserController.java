package com.project.library.controller;

import java.util.List;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.library.model.User;
import com.project.library.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private UserService UserService;

    @Operation(summary = "Gets the list of users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns a list of users filtered by a parameter", content = { @Content(mediaType = "application/json",schema = @Schema(implementation = User.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad request",content = @Content),
        @ApiResponse(responseCode = "404", description = "Users not found",content = @Content) })
    @GetMapping("/list")
    public ResponseEntity<List<User>> list(
        @RequestParam(value = "filter", required = false, defaultValue = "") String filter
    ) {
        List<User> users = (List<User>) UserService.getAllUsers(filter);
        return ResponseEntity.ok().body(users);
    }

    @Operation(summary = "Get an user by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<User> get(@Parameter(description = "Id of the user to be found") @PathVariable Long id) {
        User user = UserService.findUserById(id);
        return ResponseEntity.ok().body(user);
    }

    @Operation(summary = "Add a new User received as a parameter")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created", content = { @Content(mediaType = "application/json",schema = @Schema(implementation = User.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(responseCode = "404", description = "User not added", content = @Content) })
    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody @Valid User user) {
        User newUser = UserService.saveUser(user);
        logger.info("Added new user with id: {}" + newUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @Operation(summary = "Update an user received as a parameter")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content) })
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody  @Valid User user) {
        User updatedUser = UserService.updateUser(user);
        logger.info("Updated user with id: {}" + updatedUser.getId());
        return ResponseEntity.ok().body(updatedUser);
    }

    @Operation(summary = "Delete a user by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User deleted", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(responseCode = "404", description = "user not found", content = @Content) })
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteUser(@Parameter(description = "id of user to be deleted") @PathVariable Long id) {
        UserService.deleteUser(id);
        logger.info("Deleted user with id: {}" + id);
        return ResponseEntity.ok().body("User deleted successfully");
    }
}

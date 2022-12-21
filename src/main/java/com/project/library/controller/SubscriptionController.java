/*
    @author Andres Malagon
    @GitHub Ang-m4
*/

package com.project.library.controller;

import com.project.library.service.SubscriptionService;
import com.project.library.error.ErrorDTO;
import com.project.library.model.Subscription;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SubscriptionService subscriptionService;

    @Operation(summary = "Get the subscriptions list filtered by user nickname, book title and sort order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns a list of the subscriptions acording to the given parameters", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Subscription.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))})
    @GetMapping("/list")
    public ResponseEntity<List<Subscription>> getList(
        @RequestParam(value = "nickname", required = false, defaultValue = "") String nickname,
        @RequestParam(value = "title", required = false, defaultValue = "") String title,
        @RequestParam(value = "limit", required = false, defaultValue = "100") Integer limit
    ) {
        List<Subscription> list = subscriptionService.getAllSubscriptions(nickname, title, limit);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get a subscription by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Subscription found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Subscription.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
        @ApiResponse(responseCode = "404", description = "Subscription not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) })
    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getSubscription(@Parameter(description = "id of subscription to be searched") @PathVariable long id) {
        Subscription subscription = subscriptionService.getSubscriptionById(id);
        return ResponseEntity.ok(subscription);
    }

    @Operation(summary = "Add a new subscription")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Subscription added", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Subscription.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
        @ApiResponse(responseCode = "404", description = "Order with given id not valid", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) })
    @PostMapping("/add")
    public ResponseEntity<Subscription> addSubscription(
        @Parameter(description = "id of the order to be upgraded as a subscription") 
            @RequestParam Long idOrder, 
        @Parameter(description = "duration(in days) for the subscription to be set") 
            @RequestParam Long duration) {
        Subscription newSubscription = subscriptionService.addSubscription(idOrder, duration);
        logger.info("Updating order: {}, to subscription {}", idOrder, newSubscription.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(newSubscription);
    }

    @Operation(summary = "Update a subscription by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription updated", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Subscription.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Subscription not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) })
    @PutMapping("/{id}/update")
    public ResponseEntity<Subscription> updateSubscription(
        @Parameter(description = "id of subscription to be updated") 
            @PathVariable Long id,
        @Parameter(description = "days to be added in the duration´s subscription") 
            @RequestParam Long plus) {
        Subscription subscription = subscriptionService.updateSubscription(id, plus);
        logger.info("Updating subscription: {}, {} days added", subscription.getId(), plus);
        return ResponseEntity.ok(subscription);
    }

    @Operation(summary = "Delete a subscription by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription deleted", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Subscription not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) })
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteSubscription(@Parameter(description = "id of subscription to be deleted") @PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        logger.info("Subscription with id {} deleted", id);
        return ResponseEntity.ok("Subscription with id " + id + " deleted");
    }
}

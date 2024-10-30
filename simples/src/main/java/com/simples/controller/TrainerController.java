package com.simples.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simples.model.Trainer;
import com.simples.service.TrainerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * REST controller for managing User entities.
 * Handles HTTP requests and routes them to the appropriate service methods.
 */
@RestController // Marks this class as a RESTful controller.
@RequestMapping("/api/trainers")
public class TrainerController {

        @Autowired
    private TrainerService trainerService;

    /**
     * Handles POST requests to save a new trainer.
     * 
     * @param trainer the trainer entity to be saved
     * @return the saved trainer entity
     */
    @Operation(summary = "Create a new trainer", description = "Saves a new trainer entity to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trainer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public Trainer saveTrainer(@Valid @RequestBody Trainer trainer) {
        return trainerService.addTrainer(trainer);
    }

    /**
     * Handles GET requests to fetch the list of all trainers.
     * 
     * @return a list of trainer entities
     */
    @Operation(summary = "Get all trainers", description = "Fetches a list of all trainers in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of trainers"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<Trainer> fetchTrainerList() {
        return trainerService.getTrainerList();
    }

    /**
     * Handles GET requests to fetch the list of all trainers.
     * 
     * @return a list of trainer entities
     */
    @Operation(summary = "Get a trainer by ID", description = "Fetches a trainer entity by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the trainer"),
            @ApiResponse(responseCode = "404", description = "Trainer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public Trainer getTrainer(
            @Parameter(description = "ID of the trainer to be retrieved") @PathVariable("id") Long trainerId) {
        return trainerService.findTrainerById(trainerId);
    }

    /**
     * Handles PUT requests to update an existing trainer.
     * 
     * @param trainer   the trainer entity with updated information
     * @param trainerId the ID of the trainer to be updated
     * @return the updated trainer entity
     */
    @Operation(summary = "Update an existing trainer", description = "Updates a trainer entity identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Trainer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public Trainer updateTrainer(
            @Parameter(description = "Updated trainer data") @RequestBody Trainer trainer,
            @Parameter(description = "ID of the trainer to be updated") @PathVariable("id") Long trainerId) {
        return trainerService.updateTrainer(trainer, trainerId);
    }

    /**
     * Handles DELETE requests to remove a trainer by ID.
     * 
     * @param trainerId the ID of the trainer to be deleted
     * @return a success message
     */
    @Operation(summary = "Delete a trainer by ID", description = "Deletes a trainer entity identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Trainer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public String deleteTrainerById(@Parameter(description = "ID of the trainer to be deleted") @PathVariable("id") Long trainerId) {
        trainerService.deleteTrainerById(trainerId);
        return "Deleted Successfully";
    }

}

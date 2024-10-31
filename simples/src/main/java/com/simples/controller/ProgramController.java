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

import com.simples.dto.ProgramDTO;
import com.simples.exceptions.InvalidDataException;
import com.simples.exceptions.ResourceNotFoundException;
import com.simples.model.Program;
import com.simples.service.ProgramService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * REST controller for managing Program entities.
 * Handles HTTP requests and routes them to the appropriate service methods.
 */
@RestController // Marks this class as a RESTful controller.
@RequestMapping("/api/programs")
public class ProgramController {

        @Autowired
    private ProgramService programService;

    /**
     * Handles POST requests to save a new program.
     * 
     * @param program the program entity to be saved
     * @return the saved program entity
     */
    @Operation(summary = "Create a new program", description = "Saves a new program entity to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Program created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public Program saveProgram(@Valid @RequestBody Program program) {
        return programService.addProgram(program);
    }

    /**
     * Handles GET requests to fetch the list of all programs.
     * 
     * @return a list of program entities
     */
    @Operation(summary = "Get all programs", description = "Fetches a list of all programs in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of programs"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<ProgramDTO> fetchProgramList() throws InvalidDataException {
        return programService.getProgramList();
    }

    /**
     * Handles GET requests to fetch the list of all programs.
     * 
     * @return a list of program entities
     */
    @Operation(summary = "Get a program by ID", description = "Fetches a program entity by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the program"),
            @ApiResponse(responseCode = "404", description = "Program not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ProgramDTO getProgram(
            @Parameter(description = "ID of the program to be retrieved") @PathVariable("id") Long programId) throws ResourceNotFoundException, InvalidDataException {
        return programService.findProgramById(programId, "classrooms");
    }

    /**
     * Handles PUT requests to update an existing program.
     * 
     * @param program   the program entity with updated information
     * @param programId the ID of the program to be updated
     * @return the updated program entity
     */
    @Operation(summary = "Update an existing program", description = "Updates a program entity identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Program updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Program not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public Program updateProgram(
            @Parameter(description = "Updated program data") @RequestBody Program program,
            @Parameter(description = "ID of the program to be updated") @PathVariable("id") Long programId) throws ResourceNotFoundException {
        return programService.updateProgram(program, programId);
    }

    /**
     * Handles DELETE requests to remove a program by ID.
     * 
     * @param programId the ID of the program to be deleted
     * @return a success message
     */
    @Operation(summary = "Delete a program by ID", description = "Deletes a program entity identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Program deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Program not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public String deleteProgramById(@Parameter(description = "ID of the program to be deleted") @PathVariable("id") Long programId) throws ResourceNotFoundException {
        programService.deleteProgramById(programId);
        return "Deleted Successfully";
    }

}

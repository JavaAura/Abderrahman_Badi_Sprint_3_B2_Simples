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

import com.simples.dto.ClassroomDTO;
import com.simples.exceptions.InvalidDataException;
import com.simples.exceptions.ResourceNotFoundException;
import com.simples.model.Classroom;
import com.simples.service.ClassroomService;
import com.simples.service.ProgramService;
import com.simples.service.TrainerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * REST controller for managing Classroom entities.
 * Handles HTTP requests and routes them to the appropriate service methods.
 */
@RestController // Marks this class as a RESTful controller.
@RequestMapping("/api/classrooms")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private ProgramService programService;

    /**
     * Handles POST requests to save a new classroom.
     * 
     * @param classroom the classroom entity to be saved
     * @return the saved classroom entity
     */
    @Operation(summary = "Create a new classroom", description = "Saves a new classroom entity to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Classroom created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ClassroomDTO saveClassroom(@Valid @RequestBody Classroom classroom) {
        return classroomService.addClassroom(classroom);
    }

    /**
     * Handles GET requests to fetch the list of all classrooms.
     * 
     * @return a list of classroom entities
     */
    @Operation(summary = "Get all classrooms", description = "Fetches a list of all classrooms in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of classrooms"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<ClassroomDTO> fetchClassroomList() throws InvalidDataException {
        return classroomService.getClassroomList("program", "trainer");
    }

    /**
     * Handles GET requests to fetch the list of all classrooms.
     * 
     * @return a list of classroom entities
     */
    @Operation(summary = "Get a classroom by ID", description = "Fetches a classroom entity by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the classroom"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Classroom not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ClassroomDTO getClassroom(
            @Parameter(description = "ID of the classroom to be retrieved") @PathVariable("id") Long classroomId)
            throws ResourceNotFoundException, InvalidDataException {
        return classroomService.findClassroomById(classroomId, "students", "trainer", "program");
    }

    /**
     * Handles PUT requests to update an existing classroom.
     * 
     * @param classroom   the classroom entity with updated information
     * @param classroomId the ID of the classroom to be updated
     * @return the updated classroom entity
     */
    @Operation(summary = "Update an existing classroom", description = "Updates a classroom entity identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Classroom updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Classroom not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ClassroomDTO updateClassroom(
            @Parameter(description = "Updated classroom data") @RequestBody Classroom classroom,
            @Parameter(description = "ID of the classroom to be updated") @PathVariable("id") Long classroomId)
            throws ResourceNotFoundException, InvalidDataException {
        if (classroom.getProgram() != null) {
            programService.findProgramById(classroom.getProgram().getId());
        }
        if (classroom.getTrainer() != null) {
            trainerService.findTrainerById(classroom.getTrainer().getId());
        }
        return classroomService.updateClassroom(classroom, classroomId);
    }

    /**
     * Handles DELETE requests to remove a classroom by ID.
     * 
     * @param classroomId the ID of the classroom to be deleted
     * @return a success message
     */
    @Operation(summary = "Delete a classroom by ID", description = "Deletes a classroom entity identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Classroom deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Classroom not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public String deleteClassroomById(
            @Parameter(description = "ID of the classroom to be deleted") @PathVariable("id") Long classroomId)
            throws ResourceNotFoundException {
        classroomService.deleteClassroomById(classroomId);
        return "Deleted Successfully";
    }

}

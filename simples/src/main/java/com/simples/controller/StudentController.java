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

import com.simples.dto.StudentDTO;
import com.simples.exceptions.InvalidDataException;
import com.simples.exceptions.ResourceNotFoundException;
import com.simples.model.Student;
import com.simples.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * REST controller for managing User entities.
 * Handles HTTP requests and routes them to the appropriate service methods.
 */
@RestController // Marks this class as a RESTful controller.
@RequestMapping("/api/students")
public class StudentController {

        @Autowired
    private StudentService studentService;

    /**
     * Handles POST requests to save a new student.
     * 
     * @param student the student entity to be saved
     * @return the saved student entity
     */
    @Operation(summary = "Create a new student", description = "Saves a new student entity to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public StudentDTO saveStudent(@Valid @RequestBody Student student) {
        return studentService.addStudent(student);
    }

    /**
     * Handles GET requests to fetch the list of all students.
     * 
     * @return a list of student entities
     */
    @Operation(summary = "Get all students", description = "Fetches a list of all students in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of students"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<StudentDTO> fetchStudentList() throws InvalidDataException {
        return studentService.getStudentList();
    }

    /**
     * Handles GET requests to fetch the list of all students.
     * 
     * @return a list of student entities
     */
    @Operation(summary = "Get a student by ID", description = "Fetches a student entity by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the student"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Student not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public StudentDTO getStudent(
            @Parameter(description = "ID of the student to be retrieved") @PathVariable("id") Long studentId) throws ResourceNotFoundException, InvalidDataException {
        return studentService.findStudentById(studentId, "classroom");
    }

    /**
     * Handles PUT requests to update an existing student.
     * 
     * @param student   the student entity with updated information
     * @param studentId the ID of the student to be updated
     * @return the updated student entity
     */
    @Operation(summary = "Update an existing student", description = "Updates a student entity identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Student not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public StudentDTO updateStudent(
            @Parameter(description = "Updated student data") @RequestBody Student student,
            @Parameter(description = "ID of the student to be updated") @PathVariable("id") Long studentId) throws ResourceNotFoundException {
        return studentService.updateStudent(student, studentId);
    }

    /**
     * Handles DELETE requests to remove a student by ID.
     * 
     * @param studentId the ID of the student to be deleted
     * @return a success message
     */
    @Operation(summary = "Delete a student by ID", description = "Deletes a student entity identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Student not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public String deleteStudentById(@Parameter(description = "ID of the student to be deleted") @PathVariable("id") Long studentId) throws ResourceNotFoundException {
        studentService.deleteStudentById(studentId);
        return "Deleted Successfully";
    }

}

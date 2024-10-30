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
import org.springframework.web.bind.annotation.RestController;

import com.simples.model.Classroom;
import com.simples.service.ClassroomService;

/**
 * REST controller for managing Classroom entities.
 * Handles HTTP requests and routes them to the appropriate service methods.
 */
@RestController // Marks this class as a RESTful controller.
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    
    /**
     * Handles POST requests to save a new classroom.
     * @param classroom the classroom entity to be saved
     * @return the saved classroom entity
     */
    @PostMapping("/classrooms")
    public Classroom saveClassroom(@Valid @RequestBody Classroom classroom) {
        return classroomService.saveClassroom(classroom);
    }

    /**
     * Handles GET requests to fetch the list of all classrooms.
     * @return a list of classroom entities
     */
    @GetMapping("/classrooms")
    public List<Classroom> fetchClassroomList() {
        return classroomService.fetchClassroomList();
    }

    /**
     * Handles GET requests to fetch the list of all classrooms.
     * @return a list of classroom entities
     */
    @GetMapping("/classrooms/{id}")
    public Classroom getClassroom(@PathVariable("id") Long classroomId) {
        return classroomService.getClassroom(classroomId);
    }

    /**
     * Handles PUT requests to update an existing classroom.
     * @param classroom the classroom entity with updated information
     * @param classroomId the ID of the classroom to be updated
     * @return the updated classroom entity
     */
    @PutMapping("/classrooms/{id}")
    public Classroom updateClassroom(@RequestBody Classroom classroom, @PathVariable("id") Long classroomId) {
        return classroomService.updateClassroom(classroom, classroomId);
    }

    /**
     * Handles DELETE requests to remove a classroom by ID.
     * @param classroomId the ID of the classroom to be deleted
     * @return a success message
     */
    @DeleteMapping("/classrooms/{id}")
    public String deleteClassroomById(@PathVariable("id") Long classroomId) {
        classroomService.deleteClassroomById(classroomId);
        return "Deleted Successfully";
    }

}

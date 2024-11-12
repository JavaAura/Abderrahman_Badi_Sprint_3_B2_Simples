package com.simples.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simples.dto.AbsenceDTO;
import com.simples.exceptions.InvalidAbsenceException;
import com.simples.exceptions.InvalidDataException;
import com.simples.model.Absence;
import com.simples.service.AbsenceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;

/**
 * REST controller for managing User entities.
 * Handles HTTP requests and routes them to the appropriate service methods.
 */
@RestController // Marks this class as a RESTful controller.
@RequestMapping("/api/absences")
@Log4j2
public class AbsenceController {

        @Autowired
        private AbsenceService absenceService;

        /**
         * Handles POST requests to save a new absence.
         * 
         * @param absence the absence entity to be saved
         * @return the saved absence entity
         */
        @Operation(summary = "Create a new absence", description = "Saves a new absence entity to the system.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Absence created successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid input data")
        })
        @PostMapping
        public AbsenceDTO saveAbsence(@Valid @RequestBody Absence absence) throws InvalidAbsenceException {
                return absenceService.addAbsence(absence);
        }

        /**
         * Handles GET requests to fetch the list of all absences.
         * 
         * @return a list of absence entities
         */
        @Operation(summary = "Get all absences of a student", description = "Fetches a list of all absences of a student in the system.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of absences"),
                        @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        @GetMapping("/students/{id}")
        public List<AbsenceDTO> fetchAbsenceListByStudentId(
                        @Parameter(description = "ID of the student") @PathVariable("id") long id)
                        throws InvalidDataException {
                return absenceService.getAbsenceByStudentId(id);
        }

        /**
         * Handles GET requests to fetch the list of all absences.
         * 
         * @return a list of absence entities
         */
        @Operation(summary = "Get absent students in a date", description = "Fetches absent students in a specific date (day).")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found the absence"),
                        @ApiResponse(responseCode = "400", description = "Invalid input data"),
                        @ApiResponse(responseCode = "404", description = "Absence not found"),
                        @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        @GetMapping("/date/{date}")
        public List<AbsenceDTO> fetchAbsenceListByDate(
                        @Parameter(description = "absence date to be retrieved") @PathVariable("date") String date)
                        throws InvalidDataException {
                return absenceService.getAbsenceByDate(date);
        }

}

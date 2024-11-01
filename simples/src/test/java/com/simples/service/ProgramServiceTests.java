package com.simples.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.simples.dto.ProgramDTO;
import com.simples.exceptions.InvalidDataException;
import com.simples.exceptions.ResourceNotFoundException;
import com.simples.model.Program;
import com.simples.repository.ProgramRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@TestPropertySource(locations = "classpath:application-dev.properties")
@SpringBootTest
@Transactional
public class ProgramServiceTests {

    @Autowired
    private ProgramService programService;

    @Autowired
    private ProgramRepository programRepository;

    private Program program;

    @BeforeEach
    void setup() {
        program = new Program();
        program.setTitle("Java Bootcamp");
        program.setGrade("2");
        program.setMinCapacity(10);
        program.setMaxCapacity(50);
        program.setStarDate(LocalDate.of(2024, 10, 1));
        program.setEndDate(LocalDate.of(2025, 3, 1));
    }

    @Test
    public void testCreateProgram() {

        Program savedProgram = programRepository.save(program);
        assertNotNull(savedProgram.getId(), "Program ID should not be null after saving");
        assertEquals("Java Bootcamp", savedProgram.getTitle());
    }

    @Test
    public void testReadProgram() throws ResourceNotFoundException, InvalidDataException {

        Program savedProgram = programRepository.save(program);
        ProgramDTO foundProgram = programService.findProgramById(savedProgram.getId());
        assertTrue(foundProgram != null, "Program should be found by ID");
        assertEquals("Java Bootcamp", foundProgram.getTitle(), "Titles should match");
    }

    @Test
    public void testUpdateProgram() throws ResourceNotFoundException {

        Program savedProgram = programRepository.save(program);
        savedProgram.setTitle("Advanced Java Bootcamp");
        ProgramDTO updatedProgram = programService.updateProgram(savedProgram, savedProgram.getId());
        assertEquals("Advanced Java Bootcamp", updatedProgram.getTitle(), "Title should be updated");
    }

    @Test
    public void testSoftDeleteProgram() throws ResourceNotFoundException, InvalidDataException {

        Program savedProgram = programRepository.save(program);

        programService.deleteProgramById(savedProgram.getId());

        assertThrows(ResourceNotFoundException.class, () -> programService.findProgramById(savedProgram.getId()));
    }
}

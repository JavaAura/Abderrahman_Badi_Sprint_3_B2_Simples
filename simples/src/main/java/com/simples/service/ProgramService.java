package com.simples.service;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simples.exceptions.ResourceNotFoundException;
import com.simples.model.Program;
import com.simples.repository.ProgramRepository;

/**
 * Service interface for Program entity.
 * Defines methods for CRUD operations and additional business logic.
 */
@Service
public class ProgramService {

    @Autowired
    private ProgramRepository programRepository;

    public Program findProgramById(long id) throws ResourceNotFoundException {
        return programRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found with ID: " + id));
    }

    public Program addProgram(Program program) {
        return programRepository.save(program);
    }

    public List<Program> getProgramList() {

        return (List<Program>) programRepository.findAll();
    }

    public Program updateProgram(Program program, Long programId) throws ResourceNotFoundException {

        Program programDB = findProgramById(programId);

        // Updates fields if they are not null or empty.
        if (StringUtils.isNotBlank(program.getTitle())) {
            programDB.setTitle(program.getTitle());
        }
        if (StringUtils.isNotBlank(program.getGrade())) {
            programDB.setGrade(program.getGrade());
        }
        if (Objects.nonNull(program.getMinCapacity())) {
            programDB.setMinCapacity(program.getMinCapacity());
        }
        if (Objects.nonNull(program.getMaxCapacity())) {
            programDB.setMaxCapacity(program.getMaxCapacity());
        }
        if (Objects.nonNull(program.getStarDate())) {
            programDB.setStarDate(program.getStarDate());
        }
        if (Objects.nonNull(program.getEndDate())) {
            programDB.setEndDate(program.getEndDate());
        }
        if (Objects.nonNull(program.getProgramStatus())) {
            programDB.setProgramStatus(program.getProgramStatus());
        }

        return programRepository.save(programDB);
    }

    public void deleteProgramById(Long programId) throws ResourceNotFoundException {
        Program program = findProgramById(programId);
        programRepository.delete(program);
    }
}

package com.simples.service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.simples.dto.ProgramDTO;
import com.simples.dto.ClassroomDTO;
import com.simples.exceptions.InvalidDataException;
import com.simples.exceptions.ResourceNotFoundException;
import com.simples.model.Program;
import com.simples.model.Classroom;
import com.simples.repository.ProgramRepository;
import com.simples.specifications.ProgramSpecifications;

import lombok.extern.log4j.Log4j2;

/**
 * Service interface for Program entity.
 * Defines methods for CRUD operations and additional business logic.
 */
@Log4j2
@Service
public class ProgramService {
    private final List<String> VALID_INCLUDES = Arrays.asList("classrooms");

    @Autowired
    private ProgramRepository programRepository;

    public Program findProgramById(long id) throws ResourceNotFoundException {
        return programRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found with ID: " + id));
    }

    public Program addProgram(Program program) {
        return programRepository.save(program);
    }

    public List<ProgramDTO> getProgramList(String... with) throws InvalidDataException {
        List<String> includesList = Arrays.asList(with);

        for (String include : includesList) {
            if (!include.isEmpty() && !VALID_INCLUDES.contains(include)) {
                throw new InvalidDataException("Invalid include: " + include);
            }
        }
        Specification<Program> spec = Specification.where(null);

        if (includesList.contains("trainer")) {
            log.info("trainer spec");
            spec = spec.and(ProgramSpecifications.fetchClassrooms());
        }

        List<Program> programs = programRepository.findAll(spec);
        return convertToDTOList(programs, includesList);
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

    public ProgramDTO convertToDTO(Program program, List<String> includesList) {
        List<ClassroomDTO> classroomDTOs = null;

        if (includesList.contains("classrooms")) {
            List<Classroom> classrooms = program.getClassrooms();
            classroomDTOs = classrooms.stream().map(classroom -> ClassroomDTO.builder()
                    .id(classroom.getId())
                    .className(classroom.getClassName())
                    .classNumber(classroom.getClassNumber())
                    .build())
                    .collect(Collectors.toList());
        }

        return ProgramDTO.builder()
                .id(program.getId())
                .title(program.getTitle())
                .grade(program.getGrade())
                .minCapacity(program.getMinCapacity())
                .maxCapacity(program.getMaxCapacity())
                .starDate(program.getStarDate())
                .endDate(program.getEndDate())
                .classrooms(classroomDTOs)
                .build();
    }

    public List<ProgramDTO> convertToDTOList(List<Program> programs, List<String> includes) {
        return programs.stream()
                .map(program -> convertToDTO(program, includes))
                .collect(Collectors.toList());
    }
}

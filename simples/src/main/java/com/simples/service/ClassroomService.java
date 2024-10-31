package com.simples.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.simples.dto.ClassroomDTO;
import com.simples.dto.ProgramDTO;
import com.simples.dto.StudentDTO;
import com.simples.dto.TrainerDTO;
import com.simples.exceptions.InvalidDataException;
import com.simples.exceptions.ResourceNotFoundException;
import com.simples.model.Classroom;
import com.simples.model.Program;
import com.simples.model.Student;
import com.simples.model.Trainer;
import com.simples.repository.ClassroomRepository;
import com.simples.specifications.ClassroomSpecifications;

import lombok.extern.log4j.Log4j2;

/**
 * Service interface for Classroom entity.
 * Defines methods for CRUD operations and additional business logic.
 */
@Log4j2
@Service
public class ClassroomService {
    private final List<String> VALID_INCLUDES = Arrays.asList("trainer", "program", "students");

    @Autowired
    private ClassroomRepository classroomRepository;


    public ClassroomDTO findClassroomById(long id, String... with)
            throws ResourceNotFoundException, InvalidDataException {
        List<String> includesList = Arrays.asList(with);

        Specification<Classroom> spec = Specification.where(ClassroomSpecifications.hasId(id));

        spec = verifyIncludes(spec, includesList);

        Classroom classroom = classroomRepository.findOne(spec)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found with ID: " + id));
        return convertToDTO(classroom, includesList);
    }
    

    public ClassroomDTO addClassroom(Classroom classroom) {
        return convertToDTO(classroomRepository.save(classroom));
    }



    public List<ClassroomDTO> getClassroomList(String... with) throws InvalidDataException {

        List<String> includesList = Arrays.asList(with);

        Specification<Classroom> spec = Specification.where(null);

        spec = verifyIncludes(spec, includesList);

        List<Classroom> classrooms = classroomRepository.findAll(spec, Sort.by(Sort.Direction.ASC, "id"));
        return convertToDTOList(classrooms, includesList);

    }


    public ClassroomDTO updateClassroom(Classroom classroom, Long classroomId) throws ResourceNotFoundException {

        Classroom classroomDB = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found with ID: " + classroomId));

        if (StringUtils.isNotBlank(classroom.getClassName())) {
            classroomDB.setClassName(classroom.getClassName());
        }
        if (classroom.getClassNumber() > 0) {
            classroomDB.setClassNumber(classroom.getClassNumber());
        }
        if (classroom.getProgram() != null) {
            classroomDB.setProgram(classroom.getProgram());
        }
        if (classroom.getTrainer() != null) {
            classroomDB.setTrainer(classroom.getTrainer());
        }

        return convertToDTO(classroomRepository.save(classroomDB));
    }



    public void deleteClassroomById(Long classroomId) throws ResourceNotFoundException {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found with ID: " + classroomId));
        classroomRepository.delete(classroom);
    }


    public ClassroomDTO convertToDTO(Classroom classroom) {
        return ClassroomDTO.builder()
                .id(classroom.getId())
                .className(classroom.getClassName())
                .classNumber(classroom.getClassNumber())
                .trainer(null)
                .program(null)
                .students(null)
                .build();
    }


    public ClassroomDTO convertToDTO(Classroom classroom, List<String> includesList) {
        TrainerDTO trainerDTO = null;
        ProgramDTO programDTO = null;
        List<StudentDTO> studentDTOs = null;

        if (includesList.contains("trainer")) {
            Trainer trainer = classroom.getTrainer();
            trainerDTO = new TrainerDTO(trainer.getId(), trainer.getFirstName(), trainer.getLastName(),
                    trainer.getEmail(), trainer.getSpeciality(), null);
        }

        if (includesList.contains("program")) {
            Program program = classroom.getProgram();
            programDTO = new ProgramDTO(program.getId(), program.getTitle(), program.getGrade(),
                    program.getMinCapacity(),
                    program.getMaxCapacity(), program.getStarDate(),
                    program.getEndDate(), program.getProgramStatus(), null);
        }

        if (includesList.contains("students")) {
            List<Student> students = classroom.getStudents();
            studentDTOs = students.stream().map(student -> StudentDTO.builder()
                    .id(student.getId())
                    .firstName(student.getFirstName())
                    .lastName(student.getLastName())
                    .email(student.getEmail())
                    .grade(student.getGrade())
                    .build())
                    .collect(Collectors.toList());
        }

        return ClassroomDTO.builder()
                .id(classroom.getId())
                .className(classroom.getClassName())
                .classNumber(classroom.getClassNumber())
                .trainer(trainerDTO)
                .program(programDTO)
                .students(studentDTOs)
                .build();
    }

    public List<ClassroomDTO> convertToDTOList(List<Classroom> classrooms, List<String> includes) {
        return classrooms.stream()
                .map(classroom -> convertToDTO(classroom, includes))
                .collect(Collectors.toList());
    }

    public Specification<Classroom> verifyIncludes(Specification<Classroom> spec, List<String> includesList)
            throws InvalidDataException {

        for (String include : includesList) {
            if (!include.isEmpty() && !VALID_INCLUDES.contains(include)) {
                throw new InvalidDataException("Invalid include: " + include);
            }
        }

        if (includesList.contains("trainer")) {
            spec = spec.and(ClassroomSpecifications.fetchTrainer());
        }
        if (includesList.contains("program")) {
            spec = spec.and(ClassroomSpecifications.fetchProgram());
        }
        if (includesList.contains("students")) {
            spec = spec.and(ClassroomSpecifications.fetchStudents());
        }

        return spec;
    }
}

package com.simples.service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.simples.dto.ClassroomDTO;
import com.simples.dto.ProgramDTO;
import com.simples.dto.StudentDTO;
import com.simples.dto.TrainerDTO;
import com.simples.exceptions.InvalidDataException;
import com.simples.exceptions.ResourceNotFoundException;
import com.simples.model.Classroom;
import com.simples.model.Student;
import com.simples.model.Program;
import com.simples.model.Trainer;
import com.simples.repository.StudentRepository;
import com.simples.specifications.StudentSpecifications;

/**
 * Service interface for Student entity.
 * Defines methods for CRUD operations and additional business logic.
 */
@Service
public class StudentService {
    private final List<String> VALID_INCLUDES = Arrays.asList("classroom");

    @Autowired
    private StudentRepository studentRepository;

    public StudentDTO findStudentById(long id, String... with) throws ResourceNotFoundException, InvalidDataException {
        List<String> includesList = Arrays.asList(with);
        Specification<Student> spec = Specification.where(StudentSpecifications.hasId(id));
        spec = verifyIncludes(spec, includesList);

        Student student = studentRepository.findOne(spec)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        return convertToDTO(student, includesList);
    }

    public StudentDTO addStudent(Student student) {
        return convertToDTO(studentRepository.save(student));
    }

    public Page<StudentDTO> getStudentList(Pageable pageable, String searcgTerm,String... with) throws InvalidDataException {
        List<String> includesList = Arrays.asList(with);
        Specification<Student> spec = Specification.where(null);

        if(!searcgTerm.isEmpty() && searcgTerm != null){
            spec = spec.and(StudentSpecifications.searchByTerm(searcgTerm));
        }

        spec = verifyIncludes(spec, includesList);
        Page<Student> studentPage = studentRepository.findAll(spec, pageable);
        return studentPage.map(student -> convertToDTO(student, includesList));
    }

    public StudentDTO updateStudent(Student student, Long studentId) throws ResourceNotFoundException {

        Student studentDB = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentId));

        // Updates fields if they are not null or empty.
        if (StringUtils.isNotBlank(student.getFirstName())) {
            studentDB.setFirstName(student.getFirstName());
        }
        if (StringUtils.isNotBlank(student.getLastName())) {
            studentDB.setLastName(student.getLastName());
        }
        if (StringUtils.isNotBlank(student.getEmail())) {
            studentDB.setEmail(student.getEmail());
        }
        if (StringUtils.isNotBlank(student.getGrade())) {
            studentDB.setGrade(student.getGrade());
        }
        if (Objects.nonNull(student.getClassroom())) {
            studentDB.setClassroom(student.getClassroom());
        }
        return convertToDTO(studentRepository.save(studentDB));
    }

    public void deleteStudentById(Long studentId) throws ResourceNotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentId));

        studentRepository.delete(student);
    }

    public StudentDTO convertToDTO(Student student) {
        return StudentDTO.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .grade(student.getGrade())
                .classroom(null)
                .build();
    }

    public StudentDTO convertToDTO(Student student, List<String> includesList) {
        ClassroomDTO classroomDTO = null;
        TrainerDTO trainerDTO = null;
        ProgramDTO programDTO = null;

        if (includesList.contains("classroom")) {
            Classroom classroom = student.getClassroom();
            Trainer trainer = classroom.getTrainer();
            Program program = classroom.getProgram();
            trainerDTO = new TrainerDTO(null, trainer.getFirstName(), trainer.getLastName(), null, null, null);
            programDTO = new ProgramDTO(null, program.getTitle(), null, null, null, program.getStarDate(),
                    program.getEndDate(), null, null);
            classroomDTO = new ClassroomDTO(null, classroom.getClassName(), classroom.getClassNumber(), trainerDTO,
                    programDTO, null);
        }

        return StudentDTO.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .grade(student.getGrade())
                .classroom(classroomDTO)
                .build();
    }

    public List<StudentDTO> convertToDTOList(List<Student> students, List<String> includes) {
        return students.stream()
                .map(student -> convertToDTO(student, includes))
                .collect(Collectors.toList());
    }

    public Specification<Student> verifyIncludes(Specification<Student> spec, List<String> includesList)
            throws InvalidDataException {

        for (String include : includesList) {
            if (!include.isEmpty() && !VALID_INCLUDES.contains(include)) {
                throw new InvalidDataException("Invalid include: " + include);
            }
        }

        if (includesList.contains("classroom")) {
            spec = spec.and(StudentSpecifications.fetchClassroom());
        }

        return spec;
    }
}

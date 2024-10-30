package com.simples.service;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simples.exceptions.ResourceNotFoundException;
import com.simples.model.Student;
import com.simples.repository.StudentRepository;

/**
 * Service interface for Student entity.
 * Defines methods for CRUD operations and additional business logic.
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student findStudentById(long id) throws ResourceNotFoundException {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getStudentList() {

        return (List<Student>) studentRepository.findAll();
    }

    public Student updateStudent(Student student, Long studentId) throws ResourceNotFoundException {

        Student studentDB = findStudentById(studentId);

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
        return studentRepository.save(studentDB);
    }

    public void deleteStudentById(Long studentId) throws ResourceNotFoundException {
        Student student = findStudentById(studentId);
        studentRepository.delete(student);
    }
}

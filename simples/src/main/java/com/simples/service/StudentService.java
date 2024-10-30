package com.simples.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Student findStudentById(long id) {
        return studentRepository.findById(id).get();
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getStudentList() {

        return (List<Student>) studentRepository.findAll();
    }

    public Student updateStudent(Student student, Long studentId) {

        Student studentDB = studentRepository.findById(studentId).get();

        // Updates fields if they are not null or empty.
        if (Objects.nonNull(student.getFirstName()) && !"".equalsIgnoreCase(student.getFirstName())) {
            studentDB.setFirstName(student.getFirstName());
        }
        if (Objects.nonNull(student.getLastName()) && !"".equalsIgnoreCase(student.getLastName())) {
            studentDB.setLastName(student.getLastName());
        }
        if (Objects.nonNull(student.getEmail())  && !"".equalsIgnoreCase(student.getEmail())) {
            studentDB.setEmail(student.getEmail());
        }
        if (Objects.nonNull(student.getGrade())  && !"".equalsIgnoreCase(student.getGrade())) {
            studentDB.setGrade(student.getGrade());
        }
        if (Objects.nonNull(student.getClassroom())) {
            studentDB.setClassroom(student.getClassroom());
        }
        return studentRepository.save(studentDB);
    }

    public void deleteStudentById(Long studentId) {
        studentRepository.deleteById(studentId);
    }
}

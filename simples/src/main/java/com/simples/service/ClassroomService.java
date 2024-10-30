package com.simples.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simples.exceptions.ResourceNotFoundException;
import com.simples.model.Classroom;
import com.simples.repository.ClassroomRepository;

/**
 * Service interface for Classroom entity.
 * Defines methods for CRUD operations and additional business logic.
 */
@Service
public class ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    public Classroom findClassroomById(long id) throws ResourceNotFoundException {
        return classroomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Classroom not found with ID: " + id));
    }

    public Classroom addClassroom(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    public List<Classroom> getClassroomList() {

        return (List<Classroom>) classroomRepository.findAll();
    }

    public Classroom updateClassroom(Classroom classroom, Long classroomId) throws ResourceNotFoundException {

        Classroom classroomDB = findClassroomById(classroomId);

        // Updates fields if they are not null or empty.
        if (StringUtils.isNotBlank(classroom.getClassName())) {
            classroomDB.setClassName(classroom.getClassName());
        }
        if (classroom.getClassNumber() > 0) {
            classroomDB.setClassNumber(classroom.getClassNumber());
        }

        return classroomRepository.save(classroomDB);
    }

    public void deleteClassroomById(Long classroomId) throws ResourceNotFoundException {
        Classroom classroom = findClassroomById(classroomId);
        classroomRepository.delete(classroom);
    }
}

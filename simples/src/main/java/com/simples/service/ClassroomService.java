package com.simples.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Classroom findClassroomById(long id){
        return classroomRepository.findById(id).get();
    }

    public Classroom addClassroom(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    public List<Classroom> getClassroomList() {

        return (List<Classroom>) classroomRepository.findAll();
    }

    public Classroom updateClassroom(Classroom classroom, Long classroomId) {

        Classroom classroomDB = classroomRepository.findById(classroomId).get();

        // Updates fields if they are not null or empty.
        if (Objects.nonNull(classroom.getClassName()) && !"".equalsIgnoreCase(classroom.getClassName())) {
            classroomDB.setClassName(classroom.getClassName());
        }
        if (classroom.getClassNumber() > 0) {
            classroomDB.setClassNumber(classroom.getClassNumber());
        }
        
        return classroomRepository.save(classroomDB);
    }

    public void deleteClassroomById(Long classroomId) {
        classroomRepository.deleteById(classroomId);
    }
}

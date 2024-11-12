package com.simples.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simples.dto.ClassroomDTO;
import com.simples.dto.StudentDTO;
import com.simples.dto.AbsenceDTO;
import com.simples.exceptions.InvalidAbsenceException;
import com.simples.exceptions.InvalidDataException;
import com.simples.model.Classroom;
import com.simples.model.Absence;
import com.simples.model.Student;
import com.simples.repository.AbsenceRepository;

import lombok.extern.log4j.Log4j2;

/**
 * Service interface for Absence entity.
 * Defines methods for CRUD operations and additional business logic.
 */
@Service
@Log4j2
public class AbsenceService {

    @Autowired
    private AbsenceRepository absenceRepository;

    public AbsenceDTO addAbsence(Absence absence) throws InvalidAbsenceException {
        AbsenceDTO absenceDTO = null;

        if (absence.getDate().isAfter(LocalDate.now()))
            throw new InvalidAbsenceException("Absence date shouldn't be in the future");

        try {
            absenceDTO = convertToDTO(absenceRepository.save(absence));
        } catch (Exception e) {
            throw new InvalidAbsenceException("Student of id " + absence.getStudent().getId() + " doesn't exist");
        }

        return absenceDTO;
    }

    public List<AbsenceDTO> getAbsenceByStudentId(long id)
            throws InvalidDataException {

        List<Absence> absences = absenceRepository.findByStudent_Id(id);
        return convertToDTOList(absences);
    }

    public List<AbsenceDTO> getAbsenceByDate(String date)
            throws InvalidDataException {
        LocalDate parsedDate = null;
        try {
            parsedDate = LocalDate.parse(date);
        } catch (Exception e) {
            throw new InvalidDataException("Error parsing date");
        }

        List<Absence> absences = absenceRepository.findByDate(parsedDate);
        return convertToDTOList(absences, "student");
    }

    public AbsenceDTO convertToDTO(Absence absence, String... with) {
        List<String> includesList = Arrays.asList(with);
        StudentDTO studentDTO = null;

        if (includesList.contains("student")) {
            Student student = absence.getStudent();
            Classroom classroom = student.getClassroom();
            ClassroomDTO classroomDTO = new ClassroomDTO(null, classroom.getClassName(), null, null, null, null);

            studentDTO = new StudentDTO(null, student.getFirstName(), student.getLastName(), null, null, classroomDTO,
                    null);
        }

        return AbsenceDTO.builder()
                .id(absence.getId())
                .justification(absence.getJustification())
                .date(absence.getDate())
                .student(studentDTO)
                .build();
    }

    public List<AbsenceDTO> convertToDTOList(List<Absence> absences, String... with) {
        return absences.stream()
                .map(absence -> convertToDTO(absence, with))
                .collect(Collectors.toList());
    }

}

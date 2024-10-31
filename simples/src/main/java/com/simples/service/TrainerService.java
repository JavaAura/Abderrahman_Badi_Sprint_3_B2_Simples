package com.simples.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import com.simples.dto.ClassroomDTO;
import com.simples.dto.ProgramDTO;
import com.simples.dto.TrainerDTO;
import com.simples.exceptions.InvalidDataException;
import com.simples.exceptions.ResourceNotFoundException;
import com.simples.model.Classroom;
import com.simples.model.Trainer;
import com.simples.repository.TrainerRepository;
import com.simples.specifications.TrainerSpeciications;

import lombok.extern.log4j.Log4j2;

/**
 * Service interface for Trainer entity.
 * Defines methods for CRUD operations and additional business logic.
 */
@Log4j2
@Service
public class TrainerService {
    private final List<String> VALID_INCLUDES = Arrays.asList("classrooms");

    @Autowired
    private TrainerRepository trainerRepository;

    public TrainerDTO findTrainerById(long id, String... with) throws ResourceNotFoundException, InvalidDataException {
        List<String> includesList = Arrays.asList(with);
        Specification<Trainer> spec = Specification.where(TrainerSpeciications.hasId(id));

        spec = verifyIncludes(spec, includesList);
        Trainer trainer = trainerRepository.findOne(spec)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with ID: " + id));

        return convertToDTO(trainer, includesList);
    }

    public TrainerDTO addTrainer(Trainer trainer) {
        return convertToDTO(trainerRepository.save(trainer));
    }

    public List<TrainerDTO> getTrainerList(String... with) throws InvalidDataException {
        List<String> includesList = Arrays.asList(with);
        Specification<Trainer> spec = Specification.where(null);

        spec = verifyIncludes(spec, includesList);
        List<Trainer> trainers = trainerRepository.findAll(spec, Sort.by(Sort.Direction.ASC, "id"));
        return convertToDTOList(trainers, includesList);
    }

    public TrainerDTO updateTrainer(Trainer trainer, Long trainerId) throws ResourceNotFoundException {

        Trainer trainerDB = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with ID: " + trainerId));

        // Updates fields if they are not null or empty.
        if (StringUtils.isNotBlank(trainer.getFirstName())) {
            trainerDB.setFirstName(trainer.getFirstName());
        }
        if (StringUtils.isNotBlank(trainer.getLastName())) {
            trainerDB.setLastName(trainer.getLastName());
        }
        if (StringUtils.isNotBlank(trainer.getEmail())) {
            trainerDB.setEmail(trainer.getEmail());
        }
        if (StringUtils.isNotBlank(trainer.getSpeciality())) {
            trainerDB.setSpeciality(trainer.getSpeciality());
        }

        return convertToDTO(trainerRepository.save(trainerDB));
    }

    public void deleteTrainerById(Long trainerId) throws ResourceNotFoundException {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with ID: " + trainerId));
        trainerRepository.delete(trainer);
        log.info("Deleted trainer with ID: {}", trainerId);
    }

    public TrainerDTO convertToDTO(Trainer trainer) {
        return TrainerDTO.builder()
                .id(trainer.getId())
                .firstName(trainer.getFirstName())
                .lastName(trainer.getLastName())
                .email(trainer.getEmail())
                .speciality(trainer.getSpeciality())
                .classrooms(null)
                .build();
    }

    public TrainerDTO convertToDTO(Trainer trainer, List<String> includesList) {
        List<ClassroomDTO> classroomDTOs = null;

        if (includesList.contains("classrooms")) {
            List<Classroom> classrooms = trainer.getClassrooms();
            classroomDTOs = classrooms.stream().map(classroom -> ClassroomDTO.builder()
                    .id(classroom.getId())
                    .className(classroom.getClassName())
                    .classNumber(classroom.getClassNumber())
                    .program(new ProgramDTO(null, classroom.getProgram().getTitle(), classroom.getProgram().getGrade(),
                            null, null, classroom.getProgram().getStarDate(),
                            classroom.getProgram().getEndDate(), classroom.getProgram().getProgramStatus(), null))
                    .build())
                    .collect(Collectors.toList());
        }

        return TrainerDTO.builder()
                .id(trainer.getId())
                .firstName(trainer.getFirstName())
                .lastName(trainer.getLastName())
                .email(trainer.getEmail())
                .speciality(trainer.getSpeciality())
                .classrooms(classroomDTOs)
                .build();
    }

    public List<TrainerDTO> convertToDTOList(List<Trainer> trainers, List<String> includes) {
        return trainers.stream()
                .map(trainer -> convertToDTO(trainer, includes))
                .collect(Collectors.toList());
    }

    public Specification<Trainer> verifyIncludes(Specification<Trainer> spec, List<String> includesList)
            throws InvalidDataException {

        for (String include : includesList) {
            if (!include.isEmpty() && !VALID_INCLUDES.contains(include)) {
                throw new InvalidDataException("Invalid include: " + include);
            }
        }

        if (includesList.contains("classrooms")) {
            spec = spec.and(TrainerSpeciications.fetchClassrooms());
        }

        return spec;
    }
}

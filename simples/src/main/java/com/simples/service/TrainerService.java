package com.simples.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import com.simples.exceptions.ResourceNotFoundException;
import com.simples.model.Trainer;
import com.simples.repository.TrainerRepository;

import lombok.extern.log4j.Log4j2;

/**
 * Service interface for Trainer entity.
 * Defines methods for CRUD operations and additional business logic.
 */
@Log4j2
@Service
public class TrainerService {

    @Autowired
    private TrainerRepository trainerRepository;

    public Trainer findTrainerById(long id) throws ResourceNotFoundException {
        return trainerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with ID: " + id));

    }

    public Trainer addTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    public List<Trainer> getTrainerList() {
        return (List<Trainer>) trainerRepository.findAll();
    }

    public Trainer updateTrainer(Trainer trainer, Long trainerId) throws ResourceNotFoundException {

        Trainer trainerDB = findTrainerById(trainerId);

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

        return trainerRepository.save(trainerDB);
    }

    @Transactional
    public void deleteTrainerById(Long trainerId) throws ResourceNotFoundException {
        Trainer trainer = findTrainerById(trainerId);
        trainerRepository.delete(trainer);
        log.info("Deleted trainer with ID: {}", trainerId);
    }
}

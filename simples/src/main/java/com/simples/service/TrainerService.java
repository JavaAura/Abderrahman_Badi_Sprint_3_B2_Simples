package com.simples.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simples.model.Trainer;
import com.simples.repository.TrainerRepository;

/**
 * Service interface for Trainer entity.
 * Defines methods for CRUD operations and additional business logic.
 */
@Service
public class TrainerService {

    @Autowired
    private TrainerRepository trainerRepository;

    public Trainer findTrainerById(long id) {
        return trainerRepository.findById(id).get();
    }

    public Trainer addTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    public List<Trainer> getTrainerList() {

        return (List<Trainer>) trainerRepository.findAll();
    }

    public Trainer updateTrainer(Trainer trainer, Long trainerId) {

        Trainer trainerDB = trainerRepository.findById(trainerId).get();

        // Updates fields if they are not null or empty.
        if (Objects.nonNull(trainer.getFirstName()) && !"".equalsIgnoreCase(trainer.getFirstName())) {
            trainerDB.setFirstName(trainer.getFirstName());
        }
        if (Objects.nonNull(trainer.getLastName()) && !"".equalsIgnoreCase(trainer.getLastName())) {
            trainerDB.setLastName(trainer.getLastName());
        }
        if (Objects.nonNull(trainer.getEmail()) && !"".equalsIgnoreCase(trainer.getEmail())) {
            trainerDB.setEmail(trainer.getEmail());
        }
        if (Objects.nonNull(trainer.getSpeciality()) && !"".equalsIgnoreCase(trainer.getSpeciality())) {
            trainerDB.setSpeciality(trainer.getSpeciality());
        }

        return trainerRepository.save(trainerDB);
    }

    public void deleteTrainerById(Long trainerId) {
        trainerRepository.deleteById(trainerId);
    }
}

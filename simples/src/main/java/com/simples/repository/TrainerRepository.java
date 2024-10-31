package com.simples.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.simples.model.Trainer;

/**
 * Repository interface for Trainer entity.
 * Provides CRUD operations and custom query methods through JpaRepository.
 */
@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long>, JpaSpecificationExecutor<Trainer> {
}

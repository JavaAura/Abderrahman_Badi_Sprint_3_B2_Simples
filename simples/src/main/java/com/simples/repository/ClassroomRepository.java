package com.simples.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simples.model.Classroom;

/**
 * Repository interface for Classroom entity.
 * Provides CRUD operations and custom query methods through JpaRepository.
 */
@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
}

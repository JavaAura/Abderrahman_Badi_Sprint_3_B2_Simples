package com.simples.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simples.model.Program;

/**
 * Repository interface for Program entity.
 * Provides CRUD operations and custom query methods through JpaRepository.
 */
// @Repository
// extends JpaRepository<Program, Long>
public interface ProgramRepository {
}

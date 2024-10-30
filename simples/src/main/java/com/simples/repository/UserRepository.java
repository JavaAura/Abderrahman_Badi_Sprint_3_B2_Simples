package com.simples.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simples.model.User;

/**
 * Repository interface for User entity.
 * Provides CRUD operations and custom query methods through JpaRepository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
}

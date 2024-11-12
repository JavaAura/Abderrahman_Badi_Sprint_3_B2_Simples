package com.simples.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.simples.model.Absence;

/**
 * Repository interface for Classroom entity.
 * Provides CRUD operations and custom query methods through JpaRepository.
 */
@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long>, JpaSpecificationExecutor<Absence> {

    List<Absence> findByStudent_Id(long id);

    @Query(value = "select a from Absence a LEFT JOIN FETCH a.student s LEFT JOIN FETCH s.classroom where a.date = :date")
    List<Absence> findByDate(LocalDate date);

}

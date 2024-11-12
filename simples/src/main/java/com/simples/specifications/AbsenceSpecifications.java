package com.simples.specifications;

import java.time.LocalDate;

import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;

import com.simples.model.Absence;

public class AbsenceSpecifications {

    public static Specification<Absence> fetchStudent() {
        return (root, query, cb) -> {
            root.fetch("student", JoinType.LEFT);
            return null;
        };
    }

    public static Specification<Absence> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Absence> searchByTerm(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("date"), date);
        };
    }

}

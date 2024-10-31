package com.simples.specifications;

import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;

import com.simples.model.Trainer;

public class TrainerSpeciications {

    public static Specification<Trainer> fetchClassrooms() {
        return (root, query, cb) -> {
            root.fetch("classrooms", JoinType.LEFT);
            return null;
        };
    }

    public static Specification<Trainer> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
}

package com.simples.specifications;

import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;

import com.simples.model.Program;

public class ProgramSpecifications {

    public static Specification<Program> fetchClassrooms() {
        return (root, query, cb) -> {
            root.fetch("classrooms", JoinType.LEFT);
            return null;
        };
    }

    public static Specification<Program> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
}

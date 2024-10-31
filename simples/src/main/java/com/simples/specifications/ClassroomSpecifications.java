package com.simples.specifications;

import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;

import com.simples.model.Classroom;

public class ClassroomSpecifications {

    public static Specification<Classroom> fetchTrainer() {
        return (root, query, cb) -> {
            root.fetch("trainer", JoinType.LEFT);
            return null;
        };
    }

    public static Specification<Classroom> fetchProgram() {
        return (root, query, cb) -> {
            root.fetch("program", JoinType.LEFT);
            return null;
        };
    }

    public static Specification<Classroom> fetchStudents() {
        return (root, query, cb) -> {
            root.fetch("students", JoinType.LEFT);
            return null;
        };
    }

    public static Specification<Classroom> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
}

package com.simples.specifications;

import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;

import com.simples.model.Student;

public class StudentSpecifications {

    public static Specification<Student> fetchClassroom() {
        return (root, query, cb) -> {
            root.fetch("classroom", JoinType.LEFT);
            return null;
        };
    }


    public static Specification<Student> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
}

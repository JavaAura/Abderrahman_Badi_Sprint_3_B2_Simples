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

    public static Specification<Student> searchByTerm(String term) {
        return (root, query, criteriaBuilder) -> {
            String pattern = "%" + term.toLowerCase() + "%";
            return criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), pattern),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), pattern),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), pattern)
            );
        };
    }
    
}

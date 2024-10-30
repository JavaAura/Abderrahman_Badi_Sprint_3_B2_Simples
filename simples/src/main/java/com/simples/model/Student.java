package com.simples.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Represents a Student entity in the application.
 */
@Entity
@Table(name = "students")
@Data // Generates getters, setters, toString, equals, and hashCode methods.
@AllArgsConstructor // Generates a constructor with all arguments.
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Student extends User{

    @NotBlank(message = "Grade shoudln't be empty")
    private String grade;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;
}

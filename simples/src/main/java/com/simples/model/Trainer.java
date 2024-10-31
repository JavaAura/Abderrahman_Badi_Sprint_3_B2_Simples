package com.simples.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Represents a Trainer entity in the application.
 */
@Entity
@Table(name = "trainers")
@Data // Generates getters, setters, toString, equals, and hashCode methods.
@AllArgsConstructor // Generates a constructor with all arguments.
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Trainer extends User {
    @NotBlank(message = "Speciality shoudln't be empty")
    private String speciality;

    @OneToMany(mappedBy = "trainer", fetch = FetchType.LAZY)
    // @JsonBackReference(value = "classroom-trainer")
    private List<Classroom> classrooms;
}

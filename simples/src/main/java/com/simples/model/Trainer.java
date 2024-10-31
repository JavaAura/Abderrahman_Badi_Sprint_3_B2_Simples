package com.simples.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
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
@SQLDelete(sql = "UPDATE trainers SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@EqualsAndHashCode(callSuper = true)
public class Trainer extends User {
    @NotBlank(message = "Speciality shoudln't be empty")
    private String speciality;

    @OneToMany(mappedBy = "trainer", fetch = FetchType.LAZY)
    // @JsonBackReference(value = "classroom-trainer")
    private List<Classroom> classrooms;

    @Column(name = "deleted", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted = Boolean.FALSE;
}

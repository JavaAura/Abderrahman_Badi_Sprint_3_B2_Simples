package com.simples.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Classroom entity in the application.
 */
@Entity
@Table(name = "classrooms")
@Data // Generates getters, setters, toString, equals, and hashCode methods.
@AllArgsConstructor // Generates a constructor with all arguments.
@NoArgsConstructor
@SQLDelete(sql = "UPDATE classrooms SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Class room name shoudln't be empty")
    private String className;

    @NotNull(message = "Class number name shoudln't be empty")
    @Positive(message = "Class number must be positive")
    private int classNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id", referencedColumnName = "id")
    private Trainer trainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", referencedColumnName = "id")
    private Program program;

    @OneToMany(mappedBy = "classroom", fetch = FetchType.LAZY)
    private List<Student> students;

    @Column(name = "deleted", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted = Boolean.FALSE;
}

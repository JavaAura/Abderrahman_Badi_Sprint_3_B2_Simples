package com.simples.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.simples.model.enums.ProgramStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a User entity in the application.
 */
@Entity
@Table(name = "programs")
@Data // Generates getters, setters, toString, equals, and hashCode methods.
@AllArgsConstructor // Generates a constructor with all arguments.
@NoArgsConstructor
@SQLDelete(sql = "UPDATE programs SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title shoudln't be empty")
    private String title;

    @NotBlank(message = "Grade shoudln't be empty")
    private String grade;

    @Min(value = 1)
    private Integer minCapacity;

    @Min(value = 1)
    private Integer maxCapacity;

    @NotBlank
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate starDate;

    @NotBlank
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @NotBlank
    private ProgramStatus programStatus = ProgramStatus.PLANNED;

    @OneToMany(mappedBy = "program", fetch = FetchType.LAZY)
    // @JsonManagedReference(value = "classroom-program")
    private List<Classroom> classrooms;

    @Column(name = "deleted", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted = Boolean.FALSE;
}

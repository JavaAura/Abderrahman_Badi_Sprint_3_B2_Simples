package com.simples.dto;

import java.time.LocalDate;
import java.util.List;

import com.simples.model.enums.ProgramStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDTO {

    private Long id;
    private String title;
    private String grade;
    private Integer minCapacity;
    private Integer maxCapacity;
    private LocalDate starDate;
    private LocalDate endDate;
    private ProgramStatus programStatus;
    private List<ClassroomDTO> classrooms;

}

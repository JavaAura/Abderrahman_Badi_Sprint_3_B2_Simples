package com.simples.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomDTO {

    private Long id;
    private String className;
    private int classNumber;
    private TrainerDTO trainer;
    private ProgramDTO program;
    private List<StudentDTO> students;

}

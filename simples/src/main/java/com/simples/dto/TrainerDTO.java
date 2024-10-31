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
public class TrainerDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String speciality;
    private List<ClassroomDTO> classrooms;

}

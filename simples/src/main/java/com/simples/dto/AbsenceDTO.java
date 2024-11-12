package com.simples.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbsenceDTO {

    private long id;
    private String justification;
    private LocalDate date;
    private StudentDTO student;

}

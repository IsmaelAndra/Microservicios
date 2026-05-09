package com.microservicio.estudiantes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteRequestDTO {
    private String nombre;
    private String email;
    private Integer edad;
    private String carrera;
}

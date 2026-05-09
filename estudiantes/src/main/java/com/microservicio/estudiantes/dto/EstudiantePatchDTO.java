package com.microservicio.estudiantes.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudiantePatchDTO {
    private String nombre;
    
    @Email(message = "El email debe tener un formato válido")
    private String email;
    
    @Min(value = 16, message = "La edad mínima es 16 años")
    private Integer edad;
    
    private String carrera;
}

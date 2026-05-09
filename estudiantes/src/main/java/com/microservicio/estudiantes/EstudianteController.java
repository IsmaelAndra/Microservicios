package com.microservicio.estudiantes;

import com.microservicio.estudiantes.dto.EstudianteDTO;
import com.microservicio.estudiantes.dto.EstudiantePatchDTO;
import com.microservicio.estudiantes.dto.EstudianteRequestDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    // GET - Listar todos con paginación
    @GetMapping
    public ResponseEntity<Page<EstudianteDTO>> getAllEstudiantes(
            @PageableDefault(page = 0, size = 10, sort = "nombre") Pageable pageable) {
        Page<EstudianteDTO> estudiantes = estudianteService.getAllEstudiantes(pageable);
        return ResponseEntity.ok(estudiantes);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<EstudianteDTO> getEstudianteById(@PathVariable("id") Long id) {
        return estudianteService.getEstudianteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST
    @PostMapping
    public ResponseEntity<EstudianteDTO> createEstudiante(@Valid @RequestBody EstudianteRequestDTO requestDTO) {
        EstudianteDTO created = estudianteService.createEstudiante(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<EstudianteDTO> updateEstudiante(
            @PathVariable("id") Long id,
            @Valid @RequestBody EstudianteRequestDTO requestDTO) {
        return estudianteService.updateEstudiante(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<EstudianteDTO> patchEstudiante(
            @PathVariable("id") Long id,
            @RequestBody EstudiantePatchDTO patchDTO) {
        return estudianteService.patchEstudiante(id, patchDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstudiante(@PathVariable("id") Long id) {
        if (estudianteService.deleteEstudiante(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

package com.microservicio.estudiantes;

import com.microservicio.estudiantes.dto.EstudianteDTO;
import com.microservicio.estudiantes.dto.EstudiantePatchDTO;
import com.microservicio.estudiantes.dto.EstudianteRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    public Page<EstudianteDTO> getAllEstudiantes(Pageable pageable) {
        return estudianteRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    public Optional<EstudianteDTO> getEstudianteById(Long id) {
        return estudianteRepository.findById(id)
                .map(this::convertToDTO);
    }

    public EstudianteDTO createEstudiante(EstudianteRequestDTO requestDTO) {
        Estudiante estudiante = convertToEntity(requestDTO);
        Estudiante saved = estudianteRepository.save(estudiante);
        return convertToDTO(saved);
    }

    public Optional<EstudianteDTO> updateEstudiante(Long id, EstudianteRequestDTO requestDTO) {
        return estudianteRepository.findById(id)
                .map(estudiante -> {
                    estudiante.setNombre(requestDTO.getNombre());
                    estudiante.setEmail(requestDTO.getEmail());
                    estudiante.setEdad(requestDTO.getEdad());
                    estudiante.setCarrera(requestDTO.getCarrera());
                    return convertToDTO(estudianteRepository.save(estudiante));
                });
    }

    public Optional<EstudianteDTO> patchEstudiante(Long id, EstudiantePatchDTO patchDTO) {
        return estudianteRepository.findById(id)
                .map(estudiante -> {
                    if (patchDTO.getNombre() != null) {
                        estudiante.setNombre(patchDTO.getNombre());
                    }
                    if (patchDTO.getEmail() != null) {
                        estudiante.setEmail(patchDTO.getEmail());
                    }
                    if (patchDTO.getEdad() != null) {
                        estudiante.setEdad(patchDTO.getEdad());
                    }
                    if (patchDTO.getCarrera() != null) {
                        estudiante.setCarrera(patchDTO.getCarrera());
                    }
                    return convertToDTO(estudianteRepository.save(estudiante));
                });
    }

    public boolean deleteEstudiante(Long id) {
        return estudianteRepository.findById(id)
                .map(estudiante -> {
                    estudianteRepository.delete(estudiante);
                    return true;
                })
                .orElse(false);
    }

    private EstudianteDTO convertToDTO(Estudiante estudiante) {
        return new EstudianteDTO(
                estudiante.getId(),
                estudiante.getNombre(),
                estudiante.getEmail(),
                estudiante.getEdad(),
                estudiante.getCarrera()
        );
    }

    private Estudiante convertToEntity(EstudianteRequestDTO dto) {
        return new Estudiante(
                null,
                dto.getNombre(),
                dto.getEmail(),
                dto.getEdad(),
                dto.getCarrera()
        );
    }
}

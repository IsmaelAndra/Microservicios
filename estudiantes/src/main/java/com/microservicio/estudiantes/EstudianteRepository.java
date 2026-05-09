package com.microservicio.estudiantes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    Page<Estudiante> findAll(Pageable pageable);
}

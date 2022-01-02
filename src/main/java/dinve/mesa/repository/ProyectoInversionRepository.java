package dinve.mesa.repository;

import dinve.mesa.model.descripcion_agregada_proyecto_programa.ProyectoDeInversion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProyectoInversionRepository extends JpaRepository<ProyectoDeInversion,Long> {
}

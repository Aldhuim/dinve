package dinve.mesa.repository;

import dinve.mesa.model.descripcion_agregada_proyecto_programa.ProgramaDeInversion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramaInversionRepository extends JpaRepository<ProgramaDeInversion,Long> {
}

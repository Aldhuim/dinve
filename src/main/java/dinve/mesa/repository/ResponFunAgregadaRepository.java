package dinve.mesa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dinve.mesa.model.descripcion_agregada_proyecto_programa.ResponsabilidadFuncionalDescripcionAgregada;

@Repository
public interface ResponFunAgregadaRepository extends JpaRepository<ResponsabilidadFuncionalDescripcionAgregada,Long> {
}

package dinve.mesa.repository;

import dinve.mesa.model.descripcion_agregada_proyecto_programa.TipoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoItemRepository extends JpaRepository<TipoItem,Long> {
}

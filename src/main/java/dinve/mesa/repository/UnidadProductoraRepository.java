package dinve.mesa.repository;

import dinve.mesa.model.UnidadProductora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("UnidadProductoraRepository")
public interface UnidadProductoraRepository extends JpaRepository<UnidadProductora,Long> {
}

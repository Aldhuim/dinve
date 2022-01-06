package dinve.mesa.repository;

import dinve.mesa.model.UnidadProductora;
import dinve.mesa.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("UnidadProductoraRepository")
public interface UnidadProductoraRepository extends JpaRepository<UnidadProductora,Long> {
    @Query("select up from UnidadProductora up where up.nombre = ?1")
    UnidadProductora findByName(String name);

}

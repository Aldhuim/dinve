package dinve.mesa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dinve.mesa.model.descripcion_agregada_ioarr.ResponsabilidadFuncionalDescripcionIOARR;

@Repository
public interface ResponFunIOARRRepository extends JpaRepository<ResponsabilidadFuncionalDescripcionIOARR,Long> {
}

package dinve.mesa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dinve.mesa.model.formulario.Adjunto;

@Repository
public interface AdjuntoRepository extends JpaRepository<Adjunto,Long> {
}

package dinve.mesa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dinve.mesa.model.formulario_tipos.Formulario5B;

@Repository
public interface Formulario5BRepository extends JpaRepository<Formulario5B,Long> {
}
package dinve.mesa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dinve.mesa.model.formulario.Formulario5A;

@Repository
public interface Formulario5ARepository extends JpaRepository<Formulario5A,Long> {
}

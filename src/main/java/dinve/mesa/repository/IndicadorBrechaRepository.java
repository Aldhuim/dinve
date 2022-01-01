package dinve.mesa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dinve.mesa.model.alineamiento_brecha_prioritaria.IndicadorBrecha;

public interface IndicadorBrechaRepository extends JpaRepository<IndicadorBrecha,Long> {
}

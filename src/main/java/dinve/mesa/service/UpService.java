package dinve.mesa.service;


import dinve.mesa.converter.UnidadProductoraDatos;
import dinve.mesa.model.UnidadProductora;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface UpService {
    String save(String token, UnidadProductoraDatos unidadProductora);
    Map<String, Object> getAllUp(String token, Pageable pageable);
    Map<String, Object> getUp(String token, Pageable pageable);
    String deleteUp(String token,Long id);
    Map<String, Object> getAllUps(String token);
}

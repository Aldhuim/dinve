package dinve.mesa.service;

import dinve.mesa.converter.UsuarioDatos;
import dinve.mesa.model.Usuario;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


public interface UserService {
    String save(String token, UsuarioDatos usuarioDatos);
    Map<String,Object> login(Usuario usuario);
    String logout(Usuario usuario);
    Map<String,Object> findAll(String token, Pageable pageable);
}

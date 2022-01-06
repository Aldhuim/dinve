package dinve.mesa.service.implementation;


import dinve.mesa.converter.UnidadProductoraDatos;
import dinve.mesa.model.UnidadProductora;
import dinve.mesa.model.Usuario;
import dinve.mesa.repository.UnidadProductoraRepository;
import dinve.mesa.repository.UsuarioRepository;
import dinve.mesa.service.UpService;
import dinve.mesa.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service("UpService")
public class UpServiceImpl implements UpService {
    private final UsuarioRepository usuarioRepository;
    private final UnidadProductoraRepository unidadProductoraRepository;
    private final JWTUtil jwtUtil;

    @Autowired
    public UpServiceImpl(
            UsuarioRepository usuarioRepository,
            UnidadProductoraRepository unidadProductoraRepository,
            JWTUtil jwtUtil){
        this.usuarioRepository = usuarioRepository;
        this.unidadProductoraRepository = unidadProductoraRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String save(String token, UnidadProductoraDatos unidadProductora) {
        try{
            Usuario u = usuarioRepository.findByUser(jwtUtil.getUser(token));
            if (u.isActivo()){
                UnidadProductora up = unidadProductora.getUnidadProductora();
                if (unidadProductoraRepository.findByName(up.getNombre()) == null){
                    try{
                        unidadProductoraRepository.save(up);
                        return "Success";
                    } catch (Exception ignored){
                    }
                } else {
                    return "This UP already exist";
                }
            } else {
                return "Inactive user";
            }
        } catch(ExpiredJwtException e){
            return "Session expired";
        }
        return "Failed";
    }

    @Override
    public Map<String, Object> getAllUp(String token, Pageable pageable) {
        return null;
    }

    @Override
    public Map<String, Object> getUp(String token, Pageable pageable) {
        return null;
    }

    @Override
    public String deleteUp(String token, Long id) {
        return null;
    }
}

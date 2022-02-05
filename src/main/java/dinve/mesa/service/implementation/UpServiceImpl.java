package dinve.mesa.service.implementation;


import dinve.mesa.converter.UnidadProductoraDatos;
import dinve.mesa.model.Formulario;
import dinve.mesa.model.UnidadProductora;
import dinve.mesa.model.Usuario;
import dinve.mesa.repository.UnidadProductoraRepository;
import dinve.mesa.repository.UsuarioRepository;
import dinve.mesa.service.UpService;
import dinve.mesa.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
        Map<String,Object> lista_up = new HashMap<>();

        try{
            byte rol = jwtUtil.getRol(token);
            Usuario u = usuarioRepository.findById(Long.parseLong(jwtUtil.getId(token))).get();
            if( (rol== 1 || rol == 2) && (u.isActivo() == true) ) {

                Page<UnidadProductora> pageUP = unidadProductoraRepository.findAll(pageable);
                lista_up.put("Size",pageUP.getSize());
                lista_up.put("TotalPaginas",pageUP.getTotalPages());
                lista_up.put("TotalElementos",pageUP.getTotalElements());
                lista_up.put("Unidades productoras",pageUP.getContent());

                return lista_up;
            } else {
                lista_up.put("Message","No access");
            }
        }catch(ExpiredJwtException e){
            lista_up.put("Message","Session expired");
        }
        return lista_up;
    }

    @Override
    public Map<String, Object> getUp(String token, Pageable pageable) {

        return null;
    }

    @Override
    public String deleteUp(String token, Long id) {
        try {
            Usuario u = usuarioRepository.findByUser(jwtUtil.getUser(token));
            if (usuarioRepository.existsById(Long.valueOf(jwtUtil.getId(token))) && u.isActivo() == true) {
                UnidadProductora up = unidadProductoraRepository.findById(id).orElse(null);
                if (up != null) {
                    unidadProductoraRepository.delete(up);
                    return "Success";
                }
            }
        } catch (ExpiredJwtException e) {
            return "Session expired";
        }
        return "Failed";
    }

    @Override
    public Map<String, Object> getAllUps(String token){
        Map<String, Object> lista_ups = new HashMap<>();
        try{
            byte rol = jwtUtil.getRol(token);
            Usuario u = usuarioRepository.findById(Long.parseLong(jwtUtil.getId(token))).get();
            if( (rol== 1 || rol == 2) && (u.isActivo() == true) ) {
                lista_ups.put("Unidades productoras",unidadProductoraRepository.findAll());
                return lista_ups;
            } else {
                lista_ups.put("Message","No access");
            }
            return null;
        } catch (Exception e){
            return null;
        }
    }
}

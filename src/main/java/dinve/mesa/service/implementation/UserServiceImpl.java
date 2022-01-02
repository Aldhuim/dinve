package dinve.mesa.service.implementation;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import dinve.mesa.converter.UsuarioDatos;
import dinve.mesa.model.UnidadProductora;
import dinve.mesa.model.Usuario;
import dinve.mesa.repository.UnidadProductoraRepository;
import dinve.mesa.repository.UsuarioRepository;
import dinve.mesa.service.UserService;
import dinve.mesa.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("UserService")
public class UserServiceImpl implements UserService {
    @Qualifier("UsuarioRepository")
    private final UsuarioRepository usuarioRepository;
    @Qualifier("UnidadProductoraRepository")
    private final UnidadProductoraRepository unidadProductoraRepository;
    private final JWTUtil jwtUtil;
    private final Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    @Autowired
    public UserServiceImpl(UsuarioRepository usuarioRepository, UnidadProductoraRepository unidadProductoraRepository, JWTUtil jwtUtil){
        this.usuarioRepository = usuarioRepository;
        this.unidadProductoraRepository =unidadProductoraRepository;
        this.jwtUtil = jwtUtil;
    }
    @Override
    public String save(String token,UsuarioDatos usuarioDatos){
        byte rol = jwtUtil.getRol(token);
        if( rol== 1 || rol == 2) {
            Usuario u = usuarioDatos.getUsuario();
            if (usuarioRepository.findByUser(u.getUser()) == null) {
                try {
                    u.setPassword(argon2.hash(1, 1024, 1, u.getPassword()));
                    UnidadProductora up = unidadProductoraRepository.getById(usuarioDatos.getId_unidad());
                    u.setUnidad_productora(up);
                    usuarioRepository.save(u);
                    return "Success";
                } catch (Exception ignored) {
                }
            }
            return "Failed"; //Usuario ya registrado
        }
        return "Not authorized"; //Rol no autorizado
    }
    @Override
    public Map<String,Object> login(Usuario usuario){
        Map<String,Object> datos = new HashMap<>();
        try{
            Usuario u = usuarioRepository.findByUser(usuario.getUser());
            if(argon2.verify(u.getPassword(),usuario.getPassword())){
                if(!u.isActivo()){
                    u.setActivo(true);
                    usuarioRepository.save(u);
                }
                String tokenJWT = jwtUtil.create(String.valueOf(u.getId()),u.getUser(),u.getRol());
                datos.put("token",tokenJWT);
                u.setPassword(null);
                datos.put("usuario",u);
                return datos;
            }
            datos.put("msg","Contraseña incorrecta");
            return datos;
        }catch(Exception ignored){
        }
        datos.put("msg","Usuario no registrado");
        return datos;
    }
    @Override
    public String logout(Usuario usuario){
        try{
            Usuario u = usuarioRepository.findByUser(usuario.getUser());
            if(u.isActivo()){
                u.setActivo(false);
                usuarioRepository.save(u);
                return "Success";
            }
        }catch (Exception ignored){
        }
        return "Failed";
    }
    @Override
    public List<Usuario> findAll(String token,Pageable pageable){
        byte rol = jwtUtil.getRol(token);
        if( rol== 1 || rol == 2) {
            return usuarioRepository.findAll(pageable).getContent();
        }
        return null;
    }
}

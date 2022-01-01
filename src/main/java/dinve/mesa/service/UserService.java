package dinve.mesa.service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import dinve.mesa.converter.UsuarioDatos;
import dinve.mesa.model.UnidadProductora;
import dinve.mesa.model.Usuario;
import dinve.mesa.repository.UnidadProductoraRepository;
import dinve.mesa.repository.UsuarioRepository;
import dinve.mesa.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("UserService")
public class UserService {
    @Autowired
    @Qualifier("UsuarioRepository")
    private UsuarioRepository usuarioRepository;
    @Autowired
    @Qualifier("UnidadProductoraRepository")
    private UnidadProductoraRepository unidadProductoraRepository;
    @Autowired
    private JWTUtil jwtUtil;
    private Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    public String save(UsuarioDatos usuarioDatos){
        Usuario u = usuarioDatos.getUsuario();
        if(usuarioRepository.findByUser(u.getUser()) == null) {
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

    public List<Usuario> findAll(Pageable pageable){
        return usuarioRepository.findAll(pageable).getContent();
    }
}

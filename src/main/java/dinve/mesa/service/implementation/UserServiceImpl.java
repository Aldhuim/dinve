package dinve.mesa.service.implementation;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import dinve.mesa.converter.PasswordDatos;
import dinve.mesa.converter.UsuarioDatos;
import dinve.mesa.model.UnidadProductora;
import dinve.mesa.model.Usuario;
import dinve.mesa.repository.UnidadProductoraRepository;
import dinve.mesa.repository.UsuarioRepository;
import dinve.mesa.service.UserService;
import dinve.mesa.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
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
        try{
            byte rol = jwtUtil.getRol(token);
            if( rol== 1 || rol == 2) {
                Usuario u = usuarioDatos.getUsuario();
                if (usuarioRepository.findByUser(u.getUser()) == null) {
                    if (u.getRol() < rol){
                        try {
                            u.setPassword(argon2.hash(1, 1024, 1, u.getPassword()));
                            UnidadProductora up = unidadProductoraRepository.getById(usuarioDatos.getId_unidad());
                            u.setUnidad_productora(up);
                            usuarioRepository.save(u);
                            return "Success";
                        } catch (Exception ignored) {
                        }
                    } else{
                        return "Same level in 'rol'"; //No se puede crear un usuario del mismo nivel de rol
                    }
                }
                return "Failed"; //Usuario ya registrado
            }
        }catch(ExpiredJwtException e){
            return "Session expired";
        }
        return "Not authorized"; //Rol no autorizado
    }

    @Override
    public Map<String,Object> login(Usuario usuario){
        Map<String,Object> datos = new HashMap<>();
        try{
            Usuario u = usuarioRepository.findByUser(usuario.getUser());
            if(argon2.verify(u.getPassword(),usuario.getPassword()) && u.getRol() != -1){
                if(!u.isActivo()){
                    u.setActivo(true);
                    usuarioRepository.save(u);
                }
                String tokenJWT = jwtUtil.create(String.valueOf(u.getId()),u.getUser(),u.getRol());
                datos.put("token",tokenJWT);
                u.setPassword(null);
                datos.put("usuario",u);
                return datos;
            } else if (u.getRol() == -1){
                datos.put("msg","Usuario deshabilitado, comunicarse con el administrador si cree que es un error");
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
    public Map<String,Object> findAll(String token,Pageable pageable){
        Map<String,Object> datos = new HashMap<>();
        try{
            byte rol = jwtUtil.getRol(token);
            Usuario u = usuarioRepository.findById(Long.parseLong(jwtUtil.getId(token))).get();
            if( (rol== 1 || rol == 2) && (u.isActivo() == true) ) {
                Page<Usuario> pageUsuario = usuarioRepository.findAll(pageable);
                datos.put("Size",pageUsuario.getSize());
                datos.put("TotalPaginas",pageUsuario.getTotalPages());
                datos.put("TotalElementos",pageUsuario.getTotalElements());
                datos.put("Usuarios",pageUsuario.getContent());
                return datos;
            }
        }catch(ExpiredJwtException e){
            datos.put("Message","Session expired");
        }
        return datos;
    }

    @Override
    public Map<String, Object> getUser(String token){
        Map<String, Object> datos = new HashMap<>();
        String pass_decrypt;
        try{
            String user = jwtUtil.getUser(token);
            Usuario u = usuarioRepository.findByUser(user);
            //Para la password
            pass_decrypt = (u.getPassword());
            System.out.println("PASS: " + pass_decrypt);
            datos.put("usuario", u);
            return datos;
        }catch(ExpiredJwtException e){
            datos.put("menssage", "Session expired");
            return datos;
        }
    }

    @Override
    public String updateUser(String token, UsuarioDatos usuarioDatos, Long id_update){
        try{
            Long id_user_token = Long.parseLong(jwtUtil.getId(token));
            Byte rol_token = jwtUtil.getRol(token);
            Usuario u = usuarioRepository.getById(id_update);
            if (id_user_token == id_update){
                try {
                    u.setUser(usuarioDatos.getUser());
                    u.setNombre(usuarioDatos.getNombre());
                    u.setApellido(usuarioDatos.getApellido());
                    usuarioRepository.save(u);
                    return "Success";
                } catch (Exception ignored) {
                    return "Excepcion";
                }
            } else if(rol_token > usuarioRepository.getById(id_update).getRol()){
                try {
                    u.setUser(usuarioDatos.getUser());
                    u.setNombre(usuarioDatos.getNombre());
                    u.setApellido(usuarioDatos.getApellido());
                    u.setDni(usuarioDatos.getDni());
                    u.setCargo(usuarioDatos.getCargo());
                    u.setUnidad_productora(unidadProductoraRepository.getById(usuarioDatos.getId_unidad()));
                    if (usuarioDatos.getRol() < rol_token){
                        u.setRol(usuarioDatos.getRol());
                        usuarioRepository.save(u);
                        return "Success";
                    } else {
                        usuarioRepository.save(u);
                        return "Success, but you can't put the same level of rol as you";
                    }
                } catch (Exception ignored) {
                    return "Excepcion";
                }
            }
            else {
                return "No puedes editar al usuario con id " + id_update;
            }
        }catch(ExpiredJwtException e){
            return "Session expired";
        }
    }

    /*
    @Override
    public String updatePassword(String token, PasswordDatos passwordDatos){
        try{
            Usuario u = usuarioRepository.findByIdUser(Long.parseLong(jwtUtil.getId(token)));
            //String actual_pass_hash = argon2.hash(1, 1024, 1, passwordDatos.getActual_password());
            String new_pass_hash = argon2.hash(1, 1024, 1, passwordDatos.getNew_password());
            String confirm_new_pass_hash = argon2.hash(1, 1024, 1, passwordDatos.getConfirm_password());
            //System.out.println(u.getPassword());
            //System.out.println(actual_pass_hash);
            if (argon2.verify(passwordDatos.getActual_password(),u.getPassword())) {
                return "Contraseña actual correcta";
            } else {
                return "Contraseña actual incorrecta";
            }
        } catch (ExpiredJwtException e) {
            return "Failed";
        }
    }
    */

    @Override
    public String unableUser(String token, Long id_user){
        try{
            byte rol = jwtUtil.getRol(token);
            Long id = Long.parseLong(jwtUtil.getId(token));
            Usuario u = usuarioRepository.findByIdUser(id_user);
            //Cuando un usuario que no sea admin se quiera deshabilitar a si mismo
            if (id == id_user && rol != 2){
                u.setActivo(false);
                u.setRol(Byte.parseByte("-1"));
                usuarioRepository.save(u);
                //También se debe deshabilitar el token generado para este usuario
                return "Se deshabilitó al usuario";
            }
            //Cuando un administrador quiere deshabilitar a un usuario
            if (id != id_user && rol == 2){
                u.setActivo(false);
                u.setRol(Byte.parseByte("-1"));
                usuarioRepository.save(u);
                return "Se deshabilitó al usuario";
            }
            else {
                return "no se puede deshabilitar a este usuario";
            }
        }catch (ExpiredJwtException e){
            return "Failed";
        }
    }

    @Override
    public String enableUser(String token, Long id_user, String rol_usuario){
        try{
            byte rol = jwtUtil.getRol(token);
            Long id = Long.parseLong(jwtUtil.getId(token));
            Usuario u = usuarioRepository.findByIdUser(id_user);
            //Solo los admins pueden habilitar a los usuarios deshabilitados
            if (rol == 2){
                u.setRol(Byte.parseByte(rol_usuario));
                usuarioRepository.save(u);
                return "Se habilitó al usuario con un rol nivel " + rol_usuario;
            }
            else {
                return "no se puede habilitar a este usuario";
            }
        }catch (ExpiredJwtException e){
            return "Failed";
        }
    }
}

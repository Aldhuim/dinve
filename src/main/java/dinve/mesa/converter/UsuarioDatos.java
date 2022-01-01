package dinve.mesa.converter;

import dinve.mesa.model.UnidadProductora;
import dinve.mesa.model.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDatos {
    private Long id_unidad;
    private String nombre_up;
    private Long id_usuario;
    private String user;
    private String password;
    private String dni;
    private String nombre;
    private String apellido;
    private String cargo;
    private byte rol;

    public Usuario getUsuario(){
        Usuario usuario = new Usuario();
        usuario.setId(id_usuario);
        usuario.setUser(user);
        usuario.setPassword(password);
        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setCargo(cargo);
        usuario.setRol(rol);
        return usuario;
    }
}


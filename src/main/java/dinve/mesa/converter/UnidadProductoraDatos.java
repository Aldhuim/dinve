package dinve.mesa.converter;

import dinve.mesa.model.UnidadProductora;
import dinve.mesa.model.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UnidadProductoraDatos {
    private Long id;
    private String nombre;
    private String departamento;
    private String provincia;
    private String distrito;

    public UnidadProductora getUnidadProductora(){
        UnidadProductora unidadProductora = new UnidadProductora();
        unidadProductora.setId(id);
        unidadProductora.setNombre(nombre);
        unidadProductora.setDepartamento(departamento);
        unidadProductora.setProvincia(provincia);
        unidadProductora.setDistrito(distrito);
        return  unidadProductora;
    }
}

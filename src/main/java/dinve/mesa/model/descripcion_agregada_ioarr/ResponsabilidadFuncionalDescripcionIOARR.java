package dinve.mesa.model.descripcion_agregada_ioarr;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import dinve.mesa.model.formulario.Formulario5B;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "responsabilidad_funcional_descripcion_ioarr")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponsabilidadFuncionalDescripcionIOARR implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipo;
    private float costo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_formulario5b")
    private Formulario5B formulario5b;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponsabilidadFuncionalDescripcionIOARR)) return false;
        return id != null && id.equals(((ResponsabilidadFuncionalDescripcionIOARR) o).getId());
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

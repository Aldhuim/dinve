package dinve.mesa.model.descripcion_agregada_proyecto_programa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "programa_de_inversion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProgramaDeInversion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float total_proyectos;
    private float total_ioarr;
    private float gestion_programa;
    private float estudios_base_ioarr;
    private float total;

    @JsonIgnore
    @JoinColumn(name = "id_responsabilidad_funcional_descripcion_agregada")
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private ResponsabilidadFuncionalDescripcionAgregada responsabilidad_funcional_descripcion_agregada;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProgramaDeInversion)) return false;
        return id != null && id.equals(((ProgramaDeInversion) o).getId());
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

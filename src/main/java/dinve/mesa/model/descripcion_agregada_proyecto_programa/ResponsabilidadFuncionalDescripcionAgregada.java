package dinve.mesa.model.descripcion_agregada_proyecto_programa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import dinve.mesa.model.formulario_tipos.Formulario5A;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "responsabilidad_funcional_descripcion_agregada")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponsabilidadFuncionalDescripcionAgregada implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @JoinColumn(name = "id_formulario5a")
    @ManyToOne(fetch = FetchType.LAZY)
    private Formulario5A formulario5a;

    @OneToOne(mappedBy = "responsabilidad_funcional_descripcion_agregada",cascade = CascadeType.ALL,
            orphanRemoval = true,fetch = FetchType.LAZY)
    private ProyectoDeInversion proyectoDeInversion;

    @OneToOne(mappedBy = "responsabilidad_funcional_descripcion_agregada",cascade = CascadeType.ALL,
            orphanRemoval = true,fetch = FetchType.LAZY)
    private ProgramaDeInversion programaDeInversion;

    public void setProyectoDeInversion(ProyectoDeInversion proyectoDeInversion){
        proyectoDeInversion.setResponsabilidad_funcional_descripcion_agregada(this);
        this.proyectoDeInversion = proyectoDeInversion;
    }

    public void setProgramaDeInversion(ProgramaDeInversion programaDeInversion){
        programaDeInversion.setResponsabilidad_funcional_descripcion_agregada(this);
        this.programaDeInversion = programaDeInversion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponsabilidadFuncionalDescripcionAgregada)) return false;
        return id != null && id.equals(((ResponsabilidadFuncionalDescripcionAgregada) o).getId());
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

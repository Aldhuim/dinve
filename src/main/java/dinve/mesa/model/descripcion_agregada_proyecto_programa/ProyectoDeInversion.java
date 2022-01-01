package dinve.mesa.model.descripcion_agregada_proyecto_programa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "proyecto_de_inversion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProyectoDeInversion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float costo;

    @JsonIgnore
    @JoinColumn(name = "id_responsabilidad_funcional_descripcion_agregada")
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private ResponsabilidadFuncionalDescripcionAgregada responsabilidad_funcional_descripcion_agregada;

    @OneToMany(mappedBy = "proyecto_de_inversion",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<TipoItem> ListaTipoItem;

    @OneToMany(mappedBy = "proyecto_de_inversion",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Capacidad> ListaCapacidad;

    public void addTipoItem(TipoItem tipoItem){
        ListaTipoItem.add(tipoItem);
        tipoItem.setProyecto_de_inversion(this);
    }

    public void addCapacidad(Capacidad capacidad){
        ListaCapacidad.add(capacidad);
        capacidad.setProyecto_de_inversion(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProyectoDeInversion)) return false;
        return id != null && id.equals(((ProyectoDeInversion) o).getId());
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

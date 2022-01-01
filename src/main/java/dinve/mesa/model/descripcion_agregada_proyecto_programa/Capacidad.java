package dinve.mesa.model.descripcion_agregada_proyecto_programa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "capacidad")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Capacidad implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String servicio;
    private String um;
    private String capacidad_produccion;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto_de_inversion")
    private ProyectoDeInversion proyecto_de_inversion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Capacidad)) return false;
        return id != null && id.equals(((Capacidad) o).getId());
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

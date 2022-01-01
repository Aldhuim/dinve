package dinve.mesa.model.descripcion_agregada_proyecto_programa;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tipo_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String item;
    private float costo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto_de_inversion")
    private ProyectoDeInversion proyecto_de_inversion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoItem)) return false;
        return id != null && id.equals(((TipoItem) o).getId());
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

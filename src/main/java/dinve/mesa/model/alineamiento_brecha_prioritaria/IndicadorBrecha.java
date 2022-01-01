package dinve.mesa.model.alineamiento_brecha_prioritaria;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "indicador_brecha")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IndicadorBrecha implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int unidad_medida;
    private String espacio_geografico;
    private int anno;
    private float valor;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_alineamiento_brecha_servicios_publicos_brecha_identificada")
    private AlineamientoBrechaServiciosPublicosBrechaIdentificada alineamiento_brecha_servicios_publicos_brecha_identificada;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IndicadorBrecha)) return false;
        return id != null && id.equals(((IndicadorBrecha) o).getId());
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

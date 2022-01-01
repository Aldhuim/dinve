package dinve.mesa.model.alineamiento_brecha_prioritaria;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import dinve.mesa.model.formulario.Formulario5A;
import dinve.mesa.model.formulario.Formulario5B;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "alineamiento_brecha_servicios_publicos_brecha_identificada")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AlineamientoBrechaServiciosPublicosBrechaIdentificada implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int contribucion_valor;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_formulario5a")
    private Formulario5A formulario5a;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_formulario5b")
    private Formulario5B formulario5b;

    @OneToMany(mappedBy = "alineamiento_brecha_servicios_publicos_brecha_identificada",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<IndicadorBrecha> ListaIndicarBrecha = new ArrayList<>();

    public void addIndicadorBrecha(IndicadorBrecha indicadorBrecha){
        ListaIndicarBrecha.add(indicadorBrecha);
        indicadorBrecha.setAlineamiento_brecha_servicios_publicos_brecha_identificada(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlineamientoBrechaServiciosPublicosBrechaIdentificada)) return false;
        return id != null && id.equals(((AlineamientoBrechaServiciosPublicosBrechaIdentificada) o).getId());
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

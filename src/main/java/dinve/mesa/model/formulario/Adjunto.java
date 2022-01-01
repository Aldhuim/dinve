package dinve.mesa.model.formulario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "adjunto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Adjunto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;

    private String nombre;

    private String direccion;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private Date fecha_creacion;

    @UpdateTimestamp
    private Date fecha_actualizacion;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_formulario5a")
    private Formulario5A formulario5a;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_formulario5b")
    private Formulario5B formulario5b;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Adjunto)) return false;
        return id != null && id.equals(((Adjunto) o).getId());
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

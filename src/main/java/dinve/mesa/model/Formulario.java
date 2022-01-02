package dinve.mesa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dinve.mesa.model.formulario_tipos.Formulario5A;
import dinve.mesa.model.formulario_tipos.Formulario5B;
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
@Table(name = "formulario")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Formulario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private byte estado;

    private byte estado2;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private Date fecha_creacion;

    @UpdateTimestamp
    private Date fecha_actualizacion;

    @JsonIgnore
    @JoinColumn(name = "id_usuario")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    @OneToOne(mappedBy = "formulario",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Formulario5A formulario5A;

    @OneToOne(mappedBy = "formulario",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Formulario5B formulario5B;

    public void setFormulario5A(Formulario5A formulario5A){
        formulario5A.setFormulario(this);
        this.formulario5A = formulario5A;
    }
    public void setFormulario5B(Formulario5B formulario5B){
        formulario5B.setFormulario(this);
        this.formulario5B = formulario5B;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Formulario)) return false;
        return id != null && id.equals(((Formulario) o).getId());
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

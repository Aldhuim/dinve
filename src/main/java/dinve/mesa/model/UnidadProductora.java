package dinve.mesa.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "unidad_productora")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UnidadProductora implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 45)
    private String nombre;
    @Column(nullable = false,length = 45)
    private String departamento;
    @Column(nullable = false,length = 45)
    private String provincia;
    @Column(nullable = false,length = 45)
    private String distrito;
    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private Date fecha_creacion;
    @UpdateTimestamp
    private Date fecha_actualizacion;

    @JsonIgnore
    @OneToMany(
            mappedBy = "unidad_productora",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Usuario> ListaUsuario = new ArrayList<>();

    public void addUsuario(Usuario usuario){
        ListaUsuario.add(usuario);
        usuario.setUnidad_productora(this);
    }
    public void removeUsuario(Usuario usuario){
        ListaUsuario.remove(usuario);
        usuario.setUnidad_productora(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnidadProductora)) return false;
        return id != null && id.equals(((UnidadProductora) o).getId());
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

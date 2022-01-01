package dinve.mesa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,length = 45)
    private String user;

    @Column(nullable = false)
    private String password;

    private byte rol;

    private boolean activo;

    @Column(nullable = false,unique = true,length = 8)
    private String dni;

    @Column(nullable = false,length = 45)
    private String nombre;

    @Column(nullable = false,length = 60)
    private String apellido;

    @Column(nullable = false,length = 60)
    private String cargo;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private Date fecha_creacion;

    @UpdateTimestamp
    private Date fecha_actualizacion;

    @JsonIgnore
    @OneToMany(
            mappedBy = "usuario",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Formulario> formularios = new ArrayList<>();

    //@JsonIgnore
    @JoinColumn(name = "id_unidad_productora")
    @ManyToOne(fetch = FetchType.EAGER)
    private UnidadProductora unidad_productora;

    public void addFormulario(Formulario formulario){
        formularios.add(formulario);
        formulario.setUsuario(this);
    }

    public void deleteFormulario(Formulario formulario){
        formularios.remove(formulario);
        formulario.setUsuario(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        return id != null && id.equals(((Usuario) o).getId());
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}



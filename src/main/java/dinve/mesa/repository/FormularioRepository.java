package dinve.mesa.repository;

import dinve.mesa.model.Formulario;
import dinve.mesa.model.UnidadProductora;
import dinve.mesa.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("FormuarlioRepository")
public interface FormularioRepository extends JpaRepository<Formulario,Long> {
    /*
    Estado=  0: No enviado ; 1: Enviado
    Estado2= 0: Guardado ; 1:Deshabilitado

    Estados posibles:
    Estado=0,Estado2=0 (No enviado y guardado)
    Estado=1,Estado2=0 (Enviado)
    Estado=1,Estado2=1 (Enviado y deshabilitado)
     */

    @Query("select f from Formulario f where f.estado = 1 and f.estado2 = 0")
    Page<Formulario> findAll(Pageable pageable);

    @Query("select f from Formulario f where f.usuario = ?1")
    Page<Formulario> findAllByUsuario(Usuario usuario, Pageable pageable);

    @Query("select f from Formulario f inner join Usuario u on f.usuario = ?1 inner join UnidadProductora up on u.unidad_productora = ?2 where f.estado = 1 and f.estado2 = 0")
    Page<Formulario> findAllByUnidadProductora(Usuario usuario,UnidadProductora unidadProductora,  Pageable pageable);
}

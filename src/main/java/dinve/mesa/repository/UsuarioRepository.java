package dinve.mesa.repository;

import dinve.mesa.model.UnidadProductora;
import dinve.mesa.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository("UsuarioRepository")
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    @Query("select u from Usuario u where u.user = ?1")
    Usuario findByUser(String user);

    @Query("select u from Usuario u where u.id = ?1")
    Usuario findByIdUser(Long id);

    @Query("select u from Usuario u where u.unidad_productora = ?1")
    Page<Usuario> findAllByUnidad_productora(UnidadProductora unidadProductora, Pageable pageable);

    @Query("select u from Usuario u")
    Page<Usuario> findAll(Pageable pageable);



}

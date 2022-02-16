package br.com.seg.econotaxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.seg.econotaxi.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryCustom {

    Usuario findByLogin(@Param("login") String login);

    Usuario findByHashTrocaSenha(String hashTrocaSenha);

    @Query("Select u.nome From Usuario u where u.id = :idUsuario")
    String findNomeById(@Param("idUsuario") Long idUsuario);

    @Query("Select u.idMaxipago from Usuario u where u.id = :idUsuario")
    String findIdMaxipagoById(@Param("idUsuario") Long idUsuario);
    
    @Query("Select u.tipo from Usuario u where u.login = :login")
    Integer findTipoByLogin(@Param("login") String login);

    @Query("Select u from Usuario u where u.celular = :celular ")
	List<Usuario> findByCelular(@Param("celular") String celular);

}
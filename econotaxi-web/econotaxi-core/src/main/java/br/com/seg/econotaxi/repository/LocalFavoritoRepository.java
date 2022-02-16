package br.com.seg.econotaxi.repository;

import br.com.seg.econotaxi.model.LocalFavorito;
import br.com.seg.econotaxi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocalFavoritoRepository extends JpaRepository<LocalFavorito, Long>, LocalFavoritoRepositoryCustom {

	@Query("Select l from LocalFavorito l where l.usuario = :usuario and l.tipo is not null")
    List<LocalFavorito> findByUsuario(@Param("usuario") Usuario usuario);

}
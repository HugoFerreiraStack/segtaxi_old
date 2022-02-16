/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.seg.econotaxi.model.Perfil;

/**
 * @author bruno
 *
 */
public interface PerfilRepository extends JpaRepository<Perfil, Long>, PerfilRepositoryCustom {

	Perfil findById(@Param("id") Long id);
	
}
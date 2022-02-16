/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.seg.econotaxi.model.VeiculoLigue;

/**
 * @author bruno
 *
 */
public interface VeiculoLigueRepository extends JpaRepository<VeiculoLigue, Long>, VeiculoLigueRepositoryCustom {

	VeiculoLigue findById(@Param("id") Long id);

}
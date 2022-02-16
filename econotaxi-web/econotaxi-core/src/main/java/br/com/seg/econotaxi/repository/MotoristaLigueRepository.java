/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.seg.econotaxi.model.MotoristaLigue;

/**
 * @author bruno
 *
 */
public interface MotoristaLigueRepository extends JpaRepository<MotoristaLigue, Long>, MotoristaLigueRepositoryCustom {

	MotoristaLigue findById(@Param("id") Long id);

}
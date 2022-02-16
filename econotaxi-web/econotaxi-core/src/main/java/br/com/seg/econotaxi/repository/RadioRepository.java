/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.seg.econotaxi.model.Radio;

/**
 * @author bruno
 *
 */
public interface RadioRepository extends JpaRepository<Radio, Long>, RadioRepositoryCustom {

	Radio findById(@Param("id") Long id);
	
}
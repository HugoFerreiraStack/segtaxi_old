/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.seg.econotaxi.model.Cidade;

/**
 * @author bruno
 *
 */
public interface CidadeRepository extends JpaRepository<Cidade, Long>, CidadeRepositoryCustom {

	Cidade findById(@Param("id") Long id);
	
}
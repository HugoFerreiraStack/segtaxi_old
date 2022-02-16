/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.seg.econotaxi.model.Funcionalidade;

/**
 * @author bruno
 *
 */
public interface FuncionalidadeRepository extends JpaRepository<Funcionalidade, Long>, 
	FuncionalidadeRepositoryCustom {

	Funcionalidade findById(@Param("id") Long id);
	
}
/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.seg.econotaxi.model.Novidade;

/**
 * @author bruno
 *
 */
public interface NovidadeRepository extends JpaRepository<Novidade, Long>, NovidadeRepositoryCustom {

	Novidade findById(@Param("id") Long id);

}
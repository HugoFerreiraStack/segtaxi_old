/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.seg.econotaxi.model.Percurso;

/**
 * @author bruno
 *
 */
public interface PercursoRepository extends JpaRepository<Percurso, Long>, PercursoRepositoryCustom {

	Percurso findById(@Param("id") Long id);

}
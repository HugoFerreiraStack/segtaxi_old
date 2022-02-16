/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.seg.econotaxi.model.Promocao;

/**
 * @author bruno
 *
 */
public interface PromocaoRepository extends JpaRepository<Promocao, Long>, PromocaoRepositoryCustom {

	Promocao findById(@Param("id") Long id);

}
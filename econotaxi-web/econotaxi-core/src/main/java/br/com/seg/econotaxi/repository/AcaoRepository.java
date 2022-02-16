/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.seg.econotaxi.model.Acao;

/**
 * @author bruno
 *
 */
public interface AcaoRepository extends JpaRepository<Acao, Long>, 
		AcaoRepositoryCustom {

	
}
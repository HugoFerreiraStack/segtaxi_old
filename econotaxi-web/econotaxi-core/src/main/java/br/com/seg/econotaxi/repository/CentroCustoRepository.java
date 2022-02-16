/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.seg.econotaxi.model.CentroCusto;

/**
 * @author bruno
 *
 */
public interface CentroCustoRepository extends JpaRepository<CentroCusto, Long>, 
		CentroCustoRepositoryCustom {

}
/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.seg.econotaxi.model.EmpresaConveniada;

/**
 * @author bruno
 *
 */
public interface EmpresaConveniadaRepository extends JpaRepository<EmpresaConveniada, Long>, 
	EmpresaConveniadaRepositoryCustom {

}
/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Funcionalidade;

/**
 * @author bruno
 *
 */
public interface FuncionalidadeRepositoryCustom {

	List<Long> findByIdUsuario(Long id);
	
	List<Funcionalidade> findAllByOrderByNomeAsc();
	
}
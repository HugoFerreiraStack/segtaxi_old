/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Radio;

/**
 * @author bruno
 *
 */
public interface RadioRepositoryCustom {

	boolean verificaExistenciaRadio(Radio radio);

	Long pesquisarCountRadioPorFiltro(Radio filtro);

	List<Radio> pesquisarRadioPorFiltro(Radio filtro, int first, int pageSize);
	
	List<Radio> findAllByOrderByNomeAsc();
	
}
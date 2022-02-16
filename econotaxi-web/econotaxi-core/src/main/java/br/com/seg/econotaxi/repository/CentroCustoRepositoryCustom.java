/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.CentroCusto;

/**
 * @author bruno
 *
 */
public interface CentroCustoRepositoryCustom {

	Long pesquisarCountCentroCustoPorFiltro(CentroCusto filtro);

	List<CentroCusto> pesquisarCentroCustoPorFiltro(CentroCusto filtro, 
			int first, int pageSize);
	
	List<CentroCusto> recuperarCentroCustos(Long idEmpresaConveniada);
	
}
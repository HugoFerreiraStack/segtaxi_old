/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.EmpresaConveniada;

/**
 * @author bruno
 *
 */
public interface EmpresaConveniadaRepositoryCustom {

	Long pesquisarCountEmpresaConveniadaPorFiltro(EmpresaConveniada filtro);

	List<EmpresaConveniada> pesquisarEmpresaConveniadaPorFiltro(EmpresaConveniada filtro, 
			int first, int pageSize);
	
	List<EmpresaConveniada> recuperarEmpresas();
	
	List<EmpresaConveniada> recuperarEmpresasVoucherEletronico();
	
}
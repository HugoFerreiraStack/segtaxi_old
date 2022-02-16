/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.CentroCusto;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.EmpresaConveniada;
import br.com.seg.econotaxi.model.Voucher;

/**
 * @author bruno
 *
 */
public interface EmpresaConveniadaService {

	Long pesquisarCountEmpresaConveniadaPorFiltro(EmpresaConveniada filtro);

	List<EmpresaConveniada> pesquisarEmpresaConveniadaPorFiltro(EmpresaConveniada filtro, int first, int pageSize);

	void salvarEmpresaConveniada(EmpresaConveniada empresaConveniada);

	EmpresaConveniada consultarPorChave(Long id);

	List<EmpresaConveniada> recuperarEmpresas();

	boolean verificaExistenciaVoucher(String voucher);

	void salvarVoucher(Voucher voucher);

	void enviarEmailVoucher(Corrida corridaNova, EmpresaConveniada empresaConveniada);

	void alterarEmpresaConveniada(EmpresaConveniada empresaConveniadaAlterar);

	void delete(EmpresaConveniada empresaConveniada);

	void enviarEmailVoucherFinalizado(Corrida c, EmpresaConveniada empresa);

	List<EmpresaConveniada> recuperarEmpresasVoucherEletronico();
	
	Long pesquisarCountCentroCustoPorFiltro(CentroCusto filtro);
	
	List<CentroCusto> pesquisarCentroCustoPorFiltro(CentroCusto filtro, int first,
			int pageSize);
	
	List<CentroCusto> recuperarCentroCustos(Long idEmpresaConveniada);
	
	void salvarCentroCusto(CentroCusto centroCusto);

}
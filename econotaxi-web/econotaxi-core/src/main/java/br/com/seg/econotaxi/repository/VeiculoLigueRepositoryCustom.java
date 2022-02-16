/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.MotoristaLigue;
import br.com.seg.econotaxi.model.VeiculoLigue;

/**
 * @author bruno
 *
 */
public interface VeiculoLigueRepositoryCustom {

	Long pesquisarCountPerfilPorFiltro(VeiculoLigue filtro);

	List<VeiculoLigue> pesquisarPerfilPorFiltro(VeiculoLigue filtro, int first, int pageSize);
	
	Integer recuperarQtdVeiculoPorStatus(Integer status);

	List<VeiculoLigue> recuperarVeiculoPorStatus(Integer status);
	
	VeiculoLigue recuperarVeiculoPorRenavan(String renavan);
	
	VeiculoLigue recuperarVeiculoPorMotorista(MotoristaLigue motorista);
	
	List<VeiculoLigue> recuperarVeiculosAuxiliar(MotoristaLigue motoristaAuxiliar);
	
}
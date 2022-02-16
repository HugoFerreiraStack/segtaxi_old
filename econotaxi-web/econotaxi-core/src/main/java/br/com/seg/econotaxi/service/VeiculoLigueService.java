/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.MotoristaLigue;
import br.com.seg.econotaxi.model.VeiculoLigue;

/**
 * @author bruno
 *
 */
public interface VeiculoLigueService {

	void salvarVeiculo(VeiculoLigue veiculo);
	
	VeiculoLigue recuperarVeiculoPorChave(Long chave);

	Long pesquisarCountPerfilPorFiltro(VeiculoLigue filtro);

	List<VeiculoLigue> pesquisarPerfilPorFiltro(VeiculoLigue filtro, int first, int pageSize);

	Integer recuperarQtdVeiculoPorStatus(Integer status);

	List<VeiculoLigue> recuperarVeiculoPorStatus(Integer status);

	VeiculoLigue recuperarVeiculoPorRenavan(String renavan);

	VeiculoLigue recuperarVeiculoPorMotorista(MotoristaLigue motorista);
	
	List<VeiculoLigue> recuperarVeiculosAuxiliar(MotoristaLigue motoristaAuxiliar);
	
}
/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Veiculo;

/**
 * @author bruno
 *
 */
public interface VeiculoRepositoryCustom {

	Long pesquisarCountPerfilPorFiltro(Veiculo filtro);

	List<Veiculo> pesquisarPerfilPorFiltro(Veiculo filtro, int first, int pageSize);
	
	Integer recuperarQtdVeiculoPorStatus(Integer status);

	List<Veiculo> recuperarVeiculoPorStatus(Integer status);
	
	Veiculo recuperarVeiculoPorRenavan(String renavan);
	
	Veiculo recuperarVeiculoPorMotorista(Motorista motorista);
	
	List<Veiculo> recuperarVeiculosAuxiliar(Motorista motoristaAuxiliar);
	
	List<Veiculo> recuperarVeiculosPorCidade(Cidade cidadeSelecionada);
	
	Long pesquisarCountVeiculoEmpresaPorFiltro(Veiculo filtro);

	List<Veiculo> pesquisarVeiculoEmpresaPorFiltro(Veiculo filtro, int first, int pageSize);
	
	List<Veiculo> recuperarVeiculosEmpresa(Long idEmpresaConveniada, Long idCidade);
	
}
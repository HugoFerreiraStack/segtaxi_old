/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Veiculo;
import br.com.seg.econotaxi.model.VeiculoEmpresaConveniada;

/**
 * @author bruno
 *
 */
public interface VeiculoService {

	void salvarVeiculo(Veiculo veiculo);
	
	Veiculo recuperarVeiculoPorChave(Long chave);

	Long pesquisarCountPerfilPorFiltro(Veiculo filtro);

	List<Veiculo> pesquisarPerfilPorFiltro(Veiculo filtro, int first, int pageSize);

	Integer recuperarQtdVeiculoPorStatus(Integer status);

	List<Veiculo> recuperarVeiculoPorStatus(Integer status);

	Veiculo recuperarVeiculoPorRenavan(String renavan);

	Veiculo recuperarVeiculoPorMotorista(Motorista motorista);
	
	List<Veiculo> recuperarVeiculosAuxiliar(Motorista motoristaAuxiliar);

	List<Veiculo> recuperarVeiculosPorCidade(Cidade cidadeSelecionada);
	
	void incluirVeiculoEmpresa(VeiculoEmpresaConveniada veiculoEmpresaConveniada);
	
	void excluirVeiculoEmpresa(VeiculoEmpresaConveniada veiculoEmpresaConveniada);

	Long pesquisarCountVeiculoEmpresaPorFiltro(Veiculo filtro);

	List<Veiculo> pesquisarVeiculoEmpresaPorFiltro(Veiculo filtro, int first, int pageSize);

	List<Veiculo> recuperarVeiculosEmpresa(Long idEmpresaConveniada, Long idCidade);
	
}
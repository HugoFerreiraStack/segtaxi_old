/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Veiculo;
import br.com.seg.econotaxi.model.VeiculoEmpresaConveniada;
import br.com.seg.econotaxi.repository.VeiculoEmpresaConveniadaRepository;
import br.com.seg.econotaxi.repository.VeiculoRepository;
import br.com.seg.econotaxi.service.VeiculoService;

/**
 * @author bruno
 *
 */
@Service("veiculoService")
public class VeiculoServiceImpl implements VeiculoService {

	@Autowired
	private VeiculoRepository veiculoRepository;
	@Autowired
	private VeiculoEmpresaConveniadaRepository veiculoEmpresaConveniadaRepository;
	
	@Override
	public void salvarVeiculo(Veiculo veiculo) {
		veiculoRepository.save(veiculo);
	}

	@Override
	public Veiculo recuperarVeiculoPorChave(Long chave) {
		return veiculoRepository.findById(chave);
	}

	@Override
	public Long pesquisarCountPerfilPorFiltro(Veiculo filtro) {
		return veiculoRepository.pesquisarCountPerfilPorFiltro(filtro);
	}

	@Override
	public List<Veiculo> pesquisarPerfilPorFiltro(Veiculo filtro, int first, int pageSize) {
		return veiculoRepository.pesquisarPerfilPorFiltro(filtro, first, pageSize);
	}

	@Override
	public Integer recuperarQtdVeiculoPorStatus(Integer status) {
		return veiculoRepository.recuperarQtdVeiculoPorStatus(status);
	}

	@Override
	public List<Veiculo> recuperarVeiculoPorStatus(Integer status) {
		return veiculoRepository.recuperarVeiculoPorStatus(status);
	}

	@Override
	public Veiculo recuperarVeiculoPorRenavan(String renavan) {
		return veiculoRepository.recuperarVeiculoPorRenavan(renavan);
	}

	@Override
	public Veiculo recuperarVeiculoPorMotorista(Motorista motorista) {
		return veiculoRepository.recuperarVeiculoPorMotorista(motorista);
	}

	@Override
	public List<Veiculo> recuperarVeiculosAuxiliar(Motorista motoristaAuxiliar) {
		return veiculoRepository.recuperarVeiculosAuxiliar(motoristaAuxiliar);
	}

	@Override
	public List<Veiculo> recuperarVeiculosPorCidade(Cidade cidadeSelecionada) {
		return veiculoRepository.recuperarVeiculosPorCidade(cidadeSelecionada);
	}
	
	@Override
	public void incluirVeiculoEmpresa(VeiculoEmpresaConveniada veiculoEmpresaConveniada) {
		veiculoEmpresaConveniadaRepository.save(veiculoEmpresaConveniada);
	}
	
	@Override
	public void excluirVeiculoEmpresa(VeiculoEmpresaConveniada veiculoEmpresaConveniada) {
		veiculoEmpresaConveniadaRepository.delete(veiculoEmpresaConveniada);
	}

	@Override
	public Long pesquisarCountVeiculoEmpresaPorFiltro(Veiculo filtro) {
		return veiculoRepository.pesquisarCountVeiculoEmpresaPorFiltro(filtro);
	}

	@Override
	public List<Veiculo> pesquisarVeiculoEmpresaPorFiltro(Veiculo filtro, int first, int pageSize) {
		return veiculoRepository.pesquisarVeiculoEmpresaPorFiltro(filtro, first, pageSize);
	}

	@Override
	public List<Veiculo> recuperarVeiculosEmpresa(Long idEmpresaConveniada, Long idCidade) {
		return veiculoRepository.recuperarVeiculosEmpresa(idEmpresaConveniada, idCidade);
	}

}
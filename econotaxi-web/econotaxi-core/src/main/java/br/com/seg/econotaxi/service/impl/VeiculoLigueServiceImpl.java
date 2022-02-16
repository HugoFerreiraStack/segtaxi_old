/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.MotoristaLigue;
import br.com.seg.econotaxi.model.VeiculoLigue;
import br.com.seg.econotaxi.repository.VeiculoLigueRepository;
import br.com.seg.econotaxi.service.VeiculoLigueService;

/**
 * @author bruno
 *
 */
@Service("veiculoLigueService")
public class VeiculoLigueServiceImpl implements VeiculoLigueService {

	@Autowired
	private VeiculoLigueRepository veiculoLigueRepository;
	
	@Override
	public void salvarVeiculo(VeiculoLigue veiculo) {
		veiculoLigueRepository.save(veiculo);
	}

	@Override
	public VeiculoLigue recuperarVeiculoPorChave(Long chave) {
		return veiculoLigueRepository.findById(chave);
	}

	@Override
	public Long pesquisarCountPerfilPorFiltro(VeiculoLigue filtro) {
		return veiculoLigueRepository.pesquisarCountPerfilPorFiltro(filtro);
	}

	@Override
	public List<VeiculoLigue> pesquisarPerfilPorFiltro(VeiculoLigue filtro, int first, int pageSize) {
		return veiculoLigueRepository.pesquisarPerfilPorFiltro(filtro, first, pageSize);
	}

	@Override
	public Integer recuperarQtdVeiculoPorStatus(Integer status) {
		return veiculoLigueRepository.recuperarQtdVeiculoPorStatus(status);
	}

	@Override
	public List<VeiculoLigue> recuperarVeiculoPorStatus(Integer status) {
		return veiculoLigueRepository.recuperarVeiculoPorStatus(status);
	}

	@Override
	public VeiculoLigue recuperarVeiculoPorRenavan(String renavan) {
		return veiculoLigueRepository.recuperarVeiculoPorRenavan(renavan);
	}

	@Override
	public VeiculoLigue recuperarVeiculoPorMotorista(MotoristaLigue motorista) {
		return veiculoLigueRepository.recuperarVeiculoPorMotorista(motorista);
	}

	@Override
	public List<VeiculoLigue> recuperarVeiculosAuxiliar(MotoristaLigue motoristaAuxiliar) {
		return veiculoLigueRepository.recuperarVeiculosAuxiliar(motoristaAuxiliar);
	}

}
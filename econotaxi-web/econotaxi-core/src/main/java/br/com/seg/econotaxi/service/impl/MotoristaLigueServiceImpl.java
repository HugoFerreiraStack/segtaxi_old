/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.MotoristaLigue;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.VeiculoLigue;
import br.com.seg.econotaxi.repository.CorridaRepository;
import br.com.seg.econotaxi.repository.MotoristaLigueRepository;
import br.com.seg.econotaxi.repository.VeiculoLigueRepository;
import br.com.seg.econotaxi.service.MotoristaLigueService;
import br.com.seg.econotaxi.vo.CarrosVO;

/**
 * @author bruno
 *
 */
@Service("motoristaLigueService")
public class MotoristaLigueServiceImpl implements MotoristaLigueService {

	@Autowired
	private MotoristaLigueRepository motoristaLigueRepository;
	@Autowired
	private VeiculoLigueRepository veiculoLigueRepository;
	@Autowired
	private CorridaRepository corridaRepository;
	
	@Override
	public void salvarMotorista(MotoristaLigue motorista) {
		motoristaLigueRepository.save(motorista);
	}

	@Override
	public MotoristaLigue recuperarMotoristaPorChave(Long chave) {
		return motoristaLigueRepository.findById(chave);
	}

	@Override
	public Integer recuperarQtdMotoristaPorStatus(Integer status) {
		return motoristaLigueRepository.recuperarQtdMotoristaPorStatus(status);
	}

	@Override
	public List<MotoristaLigue> recuperarMotoristaPorStatus(Integer status) {
		return motoristaLigueRepository.recuperarMotoristaPorStatus(status);
	}

	@Override
	public Long pesquisarCountMotoristaPorFiltro(MotoristaLigue filtro) {
		return motoristaLigueRepository.pesquisarCountMotoristaPorFiltro(filtro);
	}

	@Override
	public List<MotoristaLigue> pesquisarMotoristaPorFiltro(MotoristaLigue filtro, int first, int pageSize) {
		return motoristaLigueRepository.pesquisarMotoristaPorFiltro(filtro, first, pageSize);
	}

	@Override
	public MotoristaLigue recuperarMotoristaPorUsuario(Usuario usuario) {
		return motoristaLigueRepository.recuperarMotoristaPorUsuario(usuario);
	}

	@Override
	public List<CarrosVO> recuperarMotoristasCidadeOnline(Cidade cidade) {
		return motoristaLigueRepository.recuperarMotoristasCidadeOnline(cidade);
	}

	@Override
	public void excluir(MotoristaLigue motorista) {
		
		if (motorista.getIndicadorPermissionario() != null && motorista.getIndicadorPermissionario().equals(1)
				|| (motorista.getIndicadorParticular() != null && motorista.getIndicadorParticular().equals(1))) {
			
			try {
				VeiculoLigue veiculo = veiculoLigueRepository.recuperarVeiculoPorMotorista(motorista);
				if (veiculo != null && veiculo.getId() != null) {
						veiculoLigueRepository.delete(veiculo);
				}
			} catch (Exception e) {	}
		}

		List<Corrida> corridas = corridaRepository.recuperarCorridasPorMotorista(motorista);
		
		if (corridas != null && corridas.size() > 0) {
			for (Corrida corrida : corridas) {
				corridaRepository.delete(corrida);
			}
		}
		motoristaLigueRepository.delete(motorista);
	}

	@Override
	public List<MotoristaLigue> recuperarMotoristasPorCidade(Cidade cidadeSelecionada) {
		return motoristaLigueRepository.recuperarMotoristasPorCidade(cidadeSelecionada);
	}

}
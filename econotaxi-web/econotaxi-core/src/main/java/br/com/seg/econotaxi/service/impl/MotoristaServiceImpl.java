/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.enums.TipoCorridaEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.Veiculo;
import br.com.seg.econotaxi.repository.CorridaRepository;
import br.com.seg.econotaxi.repository.MotoristaRepository;
import br.com.seg.econotaxi.repository.UsuarioRepository;
import br.com.seg.econotaxi.repository.VeiculoRepository;
import br.com.seg.econotaxi.service.MotoristaService;
import br.com.seg.econotaxi.vo.CarrosVO;

/**
 * @author bruno
 *
 */
@Service("motoristaService")
public class MotoristaServiceImpl implements MotoristaService {

	@Autowired
	private MotoristaRepository motoristaRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private VeiculoRepository veiculoRepository;
	@Autowired
	private CorridaRepository corridaRepository;
	
	@Override
	public void salvarMotorista(Motorista motorista) {
		motoristaRepository.save(motorista);
	}

	@Override
	public Motorista recuperarMotoristaPorChave(Long chave) {
		return motoristaRepository.findById(chave);
	}

	@Override
	public Integer recuperarQtdMotoristaPorStatus(Integer status) {
		return motoristaRepository.recuperarQtdMotoristaPorStatus(status);
	}

	@Override
	public List<Motorista> recuperarMotoristaPorStatus(Integer status) {
		return motoristaRepository.recuperarMotoristaPorStatus(status);
	}

	@Override
	public Long pesquisarCountMotoristaPorFiltro(Motorista filtro) {
		return motoristaRepository.pesquisarCountMotoristaPorFiltro(filtro);
	}

	@Override
	public List<Motorista> pesquisarMotoristaPorFiltro(Motorista filtro, int first, int pageSize) {
		return motoristaRepository.pesquisarMotoristaPorFiltro(filtro, first, pageSize);
	}

	@Override
	public Motorista recuperarMotoristaPorUsuario(Usuario usuario) {
		return motoristaRepository.recuperarMotoristaPorUsuario(usuario);
	}

	@Override
	public List<CarrosVO> recuperarMotoristasCidadeOnline(Cidade cidade) {
		return motoristaRepository.recuperarMotoristasCidadeOnline(cidade);
	}

	@Override
	public void excluir(Motorista motorista) {
		
		if (motorista.getIndicadorPermissionario() != null && motorista.getIndicadorPermissionario().equals(1)
				|| (motorista.getIndicadorParticular() != null && motorista.getIndicadorParticular().equals(1))) {
			
			try {
				Veiculo veiculo = veiculoRepository.recuperarVeiculoPorMotorista(motorista);
				if (veiculo != null && veiculo.getId() != null) {
						veiculoRepository.delete(veiculo);
				}
			} catch (Exception e) {	}
		}
		Usuario usuario = usuarioRepository.findOne(motorista.getIdUsuario());
		
		List<Corrida> corridas = corridaRepository.recuperarCorridasPorMotorista(motorista);
		List<Corrida> corridasUsuario = corridaRepository.recuperarCorridasPorUsuario(usuario, TipoCorridaEnum.CORRIDA.getCodigo());
		List<Corrida> entregasUsuario = corridaRepository.recuperarCorridasPorUsuario(usuario, TipoCorridaEnum.ENTREGA.getCodigo());
		
		if (corridas != null && corridas.size() > 0) {
			for (Corrida corrida : corridas) {
				corridaRepository.delete(corrida);
			}
		}
		
		if (corridasUsuario != null && corridasUsuario.size() > 0) {
			for (Corrida corrida : corridasUsuario) {
				corridaRepository.delete(corrida);
			}
		}
		
		if (entregasUsuario != null && entregasUsuario.size() > 0) {
			for (Corrida corrida : entregasUsuario) {
				corridaRepository.delete(corrida);
			}
		}
		
		motoristaRepository.delete(motorista);
		usuarioRepository.delete(usuario);
		
	}

	@Override
	public List<Motorista> recuperarMotoristasPorCidade(Cidade cidadeSelecionada) {
		return motoristaRepository.recuperarMotoristasPorCidade(cidadeSelecionada);
	}

}
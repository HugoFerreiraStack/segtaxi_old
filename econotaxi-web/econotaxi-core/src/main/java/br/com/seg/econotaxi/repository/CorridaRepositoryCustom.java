/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.EmpresaConveniada;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.MotoristaLigue;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.Veiculo;
import br.com.seg.econotaxi.vo.MinhaCorridaVo;

/**
 * @author bruno
 *
 */
public interface CorridaRepositoryCustom {

	Integer recuperarQtdCorridaPorStatus(Integer status);

	Long pesquisarCountCorridaPorFiltro(Corrida filtro);

	List<Corrida> pesquisarCorridaPorFiltro(Corrida filtro, int first, int pageSize);
	
	List<Corrida> recuperarCorridaPorStatus(Integer status, Integer tipo);
	
	List<Corrida> recuperarCorridasPorUsuario(Usuario usuario, Integer tipo);
	
	List<MinhaCorridaVo> recuperarCorridasPorUsuarioPaginada(Usuario usuario, Integer tipo, Integer paginacao);
	
	List<Corrida> recuperarCorridasPorMotorista(Motorista motorista);
	
	List<Corrida> recuperarCorridasPorCidadeMotorista(Usuario usuario, Integer tipoCorrida, Integer status);
	
	List<Corrida> recuperarEntregasPorCidadeMotorista(Usuario usuario, 
			Integer tipoCorrida, Integer status);
	
	Integer recuperarCorridasBotaoPanico();
	
	List<Corrida> recuperarCorridasPendentesParaCancelamento(Date date, Boolean radio);
	
	Corrida recuperarCorridaPendente(Usuario usuario, Integer tipoUsuario);
	
	List<Corrida> recuperarCorridasPorMotorista(Usuario usuario, Integer tipoCorrida);
	
	BigDecimal recuperarTotalReaisMotorista(Usuario user, Integer tipoCorrida);
	
	Corrida recuperarEntregaPendente(Usuario usuario, Integer tipoUsuario);
	
	List<Corrida> recuperarCorridasPendentesParaMapas();
	
	List<Corrida> recuperarCorridasCorrentesNaoFinalizadas();
	
	Long pesquisarCountCorridaTelePorFiltro(Corrida filtro);

	List<Corrida> pesquisarCorridaTelePorFiltro(Corrida filtro, int first, int pageSize);
	
	BigDecimal recuperarValorConsumido(EmpresaConveniada empresaConveniada, String mesReferencia, Integer tipoTeleTaxi, Long idCentroCusto);
	
	BigDecimal recuperarValorConsumidoMotorista(Motorista motorista, String mesReferencia, Integer tipoTeleTaxi);
	
	Integer recuperarQtdCorridaTelePorTipo(Integer tipo);
	
	Integer recuperarQtdCorridaCanceladaTelePorTipo(Integer tipo);
	
	Integer recuperarQtdCorridaTelePorStatus(Integer status);
	
	List<Corrida> recuperarCorridasPorMotorista(MotoristaLigue motorista);
	
	BigDecimal recuperarValorConsumidoMotoristaFormaPagamento(Motorista motorista, String mesReferencia,
			Integer codigo);
	
	List<MinhaCorridaVo> recuperarCorridasByUsuario(Usuario usuario, Integer codigo);
	
	List<Corrida> recuperarCorridasAgendadasParaMapas();
	
	BigDecimal recuperarValorConsumidoVeiculo(Veiculo veiculo, String mesReferencia, Integer tipoTeleTaxi);
	
}
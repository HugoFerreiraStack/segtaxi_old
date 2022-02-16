/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.EmpresaConveniada;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.Veiculo;
import br.com.seg.econotaxi.model.VisualizacaoCorrida;
import br.com.seg.econotaxi.vo.MinhaCorridaVo;

/**
 * @author bruno
 *
 */
public interface CorridaService {

	void salvarCorrida(Corrida corrida);
	
	Corrida recuperarCorridaPorChave(Long chave);
	
	Integer recuperarQtdCorridaPorStatus(Integer status);

	Long pesquisarCountCorridaPorFiltro(Corrida filtro);

	List<Corrida> pesquisarCorridaPorFiltro(Corrida filtro, int first, int pageSize);

	List<Corrida> recuperarCorridaPorStatus(Integer status, Integer tipo);

	List<Corrida> recuperarCorridasPorUsuario(Usuario usuario, Integer tipo);
	
	List<MinhaCorridaVo> recuperarCorridasPorUsuarioPaginada(Usuario usuario, Integer tipo, Integer paginacao);

	List<Corrida> recuperarCorridasPorMotorista(Motorista motorista);

	List<MinhaCorridaVo> recuperarCorridasPorUsuario(Usuario usuario);

	List<Corrida> recuperarCorridasPorCidadeMotorista(Usuario usuario, 
			Integer tipoCorrida, Integer status);

	Integer recuperarCorridasBotaoPanico();

	List<Corrida> recuperarCorridasPendentesParaCancelamento(Date date, Boolean radio);

	Corrida recuperarCorridaPendente(Usuario usuario, Integer codigo);

	List<Corrida> recuperarCorridasPorMotorista(Usuario usuario, Integer codigo);

	BigDecimal recuperarTotalReaisMotorista(Usuario user, Integer tipoCorrida);

	Corrida recuperarEntregaPendente(Usuario usuario, Integer codigo);

	List<Corrida> recuperarEntregasPorCidadeMotorista(Usuario usuario, Integer codigo, Integer status);

	List<MinhaCorridaVo> recuperarEntregasPorUsuario(Long idUsuario);

	List<Corrida> recuperarCorridasPendentesParaMapas();

	Corrida finalizarCorrida(Corrida corrida);

	List<Corrida> recuperarCorridasCorrentesNaoFinalizadas();

	Long pesquisarCountCorridaTelePorFiltro(Corrida filtro);

	List<Corrida> pesquisarCorridaTelePorFiltro(Corrida filtro, int first, int pageSize);

	BigDecimal recuperarValorConsumido(EmpresaConveniada empresaConveniada, String mesReferencia, Integer tipoTeleTaxi, Long idCentroCusto);

	BigDecimal recuperarValorConsumidoMotorista(Motorista motorista, String mesReferencia, Integer tipoTeleTaxi);

	Integer recuperarQtdCorridaTelePorTipo(Integer tipo);
	
	List<VisualizacaoCorrida> recuperarVisualizacoesCorrida(Corrida corrida);
	
	void salvarVisualizacao(VisualizacaoCorrida visualizacaoCorrida);

	Integer recuperarQtdCorridaCanceladaTelePorTipo(Integer tipo);

	Integer recuperarQtdCorridaTelePorStatus(Integer status);

	BigDecimal recuperarValorConsumidoMotoristaFormaPagamento(Motorista motorista, String mesReferencia,
			Integer codigo);

	void alterar(Corrida corrida);

	List<Corrida> recuperarCorridasAgendadasParaMapas();

	BigDecimal recuperarValorConsumidoVeiculo(Veiculo veiculo, String mesReferencia, Integer tipoTeleTaxi);

}
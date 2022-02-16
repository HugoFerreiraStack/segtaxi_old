/**
 * 
 */
package br.com.seg.econotaxi.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.seg.econotaxi.enums.StatusCorridaEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.service.CidadeService;

/**
 * @author bruno
 *
 */
public class MapaEntregaUtil {

	private static MapaEntregaUtil instance = new MapaEntregaUtil();
	
	private Map<Long, Map<Long, Corrida>> mapaEntregaCidade = new HashMap<Long, Map<Long, Corrida>>();
	private Map<Long, Map<Long, Corrida>> mapaEntregaCidadeAndamento = new HashMap<Long, Map<Long, Corrida>>();
	private Map<Long, Map<Long, Corrida>> mapaEntregaCidadeAgendamento = new HashMap<Long, Map<Long, Corrida>>();
	
	static {
		
		CidadeService cidadeService = (CidadeService) SpringContextUtil.getBean("cidadeService");
		List<Cidade> cidades = cidadeService.recuperarTodasCidades();
		instance.setMapaEntregaCidade(new HashMap<Long, Map<Long, Corrida>>());
		for (Cidade cidade : cidades) {
			instance.getMapaEntregaCidade().put(cidade.getId(), new HashMap<Long, Corrida>());
			instance.getMapaEntregaCidadeAndamento().put(cidade.getId(), new HashMap<Long, Corrida>());
			instance.getMapaEntregaCidadeAgendamento().put(cidade.getId(), new HashMap<Long, Corrida>());
		}
	}
	
	@SuppressWarnings("all")
	public static Corrida recuperarEntregaPorId(Long idCidade, Long idCorrida) {
		
		List<Corrida> corridas = new ArrayList(MapaEntregaUtil.getInstance().getMapaEntregaCidadeAndamento().get(idCidade).values());
		Corrida corridaId = null;
		Optional<Corrida> queryResult = corridas.stream()
				.filter(corrida -> corrida != null)
				.filter(corrida -> corrida.getId().equals(idCorrida))
				.findFirst();
		
		if (queryResult != null && queryResult.isPresent()) {
			corridaId = queryResult.get();
		}
		return corridaId;
	}
	
	@SuppressWarnings("all")
	public static Object recuperarEntregaAgendadaPorId(Long idCidade, Long idCorrida) {

		List<Corrida> corridas = new ArrayList(MapaEntregaUtil.getInstance().getMapaEntregaCidadeAgendamento().get(idCidade).values());
		Corrida corridaId = null;
		Optional<Corrida> queryResult = corridas.stream()
				.filter(corrida -> corrida != null)
				.filter(corrida -> corrida.getId().equals(idCorrida))
				.findFirst();
		
		if (queryResult != null && queryResult.isPresent()) {
			corridaId = queryResult.get();
		}
		return corridaId;
	}
	
	public static void adicionarEntrega(Corrida corrida) {
		MapaEntregaUtil.getInstance().getMapaEntregaCidade().get(corrida.getCidade().getId()).put(corrida.getId(), corrida);
	}
	
	public static void removerEntrega(Corrida corrida) {
		MapaEntregaUtil.getInstance().getMapaEntregaCidade().get(corrida.getCidade().getId()).remove(corrida.getId());
	}
	
	@SuppressWarnings("all")
	public static List<Corrida> recuperarEntregasDisponiveis(Long idCidade) {
		return new ArrayList(MapaEntregaUtil.getInstance().getMapaEntregaCidade().get(idCidade).values());
	}
	
	@SuppressWarnings("all")
	public static List<Corrida> recuperarEntregasFinalizadas(Long idCidade) {
		
		Date dataCorrente = new Date();
		List<Corrida> corridas = new ArrayList(MapaEntregaUtil.getInstance().getMapaEntregaCidadeAndamento().get(idCidade).values());
		List<Corrida> queryResult = corridas.stream()
				.filter(corrida -> corrida != null)
				.filter(corrida -> corrida.getStatus().equals(StatusCorridaEnum.FINALIZADO.getStatus()))
				.filter(corrida -> corrida.getDataFinalizacao().getTime() 
						< recuperaData5Minutos(dataCorrente).getTime())
				.collect(Collectors.toList());
		return queryResult;
	}
	
	private static Date recuperaData5Minutos(Date dataCorrente) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MINUTE, -5);
		return c.getTime();
	}
	
	public static void adicionarEntregaAndamento(Corrida corrida) {
		MapaEntregaUtil.getInstance().getMapaEntregaCidadeAndamento().get(
				corrida.getCidade().getId()).put(corrida.getId(), corrida);
	}
	
	public static void removerEntregaAndamento(Corrida corrida) {
		MapaEntregaUtil.getInstance().getMapaEntregaCidadeAndamento().get(
				corrida.getCidade().getId()).remove(corrida.getId());
	}
	
	@SuppressWarnings("all")
	public static Corrida recuperarEntregaPendenteUsuario(Long idUsuario, Long idCidade) {
		
		List<Corrida> entregas = new ArrayList(MapaEntregaUtil.getInstance().getMapaEntregaCidadeAndamento().get(idCidade).values());
		Corrida entregaPendente = null;
		Optional<Corrida> queryResult = entregas.stream()
				.filter(corrida -> corrida != null)
				.filter(corrida -> corrida.getUsuario().getId().equals(idUsuario))
				.findFirst();
		
		if (queryResult != null && queryResult.isPresent()) {
			entregaPendente = queryResult.get();
		}
		return entregaPendente;
	}
	
	@SuppressWarnings("all")
	public static Corrida recuperarEntregaPendenteMotorista(Long idMotorista, Long idCidade) {
		
		List<Corrida> entregas = new ArrayList(MapaEntregaUtil.getInstance().getMapaEntregaCidadeAndamento().get(idCidade).values());
		Corrida entregaPendente = null;
		Optional<Corrida> queryResult = entregas.stream()
				.filter(corrida -> corrida != null)
				.filter(corrida -> corrida.getMotorista() != null)
				.filter(corrida -> corrida.getMotorista().getId().equals(idMotorista))
				.findFirst();
		
		if (queryResult != null && queryResult.isPresent()) {
			entregaPendente = queryResult.get();
		}
		return entregaPendente;
	}

	/* Métodos Get/Set */
	public Map<Long, Map<Long, Corrida>> getMapaEntregaCidade() {
		return mapaEntregaCidade;
	}
	public void setMapaEntregaCidade(Map<Long, Map<Long, Corrida>> mapaEntregaCidade) {
		this.mapaEntregaCidade = mapaEntregaCidade;
	}
	public Map<Long, Map<Long, Corrida>> getMapaEntregaCidadeAndamento() {
		return mapaEntregaCidadeAndamento;
	}
	public void setMapaEntregaCidadeAndamento(Map<Long, Map<Long, Corrida>> mapaEntregaCidadeAndamento) {
		this.mapaEntregaCidadeAndamento = mapaEntregaCidadeAndamento;
	}
	public Map<Long, Map<Long, Corrida>> getMapaEntregaCidadeAgendamento() {
		return mapaEntregaCidadeAgendamento;
	}
	public void setMapaEntregaCidadeAgendamento(Map<Long, Map<Long, Corrida>> mapaEntregaCidadeAgendamento) {
		this.mapaEntregaCidadeAgendamento = mapaEntregaCidadeAgendamento;
	}
	public static MapaEntregaUtil getInstance() {
		return instance;
	}

	public static void adicionarEntregaAgendamento(Corrida corrida) {
		MapaEntregaUtil.getInstance().getMapaEntregaCidadeAgendamento().get(
				corrida.getCidade().getId()).put(corrida.getId(), corrida);
	}

	public static void removerEntregaAgendamento(Corrida corrida) {
		MapaEntregaUtil.getInstance().getMapaEntregaCidadeAgendamento().get(
				corrida.getCidade().getId()).remove(corrida.getId());
	}

}
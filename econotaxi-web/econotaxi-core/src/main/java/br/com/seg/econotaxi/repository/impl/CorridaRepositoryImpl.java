/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.seg.econotaxi.enums.StatusCorridaEnum;
import br.com.seg.econotaxi.enums.TipoCorridaEnum;
import br.com.seg.econotaxi.enums.TipoUsuarioEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.EmpresaConveniada;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.MotoristaLigue;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.Veiculo;
import br.com.seg.econotaxi.repository.CorridaRepositoryCustom;
import br.com.seg.econotaxi.repository.UsuarioRepository;
import br.com.seg.econotaxi.repository.VeiculoRepository;
import br.com.seg.econotaxi.vo.MinhaCorridaVo;

/**
 * @author bruno
 *
 */
public class CorridaRepositoryImpl extends RepositoryCustomImpl<Corrida, Long> implements CorridaRepositoryCustom {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@PersistenceContext(name = "default")
	private EntityManager entityManager;
	
	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.CorridaRepositoryCustom#recuperarQtdCorridaPorStatus(java.lang.Integer)
	 */
	@Override
	public Integer recuperarQtdCorridaPorStatus(Integer status) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(c) from Corrida c where c.status = :status and c.tipo = :tipo ");
		params.put("status", status);
		params.put("tipo", TipoCorridaEnum.CORRIDA.getCodigo());
		return countByParameters(hql.toString(), params).intValue();
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.CorridaRepositoryCustom#pesquisarCountCorridaPorFiltro(br.com.seg.econotaxi.model.Corrida)
	 */
	@Override
	public Long pesquisarCountCorridaPorFiltro(Corrida filtro) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(c) from Corrida c where 1=1 ");
		definirFiltro(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.CorridaRepositoryCustom#pesquisarCorridaPorFiltro(br.com.seg.econotaxi.model.Corrida, int, int)
	 */
	@Override
	public List<Corrida> pesquisarCorridaPorFiltro(Corrida filtro, int first, int pageSize) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select new br.com.seg.econotaxi.model.Corrida(c, (select u from Usuario u where c.motorista.idUsuario = u.id)) from Corrida c ");
		hql.append("where 1=1 ");
		definirFiltro(filtro, hql, params);
		hql.append("order by c.dataSolicitacao desc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}

	private void definirFiltro(Corrida filtro, StringBuilder hql, Map<String, Object> params) {
		
		if (filtro.getDataInicioPeriodo() != null) {
			hql.append(" and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d') >= :dataInicioPeriodo ");
			params.put("dataInicioPeriodo", new SimpleDateFormat("YYYYMMdd").format(filtro.getDataInicioPeriodo()));
		}
		
		if (filtro.getDataFimPeriodo() != null) {
			hql.append(" and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d') <= :dataFimPeriodo ");
			params.put("dataFimPeriodo", new SimpleDateFormat("YYYYMMdd").format(filtro.getDataFimPeriodo()));
		}
		
		if (filtro.getDataSolicitacao() != null) {
			hql.append(" and DATE_FORMAT(c.dataSolicitacao, '%d/%m/%Y') = :dataSolicitacao ");
			params.put("dataSolicitacao", new SimpleDateFormat("dd/MM/YYYY").format(filtro.getDataSolicitacao()));
		}
		
		if (filtro.getDataFinalizacao() != null) {
			hql.append(" and DATE_FORMAT(c.dataFinalizacao, '%d/%m/%Y') = :dataFinalizacao ");
			params.put("dataFinalizacao", new SimpleDateFormat("dd/MM/YYYY").format(filtro.getDataFinalizacao()));
		}
		
		if (filtro.getMotorista() != null && filtro.getMotorista().getId() != null) {
			hql.append(" and c.motorista.id = :idMotorista ");
			params.put("idMotorista", filtro.getMotorista().getId());
		}
		
		if (filtro.getMotorista() != null && filtro.getMotorista().getNome() != null && !filtro.getMotorista().getNome().isEmpty()) {
			hql.append(" and exists (select u from Usuario u where u.id = c.motorista.idUsuario and UPPER(u.nome) like :nomeMotorista) "); 
			params.put("nomeMotorista", "%" + filtro.getMotorista().getNome().toUpperCase() + "%");
		}
		
		if (filtro.getUsuario() != null && filtro.getUsuario().getId() != null) {
			hql.append(" and c.usuario.id = :idUsuario ");
			params.put("idUsuario", filtro.getUsuario().getId());
		}
		
		if (filtro.getUsuario() != null && filtro.getUsuario().getNome() != null && !filtro.getUsuario().getNome().isEmpty()) {
			hql.append(" and upper(c.usuario.nome) like :nomeUsuario "); 
			params.put("nomeUsuario", "%" + filtro.getUsuario().getNome().toUpperCase() + "%");
		}
		
		if (filtro.getMesReferencia() != null && !filtro.getMesReferencia().isEmpty()) {
			hql.append(" and DATE_FORMAT(c.dataFinalizacao, '%m/%Y') = :mesReferencia "); 
			params.put("mesReferencia", filtro.getMesReferencia());
		}
		
		if (filtro.getNomePassageiro() != null && !filtro.getNomePassageiro().isEmpty()) {
			hql.append(" and upper(c.nomePassageiro) like :nomePassageiro "); 
			params.put("nomePassageiro", "%" + filtro.getNomePassageiro().toUpperCase() + "%");
		}
		
		if (filtro.isCorridaVoucher()) {
			hql.append(" and c.idEmpresaConveniada is not null "); 
		}
		
		if (filtro.getTipo() != null) {
			hql.append(" and c.tipo = :tipo ");
			params.put("tipo", filtro.getTipo());
		}
		
		if (filtro.getStatus() != null) {
			hql.append(" and c.status = :status ");
			params.put("status", filtro.getStatus());
		}
		
		if (filtro.getVeiculo() != null && filtro.getVeiculo().getId() != null) {
			hql.append(" and c.veiculo.id = :idVeiculo ");
			params.put("idVeiculo", filtro.getVeiculo().getId());
		}
		
		if (filtro.getCidade() != null && filtro.getCidade().getId() != null) {
			hql.append(" and c.cidade.id = :idCidade ");
			params.put("idCidade", filtro.getCidade().getId());
		}
		
		if (filtro.getIndicadorPanico() != null && filtro.getIndicadorPanico() == 1) {
			hql.append(" and c.indicadorPanico = :indicadorPanico ");
			params.put("indicadorPanico", filtro.getIndicadorPanico());
		}
		
		if (filtro.getOrigem() != null && !StringUtils.isEmpty(filtro.getOrigem())) {
			hql.append(" and upper(c.origem) like :origem ");
			params.put("origem", "%" + filtro.getOrigem().toUpperCase() + "%");
		}
		
		if (filtro.getDestino() != null && !StringUtils.isEmpty(filtro.getDestino())) {
			hql.append(" and upper(c.destino) like :destino ");
			params.put("destino", "%" + filtro.getDestino().toUpperCase() + "%");
		}
		
		if (filtro.getIndicadorTeleTaxi() != null && filtro.getIndicadorTeleTaxi().equals(1)) {
			hql.append(" and c.indicadorTeleTaxi = 1 ");
		} else {
			hql.append(" and (c.indicadorTeleTaxi is null or c.indicadorTeleTaxi != 1) ");
		}
		
		if (filtro.getTipoTeleTaxi() != null && !filtro.getTipoTeleTaxi().equals(3)) {
			hql.append(" and c.tipoTeleTaxi = :tipoTele ");
			params.put("tipoTele", filtro.getTipoTeleTaxi());
		}
		
		if (filtro.getIdEmpresaConveniada() != null) {
			hql.append(" and c.idEmpresaConveniada = :empresaConveniada ");
			params.put("empresaConveniada", filtro.getIdEmpresaConveniada());
		}
		
		if (filtro.getIdCentroCusto() != null) {
			hql.append(" and c.idCentroCusto = :idCentroCusto ");
			params.put("idCentroCusto", filtro.getIdCentroCusto());
		}
		
		if (filtro.getFormaPagamento() != null) {
			hql.append(" and c.formaPagamento = :formaPagamento ");
			params.put("formaPagamento", filtro.getFormaPagamento());
		}
		
		if (filtro.getVoucher() != null && !filtro.getVoucher().isEmpty()) {
			hql.append(" and upper(c.voucher) = :voucher ");
			params.put("voucher", filtro.getVoucher().toUpperCase());
		}
		
		if (filtro.getCelularPassageiro() != null && !filtro.getCelularPassageiro().isEmpty()) {
			hql.append(" and c.celularPassageiro = :celular ");
			params.put("celular", filtro.getCelularPassageiro());
		}
		
		if (filtro.getUnidadeMotorista() != null && !filtro.getUnidadeMotorista().isEmpty()) {
			hql.append(" and c.veiculo.unidade = :unidade ");
			params.put("unidade", filtro.getUnidadeMotorista());
		}
		
		if (filtro.getPlacaVeiculo() != null && !filtro.getPlacaVeiculo().isEmpty()) {
			hql.append(" and upper(c.veiculo.placa) = :placa ");
			params.put("placa", filtro.getPlacaVeiculo().toUpperCase());
		}
		
	}

	@Override
	public List<Corrida> recuperarCorridaPorStatus(Integer status, Integer tipo) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select new br.com.seg.econotaxi.model.Corrida(c, u) from Corrida c, Usuario u ");
		hql.append("where c.motorista.idUsuario = u.id ");
		hql.append("and c.status = :status and c.tipo = :tipo ");
		params.put("status", status);
		params.put("tipo", tipo);
		hql.append("order by c.dataSolicitacao desc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public List<Corrida> recuperarCorridasPorUsuario(Usuario usuario, Integer tipo) {

		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select c from Corrida c where c.usuario.id = :idUsuario ");
		params.put("idUsuario", usuario.getId());
		hql.append("and c.tipo = :tipo ");
		params.put("tipo", tipo);
		hql.append("order by c.dataSolicitacao desc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}
	
	@Override
	public List<MinhaCorridaVo> recuperarCorridasPorUsuarioPaginada(Usuario usuario, Integer tipo, Integer paginacao) {

		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();

		hql.append("select new br.com.seg.econotaxi.vo.MinhaCorridaVo(c, m.id, m.idUsuario, v.id, ");
		hql.append("v.marca, v.modelo, v.cor, v.placa, v.tipo as tipoVeiculo, m, (select um from Usuario um where um.id = m.idUsuario)) ");
		hql.append("From Corrida c ");
		hql.append("inner join c.motorista m ");
		hql.append("inner join c.veiculo v ");
		hql.append("inner join c.usuario u ");
		
		hql.append("where u.id = :idUsuario ");
		params.put("idUsuario", usuario.getId());
		hql.append("and c.tipo = :tipo ");
		params.put("tipo", tipo);
		hql.append("order by c.dataSolicitacao desc ");
		return findByParametersPaginator(hql.toString(), params, 0, 10 * (paginacao + 1), MinhaCorridaVo.class);
	}

	@Override
	public List<Corrida> recuperarCorridasPorMotorista(Motorista motorista) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select c from Corrida c where c.motorista.id = :idMotorista ");
		params.put("idMotorista", motorista.getId());
		hql.append("order by c.dataSolicitacao desc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public List<Corrida> recuperarCorridasPorCidadeMotorista(Usuario usuario, 
			Integer tipoCorrida, Integer status) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select c from Corrida c ");
		hql.append("where c.cidade.id = (select m.cidade.id from Motorista m where m.idUsuario = :idUsuario) ");
		hql.append("and ((c.tipoVeiculo = (select v.tipo from Veiculo v where v.motorista.idUsuario = :idUsuario) ");
		hql.append("and (c.sexoMotorista is null or upper(c.sexoMotorista) = (select upper(u.sexo) from Usuario u where u.id = :idUsuario)) ");
		hql.append("and (c.indicadorAdaptado is null or c.indicadorAdaptado = (select v.indicadorAdaptado from Veiculo v where v.motorista.idUsuario = :idUsuario)) ");
		hql.append(" ) or c.indicadorPacote = 1) ");
		params.put("idUsuario", usuario.getId());
		hql.append("and c.tipo = :tipo ");
		params.put("tipo", tipoCorrida);
		hql.append("and c.status = :status ");
		params.put("status", status);
		hql.append("order by c.dataSolicitacao asc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}
	
	@Override
	public List<Corrida> recuperarEntregasPorCidadeMotorista(Usuario usuario, 
			Integer tipoCorrida, Integer status) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select c from Corrida c ");
		hql.append("where c.cidade.id = (select m.cidade.id from Motorista m where m.idUsuario = :idUsuario) ");
		params.put("idUsuario", usuario.getId());
		hql.append("and c.tipo = :tipo ");
		params.put("tipo", tipoCorrida);
		hql.append("and c.status = :status ");
		params.put("status", status);
		hql.append("order by c.dataSolicitacao asc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public Integer recuperarCorridasBotaoPanico() {

		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(c) from Corrida c ");
		hql.append("where c.status = :status and c.tipo = :tipo and c.indicadorPanico = 1 ");
		params.put("status", StatusCorridaEnum.CORRENTE.getStatus());
		params.put("tipo", TipoCorridaEnum.CORRIDA.getCodigo());
		return countByParameters(hql.toString(), params).intValue();
	}

	@Override
	public List<Corrida> recuperarCorridasPendentesParaCancelamento(Date date, Boolean radio) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select c from Corrida c ");
		hql.append("where c.status = :status ");
		params.put("status", StatusCorridaEnum.SOLICITADO.getStatus());
		if (radio != null && radio) {
			hql.append("and c.indicadorTeleTaxi = 1 ");
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d%H%i%S') < :dataSolicitacao ");
			params.put("dataSolicitacao", new SimpleDateFormat("YYYYMMddHHmmss")
					.format(recuperarData10Minutos(date)));

		} else {
			hql.append("and c.indicadorTeleTaxi is null ");
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d%H%i%S') < :dataSolicitacao ");
			params.put("dataSolicitacao", new SimpleDateFormat("YYYYMMddHHmmss")
					.format(recuperarData2Minutos(date)));
		}
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	private Date recuperarData2Minutos(Date date) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, -2);
		return c.getTime();
	}
	
	private Date recuperarData10Minutos(Date date) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, -10);
		return c.getTime();
	}

	@Override
	public Corrida recuperarCorridaPendente(Usuario usuario, Integer tipoUsuario) {
		
		Corrida corrida = null;
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select c from Corrida c ");
		hql.append("where c.tipo = :tipo ");
		params.put("tipo", TipoCorridaEnum.CORRIDA.getCodigo());
		if (tipoUsuario.equals(TipoUsuarioEnum.CLIENTE.getCodigo()) 
				|| tipoUsuario.equals(TipoUsuarioEnum.LOJISTA.getCodigo())) {
			hql.append("and c.usuario.id = :idUsuario ");
			params.put("idUsuario", usuario.getId());
		} else if (tipoUsuario.equals(TipoUsuarioEnum.MOTORISTA.getCodigo())) {
			hql.append("and c.motorista.id = :idMotorista ");
			params.put("idMotorista", usuario.getMotorista().getId());
		}
		hql.append("and c.status in (:status) ");
		List<Integer> status = new ArrayList<Integer>();
		status.add(StatusCorridaEnum.SOLICITADO.getStatus());
		status.add(StatusCorridaEnum.A_CAMINHO.getStatus());
		status.add(StatusCorridaEnum.CORRENTE.getStatus());
		params.put("status", status);
		
		try {
			corrida = findOne(hql.toString(), params);
			if (corrida != null && corrida.getMotorista() != null) {
				Usuario user = usuarioRepository.findOne(corrida.getMotorista().getIdUsuario());
				corrida.getMotorista().setNome(user.getNome());
				corrida.getMotorista().setSexo(user.getSexo());
				corrida.getMotorista().setSelfie(user.getImagem());
				if (corrida.getMotorista().getLatitudeCorrente() == null ||
						corrida.getMotorista().getLatitudeCorrente().isEmpty()) {
					corrida.getMotorista().setLatitudeCorrente(user.getLatitudeCorrente());
					corrida.getMotorista().setLongitudeCorrente(user.getLongitudeCorrente());
				}
				corrida.setVeiculo(veiculoRepository.recuperarVeiculoPorMotorista(corrida.getMotorista()));
			}
		} catch (Exception e) {}
		return corrida;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Corrida> recuperarCorridasPorMotorista(Usuario usuario, Integer tipoCorrida) {
		
		List<Corrida> corridasConsulta = new ArrayList<>();
		List<Corrida> corridas = new ArrayList<>();
		int page = 0;
		int size = 10;
		StringBuilder hql = new StringBuilder();
		
		hql.append("select c from Corrida c ");
		hql.append("where c.tipo = :tipo ");
		hql.append("and c.motorista.id = :idMotorista ");
		hql.append("and c.status = :status ");
		
		if (usuario.getMesAno() != null && !usuario.getMesAno().isEmpty()) {
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%m/%Y') = :mesAno ");
		}
		
		hql.append("order by c.dataSolicitacao desc ");
		
		Boolean continua = Boolean.TRUE;
		
		while(continua) {
			Pageable pageable = new PageRequest(page, size);
			
			Query query = entityManager.createQuery(hql.toString());
			
			query.setParameter("tipo", tipoCorrida);
			query.setParameter("idMotorista", usuario.getMotorista().getId());
			query.setParameter("status", StatusCorridaEnum.FINALIZADO.getStatus());
			
			if (usuario.getMesAno() != null && !usuario.getMesAno().isEmpty()) {
				query.setParameter("mesAno", usuario.getMesAno().length() == 6 ? "0" + usuario.getMesAno() : usuario.getMesAno());
			}
			
			query.setFirstResult(pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
			
			corridas = query.getResultList();
			
			if(corridas.size() < size) {
				continua = Boolean.FALSE;
			}
			corridasConsulta.addAll(corridas);
			
			page++;
		}
		
		return corridasConsulta;
		
		/*StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select c from Corrida c ");
		hql.append("where c.tipo = :tipo ");
		params.put("tipo", tipoCorrida);
		hql.append("and c.motorista.id = :idMotorista ");
		params.put("idMotorista", usuario.getMotorista().getId());
		hql.append("and c.status = :status ");
		params.put("status", StatusCorridaEnum.FINALIZADO.getStatus());
		hql.append("order by c.dataSolicitacao desc ");
		return findByParametersWithoutPaginator(hql.toString(), params);*/
	}

	@Override
	public BigDecimal recuperarTotalReaisMotorista(Usuario user, Integer tipoCorrida) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select sum(c.valorFinal) from Corrida c ");
		hql.append("where c.tipo = :tipo ");
		params.put("tipo", tipoCorrida);
		hql.append("and c.motorista.id = :idMotorista ");
		params.put("idMotorista", user.getMotorista().getId());
		hql.append("and c.status = :status ");
		params.put("status", StatusCorridaEnum.FINALIZADO.getStatus());
		
		if (user.getMesAno() != null && !user.getMesAno().isEmpty()) {
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%m/%Y') = :mesAno ");
			params.put("mesAno", user.getMesAno().length() == 6 ? "0" + user.getMesAno() : user.getMesAno());
		} else {
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%Y%m') = :mesCorrente ");
			params.put("mesCorrente", new SimpleDateFormat("YYYYMM").format(new Date()));
		}
		
		hql.append("and c.valorFinal is not null ");
		hql.append("and c.valorFinal > 0 ");
		return sumByParameters(hql.toString(), params);
	}

	@Override
	public Corrida recuperarEntregaPendente(Usuario usuario, Integer tipoUsuario) {
		
		Corrida corrida = null;
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select c from Corrida c ");
		hql.append("where c.tipo = :tipo ");
		params.put("tipo", TipoCorridaEnum.ENTREGA.getCodigo());
		if (tipoUsuario.equals(TipoUsuarioEnum.CLIENTE.getCodigo()) 
				|| tipoUsuario.equals(TipoUsuarioEnum.LOJISTA.getCodigo())) {
			hql.append("and c.usuario.id = :idUsuario ");
			params.put("idUsuario", usuario.getId());
		} else if (tipoUsuario.equals(TipoUsuarioEnum.MOTORISTA.getCodigo())) {
			hql.append("and c.motorista.id = :idMotorista ");
			params.put("idMotorista", usuario.getMotorista().getId());
		}
		hql.append("and c.status in (:status) ");
		List<Integer> status = new ArrayList<Integer>();
		status.add(StatusCorridaEnum.SOLICITADO.getStatus());
		status.add(StatusCorridaEnum.A_CAMINHO.getStatus());
		status.add(StatusCorridaEnum.CORRENTE.getStatus());
		params.put("status", status);
		
		try {
			corrida = findOne(hql.toString(), params);
			if (corrida.getMotorista() != null) {
				Usuario user = usuarioRepository.findOne(corrida.getMotorista().getIdUsuario());
				corrida.getMotorista().setNome(user.getNome());
				corrida.getMotorista().setSexo(user.getSexo());
				corrida.getMotorista().setSelfie(user.getImagem());
				if (corrida != null && corrida.getMotorista().getLatitudeCorrente() == null ||
						corrida.getMotorista().getLatitudeCorrente().isEmpty()) {
					corrida.getMotorista().setLatitudeCorrente(user.getLatitudeCorrente());
					corrida.getMotorista().setLongitudeCorrente(user.getLongitudeCorrente());
				}
				corrida.setVeiculo(veiculoRepository.recuperarVeiculoPorMotorista(corrida.getMotorista()));
			}
		} catch (Exception e) {}
		return corrida;
	}

	@Override
	public List<Corrida> recuperarCorridasPendentesParaMapas() {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select c from Corrida c ");
		hql.append("where c.status in (:status) ");
		List<Integer> status = new ArrayList<Integer>();
		status.add(StatusCorridaEnum.SOLICITADO.getStatus());
		status.add(StatusCorridaEnum.A_CAMINHO.getStatus());
		status.add(StatusCorridaEnum.CORRENTE.getStatus());
		params.put("status", status);
		return findByParametersWithoutPaginator(hql.toString(), params);
	}
	
	public List<Corrida> recuperarCorridasAgendadasParaMapas() {
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select c from Corrida c ");
		hql.append("where c.status = :status ");
		params.put("status", StatusCorridaEnum.AGENDADA.getStatus());
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Corrida> recuperarCorridasCorrentesNaoFinalizadas() {
		
		List<Corrida> corridas = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select c.id, c.id_motorista, c.id_veiculo, c.id_usuario, c.id_cidade from corrida c ");
		sql.append("where c.status = :status ");
		sql.append("and DATE_FORMAT(c.data_inicio + INTERVAL (c.previsao_corrida + 60) MINUTE, '%Y%m%d%H:%i:%S') < :dataCorrente ");
		
		Query query = entityManager.createNativeQuery(sql.toString());
		
		query.setParameter("status", StatusCorridaEnum.CORRENTE.getStatus());
		query.setParameter("dataCorrente", new SimpleDateFormat("YYYYMMddHHmmss").format(new Date()));
		
		List<Object[]> resultado = query.getResultList();
		
		if (resultado != null && !resultado.isEmpty()) {
			corridas = new ArrayList<Corrida>();
			for (Object[] r : resultado) {
				Corrida corrida = new Corrida();
				corrida.setId(getLong(r[0]));
				Motorista m = new Motorista();
				m.setId(getLong(r[1]));
				corrida.setMotorista(m);
				Usuario u = new Usuario();
				u.setId(getLong(r[3]));
				corrida.setUsuario(u);
				Veiculo v = new Veiculo();
				v.setId(getLong(r[2]));
				corrida.setVeiculo(v);
				Cidade c = new Cidade();
				c.setId(getLong(r[4]));
				corrida.setCidade(c);
				corridas.add(corrida);
			}
		}
		
		return corridas;
	}
	
	private Long getLong(Object valor) {
		return (valor != null) ? Long.valueOf(valor.toString()) : null;
	}

	@Override
	public Long pesquisarCountCorridaTelePorFiltro(Corrida filtro) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		//hql.append("select count(c) from Corrida c where 1=1 and exists (select 1 from Usuario u where c.motorista.idUsuario = u.id) ");
		hql.append("select count(c) from Corrida c where 1=1 ");
		definirFiltro(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	@Override
	public List<Corrida> pesquisarCorridaTelePorFiltro(Corrida filtro, int first, int pageSize) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select new br.com.seg.econotaxi.model.Corrida(c, (select u from Usuario u where c.motorista.idUsuario = u.id)) from Corrida c ");
		hql.append("where 1=1 ");
		definirFiltro(filtro, hql, params);
		hql.append("order by c.dataSolicitacao desc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}

	@Override
	public BigDecimal recuperarValorConsumido(EmpresaConveniada empresaConveniada, String mesReferencia, Integer tipoTeleTaxi, Long idCentroCusto) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select sum(c.valorFinal) from Corrida c ");
		hql.append("where c.tipo = :tipo ");
		params.put("tipo", TipoCorridaEnum.CORRIDA.getCodigo());
		hql.append("and c.idEmpresaConveniada = :idEmpresaConveniada ");
		params.put("idEmpresaConveniada", empresaConveniada.getId());
		hql.append("and c.status = :status ");
		params.put("status", StatusCorridaEnum.FINALIZADO.getStatus());
		hql.append("and DATE_FORMAT(c.dataSolicitacao, '%m/%Y') = :mesCorrente ");
		params.put("mesCorrente", mesReferencia);
		if (tipoTeleTaxi != null) {
			hql.append("and c.tipoTeleTaxi = :tipoTeleTaxi ");
			params.put("tipoTeleTaxi", tipoTeleTaxi);
		}
		if (idCentroCusto != null) {
			hql.append("and c.idCentroCusto = :idCentroCusto ");
			params.put("idCentroCusto", idCentroCusto);
		}
		hql.append("and c.valorFinal is not null ");
		hql.append("and c.valorFinal > 0 ");
		return sumByParameters(hql.toString(), params);
	}

	@Override
	public BigDecimal recuperarValorConsumidoMotorista(Motorista motorista, String mesReferencia,
			Integer tipoTeleTaxi) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select sum(c.valorFinal) from Corrida c ");
		hql.append("where c.tipo = :tipo ");
		params.put("tipo", TipoCorridaEnum.CORRIDA.getCodigo());
		hql.append("and c.motorista.id = :idMotorista ");
		params.put("idMotorista", motorista.getId());
		hql.append("and c.status = :status ");
		params.put("status", StatusCorridaEnum.FINALIZADO.getStatus());
		hql.append("and DATE_FORMAT(c.dataSolicitacao, '%m/%Y') = :mesCorrente ");
		params.put("mesCorrente", mesReferencia);
		hql.append("and c.idEmpresaConveniada is not null ");
		if (tipoTeleTaxi != null) {
			hql.append("and c.tipoTeleTaxi = :tipoTeleTaxi ");
			params.put("tipoTeleTaxi", tipoTeleTaxi);
		}
		hql.append("and c.valorFinal is not null ");
		hql.append("and c.valorFinal > 0 ");
		return sumByParameters(hql.toString(), params);
	}

	@Override
	public Integer recuperarQtdCorridaTelePorTipo(Integer tipo) {
		
		Date data = new Date();
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(c) from Corrida c where c.status = :status and c.tipo = :tipo and c.indicadorTeleTaxi = 1 ");
		params.put("status", StatusCorridaEnum.FINALIZADO.getStatus());
		params.put("tipo", TipoCorridaEnum.CORRIDA.getCodigo());
		if (tipo.equals(1)) {
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%m/%Y') = :mesCorrente ");
			params.put("mesCorrente", new SimpleDateFormat("MM/yyyy").format(data));
		} else if (tipo.equals(2)) {
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d') >= :diaCorrente ");
			params.put("diaCorrente", new SimpleDateFormat("yyyyMMdd").format(recuperarDiaAnterior(data)));
		} else if (tipo.equals(3)) {
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d') = :diaCorrente ");
			params.put("diaCorrente", new SimpleDateFormat("yyyyMMdd").format(data));
		} else if (tipo.equals(4)) {
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d%k:%i:%s') >= :turnoManha1 ");
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d%k:%i:%s') <= :turnoManha2 ");
			params.put("turnoManha1", new SimpleDateFormat("yyyyMMdd").format(data) + "044500");
			params.put("turnoManha2", new SimpleDateFormat("yyyyMMdd").format(data) + "110000");
		} else if (tipo.equals(5)) {
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d%k:%i:%s') >= :turnoTarde1 ");
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d%k:%i:%s') <= :turnoTarde2 ");
			params.put("turnoTarde1", new SimpleDateFormat("yyyyMMdd").format(data) + "110000");
			params.put("turnoTarde2", new SimpleDateFormat("yyyyMMdd").format(data) + "171500");
		} else if (tipo.equals(6)) {
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d%k%i%s') >= :turnoNoite1 ");
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d%k%i%s') <= :turnoNoite2 ");
			params.put("turnoNoite1", new SimpleDateFormat("yyyyMMdd").format(data) + "170000");
			params.put("turnoNoite2", new SimpleDateFormat("yyyyMMdd").format(data) + "230000");
		} else if (tipo.equals(7)) {
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d%k%i%s') >= :turnoNoite1 ");
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d%k%i%s') <= :turnoNoite2 ");
			params.put("turnoNoite1", new SimpleDateFormat("yyyyMMdd").format(data) + "230000");
			params.put("turnoNoite2", new SimpleDateFormat("yyyyMMdd").format(data) + "044500");
		}
		return countByParameters(hql.toString(), params).intValue();
	}
	
	@Override
	public Integer recuperarQtdCorridaCanceladaTelePorTipo(Integer tipo) {
		
		Date data = new Date();
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(c) from Corrida c where (c.status = :status or c.status = :status1) and c.tipo = :tipo and c.indicadorTeleTaxi = 1 ");
		params.put("status", StatusCorridaEnum.CANCELADA.getStatus());
		params.put("status1", StatusCorridaEnum.CANCELADA_TEMPO.getStatus());
		params.put("tipo", TipoCorridaEnum.CORRIDA.getCodigo());
		if (tipo.equals(1)) {
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%m/%Y') = :mesCorrente ");
			params.put("mesCorrente", new SimpleDateFormat("MM/yyyy").format(data));
		} else if (tipo.equals(2)) {
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d') >= :diaCorrente ");
			params.put("diaCorrente", new SimpleDateFormat("yyyyMMdd").format(recuperarDiaAnterior(data)));
		} else if (tipo.equals(3)) {
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d') = :diaCorrente ");
			params.put("diaCorrente", new SimpleDateFormat("yyyyMMdd").format(data));
		} else if (tipo.equals(4)) {
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d%k:%i:%s') >= :turnoManha1 ");
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d%k:%i:%s') <= :turnoManha2 ");
			params.put("turnoManha1", new SimpleDateFormat("yyyyMMdd").format(recuperarDiaAnterior(data)) + "044500");
			params.put("turnoManha2", new SimpleDateFormat("yyyyMMdd").format(recuperarDiaAnterior(data)) + "110000");
		} else if (tipo.equals(5)) {
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d%k:%i:%s') >= :turnoTarde1 ");
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d%k:%i:%s') <= :turnoTarde2 ");
			params.put("turnoTarde1", new SimpleDateFormat("yyyyMMdd").format(recuperarDiaAnterior(data)) + "110000");
			params.put("turnoTarde2", new SimpleDateFormat("yyyyMMdd").format(recuperarDiaAnterior(data)) + "171500");
		} else if (tipo.equals(6)) {
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d%k%i%s') >= :turnoNoite1 ");
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d%k%i%s') <= :turnoNoite2 ");
			params.put("turnoNoite1", new SimpleDateFormat("yyyyMMdd").format(recuperar2DiasAnterior(data)) + "170000");
			params.put("turnoNoite2", new SimpleDateFormat("yyyyMMdd").format(recuperarDiaAnterior(data)) + "230000");
		} else if (tipo.equals(7)) {
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d%k%i%s') >= :turnoNoite1 ");
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%Y%m%d%k%i%s') <= :turnoNoite2 ");
			params.put("turnoNoite1", new SimpleDateFormat("yyyyMMdd").format(recuperar2DiasAnterior(data)) + "230000");
			params.put("turnoNoite2", new SimpleDateFormat("yyyyMMdd").format(recuperarDiaAnterior(data)) + "044500");
		}
		return countByParameters(hql.toString(), params).intValue();
	}

	private Date recuperarDiaAnterior(Date data) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.add(Calendar.DAY_OF_MONTH, -1);
		return c.getTime();
	}
	
	private Date recuperar2DiasAnterior(Date data) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.add(Calendar.DAY_OF_MONTH, -2);
		return c.getTime();
	}

	@Override
	public Integer recuperarQtdCorridaTelePorStatus(Integer status) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(c) from Corrida c where c.status = :status and c.tipo = :tipo and c.indicadorTeleTaxi = 1 ");
		params.put("status", status);
		params.put("tipo", TipoCorridaEnum.CORRIDA.getCodigo());
		return countByParameters(hql.toString(), params).intValue();
	}

	@Override
	public List<Corrida> recuperarCorridasPorMotorista(MotoristaLigue motorista) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select c from Corrida c where c.motoristaLigue.id = :idMotorista ");
		params.put("idMotorista", motorista.getId());
		hql.append("order by c.dataSolicitacao desc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public BigDecimal recuperarValorConsumidoMotoristaFormaPagamento(Motorista motorista, String mesReferencia,
			Integer codigo) {
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select sum(c.valorFinal) from Corrida c ");
		hql.append("where c.tipo = :tipo ");
		params.put("tipo", TipoCorridaEnum.CORRIDA.getCodigo());
		hql.append("and c.motorista.id = :idMotorista ");
		params.put("idMotorista", motorista.getId());
		hql.append("and c.status = :status ");
		params.put("status", StatusCorridaEnum.FINALIZADO.getStatus());
		hql.append("and DATE_FORMAT(c.dataSolicitacao, '%m/%Y') = :mesCorrente ");
		params.put("mesCorrente", mesReferencia);
		if (codigo != null) {
			hql.append("and c.formaPagamento = :formaPagamento ");
			params.put("formaPagamento", codigo);
		}
		hql.append("and c.valorFinal is not null ");
		hql.append("and c.valorFinal > 0 ");
		return sumByParameters(hql.toString(), params);
	}

	@Override
	public List<MinhaCorridaVo> recuperarCorridasByUsuario(Usuario usuario, Integer tipoCorrida) {
		
		List<MinhaCorridaVo> corridasConsulta = new ArrayList<>();
		List<MinhaCorridaVo> corridas = new ArrayList<>();
		int page = 0;
		int size = 10;
		StringBuilder hql = new StringBuilder();
		
		hql.append("select new br.com.seg.econotaxi.vo.MinhaCorridaVo(c.id, concat(c.origem, ' - ', c.origemEndereco), "); 
		hql.append("concat(c.destino, ' - ', c.destinoEndereco), ");
		hql.append("m.id, m.idUsuario, v.id, v.marca, v.modelo, v.cor, v.placa, v.tipo, ");
		hql.append("c.dataSolicitacao, c.dataInicio, c.dataFinalizacao, c.classificacaoCorrida, m, c.valorFinal, c.status)");
		hql.append("From Corrida c ");
		hql.append("inner join c.motorista m ");
		hql.append("inner join c.veiculo v ");
		hql.append("inner join c.usuario u ");
		hql.append("where u.id = :idUsuario and c.tipo = :tipo "); 
		
		if (usuario.getMesAno() != null && !usuario.getMesAno().isEmpty()) {
			hql.append("and DATE_FORMAT(c.dataSolicitacao, '%m/%Y') = :mesAno ");
		}
		
		hql.append("order by c.dataSolicitacao desc ");
		
		Boolean continua = Boolean.TRUE;
		
		while(continua) {
			Pageable pageable = new PageRequest(page, size);
			
			Query query = entityManager.createQuery(hql.toString());
			
			query.setParameter("tipo", tipoCorrida);
			query.setParameter("idUsuario", usuario.getId());
			
			if (usuario.getMesAno() != null && !usuario.getMesAno().isEmpty()) {
				query.setParameter("mesAno", usuario.getMesAno().length() == 6 ? "0" + usuario.getMesAno() : usuario.getMesAno());
			}
			
			query.setFirstResult(pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
			
			corridas = query.getResultList();
			
			if(corridas.size() < size) {
				continua = Boolean.FALSE;
			}
			corridasConsulta.addAll(corridas);
			
			page++;
		}
		return corridasConsulta;
	}
	
	public BigDecimal recuperarValorConsumidoVeiculo(Veiculo veiculo, String mesReferencia, Integer tipoTeleTaxi) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select sum(c.valorFinal) from Corrida c ");
		hql.append("where c.tipo = :tipo ");
		params.put("tipo", TipoCorridaEnum.CORRIDA.getCodigo());
		hql.append("and c.veiculo.id = :idVeiculo ");
		params.put("idVeiculo", veiculo.getId());
		hql.append("and c.status = :status ");
		params.put("status", StatusCorridaEnum.FINALIZADO.getStatus());
		hql.append("and DATE_FORMAT(c.dataSolicitacao, '%m/%Y') = :mesCorrente ");
		params.put("mesCorrente", mesReferencia);
		hql.append("and c.idEmpresaConveniada is not null ");
		if (tipoTeleTaxi != null) {
			hql.append("and c.tipoTeleTaxi = :tipoTeleTaxi ");
			params.put("tipoTeleTaxi", tipoTeleTaxi);
		}
		hql.append("and c.valorFinal is not null ");
		hql.append("and c.valorFinal > 0 ");
		return sumByParameters(hql.toString(), params);
	}
	
}
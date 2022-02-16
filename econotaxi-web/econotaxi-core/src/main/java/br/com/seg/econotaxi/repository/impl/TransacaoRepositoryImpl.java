/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.seg.econotaxi.enums.StatusTransacaoEnum;
import br.com.seg.econotaxi.model.Transacao;
import br.com.seg.econotaxi.repository.TransacaoRepositoryCustom;

/**
 * @author bruno
 *
 */
public class TransacaoRepositoryImpl extends RepositoryCustomImpl<Transacao, Long> 
	implements TransacaoRepositoryCustom {

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.TransacaoRepositoryCustom#pesquisarCountTransacaoPorFiltro(br.com.seg.econotaxi.model.Transacao)
	 */
	@Override
	public Long pesquisarCountTransacaoPorFiltro(Transacao filtro) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(t) from Transacao t where 1=1 ");
		definirFiltro(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.TransacaoRepositoryCustom#pesquisarTransacaoPorFiltro(br.com.seg.econotaxi.model.Transacao, int, int)
	 */
	@Override
	public List<Transacao> pesquisarTransacaoPorFiltro(Transacao filtro, int first, int pageSize) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select new br.com.seg.econotaxi.model.Transacao(t, u) ");
		hql.append("from Transacao t, Usuario u where t.corrida.motorista.idUsuario = u.id ");
		definirFiltro(filtro, hql, params);
		hql.append("order by t.dataTransacao desc  ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}

	private void definirFiltro(Transacao filtro, StringBuilder hql, Map<String, Object> params) {
		
		if (filtro.getCorrida() != null && filtro.getCorrida().getDataInicioPeriodo() != null) {
			hql.append(" and DATE_FORMAT(t.corrida.dataSolicitacao, '%Y%m%d') >= :dataInicioPeriodo ");
			params.put("dataInicioPeriodo", new SimpleDateFormat("YYYYMMdd").format(filtro.getCorrida().getDataInicioPeriodo()));
		}
		
		if (filtro.getCorrida() != null && filtro.getCorrida().getDataFimPeriodo() != null) {
			hql.append(" and DATE_FORMAT(t.corrida.dataSolicitacao, '%Y%m%d') <= :dataFimPeriodo ");
			params.put("dataFimPeriodo", new SimpleDateFormat("YYYYMMdd").format(filtro.getCorrida().getDataFimPeriodo()));
		}
		
		if (filtro.getCorrida() != null && filtro.getCorrida().getFormaPagamento() != null) {
			hql.append(" and t.corrida.formaPagamento = :formaPagamento ");
			params.put("formaPagamento", filtro.getCorrida().getFormaPagamento());
		}
		
		if (filtro.getDataTransacao() != null) {
			hql.append(" and DATE_FORMAT(t.dataTransacao, '%d/%m/%Y') = :data ");
			params.put("data", new SimpleDateFormat("dd/MM/YYYY").format(filtro.getDataTransacao()));
		}
		
		if (filtro.getCorrida() != null && filtro.getCorrida().getMotorista() != null 
				&& filtro.getCorrida().getMotorista().getId() != null) {
			hql.append(" and t.corrida.motorista.id = :idMotorista ");
			params.put("idMotorista", filtro.getCorrida().getMotorista().getId());
		}
		
		if (filtro.getCorrida() != null && filtro.getCorrida().getMotorista() != null 
				&& filtro.getCorrida().getMotorista().getNome() != null && !filtro.getCorrida().getMotorista().getNome().isEmpty()) {
			hql.append(" and exists (select u from Usuario u where u.id = t.corrida.motorista.idUsuario and u.nome like :nomeMotorista) "); 
			params.put("nomeMotorista", "%" + filtro.getCorrida().getMotorista().getNome().toUpperCase() + "%");
		}
		
		if (filtro.getCorrida() != null && filtro.getCorrida().getUsuario() != null 
				&& filtro.getCorrida().getUsuario().getId() != null) {
			hql.append(" and t.corrida.usuario.id = :idUsuario ");
			params.put("idUsuario", filtro.getCorrida().getUsuario().getId());
		}
		
		if (filtro.getCorrida() != null && filtro.getCorrida().getUsuario() != null 
				&& filtro.getCorrida().getUsuario().getNome() != null && !filtro.getCorrida().getUsuario().getNome().isEmpty()) {
			hql.append(" and (t.corrida.usuario.nome like :nomeUsuario or t.corrida.nomePassageiro like :nomePassageiro) "); 
			params.put("nomeUsuario", "%" + filtro.getCorrida().getUsuario().getNome().toUpperCase() + "%");
		}
		
		if (filtro.getCorrida() != null && filtro.getCorrida().getCidade() != null && filtro.getCorrida().getCidade().getId() != null) {
			hql.append(" and t.corrida.cidade.id = :idCidade ");
			params.put("idCidade", filtro.getCorrida().getCidade().getId());
		}
		
		if (filtro.getStatus() != null) {
			hql.append(" and t.status = :status ");
			params.put("status", filtro.getStatus());
		}
		
	}

	@Override
	public List<Transacao> recuperarTransacoesPendentesEnvio() {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select new br.com.seg.econotaxi.model.Transacao(t, u) ");
		hql.append("from Transacao t, Usuario u where t.corrida.motorista.idUsuario = u.id ");
		hql.append("and (t.indicadorEmailEnviado is null or t.indicadorEmailEnviado != 1) ");
		hql.append("and (t.status = :statusAprovado or t.status = :statusRejeitado) ");
		params.put("statusAprovado", StatusTransacaoEnum.PAGAMENTO_APROVADO.getStatus());
		params.put("statusRejeitado", StatusTransacaoEnum.PAGAMENTO_REJEITADO.getStatus());
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

}
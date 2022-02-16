/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import br.com.seg.econotaxi.model.Notificacao;
import br.com.seg.econotaxi.repository.NotificacaoRepositoryCustom;

/**
 * @author bruno
 *
 */
public class NotificacaoRepositoryImpl extends RepositoryCustomImpl<Notificacao, Long>
	implements NotificacaoRepositoryCustom {

	@Override
	public Long pesquisarCountNotificacaoPorFiltro(Notificacao filtro) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(n) from Notificacao n where n.id != 1 ");
		definirFiltro(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	@Override
	public List<Notificacao> pesquisarNotificacaoPorFiltro(Notificacao filtro, int first, int pageSize) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select n from Notificacao n where n.id != 1 ");
		definirFiltro(filtro, hql, params);
		hql.append("order by n.dataNotificacao desc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}
	
	private void definirFiltro(Notificacao filtro, StringBuilder hql, Map<String, Object> params) {
		
		if (filtro.getTexto() != null && !StringUtils.isEmpty(filtro.getTexto())) {
			hql.append(" and upper(n.texto) like :texto ");
			params.put("texto", "%" + filtro.getTexto().toUpperCase() + "%");
		}
		
	}

	@Override
	public List<Notificacao> recuperarNotificacoes2Dias() {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select n from Notificacao n ");
		hql.append("where n.id != 1 and n.publico not in (5, 6, 7, 10, 11) ");
		hql.append("and DATE_FORMAT(n.dataNotificacao, '%Y%m%d') >= :dataNotificacao ");
		params.put("dataNotificacao", new SimpleDateFormat("YYYYMMdd").format(recuperaData2Dias(new Date())));
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	private Date recuperaData2Dias(Date data) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.add(Calendar.DAY_OF_MONTH, -1);
		return c.getTime();
	}
	
}
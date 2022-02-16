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

import br.com.seg.econotaxi.model.Notificacao;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.UsuarioNotificacao;
import br.com.seg.econotaxi.repository.UsuarioNotificacaoRepositoryCustom;

/**
 * @author bruno
 *
 */
public class UsuarioNotificacaoRepositoryImpl extends RepositoryCustomImpl<UsuarioNotificacao, Long> 
		implements UsuarioNotificacaoRepositoryCustom {

	public List<UsuarioNotificacao> recuperarUltimasNotificacoes(Usuario usuario) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select un from UsuarioNotificacao un, Notificacao n where n.id = un.idNotificacao and un.idUsuario = :idUsuario ");
		params.put("idUsuario", usuario.getId());
		hql.append("and DATE_FORMAT(n.dataNotificacao, '%Y%m%d') >= :dataNotificacao ");
		params.put("dataNotificacao", new SimpleDateFormat("YYYYMMdd").format(recuperaDataDias(new Date())));
		hql.append("order by un.dataNotificacao desc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public List<UsuarioNotificacao> recuperarNotificacoesNaoVistas(Usuario usuario) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select un from UsuarioNotificacao un, Notificacao n where n.id = un.idNotificacao and un.idUsuario = :idUsuario ");
		params.put("idUsuario", usuario.getId());
		hql.append("and (un.indicadorVisto is null or un.indicadorVisto != 1) ");
		hql.append("and DATE_FORMAT(n.dataNotificacao, '%Y%m%d') >= :dataNotificacao ");
		params.put("dataNotificacao", new SimpleDateFormat("YYYYMMdd").format(recuperaDataDias(new Date())));
		hql.append("order by un.dataNotificacao desc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public boolean verificaExistenciaNotificacaoUsuario(Usuario usuario, Notificacao notificacao) {
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(un.id) from UsuarioNotificacao un where un.idUsuario = :idUsuario ");
		params.put("idUsuario", usuario.getId());
		hql.append("and un.idNotificacao = :idNotificacao ");
		params.put("idNotificacao", notificacao.getId());
		return countByParameters(hql.toString(), params) > 0;
	}

	@Override
	public List<UsuarioNotificacao> recuperarNotificacoesUsuario(Notificacao notificacao) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select un from UsuarioNotificacao un where un.idNotificacao = :idNotificacao ");
		params.put("idNotificacao", notificacao.getId());
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public List<UsuarioNotificacao> recuperarNotificacoesNaoVistas() {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select un from UsuarioNotificacao un, Notificacao n where n.id = un.idNotificacao ");
		hql.append("and (un.indicadorVisto is null or un.indicadorVisto != 1) ");
		hql.append("and DATE_FORMAT(n.dataNotificacao, '%Y%m%d') >= :dataNotificacao ");
		params.put("dataNotificacao", new SimpleDateFormat("YYYYMMdd").format(recuperaDataDias(new Date())));
		return findByParametersWithoutPaginator(hql.toString(), params);
	}
	
	private Date recuperaDataDias(Date data) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.add(Calendar.DAY_OF_MONTH, -1);
		return c.getTime();
	}

}
/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.seg.econotaxi.model.Acao;
import br.com.seg.econotaxi.repository.AcaoRepositoryCustom;

/**
 * @author bruno
 *
 */
public class AcaoRepositoryImpl extends RepositoryCustomImpl<Acao, Long> implements AcaoRepositoryCustom {

	@Override
	public Long pesquisarCountAcaoPorFiltro(Acao filtro) {
		
		Map<String,Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder();
		hql.append("select count(a.id) from Acao a ");
		buildWhereByFilter(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	@Override
	public List<Acao> pesquisarAcaoPorFiltro(Acao filtro, int first, int pageSize) {
		
		Map<String,Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder();
		hql.append("select a from Acao a ");
		buildWhereByFilter(filtro, hql, params);
		hql.append(" order by a.dataAcao desc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}

	/**
	 * Método responsável por realizar consulta auditoria por filtro.
	 * 
	 * @param filtro o filtro da consulta
	 * @param hql hql da consulta
	 * @param params parâmetros da consulta
	 */
	private void buildWhereByFilter(Acao filtro, StringBuilder hql, Map<String, Object> params) {
		
		hql.append("where 1 = 1 ");
		/*if (filtro.getClasse() != null && !StringUtils.isEmpty(filtro.getClasse())) {
			hql.append(" and upper(a.classe) like :classe ");
			params.put("classe", "%" + filtro.getClasse().toUpperCase() + "%");
		}
		if (filtro.getIdUsuario() != null) {
			hql.append(" and a.idUsuario = :idUsuario ");
			params.put("idUsuario", filtro.getIdUsuario());
		}*/
		
	}

}

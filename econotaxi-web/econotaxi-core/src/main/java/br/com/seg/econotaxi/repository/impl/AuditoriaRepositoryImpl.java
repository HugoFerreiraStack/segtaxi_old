package br.com.seg.econotaxi.repository.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import br.com.seg.econotaxi.model.Auditoria;
import br.com.seg.econotaxi.repository.AuditoriaRepositoryCustom;

public class AuditoriaRepositoryImpl extends RepositoryCustomImpl<Auditoria, Long> implements AuditoriaRepositoryCustom {

	@Override
	public List<Auditoria> pesquisarPorFiltro(Auditoria filtro, int first, int pageSize) {
		
		Map<String,Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder();
		hql.append("select a from Auditoria a ");
		buildWhereByFilter(filtro, hql, params);
		hql.append(" order by a.dataEvento desc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}

	@Override
	public Long countPorFiltro(Auditoria filtro) {
		
		Map<String,Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder();
		hql.append("select count(a.id) from Auditoria a ");
		buildWhereByFilter(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}
	
	/**
	 * Método responsável por realizar consulta auditoria por filtro.
	 * 
	 * @param filtro o filtro da consulta
	 * @param hql hql da consulta
	 * @param params parâmetros da consulta
	 */
	private void buildWhereByFilter(Auditoria filtro, StringBuilder hql, Map<String, Object> params) {
		
		hql.append("where 1 = 1 ");
		if (filtro.getClasse() != null && !StringUtils.isEmpty(filtro.getClasse())) {
			hql.append(" and upper(a.classe) like :classe ");
			params.put("classe", "%" + filtro.getClasse().toUpperCase() + "%");
		}
		if (filtro.getIdUsuario() != null) {
			hql.append(" and a.idUsuario = :idUsuario ");
			params.put("idUsuario", filtro.getIdUsuario());
		}
		if(filtro.getDataInicioPeriodo() != null && !filtro.getDataInicioPeriodo().equals("")) {
			hql.append(" and DATE_FORMAT(a.dataEvento, '%d/%m/%Y') > :dataInicio ");
			params.put("dataInicio", new SimpleDateFormat("dd/MM/YYYY").format(filtro.getDataInicioPeriodo()));
		}
		if (filtro.getDataFimPeriodo() != null && !filtro.getDataFimPeriodo().equals("")) {
			hql.append(" and DATE_FORMAT(a.dataEvento, '%d/%m/%Y %H:%i:%S') < :dataFim ");
			params.put("dataFim", new SimpleDateFormat("dd/MM/YYYY").format(filtro.getDataFimPeriodo()) + " 23:59:59");
		}
		
	}
	
}
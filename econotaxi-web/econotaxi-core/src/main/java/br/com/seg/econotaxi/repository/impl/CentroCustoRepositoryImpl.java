/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.seg.econotaxi.model.CentroCusto;
import br.com.seg.econotaxi.repository.CentroCustoRepositoryCustom;

/**
 * @author bruno
 *
 */
public class CentroCustoRepositoryImpl extends RepositoryCustomImpl<CentroCusto, Long> 
	implements CentroCustoRepositoryCustom {

	@Override
	public Long pesquisarCountCentroCustoPorFiltro(CentroCusto filtro) {
		
		Map<String,Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder();
		hql.append("select count(c.id) from CentroCusto c ");
		buildWhereByFilter(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	@Override
	public List<CentroCusto> pesquisarCentroCustoPorFiltro(CentroCusto filtro, int first,
			int pageSize) {
		
		Map<String,Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder();
		hql.append("select c from CentroCusto c ");
		buildWhereByFilter(filtro, hql, params);
		hql.append(" order by c.nome asc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}

	/**
	 * Método responsável por realizar consulta por filtro.
	 * 
	 * @param filtro o filtro da consulta
	 * @param hql hql da consulta
	 * @param params parâmetros da consulta
	 */
	private void buildWhereByFilter(CentroCusto filtro, StringBuilder hql, Map<String, Object> params) {
		
		hql.append("where 1 = 1 ");
		
		if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
			hql.append(" and upper(c.nome) like :nome ");
			params.put("nome", "%" + filtro.getNome().toUpperCase() + "%");
		}
		if (filtro.getIdEmpresaConveniada() != null) {
			hql.append(" and c.empresaConveniada.id = :idEmpresaConveniada ");
			params.put("idEmpresaConveniada", filtro.getIdEmpresaConveniada());
		}
		
	}

	@Override
	public List<CentroCusto> recuperarCentroCustos(Long idEmpresaConveniada) {
		
		Map<String,Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder();
		hql.append("select c from CentroCusto c ");
		hql.append("where c.empresaConveniada.id = :idEmpresaConveniada ");
		params.put("idEmpresaConveniada", idEmpresaConveniada);
		hql.append(" order by c.nome asc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

}
/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.seg.econotaxi.model.EmpresaConveniada;
import br.com.seg.econotaxi.repository.EmpresaConveniadaRepositoryCustom;

/**
 * @author bruno
 *
 */
public class EmpresaConveniadaRepositoryImpl extends RepositoryCustomImpl<EmpresaConveniada, Long> 
	implements EmpresaConveniadaRepositoryCustom {

	@Override
	public Long pesquisarCountEmpresaConveniadaPorFiltro(EmpresaConveniada filtro) {
		
		Map<String,Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder();
		hql.append("select count(e.id) from EmpresaConveniada e ");
		buildWhereByFilter(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	@Override
	public List<EmpresaConveniada> pesquisarEmpresaConveniadaPorFiltro(EmpresaConveniada filtro, int first,
			int pageSize) {
		
		Map<String,Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder();
		hql.append("select e from EmpresaConveniada e ");
		buildWhereByFilter(filtro, hql, params);
		hql.append(" order by e.nome asc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}

	/**
	 * Método responsável por realizar consulta por filtro.
	 * 
	 * @param filtro o filtro da consulta
	 * @param hql hql da consulta
	 * @param params parâmetros da consulta
	 */
	private void buildWhereByFilter(EmpresaConveniada filtro, StringBuilder hql, Map<String, Object> params) {
		
		hql.append("where 1 = 1 ");
		
		if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
			hql.append(" and upper(e.nome) like :nome ");
			params.put("nome", "%" + filtro.getNome().toUpperCase() + "%");
		}
		if (filtro.getEmail() != null && !filtro.getEmail().isEmpty()) {
			hql.append(" and upper(e.email) like :email ");
			params.put("email", "%" + filtro.getEmail().toUpperCase() + "%");
		}
		
	}

	@Override
	public List<EmpresaConveniada> recuperarEmpresas() {
		
		Map<String,Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder();
		hql.append("select e from EmpresaConveniada e ");
		hql.append(" order by e.nome asc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public List<EmpresaConveniada> recuperarEmpresasVoucherEletronico() {
		Map<String,Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder();
		hql.append("select e from EmpresaConveniada e ");
		hql.append("where e.indicadorVoucherEletronico in (1, 6) ");
		hql.append(" order by e.nome asc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}
	
}
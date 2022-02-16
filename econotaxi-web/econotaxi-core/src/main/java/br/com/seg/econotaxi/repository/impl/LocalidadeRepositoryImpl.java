/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import br.com.seg.econotaxi.model.Localidade;
import br.com.seg.econotaxi.repository.LocalidadeRepositoryCustom;

/**
 * @author bruno
 *
 */
public class LocalidadeRepositoryImpl extends RepositoryCustomImpl<Localidade, Long> 
	implements LocalidadeRepositoryCustom {

	@Override
	public Long pesquisarCountLocalidadePorFiltro(Localidade filtro) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(l) from Localidade l where 1=1 ");
		definirFiltro(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	@Override
	public List<Localidade> pesquisarLocalidadePorFiltro(Localidade filtro, int first, int pageSize) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select l from Localidade l where 1=1 ");
		definirFiltro(filtro, hql, params);
		hql.append("order by l.nome asc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}

	private void definirFiltro(Localidade filtro, StringBuilder hql, Map<String, Object> params) {
		
		if (filtro.getNome() != null && !StringUtils.isEmpty(filtro.getNome())) {
			hql.append(" and upper(l.nome) like :nome ");
			params.put("nome", "%" + filtro.getNome().toUpperCase() + "%");
		}
		
		if (filtro.getCidade() != null && filtro.getCidade().getId() != null) {
			hql.append(" and l.cidade.id = :idCidade ");
			params.put("idCidade", filtro.getCidade().getId());
		}
		
	}

	@Override
	public List<Localidade> recuperarTodasLocalidadesPorTipo(Integer tipo, Long cidade) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select l from Localidade l where 1=1 ");
		if (tipo != null) {
			hql.append(" and l.tipo = :tipo ");
			params.put("tipo", tipo);
		}
		if (cidade != null) {
			hql.append(" and l.cidade.id = :idCidade ");
			params.put("idCidade", cidade);
		}
		hql.append("order by l.nome asc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}
	
}
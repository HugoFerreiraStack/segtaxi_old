/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import br.com.seg.econotaxi.model.Promocao;
import br.com.seg.econotaxi.repository.PromocaoRepositoryCustom;

/**
 * @author bruno
 *
 */
public class PromocaoRepositoryImpl extends RepositoryCustomImpl<Promocao, Long> 
	implements PromocaoRepositoryCustom {

	@Override
	public List<Promocao> recuperarUltimasPromocoes() {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select p from Promocao p ");
		hql.append("order by p.dataPromocao desc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public Long pesquisarCountPromocaoPorFiltro(Promocao filtro) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(p) from Promocao p where 1=1 ");
		definirFiltro(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	@Override
	public List<Promocao> pesquisarPromocaoPorFiltro(Promocao filtro, int first, int pageSize) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select p from Promocao p where 1=1 ");
		definirFiltro(filtro, hql, params);
		hql.append("order by p.dataPromocao asc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}
	
	private void definirFiltro(Promocao filtro, StringBuilder hql, Map<String, Object> params) {
		
		if (filtro.getTexto() != null && !StringUtils.isEmpty(filtro.getTexto())) {
			hql.append(" and upper(p.texto) like :texto ");
			params.put("texto", "%" + filtro.getTexto().toUpperCase() + "%");
		}
		
	}
	
}
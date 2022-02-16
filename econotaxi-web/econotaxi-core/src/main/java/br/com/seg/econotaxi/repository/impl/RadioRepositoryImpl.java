/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import br.com.seg.econotaxi.model.Radio;
import br.com.seg.econotaxi.repository.RadioRepositoryCustom;

/**
 * @author bruno
 *
 */
public class RadioRepositoryImpl extends RepositoryCustomImpl<Radio, Long>
		implements RadioRepositoryCustom {

	@Override
	public boolean verificaExistenciaRadio(Radio radio) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(r) from Radio r where upper(r.nome) = :nome ");
		params.put("nome", radio.getNome().toUpperCase());
		hql.append("and r.cidade.id = :idCidade ");
		params.put("idCidade", radio.getCidade().getId());
		if (radio.getId() != null) {
			hql.append("and r.id != :idRadio ");
			params.put("idRadio", radio.getId());
		}
		return countByParameters(hql.toString(), params) > 0;
	}

	@Override
	public Long pesquisarCountRadioPorFiltro(Radio filtro) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(r) from Radio r where 1=1 ");
		definirFiltro(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	@Override
	public List<Radio> pesquisarRadioPorFiltro(Radio filtro, int first, int pageSize) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select r from Radio r where 1=1 ");
		definirFiltro(filtro, hql, params);
		hql.append("order by r.nome asc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}

	private void definirFiltro(Radio filtro, StringBuilder hql, Map<String, Object> params) {
		
		if (filtro.getNome() != null && !StringUtils.isEmpty(filtro.getNome())) {
			hql.append(" and upper(r.nome) like :nome ");
			params.put("nome", "%" + filtro.getNome().toUpperCase() + "%");
		}
		
		if (filtro.getCidade() != null && !StringUtils.isEmpty(filtro.getCidade().getId())) {
			hql.append(" and r.cidade.id = :idCidade ");
			params.put("idCidade", filtro.getCidade().getId());
		}
		
	}

	@Override
	public List<Radio> findAllByOrderByNomeAsc() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
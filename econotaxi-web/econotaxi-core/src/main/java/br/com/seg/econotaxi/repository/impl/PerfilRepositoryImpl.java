/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import br.com.seg.econotaxi.model.Perfil;
import br.com.seg.econotaxi.repository.PerfilRepositoryCustom;

/**
 * @author bruno
 *
 */
public class PerfilRepositoryImpl extends RepositoryCustomImpl<Perfil, Long>
		implements PerfilRepositoryCustom {

	@Override
	public List<Perfil> recuperarPerfisAtivos() {
		
		StringBuilder hql = new StringBuilder();
		hql.append("select p from Perfil p ");
		hql.append("order by p.nome asc ");
		return findByParametersWithoutPaginator(hql.toString(), null);
	}

	@Override
	public boolean verificaExistenciaPerfil(Perfil grupo) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(p) from Perfil p ");
		hql.append("where upper(p.nome) = :nome ");
		params.put("nome", grupo.getNome().toUpperCase());
		if (grupo.getId() != null) {
			hql.append("and p.id != :idPerfil ");
			params.put("idPerfil", grupo.getId());
		}
		return countByParameters(hql.toString(), params) > 0;
	}

	@Override
	public Long pesquisarCountPerfilPorFiltro(Perfil filtro) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(p) from Perfil p where 1=1 ");
		definirFiltro(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	@Override
	public List<Perfil> pesquisarPerfilPorFiltro(Perfil filtro, int first, int pageSize) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select p from Perfil p where 1=1 ");
		definirFiltro(filtro, hql, params);
		hql.append("order by p.nome asc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}

	private void definirFiltro(Perfil filtro, StringBuilder hql, Map<String, Object> params) {
		
		if (filtro.getNome() != null && !StringUtils.isEmpty(filtro.getNome())) {
			hql.append(" and upper(p.nome) like :nome ");
			params.put("nome", "%" + filtro.getNome().toUpperCase() + "%");
		}
		
	}

	@Override
	public List<Perfil> findAllByOrderByNomeAsc() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
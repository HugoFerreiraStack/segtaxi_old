/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import br.com.seg.econotaxi.model.Lojista;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.repository.LojistaRepositoryCustom;

/**
 * @author bruno
 *
 */
public class LojistaRepositoryImpl extends RepositoryCustomImpl<Lojista, Long> 
		implements LojistaRepositoryCustom {

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.MotoristaRepositoryCustom#recuperarQtdMotoristaPorStatus(java.lang.Integer)
	 */
	@Override
	public Integer recuperarQtdLojistaPorStatus(Integer status) {

		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(l) from Lojista l where l.status = :status ");
		params.put("status", status);
		return countByParameters(hql.toString(), params).intValue();
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.MotoristaRepositoryCustom#recuperarMotoristaPorStatus(java.lang.Integer)
	 */
	@Override
	public List<Lojista> recuperarLojistaPorStatus(Integer status) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select new br.com.seg.econotaxi.model.Lojista(l, u) from Lojista l, ");
		hql.append("Usuario u where u.id = l.idUsuario and l.status = :status ");
		params.put("status", status);
		return findByParametersWithoutPaginator(hql.toString(), params);
	}
	
	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.MotoristaRepositoryCustom#pesquisarMotoristaPorFiltro(br.com.seg.econotaxi.model.Motorista, int, int)
	 */
	@Override
	public List<Lojista> pesquisarLojistaPorFiltro(Lojista filtro, int first, int pageSize) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select new br.com.seg.econotaxi.model.Lojista(l, u) from Lojista l, Usuario u where u.id = l.idUsuario ");
		definirFiltro(filtro, hql, params);
		hql.append("order by u.nome asc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.MotoristaRepositoryCustom#pesquisarCountMotoristaPorFiltro(br.com.seg.econotaxi.model.Motorista)
	 */
	@Override
	public Long pesquisarCountLojistaPorFiltro(Lojista filtro) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(l) from Lojista l, Usuario u where u.id = l.idUsuario ");
		definirFiltro(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	private void definirFiltro(Lojista filtro, StringBuilder hql, Map<String, Object> params) {
		
		if (filtro.getNome() != null && !StringUtils.isEmpty(filtro.getNome())) {
			hql.append(" and upper(u.nome) like :nome ");
			params.put("nome", "%" + filtro.getNome().toUpperCase() + "%");
		}
		
		if (filtro.getNomeLoja() != null && !StringUtils.isEmpty(filtro.getNomeLoja())) {
			hql.append(" and upper(u.nomeLoja) like :nomeLoja ");
			params.put("nomeLoja", "%" + filtro.getNomeLoja().toUpperCase() + "%");
		}
		
		if (filtro.getId() != null) {
			hql.append(" and l.id = :idLojista ");
			params.put("idLojista", filtro.getId());
		}
		
		if (filtro.getIdCidade() != null) {
			hql.append(" and upper(l.idCidade) like :idCidade ");
			params.put("idCidade", "%" + filtro.getIdCidade() + "%");
		}
		
		if (filtro.getSexo() != null 
				&& !StringUtils.isEmpty(filtro.getSexo())) {
			hql.append(" and upper(u.sexo) = :sexo ");
			params.put("sexo", filtro.getSexo().toUpperCase());
		}
		
		if (filtro.getStatus() != null) {
			hql.append(" and l.status = :status ");
			params.put("status", filtro.getStatus());
		}
		
	}

	@Override
	public Lojista recuperarLojistaPorUsuario(Usuario usuario) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select l from Lojista l where l.idUsuario = :idUsuario ");
		params.put("idUsuario", usuario.getId());
		return findOne(hql.toString(), params);
	}
	
}
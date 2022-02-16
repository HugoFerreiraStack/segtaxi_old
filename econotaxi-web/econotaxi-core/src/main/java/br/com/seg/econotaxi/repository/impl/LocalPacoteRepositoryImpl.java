/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import br.com.seg.econotaxi.model.LocalPacote;
import br.com.seg.econotaxi.model.Pacote;
import br.com.seg.econotaxi.repository.LocalPacoteRepositoryCustom;

/**
 * @author bruno
 *
 */
public class LocalPacoteRepositoryImpl extends RepositoryCustomImpl<LocalPacote, Long> implements LocalPacoteRepositoryCustom {

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.LocalPacoteRepositoryCustom#pesquisarCountPerfilPorFiltro(br.com.seg.econotaxi.model.LocalPacote)
	 */
	@Override
	public Long pesquisarCountPerfilPorFiltro(LocalPacote filtro) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(lp) from LocalPacote lp where 1=1 ");
		definirFiltro(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.LocalPacoteRepositoryCustom#pesquisarLocalPacotePorFiltro(br.com.seg.econotaxi.model.LocalPacote, int, int)
	 */
	@Override
	public List<LocalPacote> pesquisarLocalPacotePorFiltro(LocalPacote filtro, int first, int pageSize) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select lp from LocalPacote lp ");
		definirFiltro(filtro, hql, params);
		hql.append("order by lp.id asc  ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}

	private void definirFiltro(LocalPacote filtro, StringBuilder hql, Map<String, Object> params) {
		
		if (filtro.getNome() != null && !StringUtils.isEmpty(filtro.getNome())) {
			hql.append(" and upper(lp.nome) like :nome ");
			params.put("nome", "%" + filtro.getNome().toUpperCase() + "%");
		}
		
		if (filtro.getIdPacote() != null && filtro.getIdPacote() != null) {
			hql.append(" and upper(lp.idPacote) = :idPacote ");
			params.put("idPacote", filtro.getIdPacote());
		}
	}

	@Override
	public List<LocalPacote> findAllByOrderByNomeAsc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LocalPacote> recuperarLocaisPorPacote(Pacote pacote) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select lp from LocalPacote lp ");
		hql.append("where lp.idPacote = :idPacote ");
		params.put("idPacote", pacote.getId());
		hql.append("order by lp.id asc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public LocalPacote recuperarUltimoLocalPacote(Pacote pac) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select lp from LocalPacote lp ");
		hql.append("where lp.idPacote = :idPacote ");
		params.put("idPacote", pac.getId());
		hql.append("order by lp.id desc ");
		return findOne(hql.toString(), params);
	}

}
/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.Percurso;
import br.com.seg.econotaxi.repository.PercursoRepositoryCustom;

/**
 * @author bruno
 *
 */
public class PercursoRepositoryImpl extends RepositoryCustomImpl<Percurso, Long> 
		implements PercursoRepositoryCustom {

	@PersistenceContext(name = "default")
	private EntityManager entityManager;
	
	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.PercursoRepositoryCustom#pesquisarCountPercursoPorFiltro(br.com.seg.econotaxi.model.Percurso)
	 */
	@Override
	public Long pesquisarCountPercursoPorFiltro(Percurso filtro) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(p) from Percurso p where 1=1 ");
		definirFiltro(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.PercursoRepositoryCustom#pesquisarPercursoPorFiltro(br.com.seg.econotaxi.model.Percurso, int, int)
	 */
	@Override
	public List<Percurso> pesquisarPercursoPorFiltro(Percurso filtro, int first, int pageSize) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select p from Percurso p where 1=1 ");
		definirFiltro(filtro, hql, params);
		hql.append("order by p.data desc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}

	private void definirFiltro(Percurso filtro, StringBuilder hql, Map<String, Object> params) {
		
		if (filtro.getData() != null) {
			hql.append(" and DATE_FORMAT(p.data, '%d/%m/%Y') = :data ");
			params.put("data", new SimpleDateFormat("dd/MM/YYYY").format(filtro.getData()));
		}
		
		if (filtro.getCorrida() != null && filtro.getCorrida().getMotorista() != null 
				&& filtro.getCorrida().getMotorista().getId() != null) {
			hql.append(" and p.corrida.motorista.id = :idMotorista ");
			params.put("idMotorista", filtro.getCorrida().getMotorista().getId());
		}
		
		if (filtro.getCorrida() != null && filtro.getCorrida().getUsuario() != null 
				&& filtro.getCorrida().getUsuario().getId() != null) {
			hql.append(" and p.corrida.usuario.id = :idUsuario ");
			params.put("idUsuario", filtro.getCorrida().getUsuario().getId());
		}
		
	}

	@Override
	public List<Percurso> recuperarPercursosCorrida(Corrida corrida) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select p from Percurso p ");
		hql.append("where p.corrida.id = :idCorrida ");
		params.put("idCorrida", corrida.getId());
		hql.append("order by p.data asc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public Long segundosParadoPercurso(Corrida corrida) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("select sum(a.qtd) from (select p.latitude, p.longitude, count(*) as qtd from taxi.percurso p "); 
		sql.append("where p.id_corrida = :idCorrida ");
		sql.append("group by p.latitude, p.longitude ");
		sql.append("having count(p.id_corrida) > 1) as a ");
		
		Query query = entityManager.createNativeQuery(sql.toString());
		
		query.setParameter("idCorrida", corrida.getId());
		
		BigDecimal resultado = (BigDecimal) query.getSingleResult();
		
		return resultado != null ? resultado.longValue() : 0;
	}

}
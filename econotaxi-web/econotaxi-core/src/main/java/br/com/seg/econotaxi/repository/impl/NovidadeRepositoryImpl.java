/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import br.com.seg.econotaxi.model.Novidade;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.repository.NovidadeRepositoryCustom;

/**
 * @author bruno
 *
 */
public class NovidadeRepositoryImpl extends RepositoryCustomImpl<Novidade, Long> 
	implements NovidadeRepositoryCustom {

	@Override
	public List<Novidade> recuperarUltimasNovidades(Usuario usuario) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select n from Novidade n ");
		hql.append("where DATE_FORMAT(n.dataNovidade, '%Y%m%d') >= :dataNovidade ");
		params.put("dataNovidade", new SimpleDateFormat("YYYYMMdd").format(recuperaData60Dias(new Date())));
		hql.append("and exists (select 1 from UsuarioNovidade un where un.id.idUsuario = :idUsuario ");
		hql.append("and un.id.idNovidade = n.id) ");
		params.put("idUsuario", usuario.getId());
		hql.append("order by n.dataNovidade desc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public Long pesquisarCountNovidadePorFiltro(Novidade filtro) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(n) from Novidade n where 1=1 ");
		definirFiltro(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	@Override
	public List<Novidade> pesquisarNovidadePorFiltro(Novidade filtro, int first, int pageSize) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select n from Novidade n where 1=1 ");
		definirFiltro(filtro, hql, params);
		hql.append("order by n.dataNovidade desc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}
	
	private void definirFiltro(Novidade filtro, StringBuilder hql, Map<String, Object> params) {
		
		if (filtro.getTexto() != null && !StringUtils.isEmpty(filtro.getTexto())) {
			hql.append(" and upper(n.texto) like :texto ");
			params.put("texto", "%" + filtro.getTexto().toUpperCase() + "%");
		}
		
	}

	@Override
	public List<Novidade> recuperarNovidades2Dias() {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select n from Novidade n ");
		hql.append("where DATE_FORMAT(n.dataNovidade, '%Y%m%d') >= :dataNovidade ");
		params.put("dataNovidade", new SimpleDateFormat("YYYYMMdd").format(recuperaData2Dias(new Date())));
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	private Date recuperaData2Dias(Date data) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.add(Calendar.DAY_OF_MONTH, -1);
		return c.getTime();
	}
	
	private Date recuperaData60Dias(Date data) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.add(Calendar.DAY_OF_MONTH, -60);
		return c.getTime();
	}

	@Override
	public List<Novidade> recuperarNovidadesNaoVistas(Usuario usuario) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select n from Novidade n ");
		hql.append("where exists (select 1 from UsuarioNovidade un where un.id.idUsuario = :idUsuario ");
		hql.append("and (un.indicadorVisto is null or un.indicadorVisto != 1)) ");
		params.put("idUsuario", usuario.getId());
		hql.append("and DATE_FORMAT(n.dataNovidade, '%Y%m%d') >= :dataNovidade ");
		params.put("dataNovidade", new SimpleDateFormat("YYYYMMdd").format(recuperaDataDias(new Date())));
		return findByParametersWithoutPaginator(hql.toString(), params);
	}
	
	private Date recuperaDataDias(Date data) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.add(Calendar.DAY_OF_MONTH, -1);
		return c.getTime();
	}
	
}
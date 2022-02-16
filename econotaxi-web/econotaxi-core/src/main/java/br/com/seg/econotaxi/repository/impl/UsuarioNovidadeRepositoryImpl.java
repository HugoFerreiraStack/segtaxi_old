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

import br.com.seg.econotaxi.model.Novidade;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.UsuarioNovidade;
import br.com.seg.econotaxi.repository.UsuarioNovidadeRepositoryCustom;

/**
 * @author bruno
 *
 */
public class UsuarioNovidadeRepositoryImpl extends RepositoryCustomImpl<UsuarioNovidade, Long> 
		implements UsuarioNovidadeRepositoryCustom {

	@Override
	public List<UsuarioNovidade> recuperarNovidadesUsuario(Usuario usuario) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select un from UsuarioNovidade un where un.id.idUsuario = :idUsuario ");
		hql.append("and (un.indicadorVisto is null or un.indicadorVisto != 1) ");
		params.put("idUsuario", usuario.getId());
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public List<UsuarioNovidade> recuperarNovidadesUsuario(Novidade novidade) {

		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select un from UsuarioNovidade un where un.id.idNovidade = :idNovidade ");
		params.put("idNovidade", novidade.getId());
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public List<UsuarioNovidade> recuperarNovidadesNaoVistas() {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select un from UsuarioNovidade un, Novidade n where n.id = un.id.idNovidade ");
		hql.append("and (un.indicadorVisto is null or un.indicadorVisto != 1) ");
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
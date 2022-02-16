/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.seg.econotaxi.model.Familia;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.repository.FamiliaRepositoryCustom;

/**
 * @author bruno
 *
 */
public class FamiliaRepositoryImpl extends RepositoryCustomImpl<Familia, Long> 
		implements FamiliaRepositoryCustom {

	@Override
	public List<Familia> recuperarFamiliaresUsuario(Usuario usuario) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select f from Familia f where f.idUsuario = :idUsuario ");
		params.put("idUsuario", usuario.getId());
		hql.append("order by f.dataPedido desc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public List<Familia> recuperarFamiliaresSolicitacoesUsuario(Usuario usuario) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select f from Familia f where upper(f.email) = :emailUsuario ");
		params.put("emailUsuario", usuario.getLogin().toUpperCase());
		hql.append("order by f.dataPedido desc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

}
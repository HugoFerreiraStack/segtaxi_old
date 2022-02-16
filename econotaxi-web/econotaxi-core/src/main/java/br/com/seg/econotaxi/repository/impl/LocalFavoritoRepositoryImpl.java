/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.util.HashMap;
import java.util.Map;

import br.com.seg.econotaxi.model.LocalFavorito;
import br.com.seg.econotaxi.repository.LocalFavoritoRepositoryCustom;

/**
 * @author bruno
 *
 */
public class LocalFavoritoRepositoryImpl extends RepositoryCustomImpl<LocalFavorito, Long> implements LocalFavoritoRepositoryCustom {

	@Override
	public boolean verificaExistenciaLocalFavorito(LocalFavorito localFavorito) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(lf) from LocalFavorito lf ");
		hql.append("where lf.usuario.id = :idUsuario ");
		params.put("idUsuario", localFavorito.getUsuario().getId());
		hql.append("and (upper(lf.nome) = :nome ");
		params.put("nome", localFavorito.getNome().toUpperCase());
		hql.append("or upper(lf.descricao) = :descricao) ");
		params.put("descricao", localFavorito.getDescricao().toUpperCase());
		return countByParameters(hql.toString(), params) > 0;
	}

}
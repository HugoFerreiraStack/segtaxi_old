/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.seg.econotaxi.model.Auxiliar;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.repository.AuxiliarRepositoryCustom;

/**
 * @author bruno
 *
 */
public class AuxiliarRepositoryImpl extends RepositoryCustomImpl<Auxiliar, Long> 
		implements AuxiliarRepositoryCustom {

	@Override
	public List<Auxiliar> recuperarAuxiliaresUsuario(Usuario usuario) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select a from Auxiliar a where a.idUsuario = :idUsuario ");
		params.put("idUsuario", usuario.getId());
		hql.append("order by a.dataPedido desc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public boolean verificaExistenciaAuxiliar(Auxiliar auxiliar) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(a.id) from Auxiliar a ");
		hql.append("where a.idUsuario = :idUsuario ");
		params.put("idUsuario", auxiliar.getIdUsuario());
		hql.append("and a.cpf = :cpf ");
		params.put("cpf", auxiliar.getCpf());
		return countByParameters(hql.toString(), params) > 0;
	}

}
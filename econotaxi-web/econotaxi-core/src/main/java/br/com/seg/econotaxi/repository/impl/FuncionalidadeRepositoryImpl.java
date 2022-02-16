/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.seg.econotaxi.model.Funcionalidade;
import br.com.seg.econotaxi.repository.FuncionalidadeRepositoryCustom;

/**
 * @author bruno
 *
 */
public class FuncionalidadeRepositoryImpl 
	extends RepositoryCustomImpl<Funcionalidade, Long> implements FuncionalidadeRepositoryCustom {

	@Override
	public List<Long> findByIdUsuario(Long id) {
		
		List<Long> funcionalidades = null;
		StringBuilder hql = new StringBuilder();
		Map<String, Object> parametros = new HashMap<String, Object>();
		hql.append("select fp.funcionalidade from FuncionalidadePerfil fp ");
		hql.append("where fp.id.idPerfil in (select up.id.idPerfil ");
		hql.append("from UsuarioPerfil up where up.id.idUsuario = :idUsuario) ");
		parametros.put("idUsuario", id);
		List<Funcionalidade> funcs = findByParametersWithoutPaginator(hql.toString(), parametros);
		if (funcs != null && !funcs.isEmpty()) {
			funcionalidades = new ArrayList<Long>();
			for (Funcionalidade funcionalidade : funcs) {
				funcionalidades.add(funcionalidade.getId());
			}
		}
		return funcionalidades;
	}

	@Override
	public List<Funcionalidade> findAllByOrderByNomeAsc() {
		
		StringBuilder hql = new StringBuilder();
		hql.append("select f from Funcionalidade f ");
		hql.append("order by f.id asc ");
		return findByParametersWithoutPaginator(hql.toString(), null);
	}

}
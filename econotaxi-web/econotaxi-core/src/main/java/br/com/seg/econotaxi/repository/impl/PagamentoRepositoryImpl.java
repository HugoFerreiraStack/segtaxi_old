/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.seg.econotaxi.model.Pagamento;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.repository.PagamentoRepositoryCustom;

/**
 * @author bruno
 *
 */
public class PagamentoRepositoryImpl extends RepositoryCustomImpl<Pagamento, Long> 
	implements PagamentoRepositoryCustom {

	@Override
	public List<Pagamento> recuperarPagamentosPorUsuario(Usuario usuario) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select p from Pagamento p ");
		hql.append("where p.usuario.id = :idUsuario ");
		params.put("idUsuario", usuario.getId());
		hql.append("order by p.dataCadastro desc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public Pagamento recuperarPagamentoPadrao(Usuario usuario) {

		Pagamento pag = null;
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select p from Pagamento p ");
		hql.append("where p.usuario.id = :idUsuario ");
		params.put("idUsuario", usuario.getId());
		hql.append("and p.indicadorPadrao = 1 ");
		try {
			pag = findOne(hql.toString(), params);
		} catch (Exception e) {}
		return pag;
	}
	
}
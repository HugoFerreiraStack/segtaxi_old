/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.util.HashMap;
import java.util.Map;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.DescontoLojista;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.repository.DescontoLojistaRepositoryCustom;

/**
 * @author bruno
 *
 */
public class DescontoLojistaRepositoryImpl extends RepositoryCustomImpl<DescontoLojista, Long> 
		implements DescontoLojistaRepositoryCustom {

	@Override
	public DescontoLojista recuperarDescontoPorHash(String hash) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select d from DescontoLojista d where d.hash = :hash ");
		params.put("hash", hash);
		return findOne(hql.toString(), params);
	}

	@Override
	public int countDescontosDisponiveisPorUsuario(Long idUsuarioCliente) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(d) from DescontoLojista d ");
		hql.append("where d.idUsuarioCliente = :idUsuarioCliente ");
		hql.append("and d.dataUtilizacao is null ");
		params.put("idUsuarioCliente", idUsuarioCliente);
		return countByParameters(hql.toString(), params).intValue();
	}

	@Override
	public DescontoLojista recuperarDescontoDisponivelPorUsuario(Usuario usuario, Cidade cidade) {
		
		DescontoLojista desconto = null;
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select d from DescontoLojista d, Lojista l ");
		hql.append("where d.idLojista = l.id ");
		hql.append("and d.idUsuarioCliente = :idUsuarioCliente ");
		hql.append("and d.dataUtilizacao is null ");
		params.put("idUsuarioCliente", usuario.getId());
		hql.append("and l.idCidade = :idCidade ");
		params.put("idCidade", cidade.getId());
		hql.append("and d.dataCadastro = (select min(d2.dataCadastro) from DescontoLojista d2, Lojista l2 ");
		hql.append("where d2.idLojista = l2.id and d2.idUsuarioCliente = d.idUsuarioCliente and d2.dataUtilizacao is null ");
		hql.append("and l2.idCidade = l.idCidade) ");
		
		try {
			desconto = findOne(hql.toString(), params);
		} catch (Exception e) { }
		return desconto;
	}

}
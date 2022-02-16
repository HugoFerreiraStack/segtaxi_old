/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import br.com.seg.econotaxi.model.ClienteTeleTaxi;
import br.com.seg.econotaxi.repository.ClienteTeleTaxiRepositoryCustom;

/**
 * @author bruno
 *
 */
public class ClienteTeleTaxiRepositoryImpl extends RepositoryCustomImpl<ClienteTeleTaxi, Long> 
		implements ClienteTeleTaxiRepositoryCustom {

	@Override
	public boolean verificaExistenciaCliente(ClienteTeleTaxi clienteTeleTaxi) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(c) from ClienteTeleTaxi c where c.celular = :celular ");
		params.put("celular", clienteTeleTaxi.getCelular());
		return countByParameters(hql.toString(), params) > 0;
	}

	@Override
	public Long pesquisarCountClientePorFiltro(ClienteTeleTaxi filtro) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(c) from ClienteTeleTaxi c where 1=1 ");
		definirFiltro(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	@Override
	public List<ClienteTeleTaxi> pesquisarClientePorFiltro(ClienteTeleTaxi filtro, int first, int pageSize) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select c from ClienteTeleTaxi c where 1=1 ");
		definirFiltro(filtro, hql, params);
		hql.append("order by c.nome asc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}
	
	private void definirFiltro(ClienteTeleTaxi filtro, StringBuilder hql, Map<String, Object> params) {
		
		if (filtro.getNome() != null && !StringUtils.isEmpty(filtro.getNome())) {
			hql.append(" and upper(c.nome) like :nome ");
			params.put("nome", "%" + filtro.getNome().toUpperCase() + "%");
		}
		
		if (filtro.getEmpresa() != null) {
			hql.append(" and c.empresa = :empresa ");
			params.put("empresa", filtro.getEmpresa());
		}
		
	}

	@Override
	public ClienteTeleTaxi recuperarClientePorCelular(String celularPassageiro) {
		
		ClienteTeleTaxi cliente = null;
		List<ClienteTeleTaxi> clientes = null;
		try {
			StringBuilder hql = new StringBuilder();
			Map<String, Object> params = new HashMap<String, Object>();
			hql.append("select c from ClienteTeleTaxi c ");
			hql.append("where replace(replace(replace(replace(c.celular, '-', ''), ' ', ''), '(', ''), ')', '') = :celular ");
			params.put("celular", celularPassageiro.replace("-", "").replace(" ", "").replace("(", "").replace(")", ""));
			hql.append("order by c.dataCadastro asc ");
			clientes = findByParametersWithoutPaginator(hql.toString(), params);
			if (clientes != null && !clientes.isEmpty()) {
				cliente = clientes.get(0);
			}
			
		} catch (Exception e) {	}
		return cliente;
	}

}
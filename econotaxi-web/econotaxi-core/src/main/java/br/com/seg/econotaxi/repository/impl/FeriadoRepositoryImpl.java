/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Feriado;
import br.com.seg.econotaxi.repository.FeriadoRepositoryCustom;

/**
 * @author bruno
 *
 */
public class FeriadoRepositoryImpl extends RepositoryCustomImpl<Feriado, Long> 
		implements FeriadoRepositoryCustom {

	@Override
	public Long pesquisarCountFeriadoPorFiltro(Feriado filtro) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(f) from Feriado f where 1=1 ");
		definirFiltro(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	@Override
	public List<Feriado> pesquisarFeriadoPorFiltro(Feriado filtro, int first, int pageSize) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select f from Feriado f where 1=1 ");
		definirFiltro(filtro, hql, params);
		hql.append("order by f.dataFeriado desc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}
	
	private void definirFiltro(Feriado filtro, StringBuilder hql, Map<String, Object> params) {
		
		if (filtro.getNome() != null && !StringUtils.isEmpty(filtro.getNome())) {
			hql.append(" and upper(f.nome) like :nome ");
			params.put("nome", "%" + filtro.getNome().toUpperCase() + "%");
		}
		
		if (filtro.getCidade() != null && filtro.getCidade().getId() != null) {
			hql.append(" and f.cidade.id = :idCidade ");
			params.put("idCidade", filtro.getCidade().getId());
		}
		
		if (filtro.getDataFeriado() != null) {
			hql.append(" and DATE_FORMAT(f.dataFeriado, '%d/%m/%Y') = :data ");
			params.put("data", new SimpleDateFormat("dd/MM/YYYY").format(filtro.getDataFeriado()));
		}
		
	}

	@Override
	public boolean verificaExistenciaFeriado(Feriado feriado) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(f) from Feriado f ");
		hql.append("where DATE_FORMAT(f.dataFeriado, '%d/%m/%Y') = :dataFeriado ");
		params.put("dataFeriado", new SimpleDateFormat("dd/MM/YYYY").format(feriado.getDataFeriado()));
		
		if (feriado.getCidade() != null && feriado.getCidade().getId() != null) {
			hql.append("and f.cidade.id = :idCidade ");
			params.put("idCidade", feriado.getCidade().getId());
		}
		if (feriado.getId() != null) {
			hql.append("and f.id != :idFeriado ");
			params.put("idFeriado", feriado.getId());
		}
		return countByParameters(hql.toString(), params) > 0;
	}

	@Override
	public Boolean verificaDiaFeriado(Date dataFinalizacao, Cidade cidade) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(f) from Feriado f ");
		hql.append("where DATE_FORMAT(f.dataFeriado, '%d/%m/%Y') = :dataFeriado ");
		params.put("dataFeriado", new SimpleDateFormat("dd/MM/YYYY").format(dataFinalizacao));
		
		if (cidade != null && cidade.getId() != null) {
			hql.append("and (f.cidade.id = :idCidade or f.idCidade is null) ");
			params.put("idCidade", cidade.getId());
		}
		return countByParameters(hql.toString(), params) > 0;
	}
	
}
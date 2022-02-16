/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.VisualizacaoCorrida;
import br.com.seg.econotaxi.repository.VisualizacaoCorridaRepositoryCustom;

/**
 * @author bruno
 *
 */
public class VisualizacaoCorridaRepositoryImpl extends RepositoryCustomImpl<VisualizacaoCorrida, Long> 
	implements VisualizacaoCorridaRepositoryCustom {

	@Override
	public List<VisualizacaoCorrida> recuperarVisualizacoesCorrida(Corrida corrida) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select v from VisualizacaoCorrida v ");
		hql.append("where v.idCorrida = :idCorrida ");
		params.put("idCorrida", corrida.getId());
		hql.append("order by v.dataVisualizacao asc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public boolean verificaExistenciaVisualizacao(VisualizacaoCorrida visualizacaoCorrida) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(v) from VisualizacaoCorrida v ");
		hql.append("where v.idCorrida = :idCorrida ");
		params.put("idCorrida", visualizacaoCorrida.getIdCorrida());
		hql.append("and v.idMotorista = :idMotorista ");
		params.put("idMotorista", visualizacaoCorrida.getIdMotorista());
		hql.append("order by v.dataVisualizacao asc ");
		return countByParameters(hql.toString(), params) > 0;
	}
	
}
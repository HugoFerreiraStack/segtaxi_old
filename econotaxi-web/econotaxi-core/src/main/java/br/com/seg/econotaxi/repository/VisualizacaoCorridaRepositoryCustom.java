/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.VisualizacaoCorrida;

/**
 * @author bruno
 *
 */
public interface VisualizacaoCorridaRepositoryCustom {

	List<VisualizacaoCorrida> recuperarVisualizacoesCorrida(Corrida corrida);
	
	boolean verificaExistenciaVisualizacao(VisualizacaoCorrida visualizacaoCorrida);
	
}
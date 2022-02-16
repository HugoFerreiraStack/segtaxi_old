/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.Percurso;

/**
 * @author bruno
 *
 */
public interface PercursoRepositoryCustom {

	Long pesquisarCountPercursoPorFiltro(Percurso filtro);

	List<Percurso> pesquisarPercursoPorFiltro(Percurso filtro, int first, int pageSize);
	
	List<Percurso> recuperarPercursosCorrida(Corrida corrida);
	
	Long segundosParadoPercurso(Corrida corrida);
	
}
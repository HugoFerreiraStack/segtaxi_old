/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.Percurso;

/**
 * @author bruno
 *
 */
public interface PercursoService {

	void salvarPercurso(Percurso percurso);
	
	Percurso recuperarPercursoPorChave(Long chave);
	
	Long pesquisarCountPercursoPorFiltro(Percurso filtro);

	List<Percurso> pesquisarPercursoPorFiltro(Percurso filtro, int first, int pageSize);

	List<Percurso> recuperarPercursosCorrida(Corrida corrida);
	
	Long segundosParadoPercurso(Corrida corrida);
	
}
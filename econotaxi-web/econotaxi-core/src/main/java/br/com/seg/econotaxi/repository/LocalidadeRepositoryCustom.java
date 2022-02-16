/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Localidade;

/**
 * @author bruno
 *
 */
public interface LocalidadeRepositoryCustom {

	Long pesquisarCountLocalidadePorFiltro(Localidade filtro);

	List<Localidade> pesquisarLocalidadePorFiltro(Localidade filtro, int first, int pageSize);
	
	List<Localidade> recuperarTodasLocalidadesPorTipo(Integer tipo, Long cidade);
	
}
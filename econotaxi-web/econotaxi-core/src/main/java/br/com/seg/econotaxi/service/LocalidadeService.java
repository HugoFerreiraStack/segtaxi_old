/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Localidade;

/**
 * @author bruno
 *
 */
public interface LocalidadeService {

	Long pesquisarCountLocalidadePorFiltro(Localidade filtro);

	List<Localidade> pesquisarLocalidadePorFiltro(Localidade filtro, int first, int pageSize);

	void salvarLocalidade(Localidade localidade);

	void excluir(Localidade localidade);

	List<Localidade> recuperarTodasLocalidadesPorTipo(Integer tipo, Long cidade);

	Localidade recuperarPorChave(Long idPa);

}

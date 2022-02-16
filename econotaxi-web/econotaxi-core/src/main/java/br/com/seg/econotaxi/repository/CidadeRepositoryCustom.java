/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Cidade;

/**
 * @author bruno
 *
 */
public interface CidadeRepositoryCustom {

	Long pesquisarCountCidadePorFiltro(Cidade filtro);

	List<Cidade> pesquisarCidadePorFiltro(Cidade filtro, int first, int pageSize);
	
	boolean verificaExistenciaCidade(Cidade cidade);
	
	List<Cidade> findAllByOrderByNomeAsc();
	
	Cidade recuperarCidadeUsuarioApp(String latitude, String longitude);
	
}
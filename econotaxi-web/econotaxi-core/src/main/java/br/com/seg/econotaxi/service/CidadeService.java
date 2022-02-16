/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Cidade;

/**
 * @author bruno
 *
 */
public interface CidadeService {

	void salvarCidade(Cidade cidade);
	
	Cidade recuperarCidadePorChave(Long chave);
	
	List<Cidade> recuperarTodasCidades();

	Long pesquisarCountCidadePorFiltro(Cidade filtro);

	List<Cidade> pesquisarCidadePorFiltro(Cidade filtro, int first, int pageSize);

	void excluir(Cidade cidade);

	boolean verificaExistenciaCidade(Cidade cidade);
	
	Cidade recuperarCidadeUsuarioApp(String latitude, String longitude);
	
}
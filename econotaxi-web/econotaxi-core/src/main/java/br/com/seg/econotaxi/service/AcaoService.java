/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Acao;

/**
 * @author bruno
 *
 */
public interface AcaoService {

	void salvar(Acao acao);
	
	void delete(Acao acao);
	
	Acao consultarPorChave(Long id);

	Long pesquisarCountAcaoPorFiltro(Acao filtro);

	List<Acao> pesquisarAcaoPorFiltro(Acao filtro, int first, int pageSize);
	
}
/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Promocao;

/**
 * @author bruno
 *
 */
public interface PromocaoService {

	void salvarPromocao(Promocao promocao);
	
	Promocao recuperaPromocaoPorChave(Long chave);
	
	List<Promocao> recuperarUltimasPromocoes();

	Long pesquisarCountPromocaoPorFiltro(Promocao filtro);

	List<Promocao> pesquisarPromocaoPorFiltro(Promocao filtro, int first, int pageSize);

	void excluir(Promocao promocao);
	
}
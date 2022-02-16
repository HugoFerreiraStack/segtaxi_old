/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Promocao;

/**
 * @author bruno
 *
 */
public interface PromocaoRepositoryCustom {

	List<Promocao> recuperarUltimasPromocoes();
	
	Long pesquisarCountPromocaoPorFiltro(Promocao filtro);

	List<Promocao> pesquisarPromocaoPorFiltro(Promocao filtro, int first, int pageSize);
	
}
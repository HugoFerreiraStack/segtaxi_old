/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Acao;

/**
 * @author bruno
 *
 */
public interface AcaoRepositoryCustom {

	Long pesquisarCountAcaoPorFiltro(Acao filtro);

	List<Acao> pesquisarAcaoPorFiltro(Acao filtro, int first, int pageSize);
	
}
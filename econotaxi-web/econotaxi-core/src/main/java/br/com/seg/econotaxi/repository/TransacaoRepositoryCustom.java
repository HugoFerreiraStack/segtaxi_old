/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Transacao;

/**
 * @author bruno
 *
 */
public interface TransacaoRepositoryCustom {

	Long pesquisarCountTransacaoPorFiltro(Transacao filtro);

	List<Transacao> pesquisarTransacaoPorFiltro(Transacao filtro, int first, int pageSize);
	
	List<Transacao> recuperarTransacoesPendentesEnvio();
	
}
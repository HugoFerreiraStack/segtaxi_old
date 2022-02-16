/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Transacao;

/**
 * @author bruno
 *
 */
public interface TransacaoService {

	Transacao salvarTransacao(Transacao transacao);

	Transacao efetivarTransacao(Transacao transacao);
	
	Transacao recuperarCidadePorChave(Long chave);
	
	Long pesquisarCountTransacaoPorFiltro(Transacao filtro);

	List<Transacao> pesquisarTransacaoPorFiltro(Transacao filtro, int first, int pageSize);

	List<Transacao> recuperarTransacoesPendentesEnvio();
	
}
/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Notificacao;

/**
 * @author bruno
 *
 */
public interface NotificacaoRepositoryCustom {

	Long pesquisarCountNotificacaoPorFiltro(Notificacao filtro);

	List<Notificacao> pesquisarNotificacaoPorFiltro(Notificacao filtro, int first, int pageSize);
	
	List<Notificacao> recuperarNotificacoes2Dias();
	
}
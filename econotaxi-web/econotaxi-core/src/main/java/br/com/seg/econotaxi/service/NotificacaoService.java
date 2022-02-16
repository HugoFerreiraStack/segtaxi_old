/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Notificacao;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.UsuarioNotificacao;

/**
 * @author bruno
 *
 */
public interface NotificacaoService {

	void salvarNotificacao(Notificacao notificacao);
	
	Notificacao recuperarNotificacaoPorChave(Long chave);
	
	List<UsuarioNotificacao> recuperarUltimasNotificacoes(Usuario usuario);

	Long pesquisarCountNotificacaoPorFiltro(Notificacao filtro);

	List<Notificacao> pesquisarNotificacaoPorFiltro(Notificacao filtro, int first, int pageSize);

	List<Notificacao> recuperarNotificacoes2Dias();

	List<UsuarioNotificacao> recuperarNotificacoesNaoVistas(Usuario usuario);

	void excluirNotificacao(Notificacao notificacao);
	
}
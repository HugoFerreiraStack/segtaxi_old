/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Notificacao;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.UsuarioNotificacao;

/**
 * @author bruno
 *
 */
public interface UsuarioNotificacaoRepositoryCustom {

	List<UsuarioNotificacao> recuperarUltimasNotificacoes(Usuario usuario);
	
	List<UsuarioNotificacao> recuperarNotificacoesNaoVistas(Usuario usuario);
	
	boolean verificaExistenciaNotificacaoUsuario(Usuario usuario, Notificacao notificacao);
	
	List<UsuarioNotificacao> recuperarNotificacoesUsuario(Notificacao notificacao);
	
	List<UsuarioNotificacao> recuperarNotificacoesNaoVistas();
	
}
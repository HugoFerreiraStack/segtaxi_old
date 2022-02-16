/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Notificacao;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.UsuarioNotificacao;
import br.com.seg.econotaxi.repository.NotificacaoRepository;
import br.com.seg.econotaxi.repository.UsuarioNotificacaoRepository;
import br.com.seg.econotaxi.service.NotificacaoService;

/**
 * @author bruno
 *
 */
@Service("notificacaoService")
public class NotificacaoServiceImpl implements NotificacaoService {

	@Autowired
	private NotificacaoRepository notificacaoRepository;
	@Autowired
	private UsuarioNotificacaoRepository usuarioNotificacaoRepository;
	
	@Override
	public void salvarNotificacao(Notificacao notificacao) {
		notificacao.setIndicadorAndroid(1);
		notificacao.setIndicadorIos(1);
		notificacaoRepository.save(notificacao);
	}

	@Override
	public Notificacao recuperarNotificacaoPorChave(Long chave) {
		return notificacaoRepository.findById(chave);
	}

	@Override
	public List<UsuarioNotificacao> recuperarUltimasNotificacoes(Usuario usuario) {
		return usuarioNotificacaoRepository.recuperarUltimasNotificacoes(usuario);
	}

	@Override
	public Long pesquisarCountNotificacaoPorFiltro(Notificacao filtro) {
		return notificacaoRepository.pesquisarCountNotificacaoPorFiltro(filtro);
	}

	@Override
	public List<Notificacao> pesquisarNotificacaoPorFiltro(Notificacao filtro, int first, int pageSize) {
		return notificacaoRepository.pesquisarNotificacaoPorFiltro(filtro, first, pageSize);
	}

	@Override
	public List<Notificacao> recuperarNotificacoes2Dias() {
		return notificacaoRepository.recuperarNotificacoes2Dias();
	}

	@Override
	public List<UsuarioNotificacao> recuperarNotificacoesNaoVistas(Usuario usuario) {
		return usuarioNotificacaoRepository.recuperarNotificacoesNaoVistas(usuario);
	}

	@Override
	public void excluirNotificacao(Notificacao notificacao) {
		
		List<UsuarioNotificacao> notificacoes = 
				usuarioNotificacaoRepository.recuperarNotificacoesUsuario(notificacao);
		if (notificacoes != null && !notificacoes.isEmpty()) {
			for (UsuarioNotificacao usuarioNotificacao : notificacoes) {
				usuarioNotificacaoRepository.delete(usuarioNotificacao);
			}
		}
		notificacaoRepository.delete(notificacao);
	}

}

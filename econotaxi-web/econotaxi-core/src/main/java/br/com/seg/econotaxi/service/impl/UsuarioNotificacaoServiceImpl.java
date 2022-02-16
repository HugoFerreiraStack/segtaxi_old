package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Notificacao;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.UsuarioNotificacao;
import br.com.seg.econotaxi.repository.UsuarioNotificacaoRepository;
import br.com.seg.econotaxi.service.UsuarioNotificacaoService;

@Service("usuarioNotificacaoService")
public class UsuarioNotificacaoServiceImpl extends AbstractService implements UsuarioNotificacaoService {

    @Autowired
    private UsuarioNotificacaoRepository usuarioNotificacaoRepository;

	@Override
	public synchronized void salvar(UsuarioNotificacao usuarioNotificacao) {
		usuarioNotificacaoRepository.save(usuarioNotificacao);
	}

	@Override
	public List<UsuarioNotificacao> recuperarNotificacoesUsuario(Usuario usuario) {
		return usuarioNotificacaoRepository.recuperarNotificacoesNaoVistas(usuario);
	}

	@Override
	public boolean verificaExistenciaNotificacaoUsuario(Usuario usuario, Notificacao notificacao) {
		return usuarioNotificacaoRepository.verificaExistenciaNotificacaoUsuario(usuario, notificacao);
	}

	@Override
	public List<UsuarioNotificacao> recuperarNotificacoesNaoVistas() {
		return usuarioNotificacaoRepository.recuperarNotificacoesNaoVistas();
	}

}
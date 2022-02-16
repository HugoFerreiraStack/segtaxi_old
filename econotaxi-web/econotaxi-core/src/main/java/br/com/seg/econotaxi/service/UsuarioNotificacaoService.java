package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Notificacao;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.UsuarioNotificacao;

public interface UsuarioNotificacaoService {

	void salvar(UsuarioNotificacao usuarioNotificacao);

	List<UsuarioNotificacao> recuperarNotificacoesUsuario(Usuario usuario);

	boolean verificaExistenciaNotificacaoUsuario(Usuario usuario, Notificacao notificacao);

	List<UsuarioNotificacao> recuperarNotificacoesNaoVistas();

}
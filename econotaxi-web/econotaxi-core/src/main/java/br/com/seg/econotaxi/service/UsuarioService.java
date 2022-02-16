package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Notificacao;
import br.com.seg.econotaxi.model.Novidade;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.UsuarioPerfil;
import br.com.seg.econotaxi.vo.CodigoSmsVo;

public interface UsuarioService {

    List<Usuario> listar();

    Usuario recuperarPorLogin(String login);

	void excluirUsuario(Usuario usuario);

	void cadastrarUsuario(Usuario usuario);

	boolean verificaExistenciaUsuario(Usuario usuario);

	Usuario recuperarUsuarioPorID(Long id);

	Long pesquisarCountUsuarioPorFiltro(Usuario filtro);

	List<Usuario> pesquisarUsuarioPorFiltro(Usuario filtro, int first, int pageSize);

	Integer recuperarQtdUsuarioAppPorStatus(Integer status);

	Integer recuperarQtdUsuarioAppComPagamento();

	Usuario enviarEmailTrocaSenha(String emailTrocaSenha);

	Usuario recuperarPorHashTrocaSenha(String hashTrocaSenha);

	void trocarSenha(String login, String novaSenha, String hashTrocaSenha);

	void trocarSenhaCliente(Usuario usuario, String novaSenha);

	void bloquearPassageiro(Usuario usuario);

	void salvarUsuario(Usuario usuario);

	void atualizarDados(Usuario usuario);

	CodigoSmsVo gerarCodigoSms(CodigoSmsVo codigoSmsVo);

	void salvarCliente(Usuario usuario);

	String recuperarNomePorId(Long idUsuario);

	List<Usuario> recuperarUsuariosAdministrativo();

	String adicionarMaxipago(Long id);

	String recuperarIdMaxipago(Long idUsuario);

	List<Usuario> recuperarUsuariosSemNotificacao(Notificacao notificacao);

	List<Usuario> recuperarUsuariosSemNovidade(Novidade novidade);

	int quantidadeUsuariosSemNotificacao(Notificacao notificacao);

	int quantidadeUsuariosSemNovidade(Novidade novidade);

	List<Usuario> recuperarUsuariosPorTipoCidade(Integer codigo, Cidade cidade);

	void excluirPassageiro(Usuario passageiro);

	Usuario consultarUsuarioPorCelular(String celular);

	List<Usuario> recuperarPermissionariosUsuario(Motorista motorista);

	List<Usuario> recuperarUsuariosPorTipoCidadeRadio(Integer codigo, Cidade cidade, Integer tipoRadio);
	
	List<Usuario> recuperarPassageiros();

	void excluirPerfis(List<UsuarioPerfil> listaGrupoUsuario);
	
	void ativarUsuario(Usuario usuario);
	
	Integer recuperarTipoPorLogin(String login);

	boolean verificaExistenciaUsuarioCelular(Usuario usuario);
}
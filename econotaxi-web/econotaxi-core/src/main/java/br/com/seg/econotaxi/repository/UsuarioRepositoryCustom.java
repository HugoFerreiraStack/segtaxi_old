/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Notificacao;
import br.com.seg.econotaxi.model.Novidade;
import br.com.seg.econotaxi.model.Usuario;

/**
 * @author bruno
 *
 */
public interface UsuarioRepositoryCustom {

	boolean verificaExistenciaUsuario(Usuario usuario);

	Long pesquisarCountUsuarioPorFiltro(Usuario filtro);

	List<Usuario> pesquisarUsuarioPorFiltro(Usuario filtro, int first, int pageSize);

	Integer recuperarQtdUsuarioAppPorStatus(Integer status);
	
	Integer recuperarQtdUsuarioAppComPagamento();
	
	Usuario recuperarUsuarioPorHashTrocaSenha(String hashTrocaSenha);
	
	List<Usuario> recuperarUsuariosAdministrativo();
	
	List<Usuario> recuperarUsuariosSemNotificacao(Notificacao notificacao);

	List<Usuario> recuperarUsuariosSemNovidade(Novidade novidade);
	
	int quantidadeUsuariosSemNotificacao(Notificacao notificacao);

	int quantidadeUsuariosSemNovidade(Novidade novidade);
	
	List<Usuario> recuperarUsuariosPorTipoCidade(Integer codigo, Cidade cidade);
	
	Usuario consultarUsuarioPorCelular(String celular);
	
	List<Usuario> recuperarPermissionariosUsuario(Motorista motorista);
	
	List<Usuario> recuperarUsuariosPorTipoCidadeRadio(Integer codigo, Cidade cidade, Integer tipoRadio);
	
	List<Usuario> recuperarPassageiros();
	
	boolean verificaExistenciaUsuarioCelular(Usuario usuario);
	
}
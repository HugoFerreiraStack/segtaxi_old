/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Perfil;

/**
 * @author bruno
 *
 */
public interface PerfilService {

	void salvarPerfil(Perfil perfil);
	
	Perfil recuperarPerfilPorChave(Long chave);
	
	List<Perfil> recuperarTodosPerfis();

	List<Perfil> recuperarPerfisAtivos();

	boolean verificaExistenciaPerfil(Perfil grupo);

	void excluir(Perfil grupo);

	Long pesquisarCountPerfilPorFiltro(Perfil filtro);

	List<Perfil> pesquisarPerfilPorFiltro(Perfil filtro, int first, int pageSize);
	
}
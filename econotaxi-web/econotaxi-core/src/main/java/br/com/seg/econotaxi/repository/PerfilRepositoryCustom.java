/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Perfil;

/**
 * @author bruno
 *
 */
public interface PerfilRepositoryCustom {

	List<Perfil> recuperarPerfisAtivos();

	boolean verificaExistenciaPerfil(Perfil grupo);

	Long pesquisarCountPerfilPorFiltro(Perfil filtro);

	List<Perfil> pesquisarPerfilPorFiltro(Perfil filtro, int first, int pageSize);
	
	List<Perfil> findAllByOrderByNomeAsc();
	
}
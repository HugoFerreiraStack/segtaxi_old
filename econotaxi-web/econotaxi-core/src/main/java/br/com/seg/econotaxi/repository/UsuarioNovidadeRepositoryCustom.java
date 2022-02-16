/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Novidade;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.UsuarioNovidade;

/**
 * @author bruno
 *
 */
public interface UsuarioNovidadeRepositoryCustom {

	List<UsuarioNovidade> recuperarNovidadesUsuario(Usuario usuario);
	
	List<UsuarioNovidade> recuperarNovidadesUsuario(Novidade novidade);
	
	List<UsuarioNovidade> recuperarNovidadesNaoVistas();
	
}
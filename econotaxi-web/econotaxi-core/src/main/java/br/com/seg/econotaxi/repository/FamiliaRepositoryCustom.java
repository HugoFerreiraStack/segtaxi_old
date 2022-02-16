/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Familia;
import br.com.seg.econotaxi.model.Usuario;

/**
 * @author bruno
 *
 */
public interface FamiliaRepositoryCustom {

	List<Familia> recuperarFamiliaresUsuario(Usuario usuario);
	
	List<Familia> recuperarFamiliaresSolicitacoesUsuario(Usuario usuario);
	
}
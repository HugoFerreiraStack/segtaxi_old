/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Auxiliar;
import br.com.seg.econotaxi.model.Usuario;

/**
 * @author bruno
 *
 */
public interface AuxiliarRepositoryCustom {

	List<Auxiliar> recuperarAuxiliaresUsuario(Usuario usuario);
	
	boolean verificaExistenciaAuxiliar(Auxiliar auxiliar);
	
}
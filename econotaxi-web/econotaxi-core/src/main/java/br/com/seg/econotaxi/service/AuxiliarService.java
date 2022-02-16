/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Auxiliar;
import br.com.seg.econotaxi.model.Usuario;

/**
 * @author bruno
 *
 */
public interface AuxiliarService {

	List<Auxiliar> recuperarAuxiliaresUsuario(Usuario usuario);

	void salvar(Auxiliar auxiliar);

	void excluir(Auxiliar auxiliar);

	Auxiliar recuperarAuxiliarPorChave(Long id);

	boolean verificaExistenciaAuxiliar(Auxiliar auxiliar);

}

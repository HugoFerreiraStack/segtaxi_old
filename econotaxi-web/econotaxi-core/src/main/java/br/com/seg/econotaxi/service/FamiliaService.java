/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Familia;
import br.com.seg.econotaxi.model.Usuario;

/**
 * @author bruno
 *
 */
public interface FamiliaService {

	List<Familia> recuperarFamiliaresUsuario(Usuario usuario);

	void salvar(Familia familia);

	void excluir(Familia familia);

	Familia recuperarFamiliaPorChave(Long id);

	List<Familia> recuperarFamiliaresSolicitacoesUsuario(Usuario usuario);

}

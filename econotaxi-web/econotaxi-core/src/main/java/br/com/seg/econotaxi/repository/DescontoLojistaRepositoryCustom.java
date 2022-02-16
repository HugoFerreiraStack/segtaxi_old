/**
 * 
 */
package br.com.seg.econotaxi.repository;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.DescontoLojista;
import br.com.seg.econotaxi.model.Usuario;

/**
 * @author bruno
 *
 */
public interface DescontoLojistaRepositoryCustom {

	DescontoLojista recuperarDescontoPorHash(String hash);
	
	int countDescontosDisponiveisPorUsuario(Long idUsuarioCliente);
	
	DescontoLojista recuperarDescontoDisponivelPorUsuario(Usuario usuario, Cidade cidade);
	
}
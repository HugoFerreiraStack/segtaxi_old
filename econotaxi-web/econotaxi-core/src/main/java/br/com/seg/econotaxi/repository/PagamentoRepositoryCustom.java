/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Pagamento;
import br.com.seg.econotaxi.model.Usuario;

/**
 * @author bruno
 *
 */
public interface PagamentoRepositoryCustom {

	List<Pagamento> recuperarPagamentosPorUsuario(Usuario usuario);
	
	Pagamento recuperarPagamentoPadrao(Usuario usuario);
	
}
/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Pagamento;
import br.com.seg.econotaxi.model.Usuario;

/**
 * @author bruno
 *
 */
public interface PagamentoService {

	Pagamento salvarPagamento(Pagamento pagamento);
	
	Pagamento recuperarPagamentoPorChave(Long chave);
	
	List<Pagamento> recuperarPagamentosPorFiltro(Pagamento filtro);

	void removerPagamento(Pagamento pagamento);

	List<Pagamento> recuperarPagamentosPorUsuario(Usuario usuario);

	String recuperarTokenPorId(Long id);

	void salvar(Pagamento pagamento);

	Pagamento recuperarPagamentoPadrao(Usuario usuario);

}
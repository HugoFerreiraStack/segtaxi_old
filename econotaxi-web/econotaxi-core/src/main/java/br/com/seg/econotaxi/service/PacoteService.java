/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Pacote;

/**
 * @author bruno
 *
 */
public interface PacoteService {

	void salvarPacote(Pacote pacote);
	
	Pacote recuperarPacotePorChave(Long chave);
	
	List<Pacote> recuperarTodasPacotes();

	Long pesquisarCountPacotePorFiltro(Pacote filtro);

	List<Pacote> pesquisarPacotePorFiltro(Pacote filtro, int first, int pageSize);

	boolean verificaExistenciaPacote(Pacote pacote);

	void excluir(Pacote pacote);

	List<Pacote> recuperarPacotesComLocaisPorCidade(Cidade cidade);
	
}

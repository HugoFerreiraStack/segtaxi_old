/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Pacote;

/**
 * @author bruno
 *
 */
public interface PacoteRepositoryCustom {

	Long pesquisarCountPacotePorFiltro(Pacote filtro);

	List<Pacote> pesquisarPacotePorFiltro(Pacote filtro, int first, int pageSize);
	
	List<Pacote> findAllByOrderByNomeAsc();
	
	boolean verificaExistenciaPacote(Pacote pacote);
	
	List<Pacote> recuperarPacotesComLocaisPorCidade(Cidade cidade);
	
}
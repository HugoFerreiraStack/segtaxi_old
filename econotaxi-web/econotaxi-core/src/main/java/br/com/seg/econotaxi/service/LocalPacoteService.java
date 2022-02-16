/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.LocalPacote;
import br.com.seg.econotaxi.model.Pacote;

/**
 * @author bruno
 *
 */
public interface LocalPacoteService {

	void salvarLocalPacote(LocalPacote localPacote);
	
	LocalPacote recuperarLocalPacotePorChave(Long chave);
	
	List<LocalPacote> recuperarTodasLocalPacotes();

	Long pesquisarCountPerfilPorFiltro(LocalPacote filtro);

	List<LocalPacote> pesquisarLocalPacotePorFiltro(LocalPacote filtro, int first, int pageSize);

	List<LocalPacote> recuperarLocaisPorPacote(Pacote pacote);

	void excluir(LocalPacote localPacote);

	LocalPacote recuperarUltimoLocalPacote(Pacote pac);
	
}
/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.LocalPacote;
import br.com.seg.econotaxi.model.Pacote;

/**
 * @author bruno
 *
 */
public interface LocalPacoteRepositoryCustom {

	Long pesquisarCountPerfilPorFiltro(LocalPacote filtro);

	List<LocalPacote> pesquisarLocalPacotePorFiltro(LocalPacote filtro, int first, int pageSize);
	
	List<LocalPacote> findAllByOrderByNomeAsc();
	
	List<LocalPacote> recuperarLocaisPorPacote(Pacote pacote);
	
	LocalPacote recuperarUltimoLocalPacote(Pacote pac);
	
}
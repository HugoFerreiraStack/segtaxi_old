/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.LocalPacote;
import br.com.seg.econotaxi.model.Pacote;
import br.com.seg.econotaxi.repository.LocalPacoteRepository;
import br.com.seg.econotaxi.service.LocalPacoteService;

/**
 * @author bruno
 *
 */
@Service("localPacoteService")
public class LocalPacoteServiceImpl implements LocalPacoteService {

	@Autowired
	private LocalPacoteRepository localPacoteRepository;
	
	@Override
	public void salvarLocalPacote(LocalPacote localPacote) {
		localPacoteRepository.save(localPacote);
	}

	@Override
	public LocalPacote recuperarLocalPacotePorChave(Long chave) {
		return localPacoteRepository.findById(chave);
	}

	@Override
	public List<LocalPacote> recuperarTodasLocalPacotes() {
		return localPacoteRepository.findAllByOrderByNomeAsc();
	}

	@Override
	public Long pesquisarCountPerfilPorFiltro(LocalPacote filtro) {
		return localPacoteRepository.pesquisarCountPerfilPorFiltro(filtro);
	}

	@Override
	public List<LocalPacote> pesquisarLocalPacotePorFiltro(LocalPacote filtro, int first, int pageSize) {
		return localPacoteRepository.pesquisarLocalPacotePorFiltro(filtro, first, pageSize);
	}

	@Override
	public List<LocalPacote> recuperarLocaisPorPacote(Pacote pacote) {
		return localPacoteRepository.recuperarLocaisPorPacote(pacote);
	}

	@Override
	public void excluir(LocalPacote localPacote) {
		localPacoteRepository.delete(localPacote);
	}

	@Override
	public LocalPacote recuperarUltimoLocalPacote(Pacote pac) {
		return localPacoteRepository.recuperarUltimoLocalPacote(pac);
	}

}

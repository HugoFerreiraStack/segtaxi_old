/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Pacote;
import br.com.seg.econotaxi.repository.PacoteRepository;
import br.com.seg.econotaxi.service.PacoteService;

/**
 * @author bruno
 *
 */
@Service("pacoteService")
public class PacoteServiceImpl implements PacoteService {

	@Autowired
	private PacoteRepository pacoteRepository;
	
	@Override
	public void salvarPacote(Pacote pacote) {
		pacoteRepository.save(pacote);
	}

	@Override
	public Pacote recuperarPacotePorChave(Long chave) {
		return pacoteRepository.findById(chave);
	}

	@Override
	public List<Pacote> recuperarTodasPacotes() {
		return pacoteRepository.findAllByOrderByNomeAsc();
	}

	@Override
	public Long pesquisarCountPacotePorFiltro(Pacote filtro) {
		return pacoteRepository.pesquisarCountPacotePorFiltro(filtro);
	}

	@Override
	public List<Pacote> pesquisarPacotePorFiltro(Pacote filtro, int first, int pageSize) {
		return pacoteRepository.pesquisarPacotePorFiltro(filtro, first, pageSize);
	}

	@Override
	public boolean verificaExistenciaPacote(Pacote pacote) {
		return pacoteRepository.verificaExistenciaPacote(pacote);
	}

	@Override
	public void excluir(Pacote pacote) {
		pacoteRepository.delete(pacote);
	}

	@Override
	public List<Pacote> recuperarPacotesComLocaisPorCidade(Cidade cidade) {
		return pacoteRepository.recuperarPacotesComLocaisPorCidade(cidade);
	}
	
}
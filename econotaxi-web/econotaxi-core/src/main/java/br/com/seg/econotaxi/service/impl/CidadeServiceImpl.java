/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.repository.CidadeRepository;
import br.com.seg.econotaxi.service.CidadeService;

/**
 * @author bruno
 *
 */
@Service("cidadeService")
public class CidadeServiceImpl implements CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Override
	public void salvarCidade(Cidade cidade) {
		cidadeRepository.save(cidade);
	}

	@Override
	public Cidade recuperarCidadePorChave(Long chave) {
		return cidadeRepository.findById(chave);
	}

	@Override
	public List<Cidade> recuperarTodasCidades() {
		return cidadeRepository.findAllByOrderByNomeAsc();
	}

	@Override
	public Long pesquisarCountCidadePorFiltro(Cidade filtro) {
		return cidadeRepository.pesquisarCountCidadePorFiltro(filtro);
	}

	@Override
	public List<Cidade> pesquisarCidadePorFiltro(Cidade filtro, int first, int pageSize) {
		// TODO Auto-generated method stub
		return cidadeRepository.pesquisarCidadePorFiltro(filtro, first, pageSize);
	}

	@Override
	public void excluir(Cidade cidade) {
		cidadeRepository.delete(cidade);
	}

	@Override
	public boolean verificaExistenciaCidade(Cidade cidade) {
		return cidadeRepository.verificaExistenciaCidade(cidade);
	}

	@Override
	public Cidade recuperarCidadeUsuarioApp(String latitude, String longitude) {
		return cidadeRepository.recuperarCidadeUsuarioApp(latitude, longitude);
	}
	
}
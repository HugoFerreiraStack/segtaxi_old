/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Acao;
import br.com.seg.econotaxi.repository.AcaoRepository;
import br.com.seg.econotaxi.service.AcaoService;

/**
 * @author bruno
 *
 */
@Service("acaoService")
public class AcaoServiceImpl implements AcaoService {

	@Autowired
	private AcaoRepository acaoRepository;

	@Override
	public void salvar(Acao acao) {
		acaoRepository.save(acao);
	}

	@Override
	public void delete(Acao acao) {
		acaoRepository.delete(acao);
	}

	@Override
	public Acao consultarPorChave(Long id) {
		return acaoRepository.findOne(id);
	}

	@Override
	public Long pesquisarCountAcaoPorFiltro(Acao filtro) {
		return acaoRepository.pesquisarCountAcaoPorFiltro(filtro);
	}

	@Override
	public List<Acao> pesquisarAcaoPorFiltro(Acao filtro, int first, int pageSize) {
		return acaoRepository.pesquisarAcaoPorFiltro(filtro, first, pageSize);
	}
	
}
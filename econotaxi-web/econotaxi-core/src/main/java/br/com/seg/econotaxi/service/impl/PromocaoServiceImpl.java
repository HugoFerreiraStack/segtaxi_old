/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Promocao;
import br.com.seg.econotaxi.repository.PromocaoRepository;
import br.com.seg.econotaxi.service.PromocaoService;

/**
 * @author bruno
 *
 */
@Service("promocaoService")
public class PromocaoServiceImpl implements PromocaoService {

	@Autowired
	private PromocaoRepository promocaoRepository;
	
	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.service.PromocaoService#salvarPromocao(br.com.seg.econotaxi.model.Promocao)
	 */
	@Override
	public void salvarPromocao(Promocao promocao) {
		promocaoRepository.save(promocao);
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.service.PromocaoService#recuperaPromocaoPorChave(java.lang.Long)
	 */
	@Override
	public Promocao recuperaPromocaoPorChave(Long chave) {
		return promocaoRepository.findById(chave);
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.service.PromocaoService#recuperarUltimasPromocoes()
	 */
	@Override
	public List<Promocao> recuperarUltimasPromocoes() {
		return promocaoRepository.recuperarUltimasPromocoes();
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.service.PromocaoService#pesquisarCountPromocaoPorFiltro(br.com.seg.econotaxi.model.Promocao)
	 */
	@Override
	public Long pesquisarCountPromocaoPorFiltro(Promocao filtro) {
		return promocaoRepository.pesquisarCountPromocaoPorFiltro(filtro);
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.service.PromocaoService#pesquisarPromocaoPorFiltro(br.com.seg.econotaxi.model.Promocao, int, int)
	 */
	@Override
	public List<Promocao> pesquisarPromocaoPorFiltro(Promocao filtro, int first, int pageSize) {
		return promocaoRepository.pesquisarPromocaoPorFiltro(filtro, first, pageSize);
	}

	@Override
	public void excluir(Promocao promocao) {
		promocaoRepository.delete(promocao);
	}

}
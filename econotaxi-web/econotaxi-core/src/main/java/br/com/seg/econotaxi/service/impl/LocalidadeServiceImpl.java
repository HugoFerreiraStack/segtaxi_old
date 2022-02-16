/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Localidade;
import br.com.seg.econotaxi.repository.LocalidadeRepository;
import br.com.seg.econotaxi.service.LocalidadeService;

/**
 * @author bruno
 *
 */
@Service("localidadeService")
public class LocalidadeServiceImpl implements LocalidadeService {

	@Autowired
	private LocalidadeRepository localidadeRepository;
	
	@Override
	public Long pesquisarCountLocalidadePorFiltro(Localidade filtro) {
		return localidadeRepository.pesquisarCountLocalidadePorFiltro(filtro);
	}

	@Override
	public List<Localidade> pesquisarLocalidadePorFiltro(Localidade filtro, int first, int pageSize) {
		return localidadeRepository.pesquisarLocalidadePorFiltro(filtro, first, pageSize);
	}

	@Override
	public void salvarLocalidade(Localidade localidade) {
		localidadeRepository.save(localidade);
	}

	@Override
	public void excluir(Localidade localidade) {
		localidadeRepository.delete(localidade);
	}

	@Override
	public List<Localidade> recuperarTodasLocalidadesPorTipo(Integer tipo, Long cidade) {
		return localidadeRepository.recuperarTodasLocalidadesPorTipo(tipo, cidade);
	}

	@Override
	public Localidade recuperarPorChave(Long idPa) {
		
		Localidade localidade = null;
		try {
			localidade = localidadeRepository.findOne(idPa);
		} catch (Exception e) { }
		return localidade;
	}
	
}
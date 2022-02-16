/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.Percurso;
import br.com.seg.econotaxi.repository.PercursoRepository;
import br.com.seg.econotaxi.service.PercursoService;

/**
 * @author bruno
 *
 */
@Service("percursoService")
public class PercursoServiceImpl implements PercursoService {

	@Autowired
	private PercursoRepository percursoRepository;
	
	@Override
	public void salvarPercurso(Percurso percurso) {
		percursoRepository.save(percurso);
	}

	@Override
	public Percurso recuperarPercursoPorChave(Long chave) {
		return percursoRepository.findById(chave);
	}

	@Override
	public Long pesquisarCountPercursoPorFiltro(Percurso filtro) {
		return percursoRepository.pesquisarCountPercursoPorFiltro(filtro);
	}

	@Override
	public List<Percurso> pesquisarPercursoPorFiltro(Percurso filtro, int first, int pageSize) {
		return percursoRepository.pesquisarPercursoPorFiltro(filtro, first, pageSize);
	}

	@Override
	public List<Percurso> recuperarPercursosCorrida(Corrida corrida) {
		return percursoRepository.recuperarPercursosCorrida(corrida);
	}

	@Override
	public Long segundosParadoPercurso(Corrida corrida) {
		return percursoRepository.segundosParadoPercurso(corrida);
	}

}
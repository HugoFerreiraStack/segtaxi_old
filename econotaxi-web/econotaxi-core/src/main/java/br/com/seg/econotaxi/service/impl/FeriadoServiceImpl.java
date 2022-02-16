/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Feriado;
import br.com.seg.econotaxi.repository.FeriadoRepository;
import br.com.seg.econotaxi.service.FeriadoService;

/**
 * @author bruno
 *
 */
@Service("feriadoService")
public class FeriadoServiceImpl implements FeriadoService {

	@Autowired
	private FeriadoRepository feriadoRepository;
	
	@Override
	public Long pesquisarCountFeriadoPorFiltro(Feriado filtro) {
		return feriadoRepository.pesquisarCountFeriadoPorFiltro(filtro);
	}

	@Override
	public List<Feriado> pesquisarFeriadoPorFiltro(Feriado filtro, int first, int pageSize) {
		return feriadoRepository.pesquisarFeriadoPorFiltro(filtro, first, pageSize);
	}

	@Override
	public void salvarFeriado(Feriado feriado) {
		feriadoRepository.save(feriado);
	}

	@Override
	public boolean verificaExistenciaFeriado(Feriado feriado) {
		return feriadoRepository.verificaExistenciaFeriado(feriado);
	}

	@Override
	public void excluir(Feriado feriado) {
		feriadoRepository.delete(feriado);
	}

	@Override
	public Feriado recuperarFeriadoPorChave(Long id) {
		return feriadoRepository.findOne(id);
	}

	@Override
	public Boolean verificaDiaFeriado(Date dataFinalizacao, Cidade cidade) {
		return feriadoRepository.verificaDiaFeriado(dataFinalizacao, cidade);
	}

}
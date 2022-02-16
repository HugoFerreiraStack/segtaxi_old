/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Auditoria;
import br.com.seg.econotaxi.repository.AuditoriaRepository;
import br.com.seg.econotaxi.service.AuditoriaService;

/**
 * @author bruno
 *
 */
@Service("auditoriaService")
public class AuditoriaServiceImpl implements AuditoriaService {

	@Autowired
	private AuditoriaRepository auditoriaRepository;
	
	@Override
	public Long countPorFiltro(Auditoria filtro) {
		return auditoriaRepository.countPorFiltro(filtro);
	}

	@Override
	public List<Auditoria> pesquisarPorFiltro(Auditoria filtro, int first, int pageSize) {
		return auditoriaRepository.pesquisarPorFiltro(filtro, first, pageSize);
	}

	@Override
	public void salvar(Auditoria a) {
		auditoriaRepository.save(a);
	}
	
}
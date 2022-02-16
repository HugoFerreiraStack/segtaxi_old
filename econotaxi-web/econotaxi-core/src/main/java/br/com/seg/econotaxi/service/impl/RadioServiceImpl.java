/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Radio;
import br.com.seg.econotaxi.repository.RadioRepository;
import br.com.seg.econotaxi.service.RadioService;

/**
 * @author bruno
 *
 */
@Service("radioService")
public class RadioServiceImpl implements RadioService {

	@Autowired
	private RadioRepository radioRepository;
	
	@Override
	public void salvarRadio(Radio radio) {
		radioRepository.save(radio);
	}

	@Override
	public Radio recuperarRadioPorChave(Long chave) {
		return radioRepository.findById(chave);
	}

	@Override
	public boolean verificaExistenciaRadio(Radio radio) {
		return radioRepository.verificaExistenciaRadio(radio);
	}

	@Override
	public void excluir(Radio radio) {
		radioRepository.delete(radio);
	}

	@Override
	public Long pesquisarCountRadioPorFiltro(Radio filtro) {
		return radioRepository.pesquisarCountRadioPorFiltro(filtro);
	}

	@Override
	public List<Radio> pesquisarRadioPorFiltro(Radio filtro, int first, int pageSize) {
		return radioRepository.pesquisarRadioPorFiltro(filtro, first, pageSize);
	}

}
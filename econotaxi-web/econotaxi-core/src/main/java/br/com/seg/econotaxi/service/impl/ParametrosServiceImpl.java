package br.com.seg.econotaxi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Parametro;
import br.com.seg.econotaxi.repository.ParametrosRepository;
import br.com.seg.econotaxi.service.ParametrosService;

@Service("parametrosService")
public class ParametrosServiceImpl implements ParametrosService {

	// Atributos
    @Autowired
    private ParametrosRepository parametrosRepository;

	@Override
	public Parametro recuperarParametroSistema() {
		return parametrosRepository.recuperarParametro();
	}

	@Override
	public void salvar(Parametro parametro) {
		parametrosRepository.save(parametro);
	}
    
}
package br.com.seg.econotaxi.service.impl;

import br.com.seg.econotaxi.model.Sistema;
import br.com.seg.econotaxi.repository.SistemaRepository;
import br.com.seg.econotaxi.service.SistemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sistemaService")
public class SistemaServiceImpl extends AbstractService implements SistemaService {

    @Autowired
    private SistemaRepository sistemaRepository;

    @Override
    public Sistema recuperarPorChave(String chave) {
        return sistemaRepository.findByChave(chave);
    }
}

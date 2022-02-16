package br.com.seg.econotaxi.service;

import br.com.seg.econotaxi.model.Sistema;

public interface SistemaService {

    Sistema recuperarPorChave(String chave);

}

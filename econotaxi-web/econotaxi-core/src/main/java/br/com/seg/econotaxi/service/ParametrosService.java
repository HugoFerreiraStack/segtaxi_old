package br.com.seg.econotaxi.service;

import br.com.seg.econotaxi.model.Parametro;

public interface ParametrosService {

	Parametro recuperarParametroSistema();

	void salvar(Parametro parametro);

}
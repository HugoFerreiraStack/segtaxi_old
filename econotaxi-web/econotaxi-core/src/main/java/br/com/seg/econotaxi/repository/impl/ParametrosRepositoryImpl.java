package br.com.seg.econotaxi.repository.impl;

import java.util.HashMap;
import java.util.Map;

import br.com.seg.econotaxi.model.Parametro;
import br.com.seg.econotaxi.repository.ParametrosRepositoryCustom;

/**
 * Classe de repositório para Usuários.
 *
 * Criado em 24 de jun de 2017
 * @author Bruno rocha
 */
public class ParametrosRepositoryImpl extends RepositoryCustomImpl<Parametro, Integer> 
	implements ParametrosRepositoryCustom {

	@Override
	public Parametro recuperarParametro() {
		
		Parametro parametro = null;
		Map<String,Object> params = new HashMap<String,Object>();
		StringBuilder hql = new StringBuilder();
		hql.append("select p from Parametro p where p.id = 1 ");
		try {
			parametro = findOne(hql.toString(), params);
		} catch (Exception e) {}
		return parametro;
	}

}
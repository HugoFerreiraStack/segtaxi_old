/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Radio;

/**
 * @author bruno
 *
 */
public interface RadioService {

	void salvarRadio(Radio radio);
	
	Radio recuperarRadioPorChave(Long chave);
	
	boolean verificaExistenciaRadio(Radio radio);

	void excluir(Radio radio);

	Long pesquisarCountRadioPorFiltro(Radio filtro);

	List<Radio> pesquisarRadioPorFiltro(Radio filtro, int first, int pageSize);
	
}
/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.ClienteTeleTaxi;

/**
 * @author bruno
 *
 */
public interface ClienteTeleTaxiService {

	void salvar(ClienteTeleTaxi clienteTeleTaxi);
	
	void delete(ClienteTeleTaxi clienteTeleTaxi);
	
	ClienteTeleTaxi recuperarPorChave(Long id);

	boolean verificaExistenciaCliente(ClienteTeleTaxi clienteTeleTaxi);

	Long pesquisarCountClientePorFiltro(ClienteTeleTaxi filtro);

	List<ClienteTeleTaxi> pesquisarClientePorFiltro(ClienteTeleTaxi filtro, int first, int pageSize);

	ClienteTeleTaxi recuperarClientePorCelular(String celularPassageiro);
	
}
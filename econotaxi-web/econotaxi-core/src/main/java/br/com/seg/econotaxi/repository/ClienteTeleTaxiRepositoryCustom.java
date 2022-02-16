/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.ClienteTeleTaxi;

/**
 * @author bruno
 *
 */
public interface ClienteTeleTaxiRepositoryCustom {

	boolean verificaExistenciaCliente(ClienteTeleTaxi clienteTeleTaxi);
	
	Long pesquisarCountClientePorFiltro(ClienteTeleTaxi filtro);

	List<ClienteTeleTaxi> pesquisarClientePorFiltro(ClienteTeleTaxi filtro, int first, int pageSize);
	
	ClienteTeleTaxi recuperarClientePorCelular(String celularPassageiro);
	
}
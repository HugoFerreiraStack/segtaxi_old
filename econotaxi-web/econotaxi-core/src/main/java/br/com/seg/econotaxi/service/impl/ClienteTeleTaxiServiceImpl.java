/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.ClienteTeleTaxi;
import br.com.seg.econotaxi.repository.ClienteTeleTaxiRepository;
import br.com.seg.econotaxi.service.ClienteTeleTaxiService;

/**
 * @author bruno
 *
 */
@Service("clienteTeleTaxiService")
public class ClienteTeleTaxiServiceImpl implements ClienteTeleTaxiService {

	@Autowired
	private ClienteTeleTaxiRepository clienteTeleTaxiRepository;

	@Override
	public void salvar(ClienteTeleTaxi clienteTeleTaxi) {
		clienteTeleTaxiRepository.save(clienteTeleTaxi);
	}

	@Override
	public void delete(ClienteTeleTaxi clienteTeleTaxi) {
		clienteTeleTaxiRepository.delete(clienteTeleTaxi);
	}

	@Override
	public ClienteTeleTaxi recuperarPorChave(Long id) {
		return clienteTeleTaxiRepository.findOne(id);
	}

	@Override
	public boolean verificaExistenciaCliente(ClienteTeleTaxi clienteTeleTaxi) {
		return clienteTeleTaxiRepository.verificaExistenciaCliente(clienteTeleTaxi);
	}

	@Override
	public Long pesquisarCountClientePorFiltro(ClienteTeleTaxi filtro) {
		return clienteTeleTaxiRepository.pesquisarCountClientePorFiltro(filtro);
	}

	@Override
	public List<ClienteTeleTaxi> pesquisarClientePorFiltro(ClienteTeleTaxi filtro, int first, int pageSize) {
		return clienteTeleTaxiRepository.pesquisarClientePorFiltro(filtro, first, pageSize);
	}

	@Override
	public ClienteTeleTaxi recuperarClientePorCelular(String celularPassageiro) {
		return clienteTeleTaxiRepository.recuperarClientePorCelular(celularPassageiro);
	}
	
}
/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.DescontoLojista;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.repository.DescontoLojistaRepository;
import br.com.seg.econotaxi.service.DescontoLojistaService;

/**
 * @author bruno
 *
 */
@Service("descontoLojistaService")
public class DescontoLojistaServiceImpl implements DescontoLojistaService {

	@Autowired
	private DescontoLojistaRepository descontoLojistaRepository;

	@Override
	public void salvar(DescontoLojista descontoLojista) {
		
		descontoLojistaRepository.save(descontoLojista);
	}

	@Override
	public DescontoLojista recuperarDescontoPorHash(String hash) {
		return descontoLojistaRepository.recuperarDescontoPorHash(hash);
	}

	@Override
	public int countDescontosDisponiveisPorUsuario(Long idUsuarioCliente) {
		return descontoLojistaRepository.countDescontosDisponiveisPorUsuario(idUsuarioCliente);
	}

	@Override
	public DescontoLojista recuperarDescontoDisponivelPorUsuario(Usuario usuario, Cidade cidade) {
		return descontoLojistaRepository.recuperarDescontoDisponivelPorUsuario(usuario, cidade);
	}
	
}
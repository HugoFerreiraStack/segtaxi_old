/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Auxiliar;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.repository.AuxiliarRepository;
import br.com.seg.econotaxi.service.AuxiliarService;

/**
 * @author bruno
 *
 */
@Service("auxiliarService")
public class AuxiliarServiceImpl implements AuxiliarService {

	// Atributos
	@Autowired
	private AuxiliarRepository auxiliarRepository;
	
	@Override
	public List<Auxiliar> recuperarAuxiliaresUsuario(Usuario usuario) {
		
		return auxiliarRepository.recuperarAuxiliaresUsuario(usuario);
	}

	@Override
	public void salvar(Auxiliar auxiliar) {
		auxiliarRepository.save(auxiliar);
	}

	@Override
	public void excluir(Auxiliar auxiliar) {
		auxiliarRepository.delete(auxiliar);
	}

	@Override
	public Auxiliar recuperarAuxiliarPorChave(Long id) {
		return auxiliarRepository.findOne(id);
	}

	@Override
	public boolean verificaExistenciaAuxiliar(Auxiliar auxiliar) {
		return auxiliarRepository.verificaExistenciaAuxiliar(auxiliar);
	}
	
}
/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Familia;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.repository.FamiliaRepository;
import br.com.seg.econotaxi.service.FamiliaService;

/**
 * @author bruno
 *
 */
@Service("familiaService")
public class FamiliaServiceImpl implements FamiliaService {

	// Atributos
	@Autowired
	private FamiliaRepository familiaRepository;
	
	@Override
	public List<Familia> recuperarFamiliaresUsuario(Usuario usuario) {
		
		return familiaRepository.recuperarFamiliaresUsuario(usuario);
	}

	@Override
	public void salvar(Familia familia) {
		familiaRepository.save(familia);
	}

	@Override
	public void excluir(Familia familia) {
		familiaRepository.delete(familia);
	}

	@Override
	public Familia recuperarFamiliaPorChave(Long id) {
		return familiaRepository.findOne(id);
	}

	@Override
	public List<Familia> recuperarFamiliaresSolicitacoesUsuario(Usuario usuario) {
		return familiaRepository.recuperarFamiliaresSolicitacoesUsuario(usuario);
	}
	
}
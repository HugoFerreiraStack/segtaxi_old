/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Perfil;
import br.com.seg.econotaxi.repository.PerfilRepository;
import br.com.seg.econotaxi.service.PerfilService;

/**
 * @author bruno
 *
 */
@Service("perfilService")
public class PerfilServiceImpl implements PerfilService {

	@Autowired
	private PerfilRepository perfilRepository;
	
	@Override
	public void salvarPerfil(Perfil perfil) {
		perfilRepository.save(perfil);
	}

	@Override
	public Perfil recuperarPerfilPorChave(Long chave) {
		return perfilRepository.findById(chave);
	}

	@Override
	public List<Perfil> recuperarTodosPerfis() {
		return perfilRepository.findAllByOrderByNomeAsc();
	}

	@Override
	public List<Perfil> recuperarPerfisAtivos() {
		return perfilRepository.recuperarPerfisAtivos();
	}

	@Override
	public boolean verificaExistenciaPerfil(Perfil grupo) {
		return perfilRepository.verificaExistenciaPerfil(grupo);
	}

	@Override
	public void excluir(Perfil grupo) {
		perfilRepository.delete(grupo);
	}

	@Override
	public Long pesquisarCountPerfilPorFiltro(Perfil filtro) {
		return perfilRepository.pesquisarCountPerfilPorFiltro(filtro);
	}

	@Override
	public List<Perfil> pesquisarPerfilPorFiltro(Perfil filtro, int first, int pageSize) {
		return perfilRepository.pesquisarPerfilPorFiltro(filtro, first, pageSize);
	}

}
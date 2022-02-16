/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Lojista;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.repository.LojistaRepository;
import br.com.seg.econotaxi.service.LojistaService;

/**
 * @author bruno
 *
 */
@Service("lojistaService")
public class LojistaServiceImpl implements LojistaService {

	@Autowired
	private LojistaRepository lojistaRepository;

	@Override
	public Lojista salvar(Lojista lojista) {
		return lojistaRepository.save(lojista);
	}

	@Override
	public Lojista consultarLojistaPorChave(Long idLojista) {
		return lojistaRepository.findOne(idLojista);
	}

	@Override
	public Long pesquisarCountLojistaPorFiltro(Lojista filtro) {
		return lojistaRepository.pesquisarCountLojistaPorFiltro(filtro);
	}

	@Override
	public List<Lojista> pesquisarLojistaPorFiltro(Lojista filtro, int first, int pageSize) {
		return lojistaRepository.pesquisarLojistaPorFiltro(filtro, first, pageSize);
	}

	@Override
	public Integer recuperarQtdLojistaPorStatus(Integer status) {
		return lojistaRepository.recuperarQtdLojistaPorStatus(status);
	}

	@Override
	public List<Lojista> recuperarLojistaPorStatus(Integer status) {
		return lojistaRepository.recuperarLojistaPorStatus(status);
	}

	@Override
	public Lojista recuperarLojistaPorUsuario(Usuario usuario) {
		return lojistaRepository.recuperarLojistaPorUsuario(usuario);
	}
	
}
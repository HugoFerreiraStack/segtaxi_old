/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Novidade;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.UsuarioNovidade;
import br.com.seg.econotaxi.repository.NovidadeRepository;
import br.com.seg.econotaxi.repository.UsuarioNovidadeRepository;
import br.com.seg.econotaxi.service.NovidadeService;

/**
 * @author bruno
 *
 */
@Service("novidadeService")
public class NovidadeServiceImpl implements NovidadeService {

	@Autowired
	private NovidadeRepository novidadeRepository;
	@Autowired
	private UsuarioNovidadeRepository usuarioNovidadeRepository;
	
	@Override
	public void salvarNovidade(Novidade novidade) {
		novidadeRepository.save(novidade);
	}

	@Override
	public Novidade recuperaNovidadePorChave(Long chave) {
		return novidadeRepository.findById(chave);
	}

	@Override
	public List<Novidade> recuperarUltimasNovidades(Usuario usuario) {
		return novidadeRepository.recuperarUltimasNovidades(usuario);
	}

	@Override
	public Long pesquisarCountNovidadePorFiltro(Novidade filtro) {
		return novidadeRepository.pesquisarCountNovidadePorFiltro(filtro);
	}

	@Override
	public List<Novidade> pesquisarNovidadePorFiltro(Novidade filtro, int first, int pageSize) {
		return novidadeRepository.pesquisarNovidadePorFiltro(filtro, first, pageSize);
	}

	@Override
	public List<Novidade> recuperarNovidades2Dias() {
		return novidadeRepository.recuperarNovidades2Dias();
	}

	@Override
	public List<Novidade> recuperarNovidadesNaoVistas(Usuario usuario) {
		return novidadeRepository.recuperarNovidadesNaoVistas(usuario);
	}

	@Override
	public void excluir(Novidade novidade) {
		
		List<UsuarioNovidade> novidades = 
				usuarioNovidadeRepository.recuperarNovidadesUsuario(novidade);
		if (novidades != null && !novidades.isEmpty()) {
			for (UsuarioNovidade usuarioNovidade : novidades) {
				usuarioNovidadeRepository.delete(usuarioNovidade);
			}
		}
		novidadeRepository.delete(novidade);
	}

}
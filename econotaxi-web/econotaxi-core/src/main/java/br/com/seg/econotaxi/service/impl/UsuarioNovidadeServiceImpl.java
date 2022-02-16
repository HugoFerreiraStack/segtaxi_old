package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.UsuarioNovidade;
import br.com.seg.econotaxi.repository.UsuarioNovidadeRepository;
import br.com.seg.econotaxi.service.UsuarioNovidadeService;

@Service("usuarioNovidadeService")
public class UsuarioNovidadeServiceImpl extends AbstractService implements UsuarioNovidadeService {

    @Autowired
    private UsuarioNovidadeRepository usuarioNovidadeRepository;

	@Override
	public synchronized void salvar(UsuarioNovidade usuarioNovidade) {
		usuarioNovidadeRepository.save(usuarioNovidade);
	}

	@Override
	public List<UsuarioNovidade> recuperarNovidadesUsuario(Usuario usuario) {
		return usuarioNovidadeRepository.recuperarNovidadesUsuario(usuario);
	}

	@Override
	public List<UsuarioNovidade> recuperarNovidadesNaoVistas() {
		return usuarioNovidadeRepository.recuperarNovidadesNaoVistas();
	}

}
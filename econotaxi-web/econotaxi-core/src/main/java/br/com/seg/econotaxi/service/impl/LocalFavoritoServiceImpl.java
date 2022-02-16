package br.com.seg.econotaxi.service.impl;

import br.com.seg.econotaxi.model.LocalFavorito;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.repository.LocalFavoritoRepository;
import br.com.seg.econotaxi.service.LocalFavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("localFavoritoService")
public class LocalFavoritoServiceImpl implements LocalFavoritoService {

    @Autowired
    private LocalFavoritoRepository localFavoritoRepository;

    @Override
    public List<LocalFavorito> recuperarPorUsuario(Long idUsuario) {

        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);

        return localFavoritoRepository.findByUsuario(usuario);
    }

    @Override
    public void remover(LocalFavorito localFavorito) {
        localFavoritoRepository.delete(localFavorito);
    }

	@Override
	public void salvarLocal(LocalFavorito localFavorito) {
		localFavoritoRepository.save(localFavorito);
	}
}

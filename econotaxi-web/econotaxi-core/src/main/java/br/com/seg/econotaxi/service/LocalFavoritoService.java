package br.com.seg.econotaxi.service;

import br.com.seg.econotaxi.model.LocalFavorito;

import java.util.List;

public interface LocalFavoritoService {

    List<LocalFavorito> recuperarPorUsuario(Long idUsuario);

    void remover(LocalFavorito localFavorito);

	void salvarLocal(LocalFavorito localFavorito);

}

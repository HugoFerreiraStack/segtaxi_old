package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.UsuarioNovidade;

public interface UsuarioNovidadeService {

	void salvar(UsuarioNovidade usuarioNovidade);

	List<UsuarioNovidade> recuperarNovidadesUsuario(Usuario usuario);

	List<UsuarioNovidade> recuperarNovidadesNaoVistas();

}
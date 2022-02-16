package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.UsuarioService;

/**
 * Classe responsável pelo controle de paginação de Usuários.
 *
 * Criado em 29 de jun de 2017
 * @author Bruno rocha
 */
public class UsuarioDataModal extends PaginatorDataModel<Usuario> {
	
	// Constantes
	private static final long serialVersionUID = -2069807577382491361L;
	
	// Atributos
	private Usuario filtro;
	private UsuarioService usuarioService;
	
	/**
	 * Construtor da classe.
	 * 
	 * @param usuarioService o serviço Usuario
	 * @param filtro o filtro de consulta
	 */
	public UsuarioDataModal(UsuarioService usuarioService, Usuario filtro) {
		this.usuarioService = usuarioService;
		this.filtro = filtro;
	}
	
	/**
	 * Obtém a quantidade de registros da consulta.
	 */
	@Override
	protected Long obterRowCount() {
		return usuarioService.pesquisarCountUsuarioPorFiltro(filtro);
	}
	
	/**
	 * Obtém a listagem de objetos recuperados na consulta.
	 */
	@Override
	protected List<Usuario> obterListResult(int first, int pageSize, String sortField) {
		return usuarioService.pesquisarUsuarioPorFiltro(filtro, first, pageSize);
	}
	
	/**
	 * Recupera a chave do objeto Usuario
	 */
	@Override
	protected Object getCodigo(Usuario object) {
		return object.getId();
	}

}
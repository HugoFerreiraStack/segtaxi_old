package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.Perfil;
import br.com.seg.econotaxi.service.PerfilService;

/**
 * Classe responsável pelo controle de paginação de Grupos de Usuários.
 *
 * Criado em 29 de jun de 2017
 * @author Bruno rocha
 */
public class GrupoDataModel extends PaginatorDataModel<Perfil> {
	
	// Constantes
	private static final long serialVersionUID = -2069807577382491361L;
	
	// Atributos
	private Perfil filtro;
	private PerfilService perfilService;
	
	/**
	 * Construtor da classe.
	 * 
	 * @param grupoService o serviço Grupo de Usuario
	 * @param filtro o filtro de consulta
	 */
	public GrupoDataModel(PerfilService perfilService, Perfil filtro) {
		this.perfilService = perfilService;
		this.filtro = filtro;
	}
	
	/**
	 * Obtém a quantidade de registros da consulta.
	 */
	@Override
	protected Long obterRowCount() {
		return perfilService.pesquisarCountPerfilPorFiltro(filtro);
	}
	
	/**
	 * Obtém a listagem de objetos recuperados na consulta.
	 */
	@Override
	protected List<Perfil> obterListResult(int first, int pageSize, String sortField) {
		return perfilService.pesquisarPerfilPorFiltro(filtro, first, pageSize);
	}
	
	/**
	 * Recupera a chave do objeto Grupo de Usuario
	 */
	@Override
	protected Object getCodigo(Perfil object) {
		return object.getId();
	}

}
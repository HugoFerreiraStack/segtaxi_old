package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.Auditoria;
import br.com.seg.econotaxi.service.AuditoriaService;

/**
 * @author wellington
 *
 */
public class AuditoriaDataModel extends PaginatorDataModel<Auditoria>{

	// Constantes
	private static final long serialVersionUID = 8528665187332077499L;
	
	private Auditoria filtro;
	private AuditoriaService auditoriaService;
	
	/**
	 * Construtor da classe.
	 * 
	 * @param auditoriaService
	 * @param filtro
	 */
	public AuditoriaDataModel(AuditoriaService auditoriaService, Auditoria filtro) {
		this.auditoriaService = auditoriaService;
		this.filtro = filtro;
	}

	/**
	 * Obtém a quantidade de registros da consulta.
	 */
	@Override
	protected Long obterRowCount() {
		return auditoriaService.countPorFiltro(filtro);
	}

	/**
	 * Obtém a listagem de objetos recuperados na consulta.
	 */
	@Override
	protected List<Auditoria> obterListResult(int first, int pageSize, String sortField) {
		return auditoriaService.pesquisarPorFiltro(filtro, first, pageSize);
	}

	/**
	 * Recupera a chave do objeto auditoria
	 */
	@Override
	protected Object getCodigo(Auditoria object) {
		return object.getId();
	}

}
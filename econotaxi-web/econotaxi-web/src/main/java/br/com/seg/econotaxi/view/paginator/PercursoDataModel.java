package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.Percurso;
import br.com.seg.econotaxi.service.PercursoService;

public class PercursoDataModel extends PaginatorDataModel<Percurso> {
	
	// Constantes
	private static final long serialVersionUID = -2069807577382491361L;
	
	// Atributos
	private Percurso filtro;
	private PercursoService percursoService;
	
	public PercursoDataModel(PercursoService percursoService, Percurso filtro) {
		this.percursoService = percursoService;
		this.filtro = filtro;
	}
	
	/**
	 * Obtém a quantidade de registros da consulta.
	 */
	@Override
	protected Long obterRowCount() {
		return percursoService.pesquisarCountPercursoPorFiltro(filtro);
	}
	
	/**
	 * Obtém a listagem de objetos recuperados na consulta.
	 */
	@Override
	protected List<Percurso> obterListResult(int first, int pageSize, String sortField) {
		return percursoService.pesquisarPercursoPorFiltro(filtro, first, pageSize);
	}
	
	@Override
	protected Object getCodigo(Percurso object) {
		return object.getId();
	}

}
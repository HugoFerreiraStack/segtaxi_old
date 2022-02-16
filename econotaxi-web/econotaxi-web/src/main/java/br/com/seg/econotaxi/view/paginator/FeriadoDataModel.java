package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.Feriado;
import br.com.seg.econotaxi.service.FeriadoService;

public class FeriadoDataModel extends PaginatorDataModel<Feriado> {
	
	// Constantes
	private static final long serialVersionUID = -2069807577382491361L;
	
	// Atributos
	private Feriado filtro;
	private FeriadoService feriadoService;
	
	public FeriadoDataModel(FeriadoService feriadoService, Feriado filtro) {
		this.feriadoService = feriadoService;
		this.filtro = filtro;
	}
	
	/**
	 * Obtém a quantidade de registros da consulta.
	 */
	@Override
	protected Long obterRowCount() {
		return feriadoService.pesquisarCountFeriadoPorFiltro(filtro);
	}
	
	/**
	 * Obtém a listagem de objetos recuperados na consulta.
	 */
	@Override
	protected List<Feriado> obterListResult(int first, int pageSize, String sortField) {
		return feriadoService.pesquisarFeriadoPorFiltro(filtro, first, pageSize);
	}
	
	@Override
	protected Object getCodigo(Feriado object) {
		return object.getId();
	}

}
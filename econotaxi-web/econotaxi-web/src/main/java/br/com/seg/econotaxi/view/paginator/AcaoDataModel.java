package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.Acao;
import br.com.seg.econotaxi.service.AcaoService;

public class AcaoDataModel extends PaginatorDataModel<Acao> {
	
	// Constantes
	private static final long serialVersionUID = -2069807577382491361L;
	
	// Atributos
	private Acao filtro;
	private AcaoService acaoService;
	
	public AcaoDataModel(AcaoService acaoService, Acao filtro) {
		this.acaoService = acaoService;
		this.filtro = filtro;
	}
	
	/**
	 * Obtém a quantidade de registros da consulta.
	 */
	@Override
	protected Long obterRowCount() {
		return acaoService.pesquisarCountAcaoPorFiltro(filtro);
	}
	
	/**
	 * Obtém a listagem de objetos recuperados na consulta.
	 */
	@Override
	protected List<Acao> obterListResult(int first, int pageSize, String sortField) {
		return acaoService.pesquisarAcaoPorFiltro(filtro, first, pageSize);
	}
	
	@Override
	protected Object getCodigo(Acao object) {
		return object.getId();
	}

}
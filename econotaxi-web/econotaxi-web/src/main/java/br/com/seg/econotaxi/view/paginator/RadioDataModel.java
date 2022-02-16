package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.Radio;
import br.com.seg.econotaxi.service.RadioService;

public class RadioDataModel extends PaginatorDataModel<Radio> {
	
	// Constantes
	private static final long serialVersionUID = -2069807577382491361L;
	
	// Atributos
	private Radio filtro;
	private RadioService radioService;
	
	public RadioDataModel(RadioService radioService, Radio filtro) {
		this.radioService = radioService;
		this.filtro = filtro;
	}
	
	/**
	 * Obtém a quantidade de registros da consulta.
	 */
	@Override
	protected Long obterRowCount() {
		return radioService.pesquisarCountRadioPorFiltro(filtro);
	}
	
	/**
	 * Obtém a listagem de objetos recuperados na consulta.
	 */
	@Override
	protected List<Radio> obterListResult(int first, int pageSize, String sortField) {
		return radioService.pesquisarRadioPorFiltro(filtro, first, pageSize);
	}
	
	@Override
	protected Object getCodigo(Radio object) {
		return object.getId();
	}

}
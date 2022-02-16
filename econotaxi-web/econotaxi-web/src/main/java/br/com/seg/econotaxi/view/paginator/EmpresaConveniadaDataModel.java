package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.EmpresaConveniada;
import br.com.seg.econotaxi.service.EmpresaConveniadaService;

public class EmpresaConveniadaDataModel extends PaginatorDataModel<EmpresaConveniada> {
	
	// Constantes
	private static final long serialVersionUID = 349918308869987020L;
	
	// Atributos
	private EmpresaConveniada filtro;
	private EmpresaConveniadaService empresaConveniadaService;
	
	public EmpresaConveniadaDataModel(EmpresaConveniadaService empresaConveniadaService, EmpresaConveniada filtro) {
		this.empresaConveniadaService = empresaConveniadaService;
		this.filtro = filtro;
	}
	
	/**
	 * Obtém a quantidade de registros da consulta.
	 */
	@Override
	protected Long obterRowCount() {
		return empresaConveniadaService.pesquisarCountEmpresaConveniadaPorFiltro(filtro);
	}
	
	/**
	 * Obtém a listagem de objetos recuperados na consulta.
	 */
	@Override
	protected List<EmpresaConveniada> obterListResult(int first, int pageSize, String sortField) {
		return empresaConveniadaService.pesquisarEmpresaConveniadaPorFiltro(filtro, first, pageSize);
	}
	
	@Override
	protected Object getCodigo(EmpresaConveniada object) {
		return object.getId();
	}

}
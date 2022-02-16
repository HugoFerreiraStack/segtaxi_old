package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.CentroCusto;
import br.com.seg.econotaxi.service.EmpresaConveniadaService;

public class CentroCustoDataModel extends PaginatorDataModel<CentroCusto> {
	
	// Constantes
	private static final long serialVersionUID = 5953976336497816917L;
	
	// Atributos
	private CentroCusto filtro;
	private EmpresaConveniadaService empresaConveniadaService;
	
	public CentroCustoDataModel(EmpresaConveniadaService empresaConveniadaService, CentroCusto filtro) {
		this.empresaConveniadaService = empresaConveniadaService;
		this.filtro = filtro;
	}
	
	/**
	 * Obtém a quantidade de registros da consulta.
	 */
	@Override
	protected Long obterRowCount() {
		return empresaConveniadaService.pesquisarCountCentroCustoPorFiltro(filtro);
	}
	
	/**
	 * Obtém a listagem de objetos recuperados na consulta.
	 */
	@Override
	protected List<CentroCusto> obterListResult(int first, int pageSize, String sortField) {
		return empresaConveniadaService.pesquisarCentroCustoPorFiltro(filtro, first, pageSize);
	}
	
	@Override
	protected Object getCodigo(CentroCusto object) {
		return object.getId();
	}

}
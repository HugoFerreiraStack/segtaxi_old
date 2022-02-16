package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.VeiculoLigue;
import br.com.seg.econotaxi.service.VeiculoLigueService;

public class VeiculoLigueDataModel extends PaginatorDataModel<VeiculoLigue> {
	
	// Constantes
	private static final long serialVersionUID = -2069807577382491361L;
	
	// Atributos
	private VeiculoLigue filtro;
	private VeiculoLigueService veiculoLigueService;
	
	public VeiculoLigueDataModel(VeiculoLigueService veiculoLigueService, VeiculoLigue filtro) {
		this.veiculoLigueService = veiculoLigueService;
		this.filtro = filtro;
	}
	
	/**
	 * Obtém a quantidade de registros da consulta.
	 */
	@Override
	protected Long obterRowCount() {
		return veiculoLigueService.pesquisarCountPerfilPorFiltro(filtro);
	}
	
	@Override
	protected List<VeiculoLigue> obterListResult(int first, int pageSize, String sortField) {
		return veiculoLigueService.pesquisarPerfilPorFiltro(filtro, first, pageSize);
	}
	
	@Override
	protected Object getCodigo(VeiculoLigue object) {
		return object.getId();
	}

}
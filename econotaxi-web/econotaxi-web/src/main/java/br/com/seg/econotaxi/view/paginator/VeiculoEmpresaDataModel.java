package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.Veiculo;
import br.com.seg.econotaxi.service.VeiculoService;

public class VeiculoEmpresaDataModel extends PaginatorDataModel<Veiculo> {
	
	// Constantes
	private static final long serialVersionUID = 3223162146875057277L;
	
	// Atributos
	private Veiculo filtro;
	private VeiculoService veiculoService;
	
	public VeiculoEmpresaDataModel(VeiculoService veiculoService, Veiculo filtro) {
		this.veiculoService = veiculoService;
		this.filtro = filtro;
	}
	
	/**
	 * Obtém a quantidade de registros da consulta.
	 */
	@Override
	protected Long obterRowCount() {
		return veiculoService.pesquisarCountVeiculoEmpresaPorFiltro(filtro);
	}
	
	@Override
	protected List<Veiculo> obterListResult(int first, int pageSize, String sortField) {
		return veiculoService.pesquisarVeiculoEmpresaPorFiltro(filtro, first, pageSize);
	}
	
	@Override
	protected Object getCodigo(Veiculo object) {
		return object.getId();
	}

}
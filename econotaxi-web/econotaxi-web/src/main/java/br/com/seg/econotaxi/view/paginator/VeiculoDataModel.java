package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.Veiculo;
import br.com.seg.econotaxi.service.VeiculoService;

public class VeiculoDataModel extends PaginatorDataModel<Veiculo> {
	
	// Constantes
	private static final long serialVersionUID = -2069807577382491361L;
	
	// Atributos
	private Veiculo filtro;
	private VeiculoService veiculoService;
	
	public VeiculoDataModel(VeiculoService veiculoService, Veiculo filtro) {
		this.veiculoService = veiculoService;
		this.filtro = filtro;
	}
	
	/**
	 * Obtém a quantidade de registros da consulta.
	 */
	@Override
	protected Long obterRowCount() {
		return veiculoService.pesquisarCountPerfilPorFiltro(filtro);
	}
	
	@Override
	protected List<Veiculo> obterListResult(int first, int pageSize, String sortField) {
		return veiculoService.pesquisarPerfilPorFiltro(filtro, first, pageSize);
	}
	
	@Override
	protected Object getCodigo(Veiculo object) {
		return object.getId();
	}

}
package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.ClienteTeleTaxi;
import br.com.seg.econotaxi.service.ClienteTeleTaxiService;

public class ClienteTeleDataModel extends PaginatorDataModel<ClienteTeleTaxi> {
	
	// Constantes
	private static final long serialVersionUID = -2069807577382491361L;
	
	// Atributos
	private ClienteTeleTaxi filtro;
	private ClienteTeleTaxiService clienteTeleTaxiService;
	
	public ClienteTeleDataModel(ClienteTeleTaxiService clienteTeleTaxiService, ClienteTeleTaxi filtro) {
		this.clienteTeleTaxiService = clienteTeleTaxiService;
		this.filtro = filtro;
	}
	
	/**
	 * Obtém a quantidade de registros da consulta.
	 */
	@Override
	protected Long obterRowCount() {
		return clienteTeleTaxiService.pesquisarCountClientePorFiltro(filtro);
	}
	
	/**
	 * Obtém a listagem de objetos recuperados na consulta.
	 */
	@Override
	protected List<ClienteTeleTaxi> obterListResult(int first, int pageSize, String sortField) {
		return clienteTeleTaxiService.pesquisarClientePorFiltro(filtro, first, pageSize);
	}
	
	@Override
	protected Object getCodigo(ClienteTeleTaxi object) {
		return object.getId();
	}

}
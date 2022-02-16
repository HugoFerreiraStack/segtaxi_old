package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.Alerta;
import br.com.seg.econotaxi.service.AlertaService;
import br.com.seg.econotaxi.vo.AlertaFiltroVO;

/**
 * @author wellington
 *
 */
public class AlertaDataModal extends PaginatorDataModel<Alerta>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8888382398289322765L;
	
	private AlertaFiltroVO filtro;
	private AlertaService alertaService;
	
	/**
	 * Construtor da classe.
	 * 
	 * @param alertaService
	 * @param filtro
	 */
	public AlertaDataModal(AlertaService alertaService, AlertaFiltroVO filtro) {
		this.alertaService = alertaService;
		this.filtro = filtro;
	}

	/**
	 * Obtém a quantidade de registros da consulta.
	 */
	@Override
	protected Long obterRowCount() {
		return alertaService.countPorFiltro(filtro);
	}

	/**
	 * Obtém a listagem de objetos recuperados na consulta.
	 */
	@Override
	protected List<Alerta> obterListResult(int first, int pageSize, String sortField) {
		return alertaService.pesquisarPorFiltro(filtro, first, pageSize);
	}

	/**
	 * Recupera a chave do objeto alerta
	 */
	@Override
	protected Object getCodigo(Alerta object) {
		return object.getId();
	}

}

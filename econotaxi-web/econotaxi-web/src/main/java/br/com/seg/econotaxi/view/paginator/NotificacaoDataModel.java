package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.Notificacao;
import br.com.seg.econotaxi.service.NotificacaoService;

public class NotificacaoDataModel extends PaginatorDataModel<Notificacao> {
	
	// Constantes
	private static final long serialVersionUID = -2069807577382491361L;
	
	// Atributos
	private Notificacao filtro;
	private NotificacaoService notificacaoService;
	
	public NotificacaoDataModel(NotificacaoService notificacaoService, Notificacao filtro) {
		this.notificacaoService = notificacaoService;
		this.filtro = filtro;
	}
	
	/**
	 * Obtém a quantidade de registros da consulta.
	 */
	@Override
	protected Long obterRowCount() {
		return notificacaoService.pesquisarCountNotificacaoPorFiltro(filtro);
	}
	
	/**
	 * Obtém a listagem de objetos recuperados na consulta.
	 */
	@Override
	protected List<Notificacao> obterListResult(int first, int pageSize, String sortField) {
		return notificacaoService.pesquisarNotificacaoPorFiltro(filtro, first, pageSize);
	}
	
	@Override
	protected Object getCodigo(Notificacao object) {
		return object.getId();
	}

}
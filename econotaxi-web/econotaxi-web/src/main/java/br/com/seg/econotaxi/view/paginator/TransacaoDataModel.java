package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.Transacao;
import br.com.seg.econotaxi.service.TransacaoService;

public class TransacaoDataModel extends PaginatorDataModel<Transacao> {
	
	// Constantes
	private static final long serialVersionUID = -2069807577382491361L;
	
	// Atributos
	private Transacao filtro;
	private TransacaoService transacaoService;
	
	public TransacaoDataModel(TransacaoService transacaoService, Transacao filtro) {
		this.transacaoService = transacaoService;
		this.filtro = filtro;
	}
	
	@Override
	protected Long obterRowCount() {
		return transacaoService.pesquisarCountTransacaoPorFiltro(filtro);
	}
	
	/**
	 * Obtém a listagem de objetos recuperados na consulta.
	 */
	@Override
	protected List<Transacao> obterListResult(int first, int pageSize, String sortField) {
		return transacaoService.pesquisarTransacaoPorFiltro(filtro, first, pageSize);
	}
	
	@Override
	protected Object getCodigo(Transacao object) {
		return object.getId();
	}

}
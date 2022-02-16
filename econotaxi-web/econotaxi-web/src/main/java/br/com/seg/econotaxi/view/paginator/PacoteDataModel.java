package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.Pacote;
import br.com.seg.econotaxi.service.PacoteService;

public class PacoteDataModel extends PaginatorDataModel<Pacote> {
	
	// Constantes
	private static final long serialVersionUID = -2069807577382491361L;
	
	// Atributos
	private Pacote filtro;
	private PacoteService pacoteService;
	
	public PacoteDataModel(PacoteService pacoteService, Pacote filtro) {
		this.pacoteService = pacoteService;
		this.filtro = filtro;
	}
	
	@Override
	protected Long obterRowCount() {
		return pacoteService.pesquisarCountPacotePorFiltro(filtro);
	}
	
	/**
	 * Obtém a listagem de objetos recuperados na consulta.
	 */
	@Override
	protected List<Pacote> obterListResult(int first, int pageSize, String sortField) {
		return pacoteService.pesquisarPacotePorFiltro(filtro, first, pageSize);
	}
	
	@Override
	protected Object getCodigo(Pacote object) {
		return object.getId();
	}

}
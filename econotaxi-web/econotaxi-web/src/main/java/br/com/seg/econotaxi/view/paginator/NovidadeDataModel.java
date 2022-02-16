package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.Novidade;
import br.com.seg.econotaxi.service.NovidadeService;

public class NovidadeDataModel extends PaginatorDataModel<Novidade> {
	
	// Constantes
	private static final long serialVersionUID = -2069807577382491361L;
	
	// Atributos
	private Novidade filtro;
	private NovidadeService novidadeService;
	
	public NovidadeDataModel(NovidadeService novidadeService, Novidade filtro) {
		this.novidadeService = novidadeService;
		this.filtro = filtro;
	}
	
	@Override
	protected Long obterRowCount() {
		return novidadeService.pesquisarCountNovidadePorFiltro(filtro);
	}
	
	@Override
	protected List<Novidade> obterListResult(int first, int pageSize, String sortField) {
		return novidadeService.pesquisarNovidadePorFiltro(filtro, first, pageSize);
	}
	
	@Override
	protected Object getCodigo(Novidade object) {
		return object.getId();
	}

}
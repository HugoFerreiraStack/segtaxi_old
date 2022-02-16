package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.service.CorridaService;

public class CorridaTeleDataModel extends PaginatorDataModel<Corrida> {
	
	// Constantes
	private static final long serialVersionUID = -2069807577382491361L;
	
	// Atributos
	private Corrida filtro;
	private CorridaService corridaService;
	
	public CorridaTeleDataModel(CorridaService corridaService, Corrida filtro) {
		this.corridaService = corridaService;
		this.filtro = filtro;
	}
	
	@Override
	protected Long obterRowCount() {
		return corridaService.pesquisarCountCorridaTelePorFiltro(filtro);
	}
	
	@Override
	protected List<Corrida> obterListResult(int first, int pageSize, String sortField) {
		return corridaService.pesquisarCorridaTelePorFiltro(filtro, first, pageSize);
	}
	
	@Override
	protected Object getCodigo(Corrida object) {
		return object.getId();
	}

}
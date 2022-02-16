package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.Lojista;
import br.com.seg.econotaxi.service.LojistaService;

public class LojistaDataModel extends PaginatorDataModel<Lojista> {
	
	// Constantes
	private static final long serialVersionUID = -2069807577382491361L;
	
	// Atributos
	private Lojista filtro;
	private LojistaService lojistaService;
	
	public LojistaDataModel(LojistaService lojistaService, Lojista filtro) {
		this.lojistaService = lojistaService;
		this.filtro = filtro;
	}
	
	@Override
	protected Long obterRowCount() {
		return lojistaService.pesquisarCountLojistaPorFiltro(filtro);
	}
	
	@Override
	protected List<Lojista> obterListResult(int first, int pageSize, String sortField) {
		return lojistaService.pesquisarLojistaPorFiltro(filtro, first, pageSize);
	}
	
	@Override
	protected Object getCodigo(Lojista object) {
		return object.getId();
	}

}
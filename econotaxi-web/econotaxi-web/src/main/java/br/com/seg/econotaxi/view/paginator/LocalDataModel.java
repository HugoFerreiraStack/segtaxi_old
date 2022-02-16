package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.LocalPacote;
import br.com.seg.econotaxi.service.LocalPacoteService;

public class LocalDataModel extends PaginatorDataModel<LocalPacote> {
	
	// Constantes
	private static final long serialVersionUID = -2069807577382491361L;
	
	// Atributos
	private LocalPacote filtro;
	private LocalPacoteService localPacoteService;
	
	public LocalDataModel(LocalPacoteService localPacoteService, LocalPacote filtro) {
		this.localPacoteService = localPacoteService;
		this.filtro = filtro;
	}
	
	@Override
	protected Long obterRowCount() {
		return localPacoteService.pesquisarCountPerfilPorFiltro(filtro);
	}
	
	@Override
	protected List<LocalPacote> obterListResult(int first, int pageSize, String sortField) {
		return localPacoteService.pesquisarLocalPacotePorFiltro(filtro, first, pageSize);
	}
	
	@Override
	protected Object getCodigo(LocalPacote object) {
		return object.getId();
	}

}
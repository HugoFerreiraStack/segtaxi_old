package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.MotoristaLigue;
import br.com.seg.econotaxi.service.MotoristaLigueService;

public class MotoristaLigueDataModel extends PaginatorDataModel<MotoristaLigue> {
	
	// Constantes
	private static final long serialVersionUID = -2069807577382491361L;
	
	// Atributos
	private MotoristaLigue filtro;
	private MotoristaLigueService motoristaLigueService;
	
	public MotoristaLigueDataModel(MotoristaLigueService motoristaLigueService, MotoristaLigue filtro) {
		this.motoristaLigueService = motoristaLigueService;
		this.filtro = filtro;
	}
	
	@Override
	protected Long obterRowCount() {
		return motoristaLigueService.pesquisarCountMotoristaPorFiltro(filtro);
	}
	
	@Override
	protected List<MotoristaLigue> obterListResult(int first, int pageSize, String sortField) {
		return motoristaLigueService.pesquisarMotoristaPorFiltro(filtro, first, pageSize);
	}
	
	@Override
	protected Object getCodigo(MotoristaLigue object) {
		return object.getId();
	}

}
package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.service.MotoristaService;

public class MotoristaDataModel extends PaginatorDataModel<Motorista> {
	
	// Constantes
	private static final long serialVersionUID = -2069807577382491361L;
	
	// Atributos
	private Motorista filtro;
	private MotoristaService motoristaService;
	
	public MotoristaDataModel(MotoristaService motoristaService, Motorista filtro) {
		this.motoristaService = motoristaService;
		this.filtro = filtro;
	}
	
	@Override
	protected Long obterRowCount() {
		return motoristaService.pesquisarCountMotoristaPorFiltro(filtro);
	}
	
	@Override
	protected List<Motorista> obterListResult(int first, int pageSize, String sortField) {
		return motoristaService.pesquisarMotoristaPorFiltro(filtro, first, pageSize);
	}
	
	@Override
	protected Object getCodigo(Motorista object) {
		return object.getId();
	}

}
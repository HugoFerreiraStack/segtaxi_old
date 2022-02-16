package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.Promocao;
import br.com.seg.econotaxi.service.PromocaoService;

public class PromocaoDataModel extends PaginatorDataModel<Promocao> {
	
	// Constantes
	private static final long serialVersionUID = -4485262685868054194L;

	// Atributos
	private Promocao filtro;
	private PromocaoService promocaoService;
	
	public PromocaoDataModel(PromocaoService promocaoService, Promocao filtro) {
		this.promocaoService = promocaoService;
		this.filtro = filtro;
	}
	
	@Override
	protected Long obterRowCount() {
		return promocaoService.pesquisarCountPromocaoPorFiltro(filtro);
	}
	
	@Override
	protected List<Promocao> obterListResult(int first, int pageSize, String sortField) {
		return promocaoService.pesquisarPromocaoPorFiltro(filtro, first, pageSize);
	}
	
	@Override
	protected Object getCodigo(Promocao object) {
		return object.getId();
	}

}
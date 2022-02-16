package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.Localidade;
import br.com.seg.econotaxi.service.LocalidadeService;

/**
 * Classe responsável pelo controle de paginação de Localidades.
 *
 * Criado em 29 de jun de 2017
 * @author Bruno rocha
 */
public class LocalidadeDataModel extends PaginatorDataModel<Localidade> {
	
	// Constantes
	private static final long serialVersionUID = -2069807577382491361L;
	
	// Atributos
	private Localidade filtro;
	private LocalidadeService localidadeService;
	
	public LocalidadeDataModel(LocalidadeService localidadeService, Localidade filtro) {
		this.localidadeService = localidadeService;
		this.filtro = filtro;
	}
	
	@Override
	protected Long obterRowCount() {
		return localidadeService.pesquisarCountLocalidadePorFiltro(filtro);
	}
	
	@Override
	protected List<Localidade> obterListResult(int first, int pageSize, String sortField) {
		return localidadeService.pesquisarLocalidadePorFiltro(filtro, first, pageSize);
	}
	
	@Override
	protected Object getCodigo(Localidade object) {
		return object.getId();
	}

}
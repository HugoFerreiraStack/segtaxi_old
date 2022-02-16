package br.com.seg.econotaxi.view.paginator;

import java.util.List;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.service.CidadeService;

/**
 * Classe responsável pelo controle de paginação de Grupos de Usuários.
 *
 * Criado em 29 de jun de 2017
 * @author Bruno rocha
 */
public class CidadeDataModel extends PaginatorDataModel<Cidade> {
	
	// Constantes
	private static final long serialVersionUID = -2069807577382491361L;
	
	// Atributos
	private Cidade filtro;
	private CidadeService cidadeService;
	
	public CidadeDataModel(CidadeService cidadeService, Cidade filtro) {
		this.cidadeService = cidadeService;
		this.filtro = filtro;
	}
	
	@Override
	protected Long obterRowCount() {
		return cidadeService.pesquisarCountCidadePorFiltro(filtro);
	}
	
	@Override
	protected List<Cidade> obterListResult(int first, int pageSize, String sortField) {
		return cidadeService.pesquisarCidadePorFiltro(filtro, first, pageSize);
	}
	
	@Override
	protected Object getCodigo(Cidade object) {
		return object.getId();
	}

}
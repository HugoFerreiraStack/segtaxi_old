package br.com.seg.econotaxi.view.comum;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.model.Novidade;
import br.com.seg.econotaxi.service.NovidadeService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.NovidadeDataModel;

@Named
@Scope("view")
@ManagedBean(name = "novidadeView")
public class NovidadeView extends BaseView {

    // Constantes
	private static final long serialVersionUID = -6918164650365605188L;
	
	// Atributos
	@Autowired
	private NovidadeService novidadeService;
	private Novidade novidade;
	private Novidade filtro;
	private NovidadeDataModel novidadeDataModel;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		setNovidadeDataModel(new NovidadeDataModel(novidadeService, getFiltro()));
	}
	
    public void carregarNovidade(Novidade novidade) {
    	setNovidade(novidadeService.recuperaNovidadePorChave(novidade.getId()));
    }
    
    public void salvarNovidade() {
    	
    	definirMenu(MenuEnum.NOVIDADES.getMenu());
    	novidade.setDataNovidade(new Date());
    	novidadeService.salvarNovidade(novidade);
    	addMsgSuccess("Novidade salva com sucesso! Em breve ela será enviada ao(s) usuário(s).");
    	setNovidade(null);
    }
    
    public void pesquisarNovidadePorFiltro() {
    	
    	setNovidadeDataModel(new NovidadeDataModel(novidadeService, filtro));
    }
    
    /**
     * Método responsável por limpar formulário e resultados da pesquisa.
     */
    public void limparFormulario() {
    	
    	setFiltro(null);
    	setNovidadeDataModel(null);
    }
    
    public void excluirNovidade() {
    	novidadeService.excluir(this.novidade);
    	addMsgSuccess("Novidade excluída com sucesso.");
    }

    // Métodos get/set
	public Novidade getNovidade() {
		if (novidade == null) {
			novidade = new Novidade();
		}
		return novidade;
	}
	public void setNovidade(Novidade novidade) {
		this.novidade = novidade;
	}
	public Novidade getFiltro() {
		if (filtro == null) {
			filtro = new Novidade();
		}
		return filtro;
	}
	public void setFiltro(Novidade filtro) {
		this.filtro = filtro;
	}
	public NovidadeDataModel getNovidadeDataModel() {
		return novidadeDataModel;
	}
	public void setNovidadeDataModel(NovidadeDataModel novidadeDataModel) {
		this.novidadeDataModel = novidadeDataModel;
	}
	
}
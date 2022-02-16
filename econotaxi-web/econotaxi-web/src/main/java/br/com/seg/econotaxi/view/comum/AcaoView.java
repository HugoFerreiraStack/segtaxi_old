package br.com.seg.econotaxi.view.comum;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.model.Acao;
import br.com.seg.econotaxi.service.AcaoService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.AcaoDataModel;

@Named
@Scope("view")
@ManagedBean(name = "acaoView")
public class AcaoView extends BaseView {

    // Constantes
	private static final long serialVersionUID = -6918164650365605188L;
	
	// Atributos
	@Autowired
	private AcaoService acaoService;
	private Acao acao;
	private Acao filtro;
	private AcaoDataModel acaoDataModel;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		setAcaoDataModel(new AcaoDataModel(acaoService, getFiltro()));
	}
	
    public void carregarAcao(Acao acao) {
    	setAcao(acaoService.consultarPorChave(acao.getId()));
    }
    
    public void salvarAcao() {
    	
    	definirMenu(MenuEnum.ACAO.getMenu());
    	acao.setDataAcao(new Date());
    	acaoService.salvar(acao);
    	addMsgSuccess("Ação salva com sucesso!");
    	setAcao(null);
    }
    
    public void excluirAcao() {
    	
    	definirMenu(MenuEnum.ACAO.getMenu());
    	acaoService.delete(acao);
		addMsgSuccess("Ação excluída com sucesso!");
		setAcao(null);
    }
    
    public void pesquisarAcaoPorFiltro() {
    	
    	setAcaoDataModel(new AcaoDataModel(acaoService, filtro));
    }
    
    /**
     * Método responsável por limpar formulário e resultados da pesquisa.
     */
    public void limparFormulario() {
    	
    	setFiltro(null);
    	setAcaoDataModel(null);
    }

    // Métodos get/set
	public Acao getAcao() {
		if (acao == null) {
			acao = new Acao();
		}
		return acao;
	}

	public void setAcao(Acao acao) {
		this.acao = acao;
	}

	public Acao getFiltro() {
		if (filtro == null) {
			filtro = new Acao();
		}
		return filtro;
	}

	public void setFiltro(Acao filtro) {
		this.filtro = filtro;
	}

	public AcaoDataModel getAcaoDataModel() {
		return acaoDataModel;
	}

	public void setAcaoDataModel(AcaoDataModel acaoDataModel) {
		this.acaoDataModel = acaoDataModel;
	}

}
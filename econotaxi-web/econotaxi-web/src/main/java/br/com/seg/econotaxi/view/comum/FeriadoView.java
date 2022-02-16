package br.com.seg.econotaxi.view.comum;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Feriado;
import br.com.seg.econotaxi.service.CidadeService;
import br.com.seg.econotaxi.service.FeriadoService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.FeriadoDataModel;

@Named
@Scope("view")
@ManagedBean(name = "feriadoView")
public class FeriadoView extends BaseView {

    // Constantes
	private static final long serialVersionUID = -6918164650365605188L;
	
	// Atributos
	@Autowired
	private FeriadoService feriadoService;
	private Feriado feriado;
	private Feriado filtro;
	private FeriadoDataModel feriadoDataModel;
	private List<Cidade> cidades;
	@Autowired
	private CidadeService cidadeService;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		setFeriadoDataModel(new FeriadoDataModel(feriadoService, getFiltro()));
		setCidades(cidadeService.recuperarTodasCidades());
	}
	
    public void carregarFeriado(Feriado feriado) {
    	setFeriado(feriadoService.recuperarFeriadoPorChave(feriado.getId()));
    }
    
    public void salvarFeriado() {
    	
    	definirMenu(MenuEnum.FERIADO.getMenu());
    	if (feriadoService.verificaExistenciaFeriado(feriado)) {
    		addMsgErro("Feriado já existente!");
    	} else {
    		feriadoService.salvarFeriado(feriado);
    		addMsgSuccess("Feriado salvo com sucesso!");
    		setFeriado(null);
    	}
    }
    
    public void excluirFeriado() {
    	
    	definirMenu(MenuEnum.FERIADO.getMenu());
    	feriadoService.excluir(feriado);
		addMsgSuccess("Feriado excluído com sucesso!");
		setFeriado(null);
    }
    
    public void pesquisarFeriadoPorFiltro() {
    	
    	setFeriadoDataModel(new FeriadoDataModel(feriadoService, filtro));
    }
    
    /**
     * Método responsável por limpar formulário e resultados da pesquisa.
     */
    public void limparFormulario() {
    	
    	setFiltro(null);
    	setFeriadoDataModel(null);
    }

    // Métodos get/set
	public Feriado getFeriado() {
		if (feriado == null) {
			feriado = new Feriado();
		}
		return feriado;
	}

	public void setFeriado(Feriado feriado) {
		this.feriado = feriado;
	}

	public Feriado getFiltro() {
		if (filtro == null) {
			filtro = new Feriado();
		}
		return filtro;
	}

	public void setFiltro(Feriado filtro) {
		this.filtro = filtro;
	}

	public FeriadoDataModel getFeriadoDataModel() {
		return feriadoDataModel;
	}

	public void setFeriadoDataModel(FeriadoDataModel feriadoDataModel) {
		this.feriadoDataModel = feriadoDataModel;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

}
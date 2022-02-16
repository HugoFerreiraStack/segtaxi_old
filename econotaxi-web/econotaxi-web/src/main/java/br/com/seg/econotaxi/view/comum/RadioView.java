package br.com.seg.econotaxi.view.comum;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Radio;
import br.com.seg.econotaxi.service.CidadeService;
import br.com.seg.econotaxi.service.RadioService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.RadioDataModel;

@Named
@Scope("view")
@ManagedBean(name = "radioView")
public class RadioView extends BaseView {

    // Constantes
	private static final long serialVersionUID = -6918164650365605188L;
	
	// Atributos
	@Autowired
	private RadioService radioService;
	@Autowired
	private CidadeService cidadeService;
	private Radio radio;
	private Radio filtro;
	private RadioDataModel radioDataModel;
	private List<Cidade> cidades;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		setRadioDataModel(new RadioDataModel(radioService, getFiltro()));
		setCidades(cidadeService.recuperarTodasCidades());
	}
	
    public void carregarRadio(Radio radio) {
    	setRadio(radioService.recuperarRadioPorChave(radio.getId()));
    }
    
    /**
     * Método responsável por salvar um grupo de usuário.
     */
    public void salvarRadio() {
    	
    	definirMenu(MenuEnum.RADIO.getMenu());
    	if (radioService.verificaExistenciaRadio(radio)) {
    		addMsgErro("Perfil já existente!");
    	} else {
    		radioService.salvarRadio(radio);
    		addMsgSuccess("Rádio salva com sucesso!");
    		setRadio(null);
    	}
    }
    
    public void excluirRadio() {
    	
    	definirMenu(MenuEnum.RADIO.getMenu());
    	radioService.excluir(radio);
		addMsgSuccess("Rádio excluída com sucesso!");
		setRadio(null);
    }
    
    public void pesquisarRadioPorFiltro() {
    	
    	setRadioDataModel(new RadioDataModel(radioService, filtro));
    }
    
    /**
     * Método responsável por limpar formulário e resultados da pesquisa.
     */
    public void limparFormulario() {
    	
    	setFiltro(null);
    	setRadioDataModel(null);
    }

    // Métodos get/set
	public Radio getRadio() {
		if (radio == null) {
			radio = new Radio();
		}
		return radio;
	}
	public void setRadio(Radio radio) {
		this.radio = radio;
	}
	public Radio getFiltro() {
		if (filtro == null) {
			filtro = new Radio();
		}
		return filtro;
	}
	public void setFiltro(Radio filtro) {
		this.filtro = filtro;
	}
	public RadioDataModel getRadioDataModel() {
		return radioDataModel;
	}
	public void setRadioDataModel(RadioDataModel radioDataModel) {
		this.radioDataModel = radioDataModel;
	}
	public List<Cidade> getCidades() {
		return cidades;
	}
	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

}
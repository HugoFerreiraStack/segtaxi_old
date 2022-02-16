package br.com.seg.econotaxi.view.comum;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.enums.PromocaoEnum;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.Promocao;
import br.com.seg.econotaxi.service.PromocaoService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.PromocaoDataModel;

@Named
@Scope("view")
@ManagedBean(name = "promocaoView")
public class PromocaoView extends BaseView {

    // Constantes
	private static final long serialVersionUID = -6918164650365605188L;
	
	// Atributos
	@Autowired
	private PromocaoService promocaoService;
	private Promocao promocao;
	private Promocao filtro;
	private PromocaoDataModel promocaoDataModel;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		setPromocaoDataModel(new PromocaoDataModel(promocaoService, getFiltro()));
	}
	
    public void carregarPromocao(Promocao promocao) {
    	setPromocao(promocaoService.recuperaPromocaoPorChave(promocao.getId()));
    }
    
    public void salvarPromocao() {
    	
    	definirMenu(MenuEnum.PROMOCOES.getMenu());
    	promocao.setDataPromocao(new Date());
    	promocaoService.salvarPromocao(promocao);
    	
    	RestTemplate rest = new RestTemplate();
		rest.postForEntity(Corrida.URL + "econotaxi-rest/rest/corridaLocal/adicionarPromocao", 
				promocao, Void.class);
    	
    	addMsgSuccess("Promoção salva com sucesso!");
    	setPromocao(null);
    }
    
    public void excluirPromocao() {
    	
    	RestTemplate rest = new RestTemplate();
		rest.postForEntity(Corrida.URL + "econotaxi-rest/rest/corridaLocal/removerPromocao", 
				promocao, Void.class);
    	
    	definirMenu(MenuEnum.PROMOCOES.getMenu());
    	promocaoService.excluir(promocao);
		addMsgSuccess("Promoção excluída com sucesso!");
		setPromocao(null);
    }
    
    public void pesquisarPromocaoPorFiltro() {
    	
    	setPromocaoDataModel(new PromocaoDataModel(promocaoService, filtro));
    }
    
    /**
     * Método responsável por limpar formulário e resultados da pesquisa.
     */
    public void limparFormulario() {
    	
    	setFiltro(null);
    	setPromocaoDataModel(null);
    }

    // Métodos get/set
	public Promocao getPromocao() {
		if (promocao == null) {
			promocao = new Promocao();
		}
		return promocao;
	}
	public void setPromocao(Promocao promocao) {
		this.promocao = promocao;
	}
	public Promocao getFiltro() {
		if (filtro == null) {
			filtro = new Promocao();
		}
		return filtro;
	}
	public void setFiltro(Promocao filtro) {
		this.filtro = filtro;
	}
	public PromocaoDataModel getPromocaoDataModel() {
		return promocaoDataModel;
	}
	public void setPromocaoDataModel(PromocaoDataModel promocaoDataModel) {
		this.promocaoDataModel = promocaoDataModel;
	}
	public List<PromocaoEnum> getListaPromocaoEnum() {
		return Arrays.asList(PromocaoEnum.values());
	}
	
}
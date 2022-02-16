package br.com.seg.econotaxi.view.comum;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.enums.StatusTransacaoEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Pagamento;
import br.com.seg.econotaxi.model.Transacao;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.CidadeService;
import br.com.seg.econotaxi.service.TransacaoService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.TransacaoDataModel;

@Named
@Scope("view")
@ManagedBean(name = "transacaoView")
public class TransacaoView extends BaseView {

    // Constantes
	private static final long serialVersionUID = 1584745532035822519L;
	
	// Atributos
	@Autowired
	private TransacaoService transacaoService;
	@Autowired
	private CidadeService cidadeService;
	private Transacao transacao;
	private Transacao filtro;
	private TransacaoDataModel transacaoDataModel;
	private List<Cidade> listaCidade;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		setListaCidade(cidadeService.recuperarTodasCidades());
		getFiltro().setCorrida(new Corrida());
		getFiltro().getCorrida().setMotorista(new Motorista());
		getFiltro().getCorrida().setUsuario(new Usuario());
		getFiltro().setPagamento(new Pagamento());
		getFiltro().getPagamento().setUsuario(new Usuario());
		setTransacaoDataModel(new TransacaoDataModel(transacaoService, getFiltro()));
	}
	
	public void carregarTransacao(Transacao transacao) {
		setTransacao(transacao);
	}
	
	public String detalharPassageiro() {
		
		definirMenu(MenuEnum.PASSAGEIROS.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idUsuario", 
				getTransacao().getCorrida().getUsuario().getId());
		return "passageiro";
	}
	
	public String detalharMotorista() {
		
		definirMenu(MenuEnum.MOTORISTA.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idMotorista", 
				getTransacao().getCorrida().getMotorista().getId());
		return "motorista";
	}
	
	public String detalharCorrida() {
		
		definirMenu(MenuEnum.CORRIDA.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idCorrida", 
				getTransacao().getCorrida().getId());
		return "corrida";
	}
	
    public void pesquisarTransacaoPorFiltro() {
    	
    	setTransacaoDataModel(new TransacaoDataModel(transacaoService, filtro));
    }
    
    public void limparFormulario() {
    	
    	setFiltro(null);
    	getFiltro().setCorrida(new Corrida());
		getFiltro().getCorrida().setMotorista(new Motorista());
		getFiltro().getCorrida().setUsuario(new Usuario());
		getFiltro().setPagamento(new Pagamento());
		getFiltro().getPagamento().setUsuario(new Usuario());
    	setTransacaoDataModel(null);
    }

    // Métodos get/set
	public Transacao getTransacao() {
		if (transacao == null) {
			transacao = new Transacao();
		}
		return transacao;
	}

	public void setTransacao(Transacao transacao) {
		this.transacao = transacao;
	}

	public Transacao getFiltro() {
		if (filtro == null) {
			filtro = new Transacao();
		}
		return filtro;
	}

	public void setFiltro(Transacao filtro) {
		this.filtro = filtro;
	}

	public TransacaoDataModel getTransacaoDataModel() {
		return transacaoDataModel;
	}

	public void setTransacaoDataModel(TransacaoDataModel transacaoDataModel) {
		this.transacaoDataModel = transacaoDataModel;
	}

	public List<StatusTransacaoEnum> getListaStatusTransacao() {
		return Arrays.asList(StatusTransacaoEnum.values());
	}

	public List<Cidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}

}
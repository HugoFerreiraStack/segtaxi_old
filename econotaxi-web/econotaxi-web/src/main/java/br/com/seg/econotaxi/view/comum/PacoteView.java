package br.com.seg.econotaxi.view.comum;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.LocalPacote;
import br.com.seg.econotaxi.model.Pacote;
import br.com.seg.econotaxi.service.CidadeService;
import br.com.seg.econotaxi.service.LocalPacoteService;
import br.com.seg.econotaxi.service.PacoteService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.PacoteDataModel;

@Named
@Scope("view")
@ManagedBean(name = "pacoteView")
public class PacoteView extends BaseView {

    // Constantes
	private static final long serialVersionUID = -6918164650365605188L;
	
	// Atributos
	@Autowired
	private PacoteService pacoteService;
	@Autowired
	private CidadeService cidadeService;
	@Autowired
	private LocalPacoteService localPacoteService;
	private Pacote pacote;
	private Pacote filtro;
	private PacoteDataModel pacoteDataModel;
	private List<Cidade> cidades;
	private String cidadeMapa;
	private String latitudeLocal;
	private String longitudeLocal;
	private LocalPacote localPacote;
	private List<LocalPacote> locaisPacote;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		setPacoteDataModel(new PacoteDataModel(pacoteService, getFiltro()));
		setCidades(cidadeService.recuperarTodasCidades());
	}
	
    public void carregarPacote(Pacote pacote) {
    	setPacote(pacoteService.recuperarPacotePorChave(pacote.getId()));
    	setLocalPacote(null);
    	setLocaisPacote(localPacoteService.recuperarLocaisPorPacote(getPacote()));
    }
    
    public void salvarPacote() {
    	
    	definirMenu(MenuEnum.PACOTE.getMenu());
    	if (pacoteService.verificaExistenciaPacote(pacote)) {
    		addMsgErro("Pacote já existente!");
    	} else {
    		pacoteService.salvarPacote(pacote);
    		addMsgSuccess("Pacote salvo com sucesso!");
    		setPacote(null);
    		setLocaisPacote(null);
    	}
    }
    
    public void excluirPacote() {
    	
    	definirMenu(MenuEnum.PACOTE.getMenu());
    	pacoteService.excluir(pacote);
		addMsgSuccess("Pacote excluído com sucesso!");
		setPacote(null);
		setLocaisPacote(null);
    }
    
    public void pesquisarPacotePorFiltro() {
    	setPacoteDataModel(new PacoteDataModel(pacoteService, filtro));
    }
    
    public void adicionarLocalPacote() {
    	
    	if ((getLocalPacote().getDescricao() == null || getLocalPacote().getDescricao().isEmpty())
    			|| (getLocalPacote().getNome() == null || getLocalPacote().getNome().isEmpty())) {
    		addMsgErro("O nome e a descrição do local são obrigatórios.");
    	} else {
    		getLocalPacote().setLatitude(getLatitudeLocal());
    		getLocalPacote().setLongitude(getLongitudeLocal());
    		getLocalPacote().setIdPacote(getPacote().getId());
    		localPacoteService.salvarLocalPacote(localPacote);
    		addMsgSuccess("Local do Pacote salvo com sucesso!");
    		setLocaisPacote(localPacoteService.recuperarLocaisPorPacote(getPacote()));
    		setLocalPacote(null);
    	}
    }
    
    public void excluirLocalPacote(LocalPacote localPacote) {
    	
    	localPacoteService.excluir(localPacote);
    	addMsgSuccess("Local do Pacote excluído com sucesso!");
    	setLocaisPacote(localPacoteService.recuperarLocaisPorPacote(getPacote()));
    	setLocalPacote(null);
    }
    
    /**
     * Método responsável por limpar formulário e resultados da pesquisa.
     */
    public void limparFormulario() {
    	
    	setFiltro(null);
    	setPacoteDataModel(null);
    }

    // Métodos get/set
	public Pacote getPacote() {
		if (pacote == null) {
			pacote = new Pacote();
		}
		return pacote;
	}
	public void setPacote(Pacote pacote) {
		this.pacote = pacote;
	}
	public Pacote getFiltro() {
		if (filtro == null) {
			filtro = new Pacote();
		}
		return filtro;
	}
	public void setFiltro(Pacote filtro) {
		this.filtro = filtro;
	}
	public PacoteDataModel getPacoteDataModel() {
		return pacoteDataModel;
	}
	public void setPacoteDataModel(PacoteDataModel pacoteDataModel) {
		this.pacoteDataModel = pacoteDataModel;
	}
	public List<Cidade> getCidades() {
		return cidades;
	}
	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
	public String getCidadeMapa() {
		return cidadeMapa;
	}
	public void setCidadeMapa(String cidadeMapa) {
		this.cidadeMapa = cidadeMapa;
	}
	public String getLatitudeLocal() {
		return latitudeLocal;
	}
	public void setLatitudeLocal(String latitudeLocal) {
		this.latitudeLocal = latitudeLocal;
	}
	public String getLongitudeLocal() {
		return longitudeLocal;
	}
	public void setLongitudeLocal(String longitudeLocal) {
		this.longitudeLocal = longitudeLocal;
	}
	public LocalPacote getLocalPacote() {
		if (localPacote == null) {
			localPacote = new LocalPacote();
		}
		return localPacote;
	}
	public void setLocalPacote(LocalPacote localPacote) {
		this.localPacote = localPacote;
	}
	public List<LocalPacote> getLocaisPacote() {
		return locaisPacote;
	}
	public void setLocaisPacote(List<LocalPacote> locaisPacote) {
		this.locaisPacote = locaisPacote;
	}

}
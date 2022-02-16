package br.com.seg.econotaxi.view.comum;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.Localidade;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.CidadeService;
import br.com.seg.econotaxi.service.LocalidadeService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.LocalidadeDataModel;

@Named
@Scope("view")
@ManagedBean(name = "localidadeView")
public class LocalidadeView extends BaseView {

    // Constantes
	private static final long serialVersionUID = 1584745532035822519L;
	
	// Atributos
	@Autowired
	private LocalidadeService localidadeService;
	@Autowired
	private CidadeService cidadeService;
	private List<Cidade> cidades;
	private Localidade filtro;
	private Localidade localidade;
	private Localidade localidadeAlterar;
	private LocalidadeDataModel localidadeDataModel;
	private String cidadeMapa;
	private Usuario usuarioTeleTaxi;
	private List<Localidade> localidades;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		Usuario usuario = recuperarUsuarioSessao();
		if (usuario.getIndicadorTeleTaxi() != null && usuario.getIndicadorTeleTaxi().equals(1)) {
			setUsuarioTeleTaxi(usuario);
			getFiltro().setTipo(1);
		}
		setCidades(cidadeService.recuperarTodasCidades());
		setLocalidadeDataModel(new LocalidadeDataModel(localidadeService, getFiltro()));
	}
	
	public void definirNomeCidade() {
		
		if (getLocalidade().getCidade() != null) {
			setCidadeMapa(getLocalidade().getCidade().getNome());
		}
		carregarPontosApoio();
	}
	
	private void carregarPontosApoio() {
		
		if (getLocalidade().getCidade() != null && getLocalidade().getCidade().getId() != null) {
			setLocalidades(localidadeService.recuperarTodasLocalidadesPorTipo(1, getLocalidade().getCidade().getId()));
		}
	}
	
	private void carregarPontosApoioAlterar() {
		
		if (getLocalidadeAlterar().getCidade() != null && getLocalidadeAlterar().getCidade().getId() != null) {
			setLocalidades(localidadeService.recuperarTodasLocalidadesPorTipo(1, getLocalidadeAlterar().getCidade().getId()));
		}
	}
    
    public void salvarLocalidade() {
    	
    	Boolean verificaObrigatoriedade = Boolean.FALSE;
    	if (getLocalidade().getCidade() == null || getLocalidade().getCidade().getId() == null) {
    		verificaObrigatoriedade = Boolean.TRUE;
    		addMsgErro("A Cidade da Localidade é obrigatório.");
    	}
    	if (getLocalidade().getTipo() == null) {
    		verificaObrigatoriedade = Boolean.TRUE;
    		addMsgErro("O Tipo da Localidade é obrigatório.");
    	}
    	if (getLocalidade().getNome() == null || getLocalidade().getNome().isEmpty()) {
    		verificaObrigatoriedade = Boolean.TRUE;
    		addMsgErro("O Nome da Localidade é obrigatório.");
    	}
    	if (getLocalidade().getHorarioFuncionamento() == null) {
    		verificaObrigatoriedade = Boolean.TRUE;
    		addMsgErro("O Horário de Funcionamento da Localidade é obrigatório.");
    	}
    	if (getLocalidade().getDiasFuncionamento() == null) {
    		verificaObrigatoriedade = Boolean.TRUE;
    		addMsgErro("Os Dias de Funcionamento da Localidade é obrigatório.");
    	}
    	if (getLocalidade().getEndereco() == null || getLocalidade().getEndereco().isEmpty()) {
    		verificaObrigatoriedade = Boolean.TRUE;
    		addMsgErro("O Endereço da Localidade é obrigatório.");
    	}
    	if (getLocalidade().getCoordenadas() == null || getLocalidade().getCoordenadas().isEmpty()) {
    		verificaObrigatoriedade = Boolean.TRUE;
    		addMsgErro("Não foi possível recuperar as coordenadas do desenho.");
    	}
    	if (getLocalidade().getTipo() != null && (getLocalidade().getTipo().equals(2) || getLocalidade().getTipo().equals(3))) {
    		if (getLocalidade().getIdPa1() == null || getLocalidade().getIdPa2() == null) {
    			verificaObrigatoriedade = Boolean.TRUE;
    			addMsgErro("Para uma região ou bairro é necessário preencher pelo menos os dois primeiros Pontos de Apoio.");
    		}
    	}
    	if (!verificaObrigatoriedade) {
    		definirMenu(MenuEnum.LOCALIDADE.getMenu());
    		localidadeService.salvarLocalidade(localidade);
    		addMsgSuccess("Localidade salva com sucesso.");
    		
    		RestTemplate rest = new RestTemplate();
    		if (getLocalidade().getTipo() != null && (getLocalidade().getTipo().equals(2) || getLocalidade().getTipo().equals(3))) {
    			rest.postForEntity(Corrida.URL + "econotaxi-rest/rest/corridaLocal/adicionarRegiaoLocal", 
    					localidade, Void.class);
    		} else {
    			rest.postForEntity(Corrida.URL + "econotaxi-rest/rest/corridaLocal/adicionarPontoApoioLocal", 
    					localidade, Void.class);
    		}
    		setLocalidade(null);
    	}
    }
    
    public void alterarLocalidade() {
    	
    	Boolean verificaObrigatoriedade = Boolean.FALSE;
    	if (getLocalidadeAlterar().getCidade() == null || getLocalidadeAlterar().getCidade().getId() == null) {
    		verificaObrigatoriedade = Boolean.TRUE;
    		addMsgErro("A Cidade da Localidade é obrigatório.");
    	}
    	if (getLocalidadeAlterar().getTipo() == null) {
    		verificaObrigatoriedade = Boolean.TRUE;
    		addMsgErro("O Tipo da Localidade é obrigatório.");
    	}
    	if (getLocalidadeAlterar().getNome() == null || getLocalidadeAlterar().getNome().isEmpty()) {
    		verificaObrigatoriedade = Boolean.TRUE;
    		addMsgErro("O Nome da Localidade é obrigatório.");
    	}
    	if (getLocalidadeAlterar().getHorarioFuncionamento() == null) {
    		verificaObrigatoriedade = Boolean.TRUE;
    		addMsgErro("O Horário de Funcionamento da Localidade é obrigatório.");
    	}
    	if (getLocalidadeAlterar().getDiasFuncionamento() == null) {
    		verificaObrigatoriedade = Boolean.TRUE;
    		addMsgErro("Os Dias de Funcionamento da Localidade é obrigatório.");
    	}
    	if (getLocalidadeAlterar().getEndereco() == null || getLocalidadeAlterar().getEndereco().isEmpty()) {
    		verificaObrigatoriedade = Boolean.TRUE;
    		addMsgErro("O Endereço da Localidade é obrigatório.");
    	}
    	if (getLocalidadeAlterar().getCoordenadas() == null || getLocalidadeAlterar().getCoordenadas().isEmpty()) {
    		verificaObrigatoriedade = Boolean.TRUE;
    		addMsgErro("Não foi possível recuperar as coordenadas do desenho.");
    	}
    	if (getLocalidadeAlterar().getTipo() != null && (getLocalidadeAlterar().getTipo().equals(2) || getLocalidadeAlterar().getTipo().equals(3))) {
    		if (getLocalidadeAlterar().getIdPa1() == null || getLocalidadeAlterar().getIdPa2() == null) {
    			verificaObrigatoriedade = Boolean.TRUE;
        		addMsgErro("Para uma região ou bairro é necessário preencher pelo menos os dois primeiros Pontos de Apoio.");
    		}
    	}
    	if (!verificaObrigatoriedade) {
    		definirMenu(MenuEnum.LOCALIDADE.getMenu());
    		localidadeService.salvarLocalidade(getLocalidadeAlterar());
    		addMsgSuccess("Localidade salva com sucesso.");
    		
    		RestTemplate rest = new RestTemplate();
    		if (getLocalidadeAlterar().getTipo() != null && (getLocalidadeAlterar().getTipo().equals(2) || getLocalidadeAlterar().getTipo().equals(3))) {
    			rest.postForEntity(Corrida.URL + "econotaxi-rest/rest/corridaLocal/adicionarRegiaoLocal", 
    					getLocalidadeAlterar(), Void.class);
    		} else {
    			rest.postForEntity(Corrida.URL + "econotaxi-rest/rest/corridaLocal/adicionarPontoApoioLocal", 
    					getLocalidadeAlterar(), Void.class);
    		}
    		setLocalidadeAlterar(null);
    	}
    }
    
    public void carregarLocalidade(Localidade localidade) {
    	setLocalidade(localidade);
    	carregarPontosApoio();
    }
    
    public void carregarLocalidadeAlterar(Localidade localidade) {
    	setLocalidadeAlterar(localidade);
    	carregarPontosApoioAlterar();
    }
    
    public void excluirLocalidade() {
    	
    	definirMenu(MenuEnum.LOCALIDADE.getMenu());
    	localidadeService.excluir(localidade);
		addMsgSuccess("Localidade excluída com sucesso!");
		RestTemplate rest = new RestTemplate();
		if (localidade != null && localidade.getTipo() != null && (localidade.getTipo().equals(2) || localidade.getTipo().equals(3))) {
			rest.postForEntity(Corrida.URL + "econotaxi-rest/rest/corridaLocal/removerRegiaoLocal", 
					localidade, Void.class);
		} else {
			rest.postForEntity(Corrida.URL + "econotaxi-rest/rest/corridaLocal/removerPontoApoioLocal", 
					localidade, Void.class);
		}
		setLocalidade(null);
    }
    
    public void pesquisarLocalidadePorFiltro() {
    	
    	if (getUsuarioTeleTaxi() != null && getUsuarioTeleTaxi().getId() != null) {
    		getFiltro().setTipo(1);
    	}
    	setLocalidadeDataModel(new LocalidadeDataModel(localidadeService, filtro));
    }
    
    /**
     * Método responsável por limpar formulário e resultados da pesquisa.
     */
    public void limparFormulario() {
    	
    	setFiltro(null);
    	setLocalidadeDataModel(null);
    }

    // Métodos get/set
	public LocalidadeService getLocalidadeService() {
		return localidadeService;
	}
	public void setLocalidadeService(LocalidadeService localidadeService) {
		this.localidadeService = localidadeService;
	}
	public CidadeService getCidadeService() {
		return cidadeService;
	}
	public void setCidadeService(CidadeService cidadeService) {
		this.cidadeService = cidadeService;
	}
	public List<Cidade> getCidades() {
		return cidades;
	}
	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
	public Localidade getFiltro() {
		if (filtro == null) {
			filtro = new Localidade();
		}
		return filtro;
	}
	public void setFiltro(Localidade filtro) {
		this.filtro = filtro;
	}
	public LocalidadeDataModel getLocalidadeDataModel() {
		return localidadeDataModel;
	}
	public void setLocalidadeDataModel(LocalidadeDataModel localidadeDataModel) {
		this.localidadeDataModel = localidadeDataModel;
	}
	public String getCidadeMapa() {
		return cidadeMapa;
	}
	public void setCidadeMapa(String cidadeMapa) {
		this.cidadeMapa = cidadeMapa;
	}
	public Localidade getLocalidade() {
		if (localidade == null) {
			localidade = new Localidade();
		}
		return localidade;
	}
	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}
	public Usuario getUsuarioTeleTaxi() {
		return usuarioTeleTaxi;
	}
	public void setUsuarioTeleTaxi(Usuario usuarioTeleTaxi) {
		this.usuarioTeleTaxi = usuarioTeleTaxi;
	}
	public List<Localidade> getLocalidades() {
		return localidades;
	}
	public void setLocalidades(List<Localidade> localidades) {
		this.localidades = localidades;
	}
	public Localidade getLocalidadeAlterar() {
		if (localidadeAlterar == null) {
			localidadeAlterar = new Localidade();
		}
		return localidadeAlterar;
	}
	public void setLocalidadeAlterar(Localidade localidadeAlterar) {
		this.localidadeAlterar = localidadeAlterar;
	}

}
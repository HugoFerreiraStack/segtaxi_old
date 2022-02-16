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
import br.com.seg.econotaxi.enums.StatusMotoristaEnum;
import br.com.seg.econotaxi.model.Lojista;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.LojistaService;
import br.com.seg.econotaxi.service.UsuarioService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.LojistaDataModel;

@Named
@Scope("view")
@ManagedBean(name = "lojistaView")
public class LojistaView extends BaseView {

    // Constantes
	private static final long serialVersionUID = -6918164650365605188L;
	
	// Atributos
	@Autowired
    private UsuarioService usuarioService;
	@Autowired
    private LojistaService lojistaService;
	private Lojista lojista;
	private Lojista filtro;
	private String motivoDesautorizacao;
	private String motivoBloqueio;
	private LojistaDataModel usuarioDataModel;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		Integer status = (Integer) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("status");
		if (status != null) {
			getFiltro().setStatus(status);
		}
		Long idLojista = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idLojista");
		if (idLojista != null) {
			getFiltro().setId(idLojista);
			carregarUsuario(getFiltro());
		}
		setUsuarioDataModel(new LojistaDataModel(lojistaService, getFiltro()));
	}
	
	public void chamarModal() {
		if (getLojista().getId() != null) {
			execJavaScript("exibirDetalhesLojista();");
		}
	}
	
	/**
     * Método responsável por carregar dados de um determinado Usuário.
     * 
     * @param idUsuario
     */
    public void carregarUsuario(Lojista lojista) {
    	setLojista(lojistaService.consultarLojistaPorChave(lojista.getId()));
    	Usuario usuario = usuarioService.recuperarUsuarioPorID(getLojista().getIdUsuario());
    	getLojista().setNome(usuario.getNome());
    	getLojista().setSexo(usuario.getSexo());
    	getLojista().setSelfie(usuario.getImagem());
    }
    
    public void autorizarLojista() {
    	
    	definirMenu(MenuEnum.LOJISTAS.getMenu());
    	lojista = lojistaService.consultarLojistaPorChave(getLojista().getId());
    	lojista.setStatus(StatusMotoristaEnum.ATIVO.getStatus());
    	lojistaService.salvar(lojista);
    	addMsgSuccess("Lojista autorizado com sucesso!");
    	setLojista(null);
    }
    
    public void desautorizarLojista() {
    	
    	if (motivoDesautorizacao != null && !motivoDesautorizacao.isEmpty()) {
    		definirMenu(MenuEnum.LOJISTAS.getMenu());
    		lojista = lojistaService.consultarLojistaPorChave(getLojista().getId());
    		lojista.setStatus(StatusMotoristaEnum.NAO_AUTORIZADO.getStatus());
    		lojista.setMotivoDesautorizacao(motivoDesautorizacao);
    		lojistaService.salvar(lojista);
    		addMsgSuccess("Lojista não autorizado com sucesso!");
    	} else {
    		addMsgErro("Para desautorizar um lojista é preciso preencher um motivo.");
    	}
    	setLojista(null);
    }
    
    public void bloquearLojista() {
    	
    	if (motivoBloqueio != null && !motivoBloqueio.isEmpty()) {
    		definirMenu(MenuEnum.LOJISTAS.getMenu());
    		lojista = lojistaService.consultarLojistaPorChave(lojista.getId());
    		lojista.setStatus(StatusMotoristaEnum.BLOQUEADO_TEMPORARIAMENTE.getStatus());
    		lojista.setMotivoBloqueio(motivoBloqueio);
    		lojistaService.salvar(lojista);
    		addMsgSuccess("Lojista bloqueado com sucesso!");
    	} else {
    		addMsgErro("Para bloquear um lojista é preciso preencher um motivo.");
    	}
    	setLojista(null);
    }
    
    public void desbloquearMotorista() {
    	
    	definirMenu(MenuEnum.LOJISTAS.getMenu());
    	lojista = lojistaService.consultarLojistaPorChave(lojista.getId());
    	lojista.setStatus(StatusMotoristaEnum.ATIVO.getStatus());
    	lojistaService.salvar(lojista);
    	addMsgSuccess("Lojista desbloqueado com sucesso!");
    	setLojista(null);
    }
    
    /**
     * Método responsável por recuperar usuários por filtro.
     */
    public void pesquisarUsuarioPorFiltro() {
    	
    	setUsuarioDataModel(new LojistaDataModel(lojistaService, filtro));
    	setLojista(null);
    }
    
    /**
     * Método responsável por limpar formulário e resultados da pesquisa.
     */
    public void limparFormulario() {
    	
    	definirMenu(MenuEnum.LOJISTAS.getMenu());
    	setFiltro(null);
    	setUsuarioDataModel(null);
    }

    // Métodos get/set
	public Lojista getLojista() {
		if (lojista == null) {
			lojista = new Lojista();
		}
		return lojista;
	}
	public void setLojista(Lojista lojista) {
		this.lojista = lojista;
	}
	public Lojista getFiltro() {
		if (filtro == null) {
			filtro = new Lojista();
		}
		return filtro;
	}
	public void setFiltro(Lojista filtro) {
		this.filtro = filtro;
	}
	public LojistaDataModel getUsuarioDataModel() {
		return usuarioDataModel;
	}
	public void setUsuarioDataModel(LojistaDataModel usuarioDataModel) {
		this.usuarioDataModel = usuarioDataModel;
	}

	public String getMotivoBloqueio() {
		return motivoBloqueio;
	}

	public void setMotivoBloqueio(String motivoBloqueio) {
		this.motivoBloqueio = motivoBloqueio;
	}

	public String getMotivoDesautorizacao() {
		return motivoDesautorizacao;
	}

	public void setMotivoDesautorizacao(String motivoDesautorizacao) {
		this.motivoDesautorizacao = motivoDesautorizacao;
	}
	
	public List<StatusMotoristaEnum> getListaStatusLojista() {
		return Arrays.asList(StatusMotoristaEnum.values());
	}
	
}
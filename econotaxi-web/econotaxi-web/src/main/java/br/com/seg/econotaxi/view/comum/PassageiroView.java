package br.com.seg.econotaxi.view.comum;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.enums.TipoUsuarioEnum;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.UsuarioService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.UsuarioDataModal;

@Named
@Scope("view")
@ManagedBean(name = "passageiroView")
public class PassageiroView extends BaseView {

    // Constantes
	private static final long serialVersionUID = -6918164650365605188L;
	
	// Atributos
	@Autowired
    private UsuarioService usuarioService;
	private Usuario passageiro;
	private Usuario filtro;
	private String motivoBloqueio;
	private UsuarioDataModal usuarioDataModel;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		getFiltro().setTipo(TipoUsuarioEnum.CLIENTE.getCodigo());
		Long idUsuario = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idUsuario");
		if (idUsuario != null) {
			getFiltro().setId(idUsuario);
			carregarUsuario(usuarioService.recuperarUsuarioPorID(idUsuario));
		}
		Integer possuiCartao = (Integer) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("possuiCartao");
		if (possuiCartao != null) {
			getFiltro().setPossuiCartao(possuiCartao);
		}
		setUsuarioDataModel(new UsuarioDataModal(usuarioService, getFiltro()));
	}
	
	public void chamarModal() {
		if (getPassageiro().getId() != null) {
			execJavaScript("exibirDetalhesPassageiro();");
		}
	}
	
	/**
     * Método responsável por carregar dados de um determinado Usuário.
     * 
     * @param idUsuario
     */
    public void carregarUsuario(Usuario usuario) {
    	setPassageiro(usuarioService.recuperarUsuarioPorID(usuario.getId()));
    }
    
    public void bloquearPassageiro() {
    	
    	if (motivoBloqueio == null || motivoBloqueio.isEmpty()) {
    		addMsgErro("O motivo do bloqueio deve ser preenchido.");
    	} else {
    		definirMenu(MenuEnum.PASSAGEIROS.getMenu());
    		passageiro = usuarioService.recuperarUsuarioPorID(passageiro.getId());
    		passageiro.setIndicadorBloqueio(1);
    		passageiro.setMotivoBloqueio(motivoBloqueio);
    		usuarioService.bloquearPassageiro(passageiro);
    		addMsgSuccess("Passageiro bloqueado com sucesso.");
    		setPassageiro(null);
    	}
    }
    
    public void excluirPassageiro() {
    	
		definirMenu(MenuEnum.PASSAGEIROS.getMenu());
		passageiro = usuarioService.recuperarUsuarioPorID(passageiro.getId());
		usuarioService.excluirPassageiro(passageiro);
		addMsgSuccess("Passageiro excluído com sucesso.");
		setPassageiro(null);
    }
    
    public void desbloquearPassageiro() {
    	
    	definirMenu(MenuEnum.PASSAGEIROS.getMenu());
    	passageiro = usuarioService.recuperarUsuarioPorID(passageiro.getId());
    	passageiro.setIndicadorBloqueio(null);
    	passageiro.setMotivoBloqueio(null);
		usuarioService.bloquearPassageiro(passageiro);
		addMsgSuccess("Passageiro desbloqueado com sucesso.");
		setPassageiro(null);
    }
    
    /**
     * Método responsável por recuperar usuários por filtro.
     */
    public void pesquisarUsuarioPorFiltro() {
    	
    	getFiltro().setTipo(TipoUsuarioEnum.CLIENTE.getCodigo());
    	setUsuarioDataModel(new UsuarioDataModal(usuarioService, filtro));
    	setPassageiro(null);
    }
    
    /**
     * Método responsável por limpar formulário e resultados da pesquisa.
     */
    public void limparFormulario() {
    	
    	definirMenu(MenuEnum.PASSAGEIROS.getMenu());
    	setFiltro(null);
    	setUsuarioDataModel(null);
    }

    // Métodos get/set
	public Usuario getPassageiro() {
		if (passageiro == null) {
			passageiro = new Usuario();
		}
		return passageiro;
	}
	public void setPassageiro(Usuario passageiro) {
		this.passageiro = passageiro;
	}
	public Usuario getFiltro() {
		if (filtro == null) {
			filtro = new Usuario();
		}
		return filtro;
	}
	public void setFiltro(Usuario filtro) {
		this.filtro = filtro;
	}
	public UsuarioDataModal getUsuarioDataModel() {
		return usuarioDataModel;
	}
	public void setUsuarioDataModel(UsuarioDataModal usuarioDataModel) {
		this.usuarioDataModel = usuarioDataModel;
	}

	public String getMotivoBloqueio() {
		return motivoBloqueio;
	}

	public void setMotivoBloqueio(String motivoBloqueio) {
		this.motivoBloqueio = motivoBloqueio;
	}
    
}
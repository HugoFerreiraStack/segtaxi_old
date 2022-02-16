package br.com.seg.econotaxi.view.comum;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.enums.TipoUsuarioEnum;
import br.com.seg.econotaxi.model.Perfil;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.PerfilService;
import br.com.seg.econotaxi.service.UsuarioService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.UsuarioDataModal;

@Named
@Scope("view")
@ManagedBean(name = "usuarioTeleTaxiView")
public class UsuarioTeleTaxiView extends BaseView {

    // Constantes
	private static final long serialVersionUID = -6918164650365605188L;
	
	// Atributos
	@Autowired
    private UsuarioService usuarioService;
	@Autowired
	private PerfilService perfilService;
	private Usuario usuario;
	private List<Perfil> grupos;
	private Usuario filtro;
	private UsuarioDataModal usuarioDataModel;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		
		getFiltro().setTipo(TipoUsuarioEnum.ADMINISTRATIVO.getCodigo());
		getFiltro().setIndicadorTeleTaxi(1);
		setUsuarioDataModel(new UsuarioDataModal(usuarioService, getFiltro()));
		setGrupos(perfilService.recuperarPerfisAtivos());
	}
	
	/**
     * Método responsável por carregar dados de um determinado Usuário.
     * 
     * @param idUsuario
     */
    public void carregarUsuario(Usuario usuario) {
    	setUsuario(usuarioService.recuperarUsuarioPorID(usuario.getId()));
    }
    
    /**
     * Método responsável por salvar um usuário.
     */
    public void salvarUsuario() {
    	
    	definirMenu(MenuEnum.USUARIO_TELE_TAXI.getMenu());
    	if (usuarioService.verificaExistenciaUsuario(usuario)) {
    		addMsgErro("Usuário já existente!");
    	} else {
    		
    		Date data = new Date();
    		usuario.setIndicadorTeleTaxi(1);
    		usuario.setTipo(TipoUsuarioEnum.ADMINISTRATIVO.getCodigo());
    		if (usuario.getId() == null) {
    			usuario.setDataCadastro(data);
    			usuario.setDataAlteracao(data);
    			usuarioService.cadastrarUsuario(usuario);
    		} else {
    			usuario.setDataAlteracao(data);
    			usuarioService.salvarUsuario(usuario);
    		}
    		addMsgSuccess("Usuário salvo com sucesso!");
    		setUsuario(null);
    	}
    }
    
    /**
     * Método responsável por excluir um usuário.
     */
    public void excluirUsuario() {
    	
    	definirMenu(MenuEnum.USUARIO_TELE_TAXI.getMenu());
    	usuario.setIndicadorAtivo(2);
		usuarioService.salvarUsuario(usuario);
		addMsgSuccess("Usuário excluído com sucesso.");
		setUsuario(null);
		pesquisarUsuarioPorFiltro();
    }
    
    /**
     * Método responsável por recuperar usuários por filtro.
     */
    public void pesquisarUsuarioPorFiltro() {
    	
    	getFiltro().setTipo(TipoUsuarioEnum.ADMINISTRATIVO.getCodigo());
    	getFiltro().setIndicadorTeleTaxi(1);
    	setUsuarioDataModel(new UsuarioDataModal(usuarioService, filtro));
    }
    
    /**
     * Método responsável por limpar formulário e resultados da pesquisa.
     */
    public void limparFormulario() {
    	
    	definirMenu(MenuEnum.USUARIO_TELE_TAXI.getMenu());
    	setFiltro(null);
    	setUsuarioDataModel(null);
    }

    // Métodos get/set
	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
		}
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<Perfil> getGrupos() {
		return grupos;
	}
	public void setGrupos(List<Perfil> grupos) {
		this.grupos = grupos;
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
    
}
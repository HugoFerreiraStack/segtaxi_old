package br.com.seg.econotaxi.view.comum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.enums.TipoUsuarioEnum;
import br.com.seg.econotaxi.model.CentroCusto;
import br.com.seg.econotaxi.model.EmpresaConveniada;
import br.com.seg.econotaxi.model.Perfil;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.UsuarioPerfil;
import br.com.seg.econotaxi.model.UsuarioPerfilPk;
import br.com.seg.econotaxi.service.EmpresaConveniadaService;
import br.com.seg.econotaxi.service.PerfilService;
import br.com.seg.econotaxi.service.UsuarioService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.UsuarioDataModal;

@Named
@Scope("view")
@ManagedBean(name = "usuarioView")
public class UsuarioView extends BaseView {

    // Constantes
	private static final long serialVersionUID = -6918164650365605188L;
	
	// Atributos
	@Autowired
    private UsuarioService usuarioService;
	@Autowired
	private PerfilService perfilService;
	@Autowired
	private EmpresaConveniadaService empresaConveniadaService;
	private Usuario usuario;
	private List<Perfil> grupos;
	private Usuario filtro;
	private UsuarioDataModal usuarioDataModel;
	private List<EmpresaConveniada> empresas;
	private List<CentroCusto> centros;
	private EmpresaConveniada empresaConveniada;
	private Usuario usuarioTele;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		setUsuarioTele(recuperarUsuarioSessao());
		getFiltro().setTipo(TipoUsuarioEnum.ADMINISTRATIVO.getCodigo());
		setUsuarioDataModel(new UsuarioDataModal(usuarioService, getFiltro()));
		setGrupos(perfilService.recuperarPerfisAtivos());
		setEmpresas(empresaConveniadaService.recuperarEmpresas());
		if (getUsuarioTele() != null && getUsuarioTele().getIdEmpresaConveniada() != null) {
			getFiltro().setTipoTeleTaxi(3);
			getUsuario().setTipoTeleTaxi(3);
			getFiltro().setIdEmpresaConveniada(getUsuarioTele().getIdEmpresaConveniada());
		}
	}
	
	public void carregarCentrosCusto() {
		if (getUsuario() != null && getUsuario().getIdEmpresaConveniada() != null) {
			setCentros(empresaConveniadaService.recuperarCentroCustos(getUsuario().getIdEmpresaConveniada()));
		} else {
			setCentros(null);
		}
	}
	
	public void inicializarUsuario() {
		
		if (getUsuarioTele() != null && getUsuarioTele().getIdEmpresaConveniada() != null) {
			getUsuario().setTipoTeleTaxi(3);
			getUsuario().setCargoEmpresa(2);
			getUsuario().setIdEmpresaConveniada(getUsuarioTele().getIdEmpresaConveniada());
			carregarCentrosCusto();
		}
	}
	
	/**
     * Método responsável por carregar dados de um determinado Usuário.
     * 
     * @param idUsuario
     */
    public void carregarUsuario(Usuario usuario) {
    	setUsuario(usuarioService.recuperarUsuarioPorID(usuario.getId()));
    	List<Perfil> grupos = new ArrayList<Perfil>();
    	for (UsuarioPerfil grupoUsuario : getUsuario().getListaGrupoUsuario()) {
    		Perfil grupo = grupoUsuario.getPerfil();
			grupos.add(grupo);
		}
    	getUsuario().setGrupos(grupos);
    }
    
    /**
     * Método responsável por salvar um usuário.
     */
    public void salvarUsuario() {
    	
    	definirMenu(MenuEnum.USUARIO.getMenu());
    	getUsuario().setTipoTeleTaxi(3);
    	if (usuarioService.verificaExistenciaUsuario(usuario)) {
    		addMsgErro("Usuário já existente!");
    	} else if (getUsuario().getIdEmpresaConveniada() != null) {
    		
    		Date data = new Date();
    		getUsuario().setTipo(TipoUsuarioEnum.ADMINISTRATIVO.getCodigo());
    		if (getUsuarioTele() != null && getUsuarioTele().getIdEmpresaConveniada() != null) {
				getUsuario().setTipoTeleTaxi(3);
			}
    		if (getUsuario().getId() == null) {
    			getUsuario().setDataCadastro(data);
    			getUsuario().setDataAlteracao(data);
    			usuarioService.cadastrarUsuario(getUsuario());
    		} else {
    			getUsuario().setDataAlteracao(data);
    		}
    		addMsgSuccess("Usuário salvo com sucesso!");
    		setUsuario(null);
    		
    	} else {
    		
    		Date data = new Date();
    		getUsuario().setTipo(TipoUsuarioEnum.ADMINISTRATIVO.getCodigo());
    		if (getUsuario().getId() == null) {
    			getUsuario().setDataCadastro(data);
    			getUsuario().setDataAlteracao(data);
    			usuarioService.cadastrarUsuario(getUsuario());
    		} else {
    			getUsuario().setDataAlteracao(data);
    		}
    		if (getUsuario().getGrupos() != null) {
    			List<UsuarioPerfil> listUp = new ArrayList<UsuarioPerfil>();
    			for (Perfil perfil : getUsuario().getGrupos()) {
    				UsuarioPerfil up = new UsuarioPerfil();
    				UsuarioPerfilPk pk = new UsuarioPerfilPk();
    				pk.setIdPerfil(perfil.getId());
    				pk.setIdUsuario(usuario.getId());
    				up.setId(pk);
    				listUp.add(up);
    			}
    			getUsuario().setListaGrupoUsuario(listUp);
    		}
    		usuarioService.salvarUsuario(usuario);
    		addMsgSuccess("Usuário salvo com sucesso!");
    		setUsuario(null);
    	}
    }
    
    /**
     * Método responsável por excluir um usuário.
     */
    public void excluirUsuario() {
    	
    	definirMenu(MenuEnum.USUARIO.getMenu());
		usuarioService.excluirUsuario(usuario);
		addMsgSuccess("Usuário inativado com sucesso.");
		setUsuario(null);
    }
    
    /**
     * Método responsável por ativar usuário
     */
    public void ativarUsuario() {
    	
    	definirMenu(MenuEnum.USUARIO.getMenu());
		usuarioService.ativarUsuario(usuario);
		addMsgSuccess("Usuário ativado com sucesso.");
		setUsuario(null);
    }
    
    /**
     * Método responsável por recuperar usuários por filtro.
     */
    public void pesquisarUsuarioPorFiltro() {
    	
    	getFiltro().setTipo(TipoUsuarioEnum.ADMINISTRATIVO.getCodigo());
    	setUsuarioDataModel(new UsuarioDataModal(usuarioService, filtro));
    }
    
    /**
     * Método responsável por limpar formulário e resultados da pesquisa.
     */
    public void limparFormulario() {
    	
    	definirMenu(MenuEnum.USUARIO.getMenu());
    	setFiltro(null);
    	getFiltro().setTipo(TipoUsuarioEnum.ADMINISTRATIVO.getCodigo());
    	if (getUsuarioTele() != null && getUsuarioTele().getIdEmpresaConveniada() != null) {
			getFiltro().setTipoTeleTaxi(3);
			getUsuario().setTipoTeleTaxi(3);
			getFiltro().setIdEmpresaConveniada(getUsuarioTele().getIdEmpresaConveniada());
		}
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
	public List<EmpresaConveniada> getEmpresas() {
		return empresas;
	}
	public void setEmpresas(List<EmpresaConveniada> empresas) {
		this.empresas = empresas;
	}
	public EmpresaConveniada getEmpresaConveniada() {
		return empresaConveniada;
	}
	public void setEmpresaConveniada(EmpresaConveniada empresaConveniada) {
		this.empresaConveniada = empresaConveniada;
	}
	public List<CentroCusto> getCentros() {
		return centros;
	}
	public void setCentros(List<CentroCusto> centros) {
		this.centros = centros;
	}
	public Usuario getUsuarioTele() {
		return usuarioTele;
	}
	public void setUsuarioTele(Usuario usuarioTele) {
		this.usuarioTele = usuarioTele;
	}
    
}
package br.com.seg.econotaxi.view.comum;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.model.CentroCusto;
import br.com.seg.econotaxi.model.EmpresaConveniada;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.EmpresaConveniadaService;
import br.com.seg.econotaxi.service.UsuarioService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.UsuarioDataModal;

@Named
@Scope("view")
@ManagedBean(name = "usuarioAppEmpresaView")
public class UsuarioAppEmpresaView extends BaseView {

	// Constantes
	private static final long serialVersionUID = -2499751806360923119L;
	
	// Atributos
	@Autowired
	private EmpresaConveniadaService empresaConveniadaService;
	@Autowired
	private UsuarioService usuarioService;
	private List<EmpresaConveniada> empresas;
	private EmpresaConveniada empresaConveniada;
	private Usuario usuarioTele;
	private Usuario usuario;
	private Usuario filtro;
	private UsuarioDataModal usuarioDataModel;
	private List<CentroCusto> centros;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		
		setUsuarioTele(recuperarUsuarioSessao());
		setEmpresas(empresaConveniadaService.recuperarEmpresasVoucherEletronico());
		if (getUsuarioTele() != null && getUsuarioTele().getIdEmpresaConveniada() != null) {
			EmpresaConveniada ec = empresaConveniadaService.consultarPorChave(getUsuarioTele().getIdEmpresaConveniada());
			setEmpresaConveniada(ec);
			getFiltro().setEmpresaConveniada(ec);
			getFiltro().setCnpj(ec.getCnpj());
			setEmpresas(new ArrayList<EmpresaConveniada>());
			getEmpresas().add(ec);
			carregarCentrosCusto();
			if (getUsuarioTele() != null && getUsuarioTele().getIdCentroCusto() != null) {
				getFiltro().setIdCentroCusto(getUsuarioTele().getIdCentroCusto());
			}
		}
	}
	
	public void carregarCentrosCusto() {
		if (getEmpresaConveniada() != null && getEmpresaConveniada().getId() != null) {
			setCentros(empresaConveniadaService.recuperarCentroCustos(getEmpresaConveniada().getId()));
		} else {
			setCentros(null);
		}
	}
	
	/**
     * Método responsável por carregar dados de um determinado Usuário.
     * 
     * @param idUsuario
     */
    public void carregarUsuario(Usuario usuario) {
    	setUsuario(usuarioService.recuperarUsuarioPorID(usuario.getId()));
    	if (getUsuarioTele() != null && getUsuarioTele().getIdCentroCusto() != null) {
			getUsuario().setIdCentroCusto(getUsuarioTele().getIdCentroCusto());
		}
    }
    
    /**
     * Método responsável por excluir um usuário.
     */
    public void desautorizarUsuario() {
    	
    	definirMenu(MenuEnum.USUARIO_APP_EMPRESA.getMenu());
    	usuario.setIdEmpresaConveniada(null);
		usuarioService.salvarUsuario(usuario);
		addMsgSuccess("Usuário desautorizado com sucesso.");
		setUsuario(null);
    }
    
    /**
     * Método responsável por ativar usuário
     */
    public void autorizarUsuario() {
    	
    	definirMenu(MenuEnum.USUARIO_APP_EMPRESA.getMenu());
    	if (getFiltro().getEmpresaConveniada() == null || getFiltro().getEmpresaConveniada().getId() == null) {
    		addMsgErro("A Empresa Conveniada é obrigatória.");
    	} else {
    		usuario.setIdEmpresaConveniada(getFiltro().getEmpresaConveniada().getId());
    		usuarioService.salvarUsuario(usuario);
    		addMsgSuccess("Usuário autorizado com sucesso.");
    		consultarUsuarios();
    	}
		setUsuario(null);
    }
	
	public void consultarUsuarios() {
		
		Boolean verificaErro = Boolean.FALSE;
		if (!verificaErro) {
			getFiltro().setCnpj(getFiltro().getEmpresaConveniada().getCnpj());
			setUsuarioDataModel(new UsuarioDataModal(usuarioService, getFiltro()));
		}
	}
	
    // Métodos get/set
	public Usuario getUsuarioTele() {
		return usuarioTele;
	}

	public void setUsuarioTele(Usuario usuarioTele) {
		this.usuarioTele = usuarioTele;
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

	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

	public List<CentroCusto> getCentros() {
		return centros;
	}

	public void setCentros(List<CentroCusto> centros) {
		this.centros = centros;
	}
	
}
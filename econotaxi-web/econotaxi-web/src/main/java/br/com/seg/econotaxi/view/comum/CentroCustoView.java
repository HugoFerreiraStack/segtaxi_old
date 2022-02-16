package br.com.seg.econotaxi.view.comum;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.model.CentroCusto;
import br.com.seg.econotaxi.model.EmpresaConveniada;
import br.com.seg.econotaxi.model.Perfil;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.EmpresaConveniadaService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.CentroCustoDataModel;

@Named
@Scope("view")
@ManagedBean(name = "centroCustoView")
public class CentroCustoView extends BaseView {

    // Constantes
	private static final long serialVersionUID = -5997390048423674994L;
	
	// Atributos
	@Autowired
	private EmpresaConveniadaService empresaConveniadaService;
	private CentroCusto centroCusto;
	private List<Perfil> grupos;
	private CentroCusto filtro;
	private CentroCustoDataModel centroCustoDataModel;
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
		if (getUsuarioTele() != null && getUsuarioTele().getIdEmpresaConveniada() != null) {
			getFiltro().setIdEmpresaConveniada(getUsuarioTele().getIdEmpresaConveniada());
		}
		setCentroCustoDataModel(new CentroCustoDataModel(empresaConveniadaService, getFiltro()));
		setEmpresas(empresaConveniadaService.recuperarEmpresas());
	}
	
    /**
     * Método responsável por salvar um usuário.
     */
    public void salvarCentroCusto() {
    	
    	definirMenu(MenuEnum.USUARIO.getMenu());
    	if (getCentroCusto().getNome() == null || getCentroCusto().getNome().isEmpty()) {
    		addMsgErro("O Nome do Centro de Custo é obrigatório.");
    	} else if (getCentroCusto().getIdEmpresaConveniada() == null) {
    		addMsgErro("A Empresa Conveniada é obrigatória.");
    	} else {
    		
    		Date data = new Date();
    		if (getCentroCusto().getId() == null) {
    			getCentroCusto().setDataCadastro(data);
    			getCentroCusto().setDataAlteracao(data);
    			getCentroCusto().setStatus(1);
    		} else {
    			getCentroCusto().setDataAlteracao(data);
    		}
    		empresaConveniadaService.salvarCentroCusto(getCentroCusto());
    		addMsgSuccess("Centro de Custo salvo com sucesso!");
    		setCentroCusto(null);
    	}
    }
    
    /**
     * Método responsável por excluir um usuário.
     */
    public void excluirCentroCusto() {
    	
    	definirMenu(MenuEnum.CENTRO_CUSTO.getMenu());
    	getCentroCusto().setStatus(2);
    	empresaConveniadaService.salvarCentroCusto(getCentroCusto());
		addMsgSuccess("Centro de Custo inativado com sucesso.");
		setCentroCusto(null);
    }
    
    /**
     * Método responsável por ativar usuário
     */
    public void ativarCentroCusto() {
    	
    	definirMenu(MenuEnum.CENTRO_CUSTO.getMenu());
    	getCentroCusto().setStatus(1);
    	empresaConveniadaService.salvarCentroCusto(getCentroCusto());
		addMsgSuccess("CentroCusto ativado com sucesso.");
		setCentroCusto(null);
    }
    
    public void inicializarCentroCusto() {
    	if (getUsuarioTele() != null && getUsuarioTele().getIdEmpresaConveniada() != null) {
			getCentroCusto().setIdEmpresaConveniada(getUsuarioTele().getIdEmpresaConveniada());
		}
    }
    
    /**
     * Método responsável por recuperar usuários por filtro.
     */
    public void pesquisarCentroCustoPorFiltro() {
    	
    	setCentroCustoDataModel(new CentroCustoDataModel(empresaConveniadaService, filtro));
    }
    
    /**
     * Método responsável por limpar formulário e resultados da pesquisa.
     */
    public void limparFormulario() {
    	
    	definirMenu(MenuEnum.CENTRO_CUSTO.getMenu());
    	setFiltro(null);
    	if (getUsuarioTele() != null && getUsuarioTele().getIdEmpresaConveniada() != null) {
    		getFiltro().setIdEmpresaConveniada(getUsuarioTele().getIdEmpresaConveniada());
    	}
    	setCentroCustoDataModel(null);
    }

    // Métodos get/set
	public CentroCusto getCentroCusto() {
		if (centroCusto == null) {
			centroCusto = new CentroCusto();
		}
		return centroCusto;
	}
	public void setCentroCusto(CentroCusto centroCusto) {
		this.centroCusto = centroCusto;
	}
	public List<Perfil> getGrupos() {
		return grupos;
	}
	public void setGrupos(List<Perfil> grupos) {
		this.grupos = grupos;
	}
	public CentroCusto getFiltro() {
		if (filtro == null) {
			filtro = new CentroCusto();
		}
		return filtro;
	}
	public void setFiltro(CentroCusto filtro) {
		this.filtro = filtro;
	}
	public CentroCustoDataModel getCentroCustoDataModel() {
		return centroCustoDataModel;
	}
	public void setCentroCustoDataModel(CentroCustoDataModel centroCustoDataModel) {
		this.centroCustoDataModel = centroCustoDataModel;
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
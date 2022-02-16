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
import br.com.seg.econotaxi.model.EmpresaConveniada;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.Veiculo;
import br.com.seg.econotaxi.model.VeiculoEmpresaConveniada;
import br.com.seg.econotaxi.model.VeiculoEmpresaConveniadaPk;
import br.com.seg.econotaxi.service.EmpresaConveniadaService;
import br.com.seg.econotaxi.service.VeiculoService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.VeiculoEmpresaDataModel;

@Named
@Scope("view")
@ManagedBean(name = "carrosSelecionadosView")
public class CarrosSelecionadosView extends BaseView {

	// Constantes
	private static final long serialVersionUID = 2746426741655652605L;
	
	// Atributos
	@Autowired
	private EmpresaConveniadaService empresaConveniadaService;
	@Autowired
	private VeiculoService veiculoService;
	private List<EmpresaConveniada> empresas;
	private EmpresaConveniada empresaConveniada;
	private Usuario usuarioTele;
	private Veiculo veiculo;
	private Veiculo filtro;
	private VeiculoEmpresaDataModel veiculoDataModel;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		
		setUsuarioTele(recuperarUsuarioSessao());
		setEmpresas(empresaConveniadaService.recuperarEmpresas());
		if (getUsuarioTele() != null && getUsuarioTele().getIdEmpresaConveniada() != null) {
			EmpresaConveniada ec = empresaConveniadaService.consultarPorChave(getUsuarioTele().getIdEmpresaConveniada());
			setEmpresaConveniada(ec);
			setEmpresas(new ArrayList<EmpresaConveniada>());
			getEmpresas().add(ec);
		}
	}
	
	/**
     * Método responsável por carregar dados de um determinado Usuário.
     * 
     * @param idUsuario
     */
    public void carregarVeiculo(Veiculo veiculo) {
    	setVeiculo(veiculoService.recuperarVeiculoPorChave(veiculo.getId()));
    }
    
    /**
     * Método responsável por excluir um usuário.
     */
    public void excluirVeiculo() {
    	
    	definirMenu(MenuEnum.CARROS_SELECIONADOS.getMenu());
		addMsgSuccess("Veículo excluído com sucesso.");
		VeiculoEmpresaConveniada vec = new VeiculoEmpresaConveniada();
		vec.setId(new VeiculoEmpresaConveniadaPk());
		vec.getId().setIdEmpresaConveniada(getEmpresaConveniada().getId());
		vec.getId().setIdVeiculo(getVeiculo().getId());
		veiculoService.excluirVeiculoEmpresa(vec);
		setVeiculo(null);
    }
    
    /**
     * Método responsável por ativar usuário
     */
    public void incluirVeiculo() {
    	
    	definirMenu(MenuEnum.CARROS_SELECIONADOS.getMenu());
		addMsgSuccess("Veículo adicionado com sucesso.");
		VeiculoEmpresaConveniada vec = new VeiculoEmpresaConveniada();
		vec.setId(new VeiculoEmpresaConveniadaPk());
		vec.getId().setIdEmpresaConveniada(getEmpresaConveniada().getId());
		vec.getId().setIdVeiculo(getVeiculo().getId());
		vec.setDataCadastro(new Date());
		veiculoService.incluirVeiculoEmpresa(vec);
		setVeiculo(null);
    }
	
	public void consultarVeiculos() {
		
		Boolean verificaErro = Boolean.FALSE;
		if (!verificaErro) {
			getFiltro().setIdEmpresaConveniada(getEmpresaConveniada().getId());
			setVeiculoDataModel(new VeiculoEmpresaDataModel(veiculoService, getFiltro()));
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

	public Veiculo getVeiculo() {
		if (veiculo == null) {
			veiculo = new Veiculo();
		}
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Veiculo getFiltro() {
		if (filtro == null) {
			filtro = new Veiculo();
		}
		return filtro;
	}

	public void setFiltro(Veiculo filtro) {
		this.filtro = filtro;
	}

	public VeiculoEmpresaDataModel getVeiculoDataModel() {
		return veiculoDataModel;
	}

	public void setVeiculoDataModel(VeiculoEmpresaDataModel veiculoDataModel) {
		this.veiculoDataModel = veiculoDataModel;
	}
	
}
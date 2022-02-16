/**
 * 
 */
package br.com.seg.econotaxi.view.comum;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.model.EmpresaConveniada;
import br.com.seg.econotaxi.service.EmpresaConveniadaService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.EmpresaConveniadaDataModel;

/**
 * @author bruno
 *
 */
@Named
@ManagedBean
@Scope("view")
public class EmpresaConveniadaView extends BaseView {

	// Constantes
	private static final long serialVersionUID = -3977312331435120652L;

	// Atributos
	@Autowired
	private EmpresaConveniadaService empresaConveniadaService;
	private EmpresaConveniadaDataModel empresaConveniadaDataModel;
	private EmpresaConveniada empresaConveniada;
	private EmpresaConveniada empresaConveniadaAlterar;
	private EmpresaConveniada filtro;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		setEmpresaConveniadaDataModel(new EmpresaConveniadaDataModel(empresaConveniadaService, getFiltro()));
	}
	
    public void carregarEmpresaConveniada(EmpresaConveniada empresaConveniada) {
    	setEmpresaConveniadaAlterar(empresaConveniadaService.consultarPorChave(empresaConveniada.getId()));
    }
    
    public void salvarEmpresaConveniada() {
    	
    	Boolean verificaErro = Boolean.FALSE;
    	if (getEmpresaConveniada().getNome() == null || getEmpresaConveniada().getNome().isEmpty()) {
    		addMsgErro("O Nome da Empresa Conveniada é obrigatório.");
    		verificaErro = Boolean.TRUE;
    	}
    	
    	if (getEmpresaConveniada().getRazaoSocial() == null || getEmpresaConveniada().getRazaoSocial().isEmpty()) {
    		addMsgErro("A Razão Social da Empresa Conveniada é obrigatória.");
    		verificaErro = Boolean.TRUE;
    	}
    	
    	if (getEmpresaConveniada().getEmail() == null || getEmpresaConveniada().getEmail().isEmpty()) {
    		addMsgErro("O E-mail da Empresa Conveniada é obrigatório.");
    		verificaErro = Boolean.TRUE;
    	}
    	
    	if (getEmpresaConveniada().getCnpj() == null || getEmpresaConveniada().getCnpj().isEmpty()) {
    		addMsgErro("O CNPJ da Empresa Conveniada é obrigatório.");
    		verificaErro = Boolean.TRUE;
    	}
    	
    	if (getEmpresaConveniada().getIndicadorVoucherEletronico() == null) {
    		addMsgErro("O Tipo de Voucher da Empresa Conveniada é obrigatório.");
    		verificaErro = Boolean.TRUE;
    	}
    	
    	if (!verificaErro) {
    		if (!getEmpresaConveniada().getIndicadorVoucherEletronico().equals(6)) {
    			getEmpresaConveniada().setDescontoPorcentagem(null);
    		}
    		if (getEmpresaConveniada().getDescontoPorcentagem() != null) {
    			getEmpresaConveniada().setDescontoPorcentagem(getEmpresaConveniada().getDescontoPorcentagem().setScale(0));
    		}
    		definirMenu(MenuEnum.CONVENIADA_TELE_TAXI.getMenu());
    		empresaConveniada.setDataCadastro(new Date());
    		empresaConveniadaService.salvarEmpresaConveniada(empresaConveniada);
    		addMsgSuccess("Empresa Conveniada salva com sucesso! Será enviado para o e-mail "
    				+ "definido da Empresa Conveniada registros dos vouchers das corridas.");
    		setEmpresaConveniada(null);
    	}
    }
    
    public void alterarEmpresaConveniada() {
    	
    	Boolean verificaErro = Boolean.FALSE;
    	if (getEmpresaConveniadaAlterar().getNome() == null || getEmpresaConveniadaAlterar().getNome().isEmpty()) {
    		addMsgErro("O Nome da Empresa Conveniada é obrigatório.");
    		verificaErro = Boolean.TRUE;
    	}
    	
    	if (getEmpresaConveniadaAlterar().getRazaoSocial() == null || getEmpresaConveniadaAlterar().getRazaoSocial().isEmpty()) {
    		addMsgErro("A Razão Social da Empresa Conveniada é obrigatória.");
    		verificaErro = Boolean.TRUE;
    	}
    	
    	if (getEmpresaConveniadaAlterar().getEmail() == null || getEmpresaConveniadaAlterar().getEmail().isEmpty()) {
    		addMsgErro("O E-mail da Empresa Conveniada é obrigatório.");
    		verificaErro = Boolean.TRUE;
    	}
    	
    	if (getEmpresaConveniadaAlterar().getCnpj() == null || getEmpresaConveniadaAlterar().getCnpj().isEmpty()) {
    		addMsgErro("O CNPJ da Empresa Conveniada é obrigatório.");
    		verificaErro = Boolean.TRUE;
    	}
    	
    	if (getEmpresaConveniadaAlterar().getIndicadorVoucherEletronico() == null) {
    		addMsgErro("O Tipo de Voucher da Empresa Conveniada é obrigatório.");
    		verificaErro = Boolean.TRUE;
    	}
    	
    	if (!verificaErro) {
    		if (!getEmpresaConveniadaAlterar().getIndicadorVoucherEletronico().equals(6)) {
    			getEmpresaConveniadaAlterar().setDescontoPorcentagem(null);
    		}
    		if (getEmpresaConveniadaAlterar().getDescontoPorcentagem() != null) {
    			getEmpresaConveniadaAlterar().setDescontoPorcentagem(getEmpresaConveniadaAlterar().getDescontoPorcentagem().setScale(0));
    		}
    		definirMenu(MenuEnum.CONVENIADA_TELE_TAXI.getMenu());
    		empresaConveniadaService.alterarEmpresaConveniada(empresaConveniadaAlterar);
    		addMsgSuccess("Empresa Conveniada salva com sucesso!.");
    		setEmpresaConveniada(null);
    	}
    }
    
    public void excluirEmpresaConveniada() {
    	
    	definirMenu(MenuEnum.CONVENIADA_TELE_TAXI.getMenu());
    	empresaConveniadaService.delete(empresaConveniadaAlterar);
		addMsgSuccess("Empresa Conveniada excluída com sucesso!");
		setEmpresaConveniada(null);
    }
    
    public void pesquisarEmpresaConveniadaPorFiltro() {
    	
    	setEmpresaConveniadaDataModel(new EmpresaConveniadaDataModel(empresaConveniadaService, filtro));
    }
    
    /**
     * Método responsável por limpar formulário e resultados da pesquisa.
     */
    public void limparFormulario() {
    	
    	setFiltro(null);
    	setEmpresaConveniadaDataModel(null);
    }
	
	/* Métodos get/set */
	public EmpresaConveniadaDataModel getEmpresaConveniadaDataModel() {
		return empresaConveniadaDataModel;
	}
	public void setEmpresaConveniadaDataModel(EmpresaConveniadaDataModel empresaConveniadaDataModel) {
		this.empresaConveniadaDataModel = empresaConveniadaDataModel;
	}
	public EmpresaConveniada getEmpresaConveniada() {
		if (empresaConveniada == null) {
			empresaConveniada = new EmpresaConveniada();
		}
		return empresaConveniada;
	}
	public void setEmpresaConveniada(EmpresaConveniada empresaConveniada) {
		this.empresaConveniada = empresaConveniada;
	}
	public EmpresaConveniada getFiltro() {
		if (filtro == null) {
			filtro = new EmpresaConveniada();
		}
		return filtro;
	}
	public void setFiltro(EmpresaConveniada filtro) {
		this.filtro = filtro;
	}

	public EmpresaConveniada getEmpresaConveniadaAlterar() {
		if (empresaConveniadaAlterar == null) {
			empresaConveniadaAlterar = new EmpresaConveniada();
		}
		return empresaConveniadaAlterar;
	}

	public void setEmpresaConveniadaAlterar(EmpresaConveniada empresaConveniadaAlterar) {
		this.empresaConveniadaAlterar = empresaConveniadaAlterar;
	}
	
}
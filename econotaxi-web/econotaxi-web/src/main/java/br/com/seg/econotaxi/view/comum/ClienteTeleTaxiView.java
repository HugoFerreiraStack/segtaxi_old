package br.com.seg.econotaxi.view.comum;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.ClienteTeleTaxi;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.CidadeService;
import br.com.seg.econotaxi.service.ClienteTeleTaxiService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.ClienteTeleDataModel;

@Named
@Scope("view")
@ManagedBean(name = "clienteTeleTaxiView")
public class ClienteTeleTaxiView extends BaseView {

    // Constantes
	private static final long serialVersionUID = -6918164650365605188L;
	
	// Atributos
	@Autowired
	private ClienteTeleTaxiService clienteTeleTaxiService;
	private ClienteTeleTaxi cliente;
	private ClienteTeleTaxi clienteAlterar;
	private ClienteTeleTaxi filtro;
	private ClienteTeleDataModel clienteTeleDataModel;
	private List<Cidade> cidades;
	@Autowired
	private CidadeService cidadeService;
	private Usuario usuarioTele;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		
		setUsuarioTele(recuperarUsuarioSessao());
		setClienteTeleDataModel(new ClienteTeleDataModel(clienteTeleTaxiService, getFiltro()));
		setCidades(cidadeService.recuperarTodasCidades());
	}
	
    public void carregarCliente(ClienteTeleTaxi clienteTeleTaxi) {
    	setClienteAlterar(clienteTeleTaxiService.recuperarPorChave(clienteTeleTaxi.getId()));
    }
    
    public void salvarClienteTeleTaxi() {
    	
    	definirMenu(MenuEnum.CLIENTE_TELE_TAXI.getMenu());
    	if (clienteTeleTaxiService.verificaExistenciaCliente(cliente)) {
    		addMsgErro("Cliente já existente!");
    	} else {
    		
    		Boolean verificaErro = Boolean.FALSE;
    		if (getCliente().getNome() == null || getCliente().getNome().isEmpty()) {
    			addMsgSuccess("O Nome do Cliente é obrigatório.");
    			verificaErro = Boolean.TRUE;
    		}
    		if (getCliente().getCelular() == null || getCliente().getCelular().isEmpty()) {
    			addMsgSuccess("O Celular do Cliente é obrigatório.");
    			verificaErro = Boolean.TRUE;
    		}
    		if (getCliente().getEmpresa() == null) {
    			addMsgSuccess("A Empresa (Ligue-Táxi ou Tele-Táxi) é obrigatória.");
    			verificaErro = Boolean.TRUE;
    		}
    		if (!verificaErro) {
    			getCliente().setDataCadastro(new Date());
    			clienteTeleTaxiService.salvar(cliente);
    			addMsgSuccess("Cliente salvo com sucesso!");
    			setCliente(null);
    		}
    	}
    }
    
    public void alterarClienteTeleTaxi() {
    	
    	definirMenu(MenuEnum.CLIENTE_TELE_TAXI.getMenu());
		Boolean verificaErro = Boolean.FALSE;
		if (getClienteAlterar().getNome() == null || getClienteAlterar().getNome().isEmpty()) {
			addMsgSuccess("O Nome do Cliente é obrigatório.");
			verificaErro = Boolean.TRUE;
		}
		if (getClienteAlterar().getCelular() == null || getClienteAlterar().getCelular().isEmpty()) {
			addMsgSuccess("O Celular do Cliente é obrigatório.");
			verificaErro = Boolean.TRUE;
		}
		if (getClienteAlterar().getEmpresa() == null) {
			addMsgSuccess("A Empresa (Ligue-Táxi ou Tele-Táxi) é obrigatória.");
			verificaErro = Boolean.TRUE;
		}
		if (!verificaErro) {
			clienteTeleTaxiService.salvar(clienteAlterar);
			addMsgSuccess("Cliente salvo com sucesso!");
			setCliente(null);
		}
    }
    
    public void excluirCliente() {
    	
    	definirMenu(MenuEnum.CLIENTE_TELE_TAXI.getMenu());
    	clienteTeleTaxiService.delete(clienteAlterar);
		addMsgSuccess("Cliente excluído com sucesso!");
		setCliente(null);
    }
    
    public void pesquisarClientePorFiltro() {
    	
    	setClienteTeleDataModel(new ClienteTeleDataModel(clienteTeleTaxiService, filtro));
    }
    
    /**
     * Método responsável por limpar formulário e resultados da pesquisa.
     */
    public void limparFormulario() {
    	
    	setFiltro(null);
    	setClienteTeleDataModel(null);
    }

    /* Métodos get/set */
    public ClienteTeleTaxi getCliente() {
    	if (cliente == null) {
			cliente = new ClienteTeleTaxi();
		}
    	return cliente;
    }
    
    public void setCliente(ClienteTeleTaxi cliente) {
    	this.cliente = cliente;
    }

	public ClienteTeleTaxi getFiltro() {
		if (filtro == null) {
			filtro = new ClienteTeleTaxi();
		}
		return filtro;
	}

	public void setFiltro(ClienteTeleTaxi filtro) {
		this.filtro = filtro;
	}

	public ClienteTeleDataModel getClienteTeleDataModel() {
		return clienteTeleDataModel;
	}

	public void setClienteTeleDataModel(ClienteTeleDataModel clienteTeleDataModel) {
		this.clienteTeleDataModel = clienteTeleDataModel;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public Usuario getUsuarioTele() {
		if (usuarioTele == null) {
			usuarioTele = new Usuario();
		}
		return usuarioTele;
	}

	public void setUsuarioTele(Usuario usuarioTele) {
		this.usuarioTele = usuarioTele;
	}

	public ClienteTeleTaxi getClienteAlterar() {
		if (clienteAlterar == null) {
			clienteAlterar = new ClienteTeleTaxi();
		}
		return clienteAlterar;
	}

	public void setClienteAlterar(ClienteTeleTaxi clienteAlterar) {
		this.clienteAlterar = clienteAlterar;
	}

}
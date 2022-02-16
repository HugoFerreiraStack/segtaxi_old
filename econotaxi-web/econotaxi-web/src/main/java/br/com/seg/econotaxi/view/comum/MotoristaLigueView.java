package br.com.seg.econotaxi.view.comum;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.enums.StatusMotoristaEnum;
import br.com.seg.econotaxi.model.Auxiliar;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.MotoristaLigue;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.AuxiliarService;
import br.com.seg.econotaxi.service.CidadeService;
import br.com.seg.econotaxi.service.MotoristaLigueService;
import br.com.seg.econotaxi.service.UsuarioService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.MotoristaLigueDataModel;

@Named
@Scope("view")
@ManagedBean(name = "motoristaLigueView")
public class MotoristaLigueView extends BaseView {

    // Constantes
	private static final long serialVersionUID = 1584745532035822519L;
	
	// Atributos
	@Autowired
	private MotoristaLigueService motoristaLigueService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private AuxiliarService auxiliarService;
	@Autowired
	private CidadeService cidadeService;
	private MotoristaLigue motorista;
	private MotoristaLigue motoristaSalvar;
	private MotoristaLigue motoristaAlterar;
	private MotoristaLigue filtro;
	private MotoristaLigueDataModel motoristaDataModel;
	private String motivoDesautorizacao;
	private String motivoBloqueio;
	private List<Auxiliar> auxiliares;
	private List<Usuario> permissionarios;
	private List<Cidade> listaCidade;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		setListaCidade(cidadeService.recuperarTodasCidades());
		Integer status = (Integer) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("status");
		if (status != null) {
			getFiltro().setStatus(status);
		}
		Long idMotorista = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idMotorista");
		if (idMotorista != null) {
			getFiltro().setId(idMotorista);
			carregarMotorista(motoristaLigueService.recuperarMotoristaPorChave(idMotorista));
		}
		setMotoristaDataModel(new MotoristaLigueDataModel(motoristaLigueService, getFiltro()));
	}
	
	public void chamarModal() {
		if (getMotorista().getId() != null) {
			execJavaScript("exibirDetalhesMotorista()");
		}
	}
	
	public void atualizarLocalizacaoMotorista() {
		
		if (getMotorista().getId() != null) {
			setMotorista(motoristaLigueService.recuperarMotoristaPorChave(motorista.getId()));
		}
	}
	
    public void autorizarMotorista() {
    	
    	definirMenu(MenuEnum.MOTORISTA.getMenu());
    	motorista = motoristaLigueService.recuperarMotoristaPorChave(motorista.getId());
    	motorista.setStatus(StatusMotoristaEnum.ATIVO.getStatus());
    	motorista.setMotivoDesautorizacao(null);
    	motorista.setMotivoDesautorizacao(null);
    	motoristaLigueService.salvarMotorista(motorista);
    	addMsgSuccess("Motorista autorizado com sucesso!");
    	setMotorista(null);
    }
    
    public void desautorizarMotorista() {
    	
    	if (motivoDesautorizacao != null && !motivoDesautorizacao.isEmpty()) {
    		definirMenu(MenuEnum.MOTORISTA.getMenu());
    		motorista = motoristaLigueService.recuperarMotoristaPorChave(motorista.getId());
    		motorista.setStatus(StatusMotoristaEnum.NAO_AUTORIZADO.getStatus());
    		motorista.setMotivoDesautorizacao(motivoDesautorizacao);
    		motoristaLigueService.salvarMotorista(motorista);
    		addMsgSuccess("Motorista não autorizado com sucesso!");
    	} else {
    		addMsgErro("Para desautorizar um motorista é preciso preencher um motivo.");
    	}
    	setMotorista(null);
    	setMotivoDesautorizacao(null);
    }
    
    public void excluirMotorista() {
    	
    	definirMenu(MenuEnum.MOTORISTA.getMenu());
    	motorista = motoristaLigueService.recuperarMotoristaPorChave(motorista.getId());
    	motoristaLigueService.excluir(motorista);
    	addMsgSuccess("Motorista excluído com sucesso!");
    	setMotorista(null);
    	pesquisarMotoristaPorFiltro();
    }
    
    public void alterarMotorista() {
    	
    	definirMenu(MenuEnum.MOTORISTA.getMenu());
    	
    	Boolean verifica = Boolean.FALSE;
    	if (getMotoristaAlterar().getNome() == null || getMotoristaAlterar().getNome().isEmpty()) {
    		verifica = Boolean.TRUE;
    		addMsgErro("O campo 'Nome' é obrigatório.");
    	}
    	
    	if (getMotoristaAlterar().getCidade() == null || getMotoristaAlterar().getCidade().getId() == null) {
    		verifica = Boolean.TRUE;
    		addMsgErro("O campo 'Cidade' é obrigatório.");
    	}
    	
    	if (getMotoristaAlterar().getCpf() == null || getMotoristaAlterar().getCpf().isEmpty()) {
    		verifica = Boolean.TRUE;
    		addMsgErro("O campo 'CPF' é obrigatório.");
    	}
    	
    	if (getMotoristaAlterar().getDataNascimento() == null) {
    		verifica = Boolean.TRUE;
    		addMsgErro("O campo 'Data de Nascimento' é obrigatório.");
    	}
    	
    	if (getMotoristaAlterar().getCidadeMotorista() == null || getMotoristaAlterar().getCidadeMotorista().isEmpty()) {
    		verifica = Boolean.TRUE;
    		addMsgErro("O campo 'Cidade de Nascimento' é obrigatório.");
    	}
    	
    	if (getMotoristaAlterar().getSiglaUf() == null || getMotoristaAlterar().getSiglaUf().isEmpty()) {
    		verifica = Boolean.TRUE;
    		addMsgErro("O campo 'UF de Nascimento' é obrigatório.");
    	}
    	
    	if (getMotoristaAlterar().getStatus() == null) {
    		verifica = Boolean.TRUE;
    		addMsgErro("O campo 'Status' é obrigatório.");
    	}
    	
    	if (getMotoristaAlterar().getCelular() == null || getMotoristaAlterar().getCelular().isEmpty()) {
    		verifica = Boolean.TRUE;
    		addMsgErro("O campo 'Celular' é obrigatório.");
    	}
    	
    	if (getMotoristaAlterar().getTipoMotorista() == null) {
    		verifica = Boolean.TRUE;
    		addMsgErro("O campo 'Tipo de Motorista' é obrigatório.");
    	}
    	
    	if (getMotoristaAlterar().getCarteira() == null || getMotoristaAlterar().getCarteira().isEmpty()) {
    		verifica = Boolean.TRUE;
    		addMsgErro("O campo 'Carteira de Motorista' é obrigatório.");
    	}
    	
    	if (getMotoristaAlterar().getDataVencimentoMotorista() == null) {
    		verifica = Boolean.TRUE;
    		addMsgErro("O campo 'Vencimento Carteira de Motorista' é obrigatório.");
    	}
    	
    	if (!verifica) {
    		MotoristaLigue m = motoristaLigueService.recuperarMotoristaPorChave(motoristaAlterar.getId());
    		Boolean verificaAlteracao = Boolean.FALSE;
    		if (m.getNome() != null && !m.getNome().equals(motoristaAlterar.getNome())) {
    			verificaAlteracao = Boolean.TRUE;
    			m.setNome(motoristaAlterar.getNome());
    		}
    		if (m.getCarteira() != null && !m.getCarteira().equals(motoristaAlterar.getCarteira())) {
    			verificaAlteracao = Boolean.TRUE;
    			m.setCarteira(motoristaAlterar.getCarteira());
    		}
    		if (m.getCidade() != null && !m.getCidade().getId().equals(motoristaAlterar.getCidade().getId())) {
    			verificaAlteracao = Boolean.TRUE;
    			m.setCidade(motoristaAlterar.getCidade());
    		}
    		if (m.getCidadeMotorista() != null && !m.getCidadeMotorista().equals(motoristaAlterar.getCidadeMotorista())) {
    			verificaAlteracao = Boolean.TRUE;
    			m.setCidadeMotorista(motoristaAlterar.getCidadeMotorista());
    		}
    		if (m.getSiglaUf() != null && !m.getSiglaUf().equals(motoristaAlterar.getSiglaUf())) {
    			verificaAlteracao = Boolean.TRUE;
    			m.setSiglaUf(motoristaAlterar.getSiglaUf());
    		}
    		if (m.getDataNascimento() != null && !m.getDataNascimento().equals(motoristaAlterar.getDataNascimento())) {
    			if (motoristaAlterar.getDataNascimento() != null) {
    				verificaAlteracao = Boolean.TRUE;
    				String data = motoristaAlterar.getDataNascimento().replaceAll("/", "").replaceAll("-", "");
    				m.setDataNascimento(data);
    			}
    		}
    		if (m.getDataVencimentoMotorista() != null && !m.getDataVencimentoMotorista().equals(motoristaAlterar.getDataVencimentoMotorista())) {
    			if (motoristaAlterar.getDataVencimentoMotorista() != null) {
    				verificaAlteracao = Boolean.TRUE;
    				String data = motoristaAlterar.getDataVencimentoMotorista().replaceAll("/", "").replaceAll("-", "");
    				m.setDataVencimentoMotorista(data);
    			}
    		}
    		if (((m.getIndicadorPermissionario() != null && m.getIndicadorPermissionario().equals(1)) 
    				|| (m.getIndicadorAuxiliar() != null && m.getIndicadorAuxiliar().equals(1)))
    				&& m.getDataVencimentoTaxi() != null && !m.getDataVencimentoTaxi().equals(motoristaAlterar.getDataVencimentoTaxi())) {
    			if (motoristaAlterar.getDataVencimentoTaxi() != null) {
    				verificaAlteracao = Boolean.TRUE;
    				String data = motoristaAlterar.getDataVencimentoTaxi().replaceAll("/", "").replaceAll("-", "");
    				m.setDataVencimentoTaxi(data);
    			}
    		}
    		if (((m.getIndicadorPermissionario() != null && m.getIndicadorPermissionario().equals(1)) 
    				|| (m.getIndicadorAuxiliar() != null && m.getIndicadorAuxiliar().equals(1)))
    				&& m.getCarteiraTaxista() != null && !m.getCarteiraTaxista().equals(motoristaAlterar.getCarteiraTaxista())) {
    			verificaAlteracao = Boolean.TRUE;
    			m.setCarteiraTaxista(motoristaAlterar.getCarteiraTaxista());
    		}
    		if (((m.getIndicadorPermissionario() != null && m.getIndicadorPermissionario().equals(1)) 
    				|| (m.getIndicadorAuxiliar() != null && m.getIndicadorAuxiliar().equals(1)))
    				&& (m.getTipoTeleTaxi() != null && !m.getTipoTeleTaxi().equals(motoristaAlterar.getTipoTeleTaxi())
    				|| (m.getTipoTeleTaxi() == null && motoristaAlterar.getTipoTeleTaxi() != null))) {
    			verificaAlteracao = Boolean.TRUE;
    			m.setTipoTeleTaxi(motoristaAlterar.getTipoTeleTaxi());
    		}
    		if (((m.getIndicadorPermissionario() != null && m.getIndicadorPermissionario().equals(1)) 
    				|| (m.getIndicadorAuxiliar() != null && m.getIndicadorAuxiliar().equals(1)))
    				&& (m.getIndicadorAceitaVoucher() != null && !m.getIndicadorAceitaVoucher().equals(motoristaAlterar.getIndicadorAceitaVoucher())
    				|| (m.getIndicadorAceitaVoucher() == null && motoristaAlterar.getIndicadorAceitaVoucher() != null))) {
    			verificaAlteracao = Boolean.TRUE;
    			m.setIndicadorAceitaVoucher(motoristaAlterar.getIndicadorAceitaVoucher());
    		}
    		if (m.getIndicadorMaquinaDebito() != null && !m.getIndicadorMaquinaDebito().equals(motoristaAlterar.getIndicadorMaquinaDebito())
    				|| (m.getIndicadorMaquinaDebito() == null && motoristaAlterar.getIndicadorMaquinaDebito() != null)) {
    			verificaAlteracao = Boolean.TRUE;
    			m.setIndicadorMaquinaDebito(motoristaAlterar.getIndicadorMaquinaDebito());
    		}
    		if (m.getCpf() != null && !m.getCpf().equals(motoristaAlterar.getCpf())) {
    			verificaAlteracao = Boolean.TRUE;
    			m.setCpf(motoristaAlterar.getCpf());
    		}
    		if (m.getStatus() != null && !m.getStatus().equals(motoristaAlterar.getStatus())) {
    			verificaAlteracao = Boolean.TRUE;
    			m.setStatus(motoristaAlterar.getStatus());
    		}
    		if (m.getNome() != null && !m.getNome().equals(motoristaAlterar.getNome())) {
    			verificaAlteracao = Boolean.TRUE;
    			m.setNome(motoristaAlterar.getNome());
    		}
    		if (m.getNomeMae() != null && !m.getNomeMae().equals(motoristaAlterar.getNomeMae())) {
    			verificaAlteracao = Boolean.TRUE;
    			m.setNomeMae(motoristaAlterar.getNomeMae());
    		}
    		if (m.getBanco() != null && !m.getBanco().equals(motoristaAlterar.getBanco())
    				|| (m.getBanco() == null && motoristaAlterar.getBanco() != null
    				&& !motoristaAlterar.getBanco().isEmpty())) {
    			verificaAlteracao = Boolean.TRUE;
    			m.setBanco(motoristaAlterar.getBanco());
    		}
    		if (m.getAgencia() != null && !m.getAgencia().equals(motoristaAlterar.getAgencia())
    				|| (m.getAgencia() == null && motoristaAlterar.getAgencia() != null 
    				&& !motoristaAlterar.getAgencia().isEmpty())) {
    			verificaAlteracao = Boolean.TRUE;
    			m.setAgencia(motoristaAlterar.getAgencia());
    		}
    		if (m.getContaCorrente() != null && !m.getContaCorrente().equals(motoristaAlterar.getContaCorrente())
    				|| (m.getContaCorrente() == null && motoristaAlterar.getContaCorrente() != null
    				&& !motoristaAlterar.getContaCorrente().isEmpty())) {
    			verificaAlteracao = Boolean.TRUE;
    			m.setContaCorrente(motoristaAlterar.getContaCorrente());
    		}
    		if (m.getOperacao() != null && !m.getOperacao().equals(motoristaAlterar.getOperacao())
    				|| (m.getOperacao() == null && motoristaAlterar.getOperacao() != null
    				&& ! motoristaAlterar.getOperacao().isEmpty())) {
    			verificaAlteracao = Boolean.TRUE;
    			m.setOperacao(motoristaAlterar.getOperacao());
    		}
    		if (verificaAlteracao) {
    			motoristaLigueService.salvarMotorista(m);
    			addMsgSuccess("Motorista alterado com sucesso!");
    		} else {
    			addMsgSuccess("Nenhum dado do Motorista foi alterado de fato.");
    		}
    		setMotoristaAlterar(null);
    		pesquisarMotoristaPorFiltro();
    	}
    	
    }
    
    public void salvarMotorista() {
    	
    	definirMenu(MenuEnum.MOTORISTA_LIGUE.getMenu());
    	if (getMotoristaSalvar().getDataNascimento() != null) {
    		String data = getMotoristaSalvar().getDataNascimento().replaceAll("/", "").replaceAll("-", "");
    		getMotoristaSalvar().setDataNascimento(data);
    	}
    	
    	if (getMotoristaSalvar().getDataVencimentoMotorista() != null) {
    		String data2 = getMotoristaSalvar().getDataVencimentoMotorista().replaceAll("/", "").replaceAll("-", "");
    		getMotoristaSalvar().setDataVencimentoMotorista(data2);
    	}
    	
    	if (getMotoristaSalvar().getDataVencimentoTaxi() != null) {
    		String data3 = getMotoristaSalvar().getDataVencimentoTaxi().replaceAll("/", "").replaceAll("-", "");
    		getMotoristaSalvar().setDataVencimentoTaxi(data3);
    	}
    	
    	if (getMotoristaSalvar().getTipoMotorista() != null) {
    		if (getMotoristaSalvar().getTipoMotorista().equals(1)) {
    			getMotoristaSalvar().setIndicadorAuxiliar(null);
    			getMotoristaSalvar().setIndicadorPermissionario(1);
    		} else if (getMotoristaSalvar().getTipoMotorista().equals(2)) {
    			getMotoristaSalvar().setIndicadorAuxiliar(1);
    			getMotoristaSalvar().setIndicadorPermissionario(null);
    		}
    	}
    	getMotoristaSalvar().setDataCadastro(new Date());
    	
    	Boolean verifica = Boolean.FALSE;
    	if (getMotoristaSalvar().getNome() == null || getMotoristaSalvar().getNome().isEmpty()) {
    		verifica = Boolean.TRUE;
    		addMsgErro("O campo 'Nome' é obrigatório.");
    	}
    	
    	if (getMotoristaSalvar().getCidade() == null || getMotoristaSalvar().getCidade().getId() == null) {
    		verifica = Boolean.TRUE;
    		addMsgErro("O campo 'Cidade' é obrigatório.");
    	}
    	
    	if (getMotoristaSalvar().getCpf() == null || getMotoristaSalvar().getCpf().isEmpty()) {
    		verifica = Boolean.TRUE;
    		addMsgErro("O campo 'CPF' é obrigatório.");
    	}
    	
    	if (getMotoristaSalvar().getDataNascimento() == null) {
    		verifica = Boolean.TRUE;
    		addMsgErro("O campo 'Data de Nascimento' é obrigatório.");
    	}
    	
    	if (getMotoristaSalvar().getCidadeMotorista() == null || getMotoristaSalvar().getCidadeMotorista().isEmpty()) {
    		verifica = Boolean.TRUE;
    		addMsgErro("O campo 'Cidade de Nascimento' é obrigatório.");
    	}
    	
    	if (getMotoristaSalvar().getSiglaUf() == null || getMotoristaSalvar().getSiglaUf().isEmpty()) {
    		verifica = Boolean.TRUE;
    		addMsgErro("O campo 'UF de Nascimento' é obrigatório.");
    	}
    	
    	if (getMotoristaSalvar().getStatus() == null) {
    		verifica = Boolean.TRUE;
    		addMsgErro("O campo 'Status' é obrigatório.");
    	}
    	
    	if (getMotoristaSalvar().getCelular() == null || getMotoristaSalvar().getCelular().isEmpty()) {
    		verifica = Boolean.TRUE;
    		addMsgErro("O campo 'Celular' é obrigatório.");
    	}
    	
    	if (getMotoristaSalvar().getTipoMotorista() == null) {
    		verifica = Boolean.TRUE;
    		addMsgErro("O campo 'Tipo de Motorista' é obrigatório.");
    	}
    	
    	if (getMotoristaSalvar().getCarteira() == null || getMotoristaSalvar().getCarteira().isEmpty()) {
    		verifica = Boolean.TRUE;
    		addMsgErro("O campo 'Carteira de Motorista' é obrigatório.");
    	}
    	
    	if (getMotoristaSalvar().getDataVencimentoMotorista() == null) {
    		verifica = Boolean.TRUE;
    		addMsgErro("O campo 'Vencimento Carteira de Motorista' é obrigatório.");
    	}
    	
    	if (!verifica) {
    		motoristaLigueService.salvarMotorista(getMotoristaSalvar());
    		addMsgSuccess("Motorista Salvo com sucesso!");
    		setMotoristaSalvar(null);
    		pesquisarMotoristaPorFiltro();
    	}
    }
    
    public void bloquearMotorista() {
    	
    	if (motivoBloqueio != null && !motivoBloqueio.isEmpty()) {
    		definirMenu(MenuEnum.MOTORISTA.getMenu());
    		motorista = motoristaLigueService.recuperarMotoristaPorChave(motorista.getId());
    		motorista.setStatus(StatusMotoristaEnum.BLOQUEADO_TEMPORARIAMENTE.getStatus());
    		motorista.setMotivoBloqueio(motivoBloqueio);
    		motoristaLigueService.salvarMotorista(motorista);
    		addMsgSuccess("Motorista bloqueado com sucesso!");
    	} else {
    		addMsgErro("Para bloquear um motorista é preciso preencher um motivo.");
    	}
    	setMotorista(null);
    	setMotivoBloqueio(null);
    }
    
    public void desbloquearMotorista() {
    	
    	definirMenu(MenuEnum.MOTORISTA.getMenu());
    	motorista = motoristaLigueService.recuperarMotoristaPorChave(motorista.getId());
    	motorista.setStatus(StatusMotoristaEnum.ATIVO.getStatus());
    	motorista.setMotivoDesautorizacao(null);
    	motorista.setMotivoBloqueio(null);
    	motoristaLigueService.salvarMotorista(motorista);
    	addMsgSuccess("Motorista desbloqueado com sucesso!");
    	setMotorista(null);
    }
    
    public String carregarVeiculos(MotoristaLigue motorista) {
    	
    	definirMenu(MenuEnum.VEICULO.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idMotorista", 
				motorista.getId());
    	return "veiculo";
    }
    
    public void carregarMotorista(MotoristaLigue motorista) {
    	setMotorista(motorista);
    	//Usuario usuario = usuarioService.recuperarUsuarioPorID(motorista.getIdUsuario());
    }
    
    public void carregarMotoristaAuxiliares(MotoristaLigue motorista) {
    	setMotorista(motorista);
    	//setAuxiliares(auxiliarService.recuperarAuxiliaresUsuario(usuario));
    }
    
    public void carregarMotoristaPermissionarios(MotoristaLigue motorista) {
    	
    	setMotorista(motorista);
    	//Usuario usuario = usuarioService.recuperarUsuarioPorID(motorista.getIdUsuario());
    	//setPermissionarios(usuarioService.recuperarPermissionariosUsuario(getMotorista()));
    }
    
    public void carregarMotoristaAlterar(MotoristaLigue motorista) {
    	setMotoristaAlterar(motorista);
    	//Usuario usuario = usuarioService.recuperarUsuarioPorID(motorista.getIdUsuario());
    }
    
    public void pesquisarMotoristaPorFiltro() {
    	
    	setMotoristaDataModel(new MotoristaLigueDataModel(motoristaLigueService, filtro));
    }

    public void limparFormulario() {
    	
    	setFiltro(null);
    	setMotoristaDataModel(null);
    }

    // Métodos get/set
	public MotoristaLigue getMotorista() {
		if (motorista == null) {
			motorista = new MotoristaLigue();
		}
		return motorista;
	}
	public void setMotorista(MotoristaLigue motorista) {
		this.motorista = motorista;
	}
	public MotoristaLigue getMotoristaAlterar() {
		if (motoristaAlterar == null) {
			motoristaAlterar = new MotoristaLigue();
		}
		return motoristaAlterar;
	}

	public void setMotoristaAlterar(MotoristaLigue motoristaAlterar) {
		this.motoristaAlterar = motoristaAlterar;
	}

	public MotoristaLigue getFiltro() {
		if (filtro == null) {
			filtro = new MotoristaLigue();
		}
		return filtro;
	}
	public void setFiltro(MotoristaLigue filtro) {
		this.filtro = filtro;
	}
	public MotoristaLigue getMotoristaSalvar() {
		if (motoristaSalvar == null) {
			motoristaSalvar = new MotoristaLigue();
		}
		return motoristaSalvar;
	}
	public void setMotoristaSalvar(MotoristaLigue motoristaSalvar) {
		this.motoristaSalvar = motoristaSalvar;
	}
	public MotoristaLigueDataModel getMotoristaDataModel() {
		return motoristaDataModel;
	}
	public void setMotoristaDataModel(MotoristaLigueDataModel motoristaDataModel) {
		this.motoristaDataModel = motoristaDataModel;
	}
	public List<StatusMotoristaEnum> getListaStatusMotorista() {
		return Arrays.asList(StatusMotoristaEnum.values());
	}
	public String getMotivoDesautorizacao() {
		return motivoDesautorizacao;
	}
	public void setMotivoDesautorizacao(String motivoDesautorizacao) {
		this.motivoDesautorizacao = motivoDesautorizacao;
	}
	public String getMotivoBloqueio() {
		return motivoBloqueio;
	}
	public void setMotivoBloqueio(String motivoBloqueio) {
		this.motivoBloqueio = motivoBloqueio;
	}
	public List<Auxiliar> getAuxiliares() {
		return auxiliares;
	}
	public void setAuxiliares(List<Auxiliar> auxiliares) {
		this.auxiliares = auxiliares;
	}
	public List<Usuario> getPermissionarios() {
		return permissionarios;
	}
	public void setPermissionarios(List<Usuario> permissionarios) {
		this.permissionarios = permissionarios;
	}
	public List<Cidade> getListaCidade() {
		return listaCidade;
	}
	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}

}